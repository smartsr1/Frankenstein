package it.frankenstein.common.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Data")
public class Data {

	private List<String> prices;

	public Data() {}

	public List<String> getPrices() {
		return prices;
	}

	public void setPrices(List<String> prices) {
		this.prices = prices;
	}

}
