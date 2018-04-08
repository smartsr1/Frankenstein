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

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import it.frankenstein.common.bean.Data;
import it.frankenstein.data.registry.ThreadRegistry;
import it.frankenstein.data.service.Service;
import it.frankenstein.data.thread.DataCollectionThread;

@Path("/data")
public class DataController {

	private final Service				service;
	private final ThreadRegistry	registry;

	@Autowired
	public DataController(Service service, ThreadRegistry registry) {
		this.service = service;
		this.registry = registry;

	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/price")
	public String price() throws InterruptedException, ExecutionException {
		return service.getPrice();
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/lists")
	public Response list() {
		Data data = new Data();
		data.setPricesAll(registry.getPrices());
		return Response.ok().entity(data).build();
	}
	
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/singleList")
	public Response list(@QueryParam("stategy")String strategy) {
		Data data = new Data();
		data.setPricesStrategy(registry.getPrices().get(strategy));
		return Response.ok().entity(data).build();
	}

	@PUT
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/acquire")
	public Response acquire(@QueryParam(value = "symbol") String symbol) throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException {
		service.acquire(symbol);
		return Response.ok().build();
	}

	@PUT
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/dispose")
	public Response dispose(@QueryParam(value = "symbol") String symbol) throws JsonParseException, JsonMappingException, InterruptedException, ExecutionException, IOException {
		service.dispose(symbol);
		return Response.ok().build();
	}

}
