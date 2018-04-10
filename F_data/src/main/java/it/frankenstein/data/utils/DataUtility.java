package it.frankenstein.data.utils;

public class DataUtility {

	private static final Integer	DEFAULT_TIME_WINDOW	= 3600;
	private static final Integer	DEFAULT_SAMPLES		= 30;

	public static Integer getTimeFrame(String timeFrameSamples) {
		String timeFrame[] = timeFrameSamples.split("/");
		return Integer.valueOf(timeFrame[0]);
	}

	public static Integer getSamples(String timeFrameSamples) {
		String timeFrame[] = timeFrameSamples.split("/");
		return Integer.valueOf(timeFrame[1]);
	}

	public static Integer getSamplesPerMinute(String samples) {
		try {
			return Integer.parseInt(samples);
		}
		catch (@SuppressWarnings("unused") NumberFormatException e) {
			return DEFAULT_SAMPLES;
		}
	}

	public static Integer getSamplesPerSecond(String samples) {
		return 60 / getSamplesPerMinute(samples);
	}

	public static Integer getTimeWindow(String timeWindow) {
		try {
			return Integer.parseInt(timeWindow);
		}
		catch (@SuppressWarnings("unused") NumberFormatException e) {
			return DEFAULT_TIME_WINDOW;
		}
	}
}
