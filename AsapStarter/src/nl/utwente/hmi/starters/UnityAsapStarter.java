package nl.utwente.hmi.starters;

import java.io.IOException;
import java.util.ArrayList;

import asap.bml.ext.bmlt.BMLTInfo;
import asap.environment.AsapEnvironment;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import hmi.audioenvironment.AudioEnvironment;
import hmi.environmentbase.ClockDrivenCopyEnvironment;
import hmi.environmentbase.Environment;
import hmi.mixedanimationenvironment.MixedAnimationEnvironment;
import hmi.physicsenvironment.OdePhysicsEnvironment;
import hmi.worldobjectenvironment.WorldObjectEnvironment;
import saiba.bml.BMLInfo;
import saiba.bml.core.FaceLexemeBehaviour;
import saiba.bml.core.HeadBehaviour;
import saiba.bml.core.PostureShiftBehaviour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnityAsapStarter {

	final static Logger logger = LoggerFactory.getLogger(UnityAsapStarter.class.getName());
	private String spec;
	
    public static void main(String[] args) throws IOException {
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
		JoranConfigurator configurator = new JoranConfigurator();
	    configurator.setContext(context);
	    ClassLoader loader = UnityAsapStarter.class.getClassLoader();
	    context.reset(); 
	    try {
			configurator.doConfigure(loader.getResource("UnityAsapLogs.xml"));
		} catch (JoranException e) {
			e.printStackTrace();
		}
	    StatusPrinter.printInCaseOfErrorsOrWarnings(context);
	    
		String help = "Expecting commandline arguments in the form of \"-<argname> <arg>\".\nAccepting the following argnames: agentspec";
        String spec = "Unity/agentspecs/blueguy.xml";
        if(args.length % 2 != 0) {
        	logger.info(help);
        	System.exit(0);
        }
    	
        for(int i = 0; i < args.length; i = i + 2){
        	if(args[i].equals("-agentspec")) {
        		spec = args[i+1];
        	} else {
        		logger.warn("Unknown commandline argument: \""+args[i]+" "+args[i+1]+"\".\n"+help);
            	System.exit(0);
        	}
        }
        
    	UnityAsapStarter uas = new UnityAsapStarter(spec);
    	uas.init();
    }

    public UnityAsapStarter(String spec) {
    	this.spec = spec;
    }

    public void init() throws IOException {
        MixedAnimationEnvironment mae = new MixedAnimationEnvironment();
        final OdePhysicsEnvironment ope = new OdePhysicsEnvironment();
        WorldObjectEnvironment we = new WorldObjectEnvironment();
        AudioEnvironment aue = new AudioEnvironment("LJWGL_JOAL");

        BMLTInfo.init();
        BMLInfo.addCustomFloatAttribute(FaceLexemeBehaviour.class, "http://asap-project.org/convanim", "repetition");
        BMLInfo.addCustomStringAttribute(HeadBehaviour.class, "http://asap-project.org/convanim", "spindirection");
        BMLInfo.addCustomFloatAttribute(PostureShiftBehaviour.class, "http://asap-project.org/convanim", "amount");

        ArrayList<Environment> environments = new ArrayList<Environment>();
        final AsapEnvironment ee = new AsapEnvironment();
        
        ClockDrivenCopyEnvironment ce = new ClockDrivenCopyEnvironment(1000 / 20);

        ce.init();
        ope.init();
        mae.init(ope, 0.002f);
        we.init();
        aue.init();
        environments.add(ee);
        environments.add(ope);
        environments.add(mae);
        environments.add(we);

        environments.add(ce);
        environments.add(aue);

        ee.init(environments, ope.getPhysicsClock());
        ope.addPrePhysicsCopyListener(ee);

        ee.loadVirtualHuman("", spec, "AsapRealizer demo");

        ope.startPhysicsClock();
    }
}

