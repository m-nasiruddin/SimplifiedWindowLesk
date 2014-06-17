package context;

import java.util.ArrayList;

public class Document {
	
	private String label;              // Identifier of this document
	private ArrayList<Word> words;     // Contains all the words of this document
	private int size;                  // Number of words in the document
	
	// Indicates whether the text of this document Ã © tA © Fully © Parsa.
	// Is initially false, and as soon it is set Ã s true, it is no longer possible to add this word Ã document.
	private boolean parsingCompleted;
	
	public Document(String label) {
		this.label = label;
		this.words = new ArrayList<Word>();
		this.size = 0;
		this.parsingCompleted = false;
	}
	
	public String getLabel() { return label; }
	public int    size()     { return size;  }
	
	public Word getWord(int i) {
		return words.get(i);
	}
	
	public boolean addWord(Word w) {
		// Addition of the word w, unless the parsing is completed ©
		return (!parsingCompleted && this.words.add(w));
	}
	
	public void setCompleted() {
		parsingCompleted = true;
		size = words.size();     // Size is da © finitive
	}
	
	// TODO better understand the sequence of documents and opening / closing thread (see newspaper May 5)
	public void analyse(int windowSize, String dictName, long combinationMax, int nbThreads) {
		
		Thread threads[] = new Thread[nbThreads];
		
		// For all the words in the text
		int i = 0;
		while (i < size) {
			for (int t = 0; t < nbThreads; t++) {
				if (threads[t] == null || (!threads[t].isAlive() && i < size)) {
					// Building a window around the current word, then analysis (DA © sambiguÃ tion ¯)
				    threads[t] = new Thread(new Window(this, i, windowSize, combinationMax, dictName));
				    threads[t].start();
				    i++;
				}
			}
			for (int t = 0; t < nbThreads; t++) {
			    try {
			    	// DÃ © e © lasted finish a maximum waiting can resume from other threads
			    	threads[t].join(2000);
			    } catch (InterruptedException e) {
			    	e.printStackTrace();
			    }
			}
		}
	}
}