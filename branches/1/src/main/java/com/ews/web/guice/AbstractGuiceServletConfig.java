package com.ews.web.guice;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import com.google.inject.servlet.GuiceServletContextListener;

public abstract class AbstractGuiceServletConfig extends GuiceServletContextListener{
	private ServletContext servletContext;
    private CamelContext camelContext;

    @Override
    public final void contextInitialized(final ServletContextEvent servletContextEvent) {
        this.servletContext = servletContextEvent.getServletContext();

        // Initialize camel
        camelContext = new DefaultCamelContext();
        try {
            InitialContext initContext = new InitialContext();
            String brokerUrl = (String) initContext.lookup("java:comp/env/activeMqBrokerUrl");
            camelContext.addComponent(
                "activemq", ActiveMQComponent.activeMQComponent(brokerUrl));
            camelContext.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("direct:start").inOnly("activemq:infile");
                }
            });
            
            camelContext.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("direct:rest").inOnly("activemq:rest");
                }
            });
            camelContext.start();
            servletContext.setAttribute("camelContext", camelContext);

        } catch (Exception e) {
            // If this fails, no point in continuing
            throw new RuntimeException(e);
        }

        super.contextInitialized(servletContextEvent);
    }

    @Override
    public final void contextDestroyed(final ServletContextEvent servletContextEvent) {
        super.contextDestroyed(servletContextEvent);

        // Shutdown camel
        try {
            camelContext.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public final ServletContext getServletContext() {
        return servletContext;
    }

    public final void setServletContext(final ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public final CamelContext getCamelContext() {
        return camelContext;
    }

    public final void setCamelContext(final CamelContext camelContext) {
        this.camelContext = camelContext;
    }
}
