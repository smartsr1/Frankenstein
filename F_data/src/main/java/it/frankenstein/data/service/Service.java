package it.frankenstein.data.service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;

import it.frankenstein.common.config.CommonConfiguration;
import it.frankenstein.data.config.DataConf;
import it.frankenstein.data.handler.DataHandler;
import it.frankenstein.data.handler.LogHandler;

@Component
public class Service {

	private final Client				c;
	private final CommonConfiguration	commonConfig;
	private final DataConf	dataConf;
	private final DataHandler dataHandler;

	@Autowired
	public Service(Client c, CommonConfiguration commonConfig, DataConf dataConf, DataHandler dataHandler) {
		this.commonConfig = commonConfig;
		this.c = c;
		this.dataConf= dataConf;
		this.dataHandler=dataHandler;
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
	
	public String acquire(String symbol)  {
		dataHandler.handleAcquire();
		return "ok";
	}
	
	
	public String dispose(String symbol){
		dataHandler.handleDispose();;
		return "ok";
	}

}