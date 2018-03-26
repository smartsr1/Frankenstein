package it.frankenstein.data.handler;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("LOG")
@Component
public class LogHandler implements DataHandler{


	@Override
	public void handleAcquire() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleDispose() {
		// TODO Auto-generated method stub
		
	}

}
