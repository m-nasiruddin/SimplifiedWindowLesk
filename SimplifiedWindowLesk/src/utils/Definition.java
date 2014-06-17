package utils;

import java.util.StringTokenizer;

public class Definition {

	// This assumes that the implementation Definitions for form are
	// for sorting an array of integers in ascending order
	// these are t gnres Definitions for the creation of the dictionary adquat,
	// See the file ants / dictionaries-Lesk
	
	private int[] def;
	
	public Definition(int[] def){
		this.def = def;
	}
	
	public Definition(String d) {
		StringTokenizer st = new StringTokenizer(d);
		def = new int[st.countTokens()];
		int i = 0;
		while(st.hasMoreTokens()){
			def[i] = Integer.parseInt(st.nextToken());
			i++;
		}
	}
	
	public Definition(int size) {
		def = new int[size];
		for(int i = 0; i < size; i++) {
			def[i] = -1;
		}
	}
	
	public int getSize() {
		return def.length;
	}
	
	public void setElement(int index, int e) {
		def[index] = e;
	}
	
	public int getElement(int index) {
		return def[index];
	}
	
	/**
	* Compute the similarity between this and The definition Specifies dfinition.
	* This is the number of words that are common in both Definitions used for the Dictionary
s	*/
	public int getSim(Definition d) {
		int count = 0;
		int i = 0;
		int j = 0;
		while (i < this.getSize() && j < d.getSize()) {
			if (this.getElement(i) == d.getElement(j)) {
				count++;
				i++;
				j++;
			} else if (this.getElement(i) < d.getElement(j)) {
				i++;
			} else {
				j++;
			}
		}
		return count;
	}
	
	@Override
	public String toString() {
		String s = "";
		for(int i = 0; i < def.length; i++) {
			s += def[i] + " ";
		}
		return s;
	}
}