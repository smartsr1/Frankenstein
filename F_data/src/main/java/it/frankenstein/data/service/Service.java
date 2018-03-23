package it.frankenstein.data.service;

import java.util.concurrent.ExecutionException;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.client.Client;

import it.frankenstein.common.config.CommonConfiguration;

@Component
public class Service {

	private final Client				c;
	private final CommonConfiguration	commonConfig;

	@Autowired
	public Service(Client c, CommonConfiguration commonConfig) {
		this.commonConfig = commonConfig;
		this.c = c;
	}

	public String getPrice() throws InterruptedException, ExecutionException {
		String s = c.asyncResource(commonConfig.getUrl()).path(commonConfig.getPrice()).accept(MediaType.APPLICATION_JSON).get(String.class).get();
		System.out.println(s);
		return s;
	}

}