package it.frankenstein.data.thread;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.util.CollectionUtils;

import it.frankenstein.data.service.CallingApiService;

public class LongListDataThread extends Thread {

	private Map<String, LinkedList<String>>	prices;
	private CallingApiService							service;
	private Integer							timeWindow;
	private Integer							samples;
	private Integer							listMaxSize;
	private Integer							refreshTimeInSec;
	private String							pair;

	public LongListDataThread(CallingApiService service, String pair, int timeWindow, int samples, Map<String, LinkedList<String>> prices) {
		this.service = service;
		this.prices = prices;
		this.samples = samples;
		this.timeWindow = timeWindow;
		this.pair = pair;
		initTimeVariables();
	}

	protected void initTimeVariables() {
		listMaxSize = samples * timeWindow;
		refreshTimeInSec = 60 / samples;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Map<String, String> result = null;
				try {
					result = service.getList(pair);
				}
				catch (IOException e) {
					e.printStackTrace();
				}

				add(result);

				Thread.sleep(TimeUnit.SECONDS.toMillis(refreshTimeInSec.longValue()));
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			catch (ExecutionException e) {}
		}
	}

	private void add(Map<String, String> s) {
		if (!CollectionUtils.isEmpty(prices) && !CollectionUtils.isEmpty(prices.get(pair)) && prices.get(pair).size() >= listMaxSize) {
			prices.get(pair).removeLast();
		}
		prices.get(pair).addFirst(s.get("price"));
	}

}
