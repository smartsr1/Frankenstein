package it.frankenstein.common.bean;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Data")
public class Data {

	private List<String> pricesStrategy;
	
	private Map<String,LinkedList<String>> pricesAll;

	public Data() {}

	public List<String> getPricesStrategy() {
		return pricesStrategy;
	}

	public void setPricesStrategy(List<String> pricesStrategy) {
		this.pricesStrategy = pricesStrategy;
	}

	public Map<String, LinkedList<String>> getPricesAll() {
		return pricesAll;
	}

	public void setPricesAll(Map<String, LinkedList<String>> pricesAll) {
		this.pricesAll = pricesAll;
	}



}
