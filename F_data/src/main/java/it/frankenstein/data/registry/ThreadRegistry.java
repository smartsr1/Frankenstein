package it.frankenstein.data.registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.frankenstein.common.config.CommonConfiguration;
import it.frankenstein.data.service.Service;
import it.frankenstein.data.thread.DataCollectionThread;

@Component
public class ThreadRegistry {

	private CommonConfiguration	commonConfig;
	private Map<String,LinkedList<String>>	prices;
	private Service				service;
	private List<Thread> t=new ArrayList<>();
	
	@Autowired
	public ThreadRegistry(CommonConfiguration commonConfig, Service service){
		this.commonConfig=commonConfig;
		this.prices=Collections.synchronizedMap(new HashMap<>());
		this.service=service;
	}
	
	@PostConstruct
	public void init(){
		for(String strategy : commonConfig.getTimeframeSamples()){
			LinkedList<String> list = new LinkedList<>();
			prices.put(strategy, list);
			DataCollectionThread data = new DataCollectionThread(service,strategy,prices);
			data.start();
			t.add(data);
		}
	}
	
	public Map<String,LinkedList<String>> getPrices(){
		return prices;
	}

}
