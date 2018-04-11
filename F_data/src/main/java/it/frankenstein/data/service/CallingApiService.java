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
		dataHandler.handleDispose(symbol, map.get("price"));
		return "ok";
	}
	
	public String order(String symbol, double amount) throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException {
		String path = "/api/v3/order/test";
		String url = commonConfig.getUrl();
		MultivaluedMap<String, String> params = getQueryParams(symbol, amount);
		String response = "";
		try {
			response = c.asyncResource(url)
					.path(path)
					.queryParams(params)
					.header("X-MBX-APIKEY", "vmPUZE6mv9SD5VNHk4HlWFsOr6aKE2zvsw0MuIgwCIPy6utIco14y7Ju91duEh8A")
					.accept(MediaType.APPLICATION_JSON)
					.post(String.class).get();
		} catch(Exception e) {
			e.printStackTrace();
		}

		
		ObjectMapper ob = new ObjectMapper();
		Map<String, String> map = ob.readValue(response, Map.class);
		return map.toString();
	}
	
	public MultivaluedMap<String, String> getQueryParams(String symbol, double amount) {
		MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
		params.add("symbol", symbol);
		params.add("side", "BUY"); //BUY or SELL
		params.add("type", "LIMIT"); // LIMIT or MARKET or STOP_LOSS or STOP_LOSS_LIMIT or TAKE_PROFIT or TAKE_PROFIT_LIMIT or LIMIT_MAKER
		//params.add("timeInForce", ""); //no mandatory ENUM NO	
		params.add("quantity", Double.toString(amount));
		//params.add("price", ""); // DECIMAL	NO	
		//params.add("newClientOrderId", ""); //	STRING	NO	A unique id for the order. Automatically generated if not sent.
		//params.add("stopPrice", ""); //	DECIMAL	NO	Used with STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, and TAKE_PROFIT_LIMIT orders.
		//params.add("icebergQty", ""); //	DECIMAL	NO	Used with LIMIT, STOP_LOSS_LIMIT, and TAKE_PROFIT_LIMIT to create an iceberg order.
		params.add("newOrderRespType", "FULL"); //	ENUM	NO	Set the response JSON. ACK, RESULT, or FULL; default: RESULT.
		//params.add("recvWindow", ""); //LONG	NO	
		params.add("timestamp", Long.toString(System.currentTimeMillis())); //LONG	YES
		
		params.add("signature", "NhqPtmdSJYdKjVHjA7PZj4Mge3R5YNiP1e3UZjInClVN65XAbvqqM6A7H5fATj0j");
		
		return params;
	}
	

}