package it.frankenstein.analyze.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import it.frankenstein.analyze.controller.RestController;

@Component
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(RestController.class);
	}
}