package context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class WordIndex {
	BufferedReader br = null;
	HashMap<String, Integer> hashmap = new HashMap<String, Integer>();
	public int max = 0;

	private static WordIndex instance;

	public void init(String path) {

		try {
			// "resources/wordnet-all-words-fine-extended.xml.trans"
			br = new BufferedReader(new FileReader(path));
			String line = br.readLine();

			while ((line = br.readLine()) != null) {
				String[] entry = line.split("\t");
				if (entry.length >= 2) {
					Integer value = Integer.valueOf(entry[1]);
					hashmap.put(entry[0], value);
					max = (value > max) ? value : max;
				}
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private WordIndex() {
	}
	
	public static WordIndex getInstance(){
		if(instance==null){
			instance = new WordIndex();
		}
		return instance;
	}
	
	public synchronized Integer put(String key){
		if(!hashmap.containsKey(key)){
			max++;
			hashmap.put(key, max);
			return max;
		} else {
			return hashmap.get(key);
		}
	}
	
	public synchronized Integer get(String key){
		return hashmap.get(key);
	}
	
	public synchronized int getMax(){
		return max;
	}
	
}