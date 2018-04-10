package it.frankenstein.data.controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import it.frankenstein.common.bean.Data;
import it.frankenstein.data.service.CallingApiService;
import it.frankenstein.data.service.ListsPickerService;

@Path("/data")
public class DataController {

	private final CallingApiService		apiService;
	private final ListsPickerService	pickerService;

	@Autowired
	public DataController(CallingApiService apiService, ListsPickerService pickerService) {
		this.apiService = apiService;
		this.pickerService = pickerService;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/price")
	public String price() throws InterruptedException, ExecutionException {
		return apiService.getPrice();
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/lists")
	public Response list() {
		Data data = new Data();
		data.setPricesAll(pickerService.getStrategyPrices());
		return Response.ok().entity(data).build();
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/strategyList")
	public Response list(@QueryParam("stategy") String strategy) {
		Data data = new Data();
		data.setPricesStrategy(pickerService.getStrategyPrices(strategy));
		return Response.ok().entity(data).build();
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/symbolList")
	public Response listBySymbolTimeframe(@QueryParam("symbol") String symbol, @QueryParam("window") String window, @QueryParam("interval") String interval) {
		Data data = new Data();
		if (StringUtils.isEmpty(window) && StringUtils.isEmpty(interval)) {
			data.setPricesStrategy(pickerService.getSymbolPrices(symbol));
		}
		else {
			data.setPricesStrategy(pickerService.getSymbolPrices(symbol, window, interval));
		}
		return Response.ok().entity(data).build();
	}

	@PUT
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/acquire")
	public Response acquire(@QueryParam(value = "symbol") String symbol) throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException {
		apiService.acquire(symbol);
		return Response.ok().build();
	}

	@PUT
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/dispose")
	public Response dispose(@QueryParam(value = "symbol") String symbol) throws JsonParseException, JsonMappingException, InterruptedException, ExecutionException, IOException {
		apiService.dispose(symbol);
		return Response.ok().build();
	}

}
