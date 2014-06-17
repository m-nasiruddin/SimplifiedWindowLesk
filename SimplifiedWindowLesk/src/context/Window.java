package context;

import java.util.ArrayList;

import utils.Definition;

public class Window implements Runnable {

	private ArrayList<Word> words; // Words this window
	private Word centralWord; // Word in the center of this window, the Da �� �� sambigu�� service
	private int centralIndex; // Index centralWord in words

	/*
	 * Repr����sentation de la fen����tre sous forme de chaine :
	 * w##|dXX|d###.s###.t### centralWord (ss|cccccccccccccc) Avec : w## :
	 * taille de la fen����tre dXX : caract����ristiques du dictionnaire
	 * d###.s###.t### : identifiant du mot du corpus ���� d����sambiguiser
	 * centralWord : ce mot ss : nombre de sens de ce mot cccccccccccccc :
	 * nombre de combinaisons de sens de cette fen����tre
	 * 
	 * Exemple : w05|dR.|d005.s034.t004 worthy ( 4| 37632)
	 */
	private String presentation;

	// Number of combinations possible meanings of this window.
	// This is the product of the number of all sense of his words
	private long combinaisons;

	public Window(Document d, int centralWordIndex, int size, long combMax,
			String dictName) {
		words = new ArrayList<Word>();

		// Construction de la fen����tre ���� analyser, autour du mot central
		// sp����cifi����
		centralWord = d.getWord(centralWordIndex);
		for (int i = -(size / 2); i < (size + 1) / 2; i++) {
			try {
				words.add(d.getWord(centralWordIndex + i));
			} catch (Exception e) {
				// L'exception survient dans d.getWord(...), en d����but et en fin
				// de texte,
				// quand la fen����tre d����passe les bords.
				// On ne fait rien, les mots inexistants ne sont simplement pas
				// ajout����s
			}
		}
		centralIndex = 0;
		// No need to shutdown condition is safe because "r centralWord to find the end
		while (words.get(centralIndex).getIdTask() != centralWord.getIdTask()) {
			centralIndex++;
		}

		// Calculating the number of combinations
		//combinaisons = 1;
		//for (int i = 0; i < words.size(); i++) {
		//	combinaisons = combinaisons * words.get(i).nbSenses();
		//}
		
		// Construction of the string representation of PRA ��
		presentation = String.format(
				"w%02d|" + dictName + "|" + centralWord.getIdTask()
						+ " %-20s (%2s|%14s)", size, centralWord.getLabel(),
				centralWord.nbSenses(), combinaisons);
	}

	public Word getCentralWord() {
		return centralWord;
	}

	public void analyse() {
		// S'il y a trop de combinaisons (>=10�������), on ne l'analyse pas, et on
		// choisit aussi le premier sens
		/*if (combinaisons >= combinationMax false) {
			System.err.println(presentation + " > abandon !");
			Random r = new Random(); 
			centralWord.setBestSense(centralWord.getSense(r.nextInt(centralWord.nbSenses())));
		} else {*/
			double startTime = System.currentTimeMillis();

			// analyse(0, new ArrayList<Sense>(), 0);
			int maxs = -1;
			Sense bSense = null;
			int s = 0;
			ArrayList<String> defstr = new ArrayList<String>();
			for (int i = 0; i < words.size(); i++) {
				if (words.get(i) != centralWord) {
					defstr.add(words.get(i).getLemme());
				}
			//}

			int[] def = new int[words.size() - 1];
			WordIndex wi = WordIndex.getInstance();

			for (int win = 0; win < defstr.size(); win++) {
				def[win] = wi.put(defstr.get(win));;
			}
			// for each word of defstr check if in hashmap
			// if yes def[j] = the number from the hashmap
			// else find max number in hashmap
			// add {word, maxnum+1}
			// def[j] = maxnum+1
			Definition d = new Definition(def);

			for (int cs = 0; cs < centralWord.nbSenses(); cs++) {
				Sense p = centralWord.getSense(cs);
				// A chaque tour de boucle, r����initialisation de s et t2
				// for(int i = 0; i < t2.size(); i++) {
				// s += t2.get(i).getDef().getSim(p.getDef());
				s = p.getDef().getSim(d);
				if (s > maxs) {
					maxs = s;
					bSense = centralWord.getSense(cs);
				}
			}
			centralWord.setBestSense(bSense, maxs);
		}
			double analysisTime = System.currentTimeMillis() - startTime;
			try {
				System.err.println(presentation
						+ String.format(" > %11s", analysisTime) + "ms "
						+ ((analysisTime > 1000.0) ? "long! " : "      ")
						+ centralWord.getBestSense().getWordSense().getIDS());
			} catch (Exception ex) {
				//ex.printStackTrace();
				System.err.println(presentation
						+ String.format(" > %11s", analysisTime)
						+ "ms "
						+ ((analysisTime > 1000.0) ? "long! " : "      "
								+ "Missing assignment"));
			}
	}

	// Parcourt r����cursivement toutes les combinaisons de sens possibles pour
	// cette fen����tre de mots.
	// Chaque combinaison est repr����sent����e par un tableau de sens t, et a un
	// score somme.
	// TODO continuer l'explication
	// private void analyse(int rang, ArrayList<Sense> t, int somme) {
	// if (rang < words.size()) {
	// for (int j = 0; j < words.get(rang).nbSenses(); j++) {
	// // A chaque tour de boucle, r����initialisation de s et t2
	// int s = somme;
	// ArrayList<Sense> t2 = new ArrayList<Sense>(t);
	// Sense p = words.get(rang).getSense(j);
	// //for(int i = 0; i < t2.size(); i++) {
	// //s += t2.get(i).getDef().getSim(p.getDef());
	// ArrayList<String> defstr = new ArrayList<String>();
	// for(int i; i<words.size();i++){
	// if(i!= rang){
	// defstr.add(words.get(i).getLemme());
	// }
	// }
	// Definition d = new Definition();
	// s+=p.getDef().getSim(d);
	//
	// //}
	// // t2.add(p);
	// // this.analyse(rang + 1, t2, s);
	// }
	// } else {
	// if (somme > centralWord.getBestScore()) {
	// Sense newBest = t.get(centralIndex);
	// centralWord.setBestSense(newBest, somme);
	// }
	// }
	// }

	@Override
	public void run() {
		analyse();
	}
}