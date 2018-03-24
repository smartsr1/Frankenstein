package it.frankenstein.data.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class DataConf {

	
	private long refresh;

	public long getRefresh() {
		return refresh;
	}

	public void setRefresh(long refresh) {
		this.refresh = refresh;
	}
	
}
