package it.frankenstein.analyze.core;

import java.util.List;

import org.springframework.stereotype.Component;

import it.frankenstein.analyze.service.Service;

@Component
public class Strategy {

	public final Service service;

	public Strategy(Service service) {
		this.service = service;
	}

	public void test() {
		// download data
		List<String> data = service.getData();
		
		// gestiore i dati e applicare la strategia
		// call post acquisto binance ( io direi di chiamare bin tramite il module data in modulo data)
		service.acquire();
		// applicare stategia vendita
		// call post vendita binance ( io direi di chiamare bin tramite il module data in modulo data)
		service.dispose();
		// reiniziare dall'acquisto...

	}

}
