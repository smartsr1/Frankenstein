package it.frankenstein.data.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.supercsv.io.CsvListReader;
import org.supercsv.prefs.CsvPreference;
import org.supercsv.prefs.CsvPreference.Builder;

public class CSVReader {
	public static final String PREFIX_REJECT_RECORD = "record_CSV_";
	public static final int READER_TYPE_LINE = 0;

	public static final CsvPreference DEFAULT_PREF_LINE = new Builder('"', ';', "\n").build();
	public static final CsvPreference SPACE_PREF_LINE = new Builder('"', ' ', "\n").build();

	private CsvListReader readerLine;

	private String currentFileName;
	private String logFileName;
	private File rejectDir;
	private boolean rejectEnable;

	private int keyIndex;
	private int valueIndex;

	/**
	 * Instantiates a new CSV reader.
	 * 
	 * @param csvFile
	 *            the csv file
	 * @param rejectDir
	 *            the reject dir
	 * @param csvPreference
	 *            the csv preference
	 * @param locale
	 *            the locale
	 * @param rejectEnable
	 *            the reject enable
	 * @throws FileNotFoundException
	 *             the file not found exception
	 */
	public CSVReader(File csvFile, File rejectDir, CsvPreference csvPreference, boolean rejectEnable)
			throws FileNotFoundException {
		super();
		this.currentFileName = csvFile.getName();
		this.rejectDir = rejectDir;
		this.rejectEnable = rejectEnable;
		this.logFileName = currentFileName.substring(0, currentFileName.length() - 4) + ".log";
		readerLine = new CsvListReader(new FileReader(csvFile), csvPreference);

		// set index standard
		this.keyIndex = 0;
		this.valueIndex = 1;
	}

	/**
	 * Sets the key index.
	 * 
	 * @param keyIndex
	 *            the new key index
	 */
	public void setKeyIndex(int keyIndex) {
		this.keyIndex = keyIndex;
	}

	/**
	 * Sets the value index.
	 * 
	 * @param valueIndex
	 *            the new value index
	 */
	public void setValueIndex(int valueIndex) {
		this.valueIndex = valueIndex;
	}

	/**
	 * Sets the key and value index.
	 * 
	 * @param keyLable
	 *            the key lable
	 * @param valueLable
	 *            the value lable
	 */
	public void setKeyAndValueIndex(String keyLable, String valueLable) {
		List<String> header = readLine();
		if (keyLable != null && !keyLable.equals("") && valueLable != null && !valueLable.equals("")) {
			int i = 0;
			for (Iterator<String> iterator = header.iterator(); iterator.hasNext(); i++) {
				String str = iterator.next();
				if (str.equalsIgnoreCase(keyLable)) {
					keyIndex = i;
				} else if (str.equalsIgnoreCase(valueLable)) {
					valueIndex = i;
				}
			}
		}
	}

	/**
	 * Skip line.
	 * 
	 * @param numberSkipLine
	 *            the number skip line
	 */
	public void skipLine(int numberSkipLine) {
		try {
			for (int i = 0; i < numberSkipLine; i++) {
				readerLine.read();
			}
		} catch (Exception e) {
			System.err.println("Errore durante la lettura da csv : ");
			e.printStackTrace();
		}
	}

	/**
	 * Read line.
	 * 
	 * @return the list
	 */
	public List<String> readLine() {
		try {
			return readerLine.read();
		} catch (IOException e) {
			System.err.println("errore nella lettura della riga.");
			e.printStackTrace();
		}
		return new ArrayList<String>();
	}

	/**
	 * Read record line.
	 * 
	 * @return the object[]
	 * @throws CSVReaderException
	 *             the CSV reader exception
	 */
	public Object[] readRecordLine() throws CSVReaderException {
		Object[] record = null;
		try {
			List<String> line = readerLine.read();
			if (line == null)
				return null;
			// clear line commentata perché mi interessano anche i valori nulli
			// dell'elemento
			// line = clearLine(line);

			record = new Object[2];
			record[0] = line.get(keyIndex); // codice
			record[1] = new Double(line.get(valueIndex).replace(",", ".")); // valore
		} catch (Exception e) {
			System.err.println("Errore durante la lettura da csv");
			e.printStackTrace();	
			if (rejectEnable) {
				File rejectFile = new File(rejectDir, logFileName);
				FileWriter writer = null;
				try {
					writer = new FileWriter(rejectFile, true);
					writer.write("Errore durante la lettura da csv : " + e.getMessage() + "\n");
					writer.flush();
				} catch (IOException e1) {
					System.err.println("Si è verificato un'errore! Non è stato possibile creare il file di log : ");
					e1.printStackTrace();
				} finally {
					if (writer != null)
						try {
							writer.close();
						} catch (IOException e1) {
							System.err.println(e1.getMessage());
						}
				}
				throw new CSVReaderException("Errore nella lettura del csv", e);
			}
		}

		return record;
	}

	/**
	 * Close reader.
	 */
	public void closeReader() {
		try {
			readerLine.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
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
	 * The Class CSVReaderException.
	 */
	public static class CSVReaderException extends Exception {
		private static final long serialVersionUID = 3171697164392438751L;

		/**
		 * Instantiates a new CSV reader exception.
		 * 
		 * @param msg
		 *            the msg
		 */
		public CSVReaderException(String msg) {
			super(msg);
		}

		/**
		 * Instantiates a new CSV reader exception.
		 * 
		 * @param exc
		 *            the exc
		 */
		public CSVReaderException(Throwable exc) {
			super(exc);
		}

		/**
		 * Instantiates a new CSV reader exception.
		 * 
		 * @param msg
		 *            the msg
		 * @param exc
		 *            the exc
		 */
		public CSVReaderException(String msg, Throwable exc) {
			super(msg, exc);
		}
	}

}
