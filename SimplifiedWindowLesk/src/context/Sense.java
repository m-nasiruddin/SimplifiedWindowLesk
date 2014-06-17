package context;

import utils.Definition;
import utils.MyWordSense;

public class Sense implements Comparable<Object> {
	
	private String label;        // Polys√ word √ © nomic corresponding sense
	private MyWordSense sense;   // Has a particular meaning of a word, and the discovery © finish this sense
	private String idTask;       // Identifies the end of that corpus sense
	
	public Sense(String label, MyWordSense sense, String idTask) {
		this.label  = label;
		this.sense  = sense;
		this.idTask = idTask;
	}
	
	public String      getLabel()     { return label; }
	public MyWordSense getWordSense() { return sense; }
	public String      getIdTask()    { return idTask; }
	
	public Definition getDef() {
		return sense.getDef();
	}
	
	// Use © √ © e to write the file © RA results in the order of words in the corpus
	@Override
	public int compareTo(Object o) {
		Sense s = (Sense)o;
		return this.getIdTask().compareTo(s.getIdTask());
	}
}
