package it.frankenstein.analyze.core;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.frankenstein.analyze.service.Service;

@Component
public class AnalyzingThread extends Thread {

	// min.
	private static final String	DEFAULT_WINDOW		= "10";
	// sec.
	private static final String	DEFAULT_INTERVAL	= "20";

	private Service				service;
	private String				pair;
	private String				window;
	private String				interval;

	@Autowired
	public AnalyzingThread(Service service) {
		this.service = service;
	}

	@Override
	public void run() {
		while (true) {
			try {

				// getPunctualPriceVariations();
				getRobaBenPrecisa();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unused")
	private void getPunctualPriceVariations() throws InterruptedException {
		// List<String> lastprices = service.getDataByStrategy("60/5");
		List<String> lastprices = service.getDataBySymbol(pair);
		System.out.println(pair + " - List size: " + lastprices.size());
		System.out.println("--------");
		System.out.println(lastprices.stream().findFirst().orElse("bububuh!"));
		Thread.sleep(1000);
	}

	private void getRobaBenPrecisa() throws InterruptedException {

		String interval = StringUtils.defaultString(this.interval, DEFAULT_INTERVAL);
		List<String> prices = service.getDataBySymbolTimeframe(pair, StringUtils.defaultString(window, DEFAULT_WINDOW), interval);
		System.out.println(new Date().toString() + " --------");
		prices.stream().forEach(p -> System.out.println(p));
		Thread.sleep(TimeUnit.SECONDS.toMillis(Integer.parseInt(interval)));
	}

	public void setPair(String pair) {
		this.pair = pair;
	}

	public String getWindow() {
		return window;
	}

	public void setWindow(String window) {
		this.window = window;
	}

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

}
