package org.measure.svn.svnrepositorystatus;

import java.util.ArrayList;
import java.util.List;

import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
import org.tmatesoft.svn.core.wc2.ISvnObjectReceiver;
import org.tmatesoft.svn.core.wc2.SvnList;
import org.tmatesoft.svn.core.wc2.SvnOperationFactory;
import org.tmatesoft.svn.core.wc2.SvnTarget;

public class SVNRepositoryImpl extends DirectMeasure {

	private Long dirs = 0L;
	private Long files = 0L;
	private Long repositorySize = 0L;
    @Override
    public List<IMeasurement> getMeasurement() throws Exception {
        final SvnOperationFactory svnOperationFactory = new SvnOperationFactory();
        try {
        
            List<IMeasurement> result = new ArrayList<>();
        
            String uri = getProperty("url");
            String login = getProperty("login");
            String password = getProperty("password");
            
            SVNRepository repository = SVNRepositoryFactory.create(SVNURL.parseURIDecoded(uri));
            ISVNAuthenticationManager authManager =  SVNWCUtil.createDefaultAuthenticationManager(login, password);
            repository.setAuthenticationManager(authManager);
            
            long lastRevision = repository.getLatestRevision();
            int numberofLocks = repository.getLocks("/").length;
            
            svnOperationFactory.setAuthenticationManager(new BasicAuthenticationManager(login, password));
            SvnList list = svnOperationFactory.createList();
            list.setSingleTarget(SvnTarget.fromURL(SVNURL.parseURIDecoded(uri), SVNRevision.HEAD));
            list.setDepth(SVNDepth.INFINITY);
            list.setReceiver(new ISvnObjectReceiver<SVNDirEntry>() {
                public void receive(SvnTarget target, SVNDirEntry dirEntry) throws SVNException {
                	dirs++;
                	if(dirEntry.getKind().equals(SVNNodeKind.DIR)){
                		dirs++;
                	}
                	if(dirEntry.getKind().equals(SVNNodeKind.FILE)){
                		files++;
                		repositorySize = repositorySize + dirEntry.getSize();
                	}
                	
                }
            });
            list.run();
            
            
            SVNRepositoryMeasurement measurement = new SVNRepositoryMeasurement();
            measurement.getValues().put("LastRevision", lastRevision);
            measurement.getValues().put("NumberOfLockedFiles", numberofLocks);
            measurement.getValues().put("NumberOfFiles", files);
            measurement.getValues().put("NumberOfDirectory", dirs);
            measurement.getValues().put("RepositorySize", repositorySize / 1000000);
            
            result.add(measurement);
        
            return result;
        } catch (SVNException e) {
            throw e;
        } finally {
            svnOperationFactory.dispose();
        }
    }

}