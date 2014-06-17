package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class ConfigFileGenerator {

	// Execution of Brahms
	// Directory where files are written to configuration
	private final static String configBase = "/media/DATA/Documents/Cours/3A-MoSIG/stage/fourmis/LeskFenetres/config_brahms/";
	// Directory will be launched or executions
	private final static String execBase   = "/home/martinu/fenetres/";
	private final static int nbThreads = 4;
	
	// Limited number of combinations above which a window will not be analyzed e.
	private final static long combinationMax = 10000000000L;     // 10 milliards
	
	// Directory where are stored dictionaries (relatively execBase)
	private static final String dictionnariesBase = "dictionnaires-lesk/";
	
	// List of dictionaries use
	private static final String[] dictionnaries = {
			"dict",
			"dict-adapted-allRelations",
			"dict-apriori",
			"dict-adapted-allRelations-apriori",
	};

	// List of window sizes used
	private static final int[] windowSizes = {15};
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// For all possible configurations, we gn rer file
		for (int i = 0; i < dictionnaries.length; i++) {
			String dict = dictionnaries[i];
			for (int w : windowSizes) {
				try {
					String fileName = configBase + "config_w" + String.format("%02d", w) + "_" + dict + ".xml";
					PrintWriter configPrinter = new PrintWriter(new File(fileName));
					String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
						+ "<configuration>\n"
						+ "<param name=\"task\"           value=\"Semeval-7\"/>\n"
						+ "<param name=\"inFile\"         value=\"" + execBase + "semeval-task7/test/eng-coarse-all-words.xml\"/>\n"
						+ "<param name=\"outFile\"        value=\"" + execBase + "LeskFenetres/answers/answer_w" + String.format("%02d", w) + "_" + dict + ".ans\"/>\n"
						+ "<param name=\"dictFile\"       value=\"" + execBase + dictionnariesBase + dict + ".xml\"/>\n"
						+ "<param name=\"windowSize\"     value=\"" + w + "\"/>\n"
						+ "<param name=\"combinationMax\" value=\"" + combinationMax + "\"/>\n"
						+ "<param name=\"nbThreads\"      value=\"" + nbThreads + "\"/>\n"
						+ "</configuration>\n";
					configPrinter.write(s);
					configPrinter.flush();
					configPrinter.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
}