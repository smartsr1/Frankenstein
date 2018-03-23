package it.frankenstein.data.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import it.frankenstein.data.controller.DataController;

@Component
public class JerseyConfig extends ResourceConfig {
    
	
	
	public JerseyConfig() {
        register(DataController.class);
    }
}