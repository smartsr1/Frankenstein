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

import com.google.common.collect.Lists;

import it.frankenstein.common.config.CommonConfiguration;
import it.frankenstein.data.service.CallingApiService;
import it.frankenstein.data.thread.DataCollectionThread;
import it.frankenstein.data.thread.LongListDataThread;
import it.frankenstein.data.utils.DataUtility;

@Component
public class ThreadRegistry {

	private CommonConfiguration				commonConfig;

	// ex. key="BTCUSDT", value=List of prices
	private Map<String, LinkedList<String>>	symbolPricesMap;

	// ex. key="600/5", value=List of prices
	private Map<String, LinkedList<String>>	strategyPricesMap;
	private CallingApiService							service;
	private List<Thread>					t	= new ArrayList<>();

	@Autowired
	public ThreadRegistry(CommonConfiguration commonConfig, CallingApiService service) {
		this.commonConfig = commonConfig;
		strategyPricesMap = Collections.synchronizedMap(new HashMap<>());
		symbolPricesMap = Collections.synchronizedMap(new HashMap<>());
		this.service = service;
	}

	@PostConstruct
	public void init() {
		initDataCollectionByStrategy();
		initLongListDataCollection();
	}

	private void initLongListDataCollection() {
		int samples = DataUtility.getSamplesPerMinute(commonConfig.getSamples());
		int timeWindow = DataUtility.getTimeWindow(commonConfig.getTimeWindow());
		for (String pair : commonConfig.getSymbols()) {
			symbolPricesMap.put(pair, Lists.newLinkedList());
			LongListDataThread thread = new LongListDataThread(service, pair, timeWindow, samples, symbolPricesMap);
			thread.start();
			t.add(thread);
		}

	}

	private void initDataCollectionByStrategy() {
		for (String strategy : commonConfig.getTimeframeSamples()) {
			strategyPricesMap.put(strategy, Lists.newLinkedList());
			DataCollectionThread thread = new DataCollectionThread(service, strategy, strategyPricesMap);
			thread.start();
			t.add(thread);
		}
	}

	public Map<String, LinkedList<String>> getStrategyPrices() {
		return strategyPricesMap;
	}

	public Map<String, LinkedList<String>> getSymbolPrices() {
		return symbolPricesMap;
	}

}
