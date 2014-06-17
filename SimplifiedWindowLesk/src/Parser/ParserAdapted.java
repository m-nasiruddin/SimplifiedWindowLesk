package Parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.LocatorImpl;

import utils.MyWordSense;


public class ParserAdapted implements ContentHandler {
	
	private HashMap<String, ArrayList<MyWordSense>> dico;
	private String word;
	
	private ArrayList<MyWordSense> mws;
	private MyWordSense mw;
	
	private boolean ids, def;
	
    public ParserAdapted(HashMap<String, ArrayList<MyWordSense>> c) {
    	super();
    	this.dico = c;
    	ids = false;
    	def = false;
        // We define the default locator.
        locator = new LocatorImpl();
    }

    /**
     * Definition locator that allows at any time during the scan, locate the processing in the stream. The default locator indicates,
     * for example, line number and character number on the line.
     * @author smeric
     * @param value le locator a utiliser.
     * @see org.xml.sax.ContentHandler#setDocumentLocator(org.xml.sax.Locator)
     */
    @Override
	public void setDocumentLocator(Locator value) {
        locator = value;
    }

    /**
     * Event sent at boot parse the xml stream.
     * @ Throws SAXException if quelquonque problem does not allow to get into parsing the document.
     * @ See # org.xml.sax.ContentHandler startDocument ()
     */
    @Override
	public void startDocument() throws SAXException {
    }

    /**
     * Event sent at the end of the xml flow analysis.
     * @ throws SAXException if the problem does not allow quelquonque
     * Consider parsing the document as being complete.
     * @ See # org.xml.sax.ContentHandler endDocument ()
     */
    @Override
	public void endDocument() throws SAXException {
    }

    /**
     * Debut de traitement dans un espace de nommage.
     * @param prefixe utilise pour cet espace de nommage dans cette partie de l'arborescence.
     * @param URI de l'espace de nommage.
     * @see org.xml.sax.ContentHandler#startPrefixMapping(java.lang.String, java.lang.String)
     */
    @Override
	public void startPrefixMapping(String prefix, String URI) throws SAXException {

    }

    /**
     * Fin de traitement de l'espace de nommage.
     * @param prefixe le prefixe choisi a l'ouverture du traitement de l'espace nommage.
     * @see org.xml.sax.ContentHandler#endPrefixMapping(java.lang.String)
     */
    @Override
	public void endPrefixMapping(String prefix) throws SAXException {
            
    }

    /**
     * Evenement recu a chaque fois que l'analyseur rencontre une balise xml ouvrante.
     * @param nameSpaceURI l'url de l'espace de nommage.
     * @param localName le nom local de la balise.
     * @param rawName nom de la balise en version 1.0 <code>nameSpaceURI + ":" + localName</code>
     * @throws SAXException si la balise ne correspond pas a ce qui est attendu,
     * comme par exemple non respect d'une dtd.
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    @Override
	public void startElement(String nameSpaceURI, String localName, String rawName, Attributes attributs) throws SAXException {
    	if (localName == "dict") {
//    		System.err.println("startElement dict");
    		
    	} else if (localName == "word") {
    		word = attributs.getValue(0);
//    		System.err.println("startElement word " + word);
    		mws = new ArrayList<MyWordSense>();
    	} else if (localName == "sense") {
//    		System.err.println("startElement sense");        		
    		mw = new MyWordSense();
    	} else if (localName == "ids") {
    		ids = true;
    	} else if (localName == "def") {
    		def = true;
    	}
    }

    /**
     * Evenement recu a chaque fermeture de balise.
     * @see org.xml.sax.ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
	public void endElement(String nameSpaceURI, String localName, String rawName) throws SAXException {
    	if (localName == "dict") {
//    		System.err.println("endElement dict");
    		
    	} else if (localName == "word") {
//    		System.err.println("endElement word");
    		dico.put(word, mws);
    	} else if (localName == "sense") {
//    		System.err.println("endElement sense");
    		mws.add(mw);
    	} else if (localName == "ids") {
    		ids = false;
    	} else if (localName == "def") {
    		def = false;
    	}
    }

    /**
     * Evenement recu a chaque fois que l'analyseur rencontre des caracteres (entre
     * deux balises).
     * @param ch les caracteres proprement dits.
     * @param start le rang du premier caractere a traiter effectivement.
     * @param end le rang du dernier caractere a traiter effectivement
     * @see org.xml.sax.ContentHandler#characters(char[], int, int)
     */
    @Override
	public void characters(char[] ch, int start, int end) throws SAXException {
    	if (ids) {
    		mw.setIDS(new String(ch, start, end));
    	} else if (def) {
    		mw.setDef(new String(ch, start, end));
    	}
    }

    /**
     * Recu chaque fois que des caracteres d'espacement peuvent etre ignores au sens de
     * XML. C'est a dire que cet evenement est envoye pour plusieurs espaces se succedant,
     * les tabulations, et les retours chariot se succedants ainsi que toute combinaison de ces
     * trois types d'occurrence.
     * @param ch les caracteres proprement dits.
     * @param start le rang du premier caractere a traiter effectivement.
     * @param end le rang du dernier caractere a traiter effectivement
     * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
     */
    @Override
	public void ignorableWhitespace(char[] ch, int start, int end) throws SAXException {
//        System.err.println("\tespaces inutiles : ..." + new String(ch, start, end) +  "...");
    }

    /**
     * Rencontre une instruction de fonctionnement.
     * @param target la cible de l'instruction de fonctionnement.
     * @param data les valeurs associees a cette cible. En general, elle se presente sous la forme 
     * d'une serie de paires nom/valeur.
     * @see org.xml.sax.ContentHandler#processingInstruction(java.lang.String, java.lang.String)
     */
    @Override
	public void processingInstruction(String target, String data) throws SAXException {
//        System.err.println("\tprocessingInstruction(target = " + target + ",\n\t\tdata = " + data + ")");
    }

    /**
     * Recu a chaque fois qu'une balise est evitee dans le traitement a cause d'un
     * probleme non bloque par le parser. Pour ma part je ne pense pas que vous
     * en ayez besoin dans vos traitements.
     * @see org.xml.sax.ContentHandler#skippedEntity(java.lang.String)
     */
    @Override
	public void skippedEntity(String arg0) throws SAXException {
        // Je ne fais rien, ce qui se passe n'est pas franchement normal.
        // Pour eviter cet evenement, le mieux est quand meme de specifier une dtd pour vos
        // documents xml et de les faire valider par votre parser.              
    }

    @SuppressWarnings("unused")
	private Locator locator;
}