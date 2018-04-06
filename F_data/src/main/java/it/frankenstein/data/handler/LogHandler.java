package it.frankenstein.data.handler;

import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

import it.frankenstein.common.config.Operation;

@Profile("LOG")
@Component
public class LogHandler implements DataHandler {

	@Override
	public void handleAcquire(String symbol, String price) {
		final String[] header = { "Operation", "symbol", "price", "timestamp" };
		try {
			File file = new File("C:/Users/sruffolo/frank.csv");
			boolean exists = file.exists();
			FileWriter writer;
			if (exists) {
				writer = new FileWriter(file, true);
			}
			else {
				writer = new FileWriter(file);
			}
			final ICsvMapWriter csvWriter = new CsvMapWriter(writer, CsvPreference.STANDARD_PREFERENCE);

			if (!exists) {
				csvWriter.writeHeader(header);
			}
			Map<String, String> values = new HashMap<>();
			values.put("Operation", Operation.BUY.name());
			values.put("symbol", symbol);
			values.put("price", price);
			values.put("timestamp", new Date().toString());
			csvWriter.write(values, header);
			csvWriter.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handleDispose(String symbol, String price) {
		final String[] header = { "Operation", "symbol", "price", "timestamp" };
		try {
			File file = new File("C:/Users/sruffolo/frank.csv");
			boolean exists = file.exists();
			FileWriter writer;
			if (exists) {
				writer = new FileWriter(file, true);
			}
			else {
				writer = new FileWriter(file);
			}
			final ICsvMapWriter csvWriter = new CsvMapWriter(writer, CsvPreference.STANDARD_PREFERENCE);

			if (!exists) {
				csvWriter.writeHeader(header);
			}
			Map<String, String> values = new HashMap<>();
			values.put("Operation", Operation.SELL.name());
			values.put("symbol", symbol);
			values.put("price", price);
			values.put("timestamp", new Date().toString());
			csvWriter.write(values, header);
			csvWriter.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
