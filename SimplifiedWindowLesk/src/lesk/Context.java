package lesk;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import utils.MyDico;

import Parser.ParserText;
import Parser.ParserConfig;

import context.Sense;
import context.Document;
import context.WordIndex;

public class Context {

	private ArrayList<Document> corpus; // All sentences of all texts
	private String task; // Name of the task performed
	private String inFile = "eng-coarse-all-words.xml"; // Corpus of ambiguous service
	private String outFile = "answers.ans"; // Answer file
	private String dictFile = "dict-adapted-all-relations.xml"; // Inventory sense used
	private int windowSize; // Window size analysis
	private long combinationMax; // Limited number of combinations of window
	private int nbThreads; // Number of threads used to execute

	private MyDico dict;

	public Context(String configFile) {
		corpus = new ArrayList<Document>();
		task = "Semeval-7";
		inFile = "eng-coarse-all-words.xml"; // Corpus of ambiguous service
		outFile = "answers.ans"; // Answer file
		dictFile = "dict-adapted-all-relations.xml"; // Sense inventory used
		windowSize = 6;
		combinationMax = 1000000000;
		nbThreads = 2;

		try {
			XMLReader saxReader = XMLReaderFactory.createXMLReader();
			ParserConfig config = new ParserConfig();
			saxReader.setContentHandler(config);
			saxReader.parse(configFile);
			task = config.getTask();
			inFile = config.getInFile();
			outFile = config.getOutFile();
			dictFile = config.getDictFile();
			windowSize = config.getWindowSize();
			combinationMax = config.getCombinationMax();
			nbThreads = config.getNbThreads();
			WordIndex.getInstance().init(dictFile+".trans");
		} catch (Throwable t) {
			System.err.println("Error parsing the configuration file");
			t.printStackTrace();
		}
		dict = new MyDico(dictFile);

		// Reading the input file
		if (this.task.equals("Semeval-7")) {
			try {
				XMLReader saxReader = XMLReaderFactory.createXMLReader();
				saxReader.setContentHandler(new ParserText(this));
				saxReader.parse(inFile);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

	public int getWindowSize() {
		return windowSize;
	}

	public String getTask() {
		return task;
	}

	public MyDico getDict() {
		return dict;
	}

	public void add(Document d) {
		this.corpus.add(d);
	}

	public void analyse() {
		// Analyzing texts one by one
		for (int i = 0; i < corpus.size(); i++) {
			System.out.println("Processing text "+i);
			corpus.get(i).analyse(windowSize, dict.getShortName(),
					combinationMax, nbThreads);
		}
	}

	public void writeResult() throws FileNotFoundException {
		ArrayList<Sense> result = new ArrayList<Sense>();
		// It traverses the nodes Tags
		for (int i = 0; i < this.corpus.size(); i++) {
			Document d = corpus.get(i);
			for (int j = 0; j < d.size(); j++) {
				if (d.getWord(j).getBestSense() != null) {
					result.add(d.getWord(j).getBestSense());
				}
			}
		}

		// --------- Recording results ------------
		// We sort the result
		Collections.sort(result, new Comparator<Sense>() {
			@Override
			public int compare(Sense o1, Sense o2) {
				return o1.compareTo(o2);
			}
		});
		// It is recorded in a file
		PrintWriter answer = new PrintWriter(new File(this.outFile));
		String s = "";
		for (int j = 0; j < result.size(); j++) {
			StringTokenizer st = new StringTokenizer(result.get(j).getIdTask(),
					".");
			String temp = st.nextToken() + " " + result.get(j).getIdTask()
					+ " " + result.get(j).getWordSense().getIDS();
			s += temp;
			s += "\n";
		}
		answer.write(s);
		answer.flush();
		answer.close();
	}

	// Used to write the results of the tests execution time Executive
	@Override
	public String toString() {
		// Short version
		return this.windowSize + ","
				+ dictFile.substring(dictFile.lastIndexOf('/') + 1);
	}

}
