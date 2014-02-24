package com.ews.enterprise.integration;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.main.Main;
import org.apache.camel.processor.interceptor.Tracer;

import com.ews.web.guice.module.MongoModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Startup {
	private Startup() { }
	public static void main(final String[] args) throws Throwable {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("environment.properties"));
            props.load(new FileInputStream("application.properties"));
            String camelDcOnString = props.getProperty("org.airedale.dc.camel");
            
            
            Injector injector = Guice.createInjector(new MongoModule( props));
            Main camelMain = new Main();
            camelMain.enableHangupSupport();
            camelMain
                .bind("activemq", ActiveMQComponent.activeMQComponent(props.getProperty("org.activeMQ.brokerURL")));
            // if camel data collection, make mods
            if ("ON".equals(camelDcOnString)) {
                camelMain = addTracingToMain(camelMain);
            }
            
            camelMain.run();
        } catch (Exception e) {
            System.out.println("The DB configuration file is missing.\n" + e.getMessage());
            e.printStackTrace();
        }
    }
    //CHECKSTYLE:ON

    private static Main addTracingToMain(final Main camelMain) {
        // create a new tracer
        Tracer tracer = new Tracer();
        // create an endpoint for tracer processing
        tracer.setDestinationUri("seda:traced");
        // don't allow default tracing logging behavior
        tracer.setLogLevel(LoggingLevel.INFO);
        // trace out exchanges
        tracer.setTraceOutExchanges(true);
        // add the tracer to contexts
        for (CamelContext cc : camelMain.getCamelContexts()) {
            cc.addInterceptStrategy(tracer);
            cc.setTracing(true);
        }

        return camelMain;
    }
}
