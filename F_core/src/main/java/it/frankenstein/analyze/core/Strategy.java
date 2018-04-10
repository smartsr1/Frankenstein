package it.frankenstein.analyze.core;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.frankenstein.analyze.service.Service;

@Component
public class Strategy {

	public final Service service;

	@Autowired
	public Strategy(Service service) {
		this.service = service;
	}

	public void test() {
		// download data
		Map<String, LinkedList<String>> lists = service.getLists();
		// or
		List<String> listStrategy = service.getDataByStrategy("600/10");

		// gestiore i dati e applicare la strategia
		// call post acquisto binance ( io direi di chiamare bin tramite il module data in modulo data)
		service.acquire("symbol");
		// applicare stategia vendita
		// call post vendita binance ( io direi di chiamare bin tramite il module data in modulo data)
		service.dispose("symbol");
		// reiniziare dall'acquisto...

	}

}
