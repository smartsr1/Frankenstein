package it.frankenstein.data.utils;

public class DataUtility {

	public static Integer getTimeFrame(String timeFrameSamples){
		String timeFrame[]= timeFrameSamples.split("/");
		return Integer.valueOf(timeFrame[0]);
	}
	
	public static Integer getSamples(String timeFrameSamples){
		String timeFrame[]= timeFrameSamples.split("/");
		return Integer.valueOf(timeFrame[1]);
	}

}
