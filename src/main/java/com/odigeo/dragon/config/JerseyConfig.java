package com.odigeo.dragon.config;

//import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
        register(RequestContextFilter.class);
        packages("com.odigeo.dragon.rest");
//        register(LoggingFilter.class);
    }
}
