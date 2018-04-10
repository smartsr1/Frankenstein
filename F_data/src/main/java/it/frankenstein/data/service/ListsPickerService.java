package it.frankenstein.data.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import it.frankenstein.common.config.CommonConfiguration;
import it.frankenstein.data.registry.ThreadRegistry;
import it.frankenstein.data.utils.DataUtility;

@Component
public class ListsPickerService {

	private final CommonConfiguration	commonConfigs;
	private final ThreadRegistry		registry;

	@Autowired
	public ListsPickerService(CommonConfiguration commonConfigs, ThreadRegistry registry) {
		this.registry = registry;
		this.commonConfigs = commonConfigs;
	}

	public Map<String, LinkedList<String>> getStrategyPrices() {
		return registry.getStrategyPrices();
	}

	public List<String> getStrategyPrices(String strategy) {
		return registry.getStrategyPrices().get(strategy);
	}

	public List<String> getSymbolPrices(String symbol) {
		return registry.getSymbolPrices().get(symbol);
	}

	public List<String> getSymbolPrices(String symbol, String window, String samplesInterval) {

		int samplesPerSecond = DataUtility.getSamplesPerSecond(commonConfigs.getSamples());
		int windowMinutes = Integer.parseInt(window);
		int intervalSeconds = Math.max(Integer.parseInt(samplesInterval), samplesPerSecond);

		int samplesWindowLimit = samplesPerSecond * windowMinutes * 60;
		int jumper = intervalSeconds / samplesPerSecond;

		List<String> list = registry.getSymbolPrices().get(symbol);
		if (list.size() > samplesWindowLimit) {
			list = list.subList(0, samplesWindowLimit);
		}

		List<String> newPriceList = Lists.newArrayList();
		for (int i = 0; i < list.size(); i += jumper) {
			newPriceList.add(list.get(i));
		}
		return newPriceList;
	}

}
