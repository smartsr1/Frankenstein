package it.frankenstein.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "common", ignoreInvalidFields = true)
public class CommonConfiguration {

	private String	url;
	private String	price;
	private String	dataUrl;
	private String	coreUrl;

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

}
