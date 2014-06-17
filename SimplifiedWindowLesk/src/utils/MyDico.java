package utils;

import java.util.ArrayList;
import java.util.HashMap;

import lesk.Global;

import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import Parser.ParserAdapted;

public class MyDico {

	private HashMap<String, ArrayList<MyWordSense>> dico;
	
	// R sum two letters in the character's characteristics of this dictionary:
	// All relations (R) / classic (c)
	// A priori (i) / post (.)
	// See the buildShortName method
	
	private String shortName;
	
	public MyDico(String file) {
		this.dico = new HashMap<String, ArrayList<MyWordSense>>();
		try {
			XMLReader saxReader = XMLReaderFactory.createXMLReader();
			saxReader.setContentHandler(new ParserAdapted(dico));
			saxReader.parse(file);
		} catch(Throwable t) {
			t.printStackTrace();
		}
		buildShortName(file);
	}

	public String getShortName() { return shortName; }
	
	public ArrayList<MyWordSense> getWordSenses(String lemme, int pos) {
		String l = lemme.toLowerCase();
		char posChar = '\0';
		switch (pos) {
		case Global.NOUN:      { posChar = 'n'; break; }
		case Global.VERB:      { posChar = 'v'; break; }
		case Global.ADJECTIVE_SATELLITE:
		case Global.ADJECTIVE: { posChar = 'a'; break; }
		case Global.ADVERB:    { posChar = 'r'; break; }
		}
		try {
		return new ArrayList<MyWordSense>(this.dico.get(l + "%" + posChar));
		} catch (Exception ex){
			return new ArrayList<MyWordSense>();
		}
	}

	private void buildShortName(String file) {
		String s = file.substring(file.lastIndexOf('/') + 1);
		char[] name = {'d', '.', '.'};
		
		if (s.contains("firstSense")) {
			name[1] = 'f';
			name[2] = 's';
		}
		if (s.contains("classic")) {
			name[1] = 'c';
		}
		if (s.contains("allRelations")) {
			name[1] = 'R';
		}
		if (s.contains("apriori")) {
			name[2] = 'i';
		}
		shortName = String.copyValueOf(name);
	}
}