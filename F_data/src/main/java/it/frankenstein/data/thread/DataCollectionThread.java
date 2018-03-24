package it.frankenstein.data.thread;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import it.frankenstein.common.config.CommonConfiguration;
import it.frankenstein.data.config.DataConf;
import it.frankenstein.data.service.Service;

@Component
public class DataCollectionThread extends Thread{
	
	public LinkedList<String> prices=new LinkedList<>();
	private Service service;
	private DataConf conf;
	private CommonConfiguration commonConfig;
	
	@Autowired
	public DataCollectionThread(Service service, CommonConfiguration commonConfig, DataConf conf){
		this.service=service;
		this.conf=conf;
		this.commonConfig=commonConfig;
		
	}
	@Override
	public void run(){
		while(true){
			try {
				Map<String, String> result=null;
				try {
					result = service.getList();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				add(result);
				Thread.sleep(conf.getRefresh());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
			}
		}
	}
	
	private void add(Map<String,String> s){
		if(!CollectionUtils.isEmpty(prices) && prices.size()>=Integer.valueOf(commonConfig.getTimeframe())){
			prices.removeFirst();
		}
		prices.addLast((String)s.get("price"));
	}
	
	public synchronized List<String> getPrices() {
		return prices;
	}

}
