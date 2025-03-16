package com.cft.rest.config;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import com.cft.rest.resources.BookResource;
import com.cft.rest.resources.LoanResource;
import com.cft.rest.resources.UserResource;
import com.google.inject.Guice;
import com.google.inject.Injector;

import jakarta.inject.Inject;
import jakarta.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class ApplicationConfig extends ResourceConfig {

	@Inject
	public ApplicationConfig(ServiceLocator serviceLocator) {
		register(UserResource.class);
		register(LoanResource.class);
		register(BookResource.class);
		register(ObjectMapperProvider.class);
		
        // bridge the Guice container (Injector) into the HK2 container (ServiceLocator)
        Injector injector = Guice.createInjector(new GuiceModule());
        GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
        GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
        guiceBridge.bridgeGuiceInjector(injector);		
	}
}
