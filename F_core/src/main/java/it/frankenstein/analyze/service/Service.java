package it.frankenstein.analyze.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

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
		WebResource resource = Client.create().resource(commonConfig.getDataUrl());
	    ClientResponse clientResponse =
	        resource.path("data/list")
	        .type(MediaType.APPLICATION_JSON)
	        .get(ClientResponse.class);
	   String response = clientResponse.getEntity(String.class);
	   ObjectMapper mapper = new ObjectMapper();
	   List<String> result=null;
		try {
			result = mapper.readValue(response, List.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  return result;
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