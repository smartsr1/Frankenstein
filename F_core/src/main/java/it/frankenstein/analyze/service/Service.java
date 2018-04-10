package it.frankenstein.analyze.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

	public Map<String, LinkedList<String>> getLists() {
		ClientResponse response = c.resource(commonConfig.getDataUrl())
				.path("data/lists")
				.accept(MediaType.APPLICATION_JSON)
				.get(ClientResponse.class);

		Data item = response.getEntity(Data.class);
		return item.getPricesAll();
	}

	public List<String> getDataByStrategy(String strategy) {
		ClientResponse response = c.resource(commonConfig.getDataUrl())
				.path("data/strategyList")
				.queryParam("stategy", strategy)
				.accept(MediaType.APPLICATION_JSON)
				.get(ClientResponse.class);

		Data item = response.getEntity(Data.class);
		return item.getPricesStrategy();
	}

	public List<String> getDataBySymbol(String symbol) {
		ClientResponse response = c.resource(commonConfig.getDataUrl())
				.path("data/symbolList")
				.queryParam("symbol", symbol)
				.accept(MediaType.APPLICATION_JSON)
				.get(ClientResponse.class);

		Data item = response.getEntity(Data.class);
		return item.getPricesStrategy();
	}

	public List<String> getDataBySymbolTimeframe(String symbol, String timeWindow, String interval) {
		ClientResponse response = c.resource(commonConfig.getDataUrl())
				.path("data/symbolList")
				.queryParam("symbol", symbol)
				.queryParam("window", timeWindow)
				.queryParam("interval", interval)
				.accept(MediaType.APPLICATION_JSON)
				.get(ClientResponse.class);

		Data item = response.getEntity(Data.class);
		return item.getPricesStrategy();
	}

	public String acquire(String symbol) {
		ClientResponse response = c.resource(commonConfig.getDataUrl())
				.path("data/acquire")
				.queryParam("symbol", symbol)
				.accept(MediaType.APPLICATION_JSON)
				.put(ClientResponse.class);
		return response.getStatusInfo().getFamily().toString();
	}

	public String dispose(String symbol) {
		ClientResponse response = c.resource(commonConfig.getDataUrl())
				.path("data/dispose")
				.queryParam("symbol", symbol)
				.accept(MediaType.APPLICATION_JSON)
				.put(ClientResponse.class);
		return response.getStatusInfo().getFamily().toString();
	}

}