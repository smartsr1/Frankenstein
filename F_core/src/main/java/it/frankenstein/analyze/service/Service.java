package it.frankenstein.analyze.service;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

import it.frankenstein.common.bean.Data;
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

	public List<String> getData() {
		ClientResponse response = c.resource(commonConfig.getDataUrl())
				.path("data/list")
				.accept(MediaType.APPLICATION_JSON)
				.get(ClientResponse.class);

		Data item = response.getEntity(Data.class);
		return item.getPrices();
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