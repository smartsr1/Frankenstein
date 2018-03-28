package it.frankenstein.data.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;
import org.supercsv.prefs.CsvPreference.Builder;

public class CSVWriter {

	public static final char DEFAULT_QUOTE_CHAR = '"';
	public static final int DEFAULT_DELIMITER_CHAR = ';';
	public static final String DEFAULT_END_OF_SYMBOLS = "\n";

	/**
	 * Metodo per la scrittura di un CSV su Stream partendo da una lista di oggetti.
	 * <br />
	 * 
	 * @param out
	 *            the out
	 * @param csvPreference
	 *            the csv preference
	 * @param cellProcessors
	 *            the cell processors
	 * @param objects
	 *            the objects
	 * @param fields
	 *            the fields
	 * @param header
	 *            the header
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void writeCsvFromBean(OutputStream out, CsvPreference csvPreference, CellProcessor[] cellProcessors,
			List<?> objects, String[] fields, String[] header) throws IOException {
		ICsvBeanWriter csvWriter = null;
		try {
			Writer writer = new OutputStreamWriter(out);
			csvWriter = new CsvBeanWriter(writer, csvPreference);

			csvWriter.writeHeader(header);

			for (Iterator<?> iterator = objects.iterator(); iterator.hasNext();) {
				csvWriter.write(iterator.next(), fields, cellProcessors);
			}

		} finally {
			if (csvWriter != null) {
				csvWriter.close();
			}
		}
	}

	/**
	 * Metodo per la scrittura di un CSV su Stream partendo da una lista. <br />
	 * 
	 * @param out
	 *            the out
	 * @param csvPreference
	 *            the csv preference
	 * @param cellProcessors
	 *            the cell processors
	 * @param body
	 *            the body
	 * @param header
	 *            the header
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void writeCsvFromList(OutputStream out, CsvPreference csvPreference, CellProcessor[] cellProcessors,
			List<?> body, String[] header) throws IOException {
		ICsvListWriter csvWriter = null;
		try {
			Writer writer = new OutputStreamWriter(out);
			csvWriter = new CsvListWriter(writer, csvPreference);

			csvWriter.writeHeader(header);

			// write the customer lists
			Iterator<? extends Object> iterator = body.iterator();
			while (iterator.hasNext()) {
				List<Object> row = Arrays.asList((Object[]) iterator.next());
				csvWriter.write(row, cellProcessors);
			}

		} finally {
			if (csvWriter != null) {
				csvWriter.close();
			}
		}
	}

	/**
	 * Gets the cell processor.
	 * 
	 * @param model
	 *            the model
	 * @return the cell processor
	 */
	public static CellProcessor[] getCellProcessor(Class<Object> model) {
		// TODO sarebbe bello realizzare il robetto che te lo fa in automatico
		return null;
	}

	/**
	 * Gets the csv preference.
	 * 
	 * @param quoteChar
	 *            the quote char
	 * @param delimiterChar
	 *            the delimiter char
	 * @param endOfLineSymbols
	 *            the end of line symbols
	 * @return the csv preference
	 */
	public static CsvPreference getCsvPreference(char quoteChar, int delimiterChar, String endOfLineSymbols) {
		return new Builder(quoteChar, delimiterChar, endOfLineSymbols).build();
	}

	/**
	 * Gets the default csv preference.
	 * 
	 * @return the default csv preference
	 */
	public static CsvPreference getDefaultCsvPreference() {
		return getCsvPreference(DEFAULT_QUOTE_CHAR, DEFAULT_DELIMITER_CHAR, DEFAULT_END_OF_SYMBOLS);
	}
}
