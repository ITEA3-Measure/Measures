package org.measure.mmtplugin.logics;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.bson.Document;
import org.measure.mmtplugin.MMTMeasurement;

public class MMTUniqueProtocols extends MMTLogic {

    @Override
    public MMTMeasurement computeMetrics(MongoCollection collection) {
        Map <Integer, String> appsCategories = this.generateAppsMap();
        // Here there are two methods of computing the "last minute":
        // The first one: Get the last registered timestamp and rest 60.000 ms
        //retrieve the last timestamp
        /*
        double maxTstamp = -1.0;
        MongoCursor<Document> cursora = collection.aggregate(
                Arrays.asList(
                        Aggregates.group("$0", Accumulators.max("maxTstamp", "$3"))
                )
        ).iterator();
        while(cursora.hasNext()){
            Document doc = cursora.next();
            //System.out.println(doc.toString());
            // We will ignore statistics reports from MMT
            if(doc.getInteger("_id") == 99)
                continue;
            maxTstamp = doc.getDouble("maxTstamp");
        }
        */
        // The second one: Get the timestamp of the last COMPLETED minute and rest 60.000 ms
        long actualTime =  System.currentTimeMillis();
        //long actualTime =  1504098147181L;
        double maxTstamp = actualTime - (actualTime % 10000);
        // Aggregate the column #14 (DL_DATA_VOLUME) or #15(DL_PAYLOAD_VOLUME)
        MongoCursor<Document> cursorb = collection.aggregate(
                Arrays.asList(
                        Aggregates.match(
                                Filters.and(
                                        Filters.eq("0", 100),
                                        Filters.lte("3", maxTstamp),
                                        Filters.gt("3", maxTstamp - 60000) // -60000 since timestamps are in milliseconds
                                )
                        ),
                        Aggregates.group("$5", Accumulators.sum("protocolSum", 1))
                )
        ).iterator();

        // Create the MMTMeasurement object to store the retrieved data
        MMTMeasurement measurement = new MMTMeasurement();
        Map<String, Integer> protocols = new HashMap<>();

        while(cursorb.hasNext()){
            // TODO - Insert every retrieved row in the MMTMeasurement object
            Document doc = cursorb.next();
            protocols.put(appsCategories.get(doc.getInteger("_id")), doc.getInteger("protocolSum"));
        }

        measurement.addValue("totalProtocolsmeasure", protocols.keySet().size());
        measurement.addValue("protocols", protocols.keySet().toString());
        
        measurement.setValue(protocols.keySet().size());

        return measurement;
    }
    
    private Map<Integer, String> generateAppsMap(){
        Map<Integer, String> map;
        // These are the "Application types" defined for MMT.
        // They will be parsed into a HashMa using Bson parser and java lambdas
        Document protocolsMap = Document.parse("{\n" +
        "\"-1\": \"_other\",\n" +
        "\"0\": \"All\", \"2\": \"163\", \"3\": \"360\", \"4\": \"ZONE_TELECHARGEMENT\", \"5\": \"360BUY\", \"6\": \"56\", \"7\": \"VLAN\", \"8\": \"888\", \"9\": \"ABOUT\", \"10\": \"ADCASH\", \"11\": \"ADDTHIS\", \"12\": \"ADF\", \"13\": \"ADOBE\", \"14\": \"AFP\", \"15\": \"AH\", \"16\": \"AIM\", \"17\": \"AIMINI\", \"18\": \"ALIBABA\", \"19\": \"ALIPAY\", \"20\": \"ALLEGRO\", \"21\": \"AMAZON\", \"22\": \"AMEBLO\", \"23\": \"ANCESTRY\", \"24\": \"ANGRYBIRDS\", \"25\": \"ANSWERS\", \"26\": \"AOL\", \"27\": \"APPLE\", \"28\": \"APPLEJUICE\", \"29\": \"ARMAGETRON\", \"30\": \"ARP\", \"31\": \"ASK\", \"32\": \"AVG\", \"33\": \"AVI\", \"34\": \"AWEBER\", \"35\": \"AWS\", \"36\": \"BABYLON\", \"37\": \"BADOO\", \"38\": \"BAIDU\", \"39\": \"BANKOFAMERICA\", \"40\": \"BARNESANDNOBLE\", \"41\": \"BATMAN\", \"42\": \"BATTLEFIELD\", \"43\": \"BATTLENET\", \"44\": \"BBB\", \"45\": \"BBC_ONLINE\", \"46\": \"BESTBUY\", \"47\": \"BETFAIR\", \"48\": \"BGP\", \"49\": \"BIBLEGATEWAY\", \"50\": \"BILD\", \"51\": \"BING\", \"52\": \"BITTORRENT\", \"53\": \"BLEACHERREPORT\", \"54\": \"BLOGFA\", \"55\": \"BLOGGER\", \"56\": \"BLOGSPOT\", \"57\": \"BODYBUILDING\", \"58\": \"BOOKING\", \"59\": \"CBSSPORTS\", \"60\": \"CENT\", \"61\": \"CHANGE\", \"62\": \"CHASE\", \"63\": \"CHESS\", \"64\": \"CHINAZ\", \"65\": \"CITRIX\", \"66\": \"CITRIXONLINE\", \"67\": \"CLICKSOR\", \"68\": \"CNN\", \"69\": \"CNZZ\", \"70\": \"COMCAST\", \"71\": \"CONDUIT\", \"72\": \"COPYSCAPE\", \"73\": \"CORREIOS\", \"74\": \"CRAIGSLIST\", \"75\": \"CROSSFIRE\", \"76\": \"DAILYMAIL\", \"77\": \"DAILYMOTION\", \"78\": \"DCERPC\", \"79\": \"DIRECT_DOWNLOAD_LINK\", \"80\": \"DEVIANTART\", \"81\": \"DHCP\", \"82\": \"DHCPV6\", \"83\": \"DIGG\", \"84\": \"DIRECTCONNECT\", \"85\": \"DNS\", \"86\": \"DOFUS\", \"87\": \"DONANIMHABER\", \"88\": \"DOUBAN\", \"89\": \"DOUBLECLICK\", \"90\": \"DROPBOX\", \"91\": \"EBAY\", \"92\": \"EDONKEY\", \"93\": \"EGP\", \"94\": \"EHOW\", \"95\": \"EKSISOZLUK\", \"96\": \"ELECTRONICSARTS\", \"97\": \"ESP\", \"98\": \"ESPN\", \"99\": \"ETH\", \"100\": \"ETSY\", \"101\": \"EUROPA\", \"102\": \"EUROSPORT\", \"103\": \"FACEBOOK\", \"104\": \"FACETIME\", \"105\": \"FASTTRACK\", \"106\": \"FC2\", \"107\": \"FEIDIAN\", \"108\": \"FIESTA\", \"109\": \"FILETOPIA\", \"110\": \"FIVERR\", \"111\": \"FLASH\", \"112\": \"FLICKR\", \"113\": \"FLORENSIA\", \"114\": \"FOURSQUARE\", \"115\": \"FOX\", \"116\": \"FREE\", \"117\": \"FTP\", \"118\": \"GADUGADU\", \"119\": \"GAMEFAQS\", \"120\": \"GAMESPOT\", \"121\": \"GAP\", \"122\": \"GARANTI\", \"123\": \"GAZETEVATAN\", \"124\": \"GIGAPETA\", \"125\": \"GITHUB\", \"126\": \"GITTIGIDIYOR\", \"127\": \"GLOBO\", \"128\": \"GMAIL\", \"129\": \"GNUTELLA\", \"130\": \"GOOGLE_MAPS\", \"131\": \"GO\", \"132\": \"GODADDY\", \"133\": \"GOO\", \"134\": \"GOOGLE\", \"135\": \"GOOGLE_USER_CONTENT\", \n" +
        "\"136\": \"JEUXVIDEO\", \"137\": \"GRE\", \"138\": \"GROOVESHARK\", \"139\": \"GROUPON\", \"140\": \"GTALK\", \"141\": \"GTP\", \"142\": \"20MINUTES\", \"143\": \"GUARDIAN\", \"144\": \"GUILDWARS\", \"145\": \"HABERTURK\", \"146\": \"HAO123\", \"147\": \"HEPSIBURADA\", \"148\": \"HI5\", \"149\": \"HALFLIFE2\", \"150\": \"HOMEDEPOT\", \"151\": \"HOOTSUITE\", \"152\": \"HOTMAIL\", \"153\": \"HTTP\", \"154\": \"REUTERS\", \"155\": \"HTTP_PROXY\", \"156\": \"HTTP_APPLICATION_ACTIVESYNC\", \"157\": \"HUFFINGTON_POST\", \"158\": \"HURRIYET\", \"159\": \"I23V5\", \"160\": \"IAX\", \"161\": \"ICECAST\", \"162\": \"APPLE_ICLOUD\", \"163\": \"ICMP\", \"164\": \"ICMPV6\", \"165\": \"IFENG\", \"166\": \"IGMP\", \"167\": \"IGN\", \"168\": \"IKEA\", \"169\": \"IMAP\", \"170\": \"IMAPS\", \"171\": \"INTERNET_MOVIE_DATABASE\", \"172\": \"IMESH\", \"173\": \"ALIEXPRESS\", \"174\": \"IMGUR\", \n" +
        "\"175\": \"LEBONCOIN\", \"176\": \"INDIATIMES\", \"177\": \"INSTAGRAM\", \"178\": \"IP\", \"179\": \"IP_IN_IP\", \"180\": \"IPP\", \"181\": \"IPSEC\", \"182\": \"IP6\", \"183\": \"IRC\", \"184\": \"IRS\", \"185\": \"APPLE_ITUNES\", \"186\": \"UNENCRYPED_JABBER\", \"187\": \"JAPANPOST\", \"188\": \"KAKAO\", \"189\": \"KAT\", \n" +
        "\"190\": \"ORANGEFR\", \"191\": \"KERBEROS\", \"192\": \"KING\", \"193\": \"KOHLS\", \"194\": \"KONGREGATE\", \"195\": \"KONTIKI\", \"196\": \"L2TP\", \"197\": \"LASTFM\", \"198\": \"LDAP\", \"199\": \"LEAGUEOFLEGENDS\", \"200\": \"LEGACY\", \"201\": \"LETV\", \"202\": \"LINKEDIN\", \"203\": \"LIVE\", \"204\": \"LIVEDOOR\", \"205\": \"LIVEMAIL\", \"206\": \"LIVEINTERNET\", \"207\": \"LIVEJASMIN\", \"208\": \"LIVEJOURNAL\", \"209\": \"LIVESCORE\", \"210\": \"LIVINGSOCIAL\", \"211\": \"LOWES\", \"212\": \"MACYS\", \"213\": \"MAIL_RU\", \"214\": \"FNAC\", \"215\": \"MANOLITO\", \"216\": \"MAPLESTORY\", \"217\": \"MATCH\", \"218\": \"MDNS\", \"219\": \"MEDIAFIRE\", \"220\": \"MEEBO\", \"221\": \"MGCP\", \"222\": \"MICROSOFT\", \"223\": \"MILLIYET\", \"224\": \"MINECRAFT\", \"225\": \"MINICLIP\", \"226\": \"MLBASEBALL\", \"227\": \"MMO_CHAMPION\", \"228\": \"MMS\", \"229\": \"MOVE\", \"230\": \"MOZILLA\", \"231\": \"MPEG\", \"232\": \"MSN\", \"233\": \"MSSQL\", \"234\": \"MULTIPLY\", \"235\": \"MYNET\", \"236\": \"MYSPACE\", \"237\": \"MYSQL\", \"238\": \"MYWEBSEARCH\", \"239\": \"NBA\", \"240\": \"NEOBUX\", \"241\": \"NETBIOS\", \"242\": \"NETFLIX\", \"243\": \"NETFLOW\", \"244\": \"NEWEGG\", \"245\": \"NEWSMAX\", \"246\": \"NFL\", \"247\": \"NFS\", \"248\": \"NICOVIDEO\", \"249\": \"NIH\", \"250\": \"NORDSTROM\", \"251\": \"NTP\", \"252\": \"NYTIMES\", \"253\": \"ODNOKLASSNIKI\", \"254\": \"OFF\", \"255\": \"OGG\", \"256\": \"ONET\", \"257\": \"OPENFT\", \"258\": \"ORANGEDONKEY\", \"259\": \"OSCAR\", \"260\": \"OSPF\", \"261\": \"OUTBRAIN\", \"262\": \"OVERSTOCK\", \"263\": \"PANDO\", \"264\": \"PAYPAL\", \"265\": \"PCANYWHERE\", \"266\": \"PCH\", \"267\": \"PCONLINE\", \"268\": \"PHOTOBUCKET\", \"269\": \"PINTEREST\", \"270\": \"PLAYSTATION\", \"271\": \"POGO\", \"272\": \"POP\", \"273\": \"POPS\", \"274\": \"POPO\", \"275\": \"PORNHUB\", \"276\": \"POSTGRES\", \"277\": \"PPLIVE\", \"278\": \"PPP\", \"279\": \"PPPOE\", \"280\": \"PPSTREAM\", \"281\": \"PPTP\", \"282\": \"PREMIERLEAGUE\", \"283\": \"QQ\", \"284\": \"QQLIVE\", \"285\": \"QUAKE\", \"286\": \"FORBES\", \"287\": \"R10\", \"288\": \"RADIUS\", \"289\": \"RAKUTEN\", \"290\": \"RDP\", \"291\": \"REALMEDIA\", \"292\": \"REDDIT\", \"293\": \"REDTUBE\", \"294\": \"REFERENCE\", \"295\": \"RENREN\", \"296\": \"ROBLOX\", \"297\": \"ROVIO\", \"298\": \"RTP\", \"299\": \"RTSP\", \"300\": \"SABAHTR\", \"301\": \"SAHIBINDEN\", \"302\": \"SALESFORCE\", \"303\": \"SALON\", \"304\": \"SCTP\", \"305\": \"SEARCHNU\", \"306\": \"SEARCH_RESULTS\", \"307\": \"SEARS\", \"308\": \"SECONDLIFE\", \"309\": \"SECURESERVER\", \"310\": \"SFLOW\", \"311\": \"SHAZAM\", \"312\": \"SHOUTCAST\", \"313\": \"SINA\", \"314\": \"SIP\", \"315\": \"SITEADVISOR\", \"316\": \"SKY\", \"317\": \"SKYPE\", \"318\": \"SKYROCK\", \"319\": \"SKYSPORTS\", \"320\": \"SLATE\", \"321\": \"SLIDESHARE\", \"322\": \"SMB\", \"323\": \"SMTP\", \"324\": \"SMTPS\", \"325\": \"SNMP\", \"326\": \"SOCRATES\", \"327\": \"SOFTONIC\", \"328\": \"SOGOU\", \"329\": \"SOHU\", \"330\": \"SOPCAST\", \"331\": \"SOSO\", \"332\": \"SOULSEEK\", \"333\": \"SOUNDCLOUD\", \"334\": \"SOURGEFORGE\", \"335\": \"SPIEGEL\", \"336\": \"SPORX\", \"337\": \"SPOTIFY\", \"338\": \"SQUIDOO\", \"339\": \"SSDP\", \"340\": \"SSH\", \"341\": \"SSL\", \"342\": \"STACK_OVERFLOW\", \"343\": \"STATCOUNTER\", \"344\": \"STEALTHNET\", \"345\": \"STEAM\", \"346\": \"STUMBLEUPON\", \"347\": \"STUN\", \"348\": \"SULEKHA\", \"349\": \"SYSLOG\", \"350\": \"TAGGED\", \"351\": \"TAOBAO\", \"352\": \"TARGET\", \"353\": \"TCO\", \"354\": \"TCP\", \"355\": \"TDS\", \"356\": \"TEAMVIEWER\", \"357\": \"TELNET\", \"358\": \"TFTP\", \"359\": \"THEMEFOREST\", \"360\": \"THE_PIRATE_BAY\", \"361\": \"THUNDER\", \"362\": \"TIANYA\", \n" +
        "\"363\": \"CDISCOUNT\", \"364\": \"TMALL\", \"365\": \"TORRENTZ\", \"366\": \"TRUPHONE\", \"367\": \"TUBE8\", \"368\": \"TUDOU\", \"369\": \"TUENTI\", \"370\": \"TUMBLR\", \"371\": \"TVANTS\", \"372\": \"TVUPLAYER\", \"373\": \"TWITTER\", \"374\": \"UBI\", \"375\": \"UCOZ\", \"376\": \"UDP\", \"377\": \"UDPLITE\", \"378\": \"UOL\", \"379\": \"USDEPARTMENTOFSTATE\", \"380\": \"USENET\", \"381\": \"USTREAM\", \"382\": \"HTTP_APPLICATION_VEOHTV\", \"383\": \"VIADEO\", \"384\": \"VIBER\", \"385\": \"VIMEO\", \"386\": \"VK\", \"387\": \"VKONTAKTE\", \"388\": \"VNC\", \"389\": \"WALMART\", \"390\": \"WARRIORFORUM\", \"391\": \"WAYN\", \"392\": \"WEATHER\", \"393\": \"WEBEX\", \"394\": \"WEEKLYSTANDARD\", \"395\": \"WEIBO\", \"396\": \"WELLSFARGO\", \"397\": \"WHATSAPP\", \"398\": \"WIGETMEDIA\", \"399\": \"WIKIA\", \"400\": \"WIKIMEDIA\", \"401\": \"WIKIPEDIA\", \"402\": \"WILLIAMHILL\", \"403\": \"WINDOWSLIVE\", \"404\": \"WINDOWSMEDIA\", \"405\": \"WINMX\", \"406\": \"WINUPDATE\", \"407\": \"WORLD_OF_KUNG_FU\", \"408\": \"WORDPRESS_ORG\", \"409\": \"WARCRAFT3\", \"410\": \"WORLDOFWARCRAFT\", \"411\": \"WOWHEAD\", \"412\": \"WWE\", \"413\": \"XBOX\", \"414\": \"XDMCP\", \"415\": \"XHAMSTER\", \"416\": \"XING\", \"417\": \"XINHUANET\", \"418\": \"XNXX\", \"419\": \"XVIDEOS\", \"420\": \"YAHOO\", \n" +
        "\"421\": \"ALLOCINE\", \"422\": \"YAHOOMAIL\", \"423\": \"YANDEX\", \"424\": \"YELP\", \"425\": \"YOUKU\", \"426\": \"YOUPORN\", \"427\": \"YOUTUBE\", \"428\": \"ZAPPOS\", \"429\": \"ZATTOO\", \"430\": \"ZEDO\", \"431\": \"ZOL\", \"432\": \"ZYNGA\", \"433\": \"3PC\", \"434\": \"ANY_0HOP\", \"435\": \"ANY_DFS\", \"436\": \"ANY_HIP\", \"437\": \"ANY_LOCAL\", \"438\": \"ANY_PES\", \"439\": \"ARGUS\", \"440\": \"ARIS\", \"441\": \"AX_25\", \"442\": \"BBN_RCC_MON\", \"443\": \"BNA\", \"444\": \"BR_SAT_MON\", \"445\": \"CBT\", \"446\": \"CFTP\", \"447\": \"CHAOS\", \"448\": \"COMPAQ_PEER\", \"449\": \"CPHB\", \"450\": \"CPNX\", \"451\": \"CRTP\", \"452\": \"CRUDP\", \"453\": \"DCCP\", \"454\": \"DCN_MEAS\", \"455\": \"DDP\", \"456\": \"DDX\", \"457\": \"DGP\", \"458\": \"EIGRP\", \"459\": \"EMCON\", \"460\": \"ENCAP\", \"461\": \"ETHERIP\", \"462\": \"FC\", \"463\": \"FIRE\", \"464\": \"GGP\", \"465\": \"GMTP\", \"466\": \"HIP\", \"467\": \"HMP\", \"468\": \"I_NLSP\", \"469\": \"IATP\", \"470\": \"IDPR\", \"471\": \"IDPR_CMTP\", \"472\": \"IDRP\", \"473\": \"IFMP\", \"474\": \"IGP\", \"475\": \"IL\", \"476\": \"IPCOMP\", \"477\": \"IPCV\", \"478\": \"IPLT\", \"479\": \"IPPC\", \"480\": \"IPTM\", \"481\": \"IPX_IN_IP\", \"482\": \"IRTP\", \"483\": \"IS_IS\", \"484\": \"ISO_IP\", \"485\": \"ISO_TP4\", \"486\": \"KRYPTOLAN\", \"487\": \"LARP\", \"488\": \"LEAF_1\", \"489\": \"LEAF_2\", \"490\": \"MERIT_INP\", \"491\": \"MFE_NSP\", \"492\": \"MHRP\", \"493\": \"MICP\", \"494\": \"MOBILE\", \"495\": \"MOBILITY_HEADER\", \"496\": \"MPLS_IN_IP\", \"497\": \"MTP\", \"498\": \"MUX\", \"499\": \"NARP\", \"500\": \"NETBLT\", \"501\": \"NSFNET_IGP\", \"502\": \"NVP_II\", \"503\": \"PGM\", \"504\": \"PIM\", \"505\": \"PIPE\", \"506\": \"PNNI\", \"507\": \"PRM\", \"508\": \"PTP\", \"509\": \"PUP\", \"510\": \"PVP\", \"511\": \"QNX\", \"512\": \"RSVP\", \"513\": \"RSVP_E2E_IGNORE\", \"514\": \"RVD\", \"515\": \"SAT_EXPAK\", \"516\": \"SAT_MON\", \"517\": \"SCC_SP\", \"518\": \"SCPS\", \"519\": \"SDRP\", \"520\": \"SECURE_VMTP\", \"521\": \"SHIM6\", \"522\": \"SKIP\", \"523\": \"SM\", \"524\": \"SMP\", \"525\": \"SNP\", \"526\": \"SPRITE_RPC\", \"527\": \"SPS\", \"528\": \"SRP\", \"529\": \"SSCOPMCE\", \"530\": \"ST\", \"531\": \"STP\", \"532\": \"SUN_ND\", \"533\": \"SWIPE\", \"534\": \"TCF\", \"535\": \"TLSP\", \"536\": \"TP_PP\", \"537\": \"TRUNK_1\", \"538\": \"TRUNK_2\", \"539\": \"UTI\", \"540\": \"VINES\", \"541\": \"VISA\", \"542\": \"VMTP\", \"543\": \"VRRP\", \"544\": \"WB_EXPAK\", \"545\": \"WB_MON\", \"546\": \"WSN\", \"547\": \"XNET\", \"548\": \"XNS_IDP\", \"549\": \"XTP\", \"550\": \"BUZZNET\", \"551\": \"COMEDY\", \"552\": \"RAMBLER\", \"553\": \"SMUGMUG\", \"554\": \"ARCHIEVE\", \"555\": \"CITYNEWS\", \"556\": \"SCIENCESTAGE\", \"557\": \"ONEWORLD\", \"558\": \"DISQUS\", \"559\": \"BLOGCU\", \"560\": \"EKOLEY\", \"561\": \"500PX\", \"562\": \"FOTKI\", \"563\": \"FOTOLOG\", \"564\": \"JALBUM\", \n" +
        "\"565\": \"LEMONDE\", \"566\": \"PANORAMIO\", \"567\": \"SNAPFISH\", \"568\": \"WEBSHOTS\", \"569\": \"MEGA\", \"570\": \"VIDOOSH\", \"571\": \"AFREECA\", \"572\": \"WILDSCREEN\", \"573\": \"BLOGTV\", \"574\": \"HULU\", \"575\": \"MEVIO\", \"576\": \"LIVESTREAM\", \"577\": \"LIVELEAK\", \"578\": \"DEEZER\", \"579\": \"BLIPTV\", \"580\": \"BREAK\", \"581\": \"CITYTV\", \"582\": \"COMEDYCENTRAL\", \"583\": \"ENGAGEMEDIA\", \"584\": \"SCREENJUNKIES\", \"585\": \"RUTUBE\", \"586\": \"SEVENLOAD\", \"587\": \"MUBI\", \"588\": \"IZLESENE\", \"589\": \"VIDEO_HOSTING\", \"590\": \"BOX\", \"591\": \"SKYDRIVE\", \"592\": \"7DIGITAL\", \"593\": \"CLOUDFRONT\", \"594\": \"TANGO\", \"595\": \"WECHAT\", \"596\": \"LINE\", \"597\": \"BLOOMBERG\", \n" +
        "\"598\": \"LEFIGARO\", \"599\": \"AKAMAI\", \"600\": \"YAHOOMSG\", \"601\": \"BITGRAVITY\", \"602\": \"CACHEFLY\", \"603\": \"CDN77\", \"604\": \"CDNETWORKS\", \"605\": \"CHINACACHE\", \n" +
        "\"606\": \"FRANCETVINFO\", \"607\": \"EDGECAST\", \"608\": \"FASTLY\", \"609\": \"HIGHWINDS\", \"610\": \"INTERNAP\", \"611\": \"LEVEL3\", \"612\": \"LIMELIGHT\", \"613\": \"MAXCDN\", \"614\": \"NETDNA\", \"615\": \"VOXEL\", \"616\": \"RACKSPACE\", \"617\": \"GAMEFORGE\", \"618\": \"METIN2\", \"619\": \"OGAME\", \"620\": \"BATTLEKNIGHT\", \"621\": \"4STORY\", \"622\": \"FBMSG\", \n" +
        "\"623\": \"TWITCH\",\n" +
        "\"625\": \"NDN\",\n" +
        "\"626\": \"NDN_HTTP\",\n" +
        "\"627\": \"QUIC\",\n" +
        "\"628\": \"ORACLE\",\n" +
        "\"629\": \"REDIS\",\n" +
        "\"630\": \"VMWARE\",\n" +
        "\"631\": \"MP2T\",\n" +
        "\"632\": \"M3U8\"\n" +
        "}");
        
        map = protocolsMap.keySet().stream().collect(Collectors.toMap(
                key -> Integer.parseInt(key),
                key -> protocolsMap.getString(key)
        ));
        return map;
    }
}
