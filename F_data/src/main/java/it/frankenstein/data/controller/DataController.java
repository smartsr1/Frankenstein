package it.frankenstein.data.controller;

import java.util.concurrent.ExecutionException;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import it.frankenstein.common.bean.Data;
import it.frankenstein.data.service.Service;
import it.frankenstein.data.thread.DataCollectionThread;

@Path("/data")
public class DataController {

	private final Service				service;
	private final DataCollectionThread	dataCollectionThread;

	@Autowired
	public DataController(Service service, DataCollectionThread dataCollectionThread) {
		this.service = service;
		this.dataCollectionThread = dataCollectionThread;

	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/start")
	public String start() throws InterruptedException, ExecutionException {
		dataCollectionThread.start();
		return "ok";
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/stop")
	public String stop() throws InterruptedException, ExecutionException {
		dataCollectionThread.interrupt();
		return "ok";
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/price")
	public String price() throws InterruptedException, ExecutionException {
		return service.getPrice();
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/list")
	public Response list() {
		Data data = new Data();
		data.setPrices(dataCollectionThread.getPrices());
		return Response.ok().entity(data).build();
	}
	
	@PUT
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/acquire")
	public Response acquire(@QueryParam(value = "symbol") String symbol	) {
		service.acquire(symbol);
		return Response.ok().build();
	}
	
	@PUT
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/dispose")
	public Response dispose(@QueryParam(value = "symbol") String symbol) {
		service.dispose(symbol);
		return Response.ok().build();
	}
	

}
