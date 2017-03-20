package org.measure.svn.commit;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;
import org.measure.smm.measure.defaultimpl.measurements.IntegerMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc2.ISvnObjectReceiver;
import org.tmatesoft.svn.core.wc2.SvnLog;
import org.tmatesoft.svn.core.wc2.SvnOperationFactory;
import org.tmatesoft.svn.core.wc2.SvnRevisionRange;
import org.tmatesoft.svn.core.wc2.SvnTarget;

public class SVNCommitImpl extends DirectMeasure {
	
	
	public Date from = null;

	
	
	@Override
	public List<IMeasurement> getMeasurement() throws Exception{
		final SvnOperationFactory svnOperationFactory = new SvnOperationFactory();
		try {
			
			List<IMeasurement> result = new ArrayList<>();

			String uri = getProperty("URI");
			String login = getProperty("LOGIN");
			String password = getProperty("PASSWORD");
			Long lastUpdate = Long.valueOf(getProperty("LastUpdate"));

			final SVNURL svnurl = SVNURL.parseURIEncoded(uri.toString());
			svnOperationFactory.setAuthenticationManager(new BasicAuthenticationManager(login, password));

			final SvnLog log = svnOperationFactory.createLog();
						
			if(lastUpdate != 0){
				from = new Date(lastUpdate);
				log.addRange(SvnRevisionRange.create(SVNRevision.create(from), SVNRevision.create(new Date())));
			}else{
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				c.add(Calendar.YEAR, -1);
				from = c.getTime();
				log.addRange(SvnRevisionRange.create(SVNRevision.create(from), SVNRevision.create(new Date())));

			}
			log.setDiscoverChangedPaths(true);
			log.setSingleTarget(SvnTarget.fromURL(svnurl));

			log.setReceiver(new ISvnObjectReceiver<SVNLogEntry>() {
				@Override
				public void receive(SvnTarget target, SVNLogEntry logEntry) throws SVNException {				
					if(logEntry.getDate().getTime() >  SVNCommitImpl.this.from.getTime()){
						SVNMeasurement measurement = new SVNMeasurement( logEntry.getAuthor(),logEntry.getMessage(),logEntry.getDate());
						result.add(measurement);
					}
				}
			});
			log.run();

			getProperties().put("LastUpdate",new Long(new Date().getTime()).toString());
			return result;
		} catch (SVNException e) {
			throw e;
		} finally {
			svnOperationFactory.dispose();
		}

	}
}
