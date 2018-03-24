package it.frankenstein.data.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;

import it.frankenstein.common.config.CommonConfiguration;
import it.frankenstein.data.config.DataConf;

@Component
public class Service {

	private final Client				c;
	private final CommonConfiguration	commonConfig;
	private final DataConf	dataConf;

	@Autowired
	public Service(Client c, CommonConfiguration commonConfig, DataConf dataConf) {
		this.commonConfig = commonConfig;
		this.c = c;
		this.dataConf= dataConf;
	}

	public String getPrice() throws InterruptedException, ExecutionException {
		String s = c.asyncResource(commonConfig.getUrl()).path(commonConfig.getPrice()).accept(MediaType.APPLICATION_JSON).get(String.class).get();
		System.out.println(s);
		return s;
	}

	public Map<String,String> getList() throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException {
		MultivaluedMap<String,String> params = new  MultivaluedHashMap<>();
		params.put("symbol", commonConfig.getSymbol());
		String response =  c.asyncResource(commonConfig.getUrl())
				.path(commonConfig.getPrice())
				.queryParams(params)
				.accept(MediaType.APPLICATION_JSON).get(String.class).get();
		ObjectMapper ob = new ObjectMapper();
		Map<String, String> map  = ob.readValue(response, Map.class);
		return map;
	}

}