package context;

import java.util.ArrayList;

import utils.MyWordSense;

import lesk.Context;
import lesk.Global;

public class Word {
	
	private String label;            // The word itself
	private String lemme;            // Lemma corresponding word
	private String idTask;           // Identifier of the position of the word in the corpus
	private int pos;                 // Part of speech, nature of the word
	private ArrayList<Sense> senses; // Possible meanings of the word
	private Sense bestSense;         // Chosen for the best sense of the word disambiguous station
	private int bestScore;           // Corresponding score in the best sense
	
	private Context context;
	
	public Word(String lemme, String idTask, int pos, Context c) {
		this.label = lemme;
		this.lemme = lemme;
		this.idTask = idTask;
		this.pos = pos;
		this.bestSense = null;
		this.bestScore = -1;
		this.context = c;
		createSenses();
	}
	
	public String getLabel()     { return label; }
	public String getLemme()     { return lemme; }
	public String getIdTask()    { return idTask; }
	public int    getPos()       { return pos; }
	public int    getBestScore() { return bestScore; }
	public Sense  getBestSense() { return bestSense; }
	
	public void  setBestSense(Sense p) {
		this.bestSense = p;
	}
	
	public void  setBestSense(Sense p, int score) {
		this.bestSense = p;
		this.bestScore = score;
	}

	public int nbSenses() {
		return this.senses.size();
	}
	
	public Sense getSense(int i) {
		return this.senses.get(i);
	}

	private void createSenses() {
		senses = new ArrayList<Sense>();
		if (pos == Global.NONE) {
			return;
		}
			
		Sense p;
		ArrayList<MyWordSense> wordSenses = context.getDict().getWordSenses(lemme, pos);
		if (wordSenses == null) {
			return;	
		}
			
		for (int j = 0; j < wordSenses.size(); j++) {
			MyWordSense sense = wordSenses.get(j);
			p = new Sense(lemme, sense, idTask);
			senses.add(p);
		}
	}
}
