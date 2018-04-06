package it.frankenstein.data.handler;

public interface DataHandler {

	public void handleAcquire(String symbol, String price);

	public void handleDispose(String symbol, String price);
}
