package it.frankenstein.analyze.service;

import java.util.concurrent.ExecutionException;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.client.Client;

import it.frankenstein.common.config.CommonConfiguration;

@Component
public class Service {

	private final Client				c;
	private final CommonConfiguration	commonConfig;
	@Value("${common.url}")
	private String						test;

	@Autowired
	public Service(Client c, CommonConfiguration commonConfig) {
		this.c = c;
		this.commonConfig = commonConfig;
	}

	public String getData() {
		String s = null;
		try {
			s = c.asyncResource(commonConfig.getDataUrl()).path("data/price").accept(MediaType.APPLICATION_JSON).get(String.class).get();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		catch (ExecutionException e) {
			e.printStackTrace();
		}
		System.out.println(s);
		return s;
	}

	public String acquire() {
		String s = null;
		return s;
	}

	public String dispose() {
		String s = null;
		return s;
	}

}