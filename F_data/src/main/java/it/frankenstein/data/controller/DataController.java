package it.frankenstein.data.controller;

import java.util.concurrent.ExecutionException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import it.frankenstein.data.service.Service;

@Path("/data")
public class DataController {

	private final Service service;

	@Autowired
	public DataController(Service service) {
		this.service = service;
	}

	// @GET
	// @Produces({ MediaType.APPLICATION_JSON })
	// @Path("/status")
	// public String status() {
	// return service.getStatus();
	// }

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/price")
	public String price() throws InterruptedException, ExecutionException {
		return service.getPrice();
	}

	// @GET
	// @Produces({ MediaType.APPLICATION_JSON })
	// @Path("/allDay")
	// public String allDay() {
	// return service.getAllDayPrice();
	// }
	//
	// @GET
	// @Produces({ MediaType.APPLICATION_JSON })
	// @Path("/diff")
	// public String diff() {
	// try {
	// return service.getDiff();
	// }
	// catch (JsonParseException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// catch (JsonMappingException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return null;
	// }

}
