package it.frankenstein.data.service;

import java.io.IOException;
import java.util.List;
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

@Component
public class CallingApiService {

	private final Client				c;
	private final CommonConfiguration	commonConfig;
	private final DataConf				dataConf;
	private final DataHandler			dataHandler;

	@Autowired
	public CallingApiService(Client c, CommonConfiguration commonConfig, DataConf dataConf, DataHandler dataHandler) {
		this.commonConfig = commonConfig;
		this.c = c;
		this.dataConf = dataConf;
		this.dataHandler = dataHandler;
	}

	public String getPrice() throws InterruptedException, ExecutionException {
		String s = c.asyncResource(commonConfig.getUrl()).path(commonConfig.getPrice()).accept(MediaType.APPLICATION_JSON).get(String.class).get();
		System.out.println(s);
		return s;
	}

	public String getPrice(String symbol) throws InterruptedException, ExecutionException {
		MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
		params.putSingle("symbol", symbol);
		String s = c.asyncResource(commonConfig.getUrl())
				.path(commonConfig.getPrice())
				.queryParams(params)
				.accept(MediaType.APPLICATION_JSON).get(String.class).get();
		System.out.println(s);
		return s;
	}

	public Map<String, String> getList() throws JsonParseException, JsonMappingException, InterruptedException, ExecutionException, IOException {
		return getList(commonConfig.getSymbols());
	}

	public Map<String, String> getList(List<String> list) throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException {
		MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
		params.put("symbol", list);
		String response = c.asyncResource(commonConfig.getUrl())
				.path(commonConfig.getPrice())
				.queryParams(params)
				.accept(MediaType.APPLICATION_JSON).get(String.class).get();
		ObjectMapper ob = new ObjectMapper();
		Map<String, String> map = ob.readValue(response, Map.class);
		return map;
	}

	public Map<String, String> getList(String symbol) throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException {
		String response = c.asyncResource(commonConfig.getUrl())
				.path(commonConfig.getPrice())
				.queryParam("symbol", symbol)
				.accept(MediaType.APPLICATION_JSON).get(String.class).get();
		ObjectMapper ob = new ObjectMapper();
		Map<String, String> map = ob.readValue(response, Map.class);
		return map;
	}

	public String acquire(String symbol) throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException {
		String price = getPrice(symbol);
		ObjectMapper ob = new ObjectMapper();
		Map<String, String> map = ob.readValue(price, Map.class);
		dataHandler.handleAcquire(symbol, map.get("price"));
		return "ok";
	}

	public String dispose(String symbol) throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException {
		String price = getPrice(symbol);
		ObjectMapper ob = new ObjectMapper();
		Map<String, String> map = ob.readValue(price, Map.class);
		dataHandler.handleDispose(symbol, map.get("price"));;
		return "ok";
	}

}