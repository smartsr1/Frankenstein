package it.frankenstein.data.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import it.frankenstein.data.service.Service;
import it.frankenstein.data.thread.DataCollectionThread;

@Path("/data")
public class DataController {

	private final Service service;
	private final DataCollectionThread dataCollectionThread;

	@Autowired
	public DataController(Service service, DataCollectionThread dataCollectionThread) {
		this.service = service;
		this.dataCollectionThread=dataCollectionThread;
		
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
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Path("/list")
	public List<String> list() throws InterruptedException, ExecutionException {
		 return dataCollectionThread.getPrices();
	}

	
}
