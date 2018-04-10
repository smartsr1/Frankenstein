package it.frankenstein.analyze.controller;

import java.util.LinkedList;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import it.frankenstein.analyze.core.AnalyzingThread;
import it.frankenstein.analyze.service.Service;
import it.frankenstein.common.config.CommonConfiguration;

@Path("/core")
public class RestController {

	private CommonConfiguration		commonConfig;
	private final Service			service;
	private final AnalyzingThread	thread;

	@Autowired
	public RestController(CommonConfiguration commonConfig, Service service, AnalyzingThread thread) {
		this.commonConfig = commonConfig;
		this.service = service;
		this.thread = thread;
	}

	@POST
	@Path("/start")
	public void startThread() {
		startThread(commonConfig.getSymbols().get(0));
	}

	@POST
	@Path("/start/{pair}")
	public void startThread(@PathParam("pair") String pair) {
		thread.setPair(pair);
		thread.start();
	}

	@POST
	@Path("/stop")
	public void stopThread() {
		thread.interrupt();
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/getLists")
	public Map<String, LinkedList<String>> data() {
		return service.getLists();
	}

}
