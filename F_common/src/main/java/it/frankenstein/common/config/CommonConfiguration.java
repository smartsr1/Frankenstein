package it.frankenstein.common.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "common", ignoreInvalidFields = true)
public class CommonConfiguration {

	private String			url;
	private String			price;
	private String			dataUrl;
	private String			coreUrl;
	private List<String>	symbol;
	private String			timeframe;
	private String			samples;
	private String			recent;

	public String getDataUrl() {
		return dataUrl;
	}

	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCoreUrl() {
		return coreUrl;
	}

	public void setCoreUrl(String coreUrl) {
		this.coreUrl = coreUrl;
	}

	public List<String> getSymbol() {
		return symbol;
	}

	public void setSymbol(List<String> symbol) {
		this.symbol = symbol;
	}

	public String getTimeframe() {
		return timeframe;
	}

	public void setTimeframe(String timeframe) {
		this.timeframe = timeframe;
	}

	public String getRecent() {
		return recent;
	}

	public void setRecent(String recent) {
		this.recent = recent;
	}

	public String getSamples() {
		return samples;
	}

	public void setSamples(String samples) {
		this.samples = samples;
	}

}
