package it.frankenstein.data.thread;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.util.CollectionUtils;
import it.frankenstein.data.service.Service;
import it.frankenstein.data.utils.DataUtility;

public class DataCollectionThread extends Thread {

	private Map<String,LinkedList<String>>	prices;
	private Service				service;
	private String timeFrameSamples;
	public DataCollectionThread(Service service, String timeFramesamples, Map<String,LinkedList<String>> prices) {
		this.service = service;
		this.timeFrameSamples=timeFramesamples;
		this.prices= prices;

	}

	@Override
	public void run() {
		while (true) {
			try {
				Map<String, String> result = null;
				try {
					result = service.getList();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				add(result);
				Integer refres = DataUtility.getTimeFrame(timeFrameSamples) / DataUtility.getSamples(timeFrameSamples);
				Thread.sleep(TimeUnit.SECONDS.toMillis(refres.longValue()));
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			catch (ExecutionException e) {}
		}
	}

	private void add(Map<String, String> s) {
		if (!CollectionUtils.isEmpty(prices) && !CollectionUtils.isEmpty(prices.get(timeFrameSamples)) && prices.get(timeFrameSamples).size() >= Integer.valueOf(DataUtility.getSamples(timeFrameSamples))) {
			prices.get(timeFrameSamples).removeLast();
		}
		prices.get(timeFrameSamples).addFirst(s.get("price"));
	}

}
