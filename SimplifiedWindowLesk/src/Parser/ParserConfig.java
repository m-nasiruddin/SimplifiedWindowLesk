package Parser;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.LocatorImpl;

public class ParserConfig implements ContentHandler {

	private String task;
	private String inFile;
	private String outFile;
	private String dictFile;
	private int    windowSize;
	private long   combinationMax;
	private int    nbThreads;
	

    public ParserConfig() {
    	super();
    	task           = "";
    	inFile         = "";
    	outFile        = "";
    	dictFile       = "";
    	windowSize     = 0;
    	combinationMax = 0;
    	nbThreads      = 0;
        // We define the default locator.
        locator = new LocatorImpl();
    }

	public String getTask()           { return task; }
	public String getInFile()         { return inFile; }
	public String getOutFile()        { return outFile; }
	public String getDictFile()       { return dictFile; }
	public int    getWindowSize()     { return windowSize; }
	public long   getCombinationMax() { return combinationMax; }
	public int    getNbThreads()      { return nbThreads; }

    
    /**
     * Definition locator that allows at any time during the scan, locate the processing in the stream. The default locator indicates, for example, line number and character number on the line.
     * @ Author smeric
     * @ Param value the locator to use.
     * @ See # org.xml.sax.ContentHandler setDocumentLocator (org.xml.sax.Locator)
     */
    @Override
	public void setDocumentLocator(Locator value) {
        locator =  value;
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
     * @ Throws SAXException if the problem does not allow quelquonque consider parsing the document as being complete.
     * @ See # org.xml.sax.ContentHandler endDocument ()
     */
    @Override
	public void endDocument() throws SAXException {
    }

    /**
     * Beginning of treatment in a namespace.
     * @ Param prefix used for the namespace in this part of the tree.
     * @ Param URI namespace.
     * @ See # org.xml.sax.ContentHandler startPrefixMapping (java.lang.String, java.lang.String)
     */
    @Override
	public void startPrefixMapping(String prefix, String URI) throws SAXException {

    }

    /**
     * End processing namespace.
     * @ Param prefix the prefix was chosen to open the treatment of naming space.
     * @ See # org.xml.sax.ContentHandler endPrefixMapping (java.lang.String)
     */
    @Override
	public void endPrefixMapping(String prefix) throws SAXException {
            
    }

    /**
     * Event received each time the parser encounters an opening xml tag.
     * @ Param namespaceURI the URL of the namespace.
     * @ Param localName the local name of the tag.
     * @ Param tag name rawName version 1.0 <code> namespaceURI + ":" + localName </ code>
     * @ Throws SAXException if the tag does not correspond to what is expected, such as non-compliance with a dtd.
     * @ See # org.xml.sax.ContentHandler startElement (java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    @Override
	public void startElement(String nameSpaceURI, String localName, String rawName, Attributes attributs) throws SAXException {
    	if (localName == "param") {
    		String name = attributs.getValue("name");
    		String value = attributs.getValue("value");
    		if (name.equals("task")) {
    			this.task = value;
    		} else if(name.equals("inFile")) {
    			this.inFile = value;
    		} else if(name.equals("outFile")) {
    			this.outFile = value;
    		} else if(name.equals("dictFile")) {
    			this.dictFile = value;
    		} else if(name.equals("windowSize")) {
    			this.windowSize = Integer.parseInt(value);
    		} else if(name.equals("combinationMax")) {
    			this.combinationMax = Long.parseLong(value);
    		} else if(name.equals("nbThreads")) {
    			this.nbThreads = Integer.parseInt(value);
    		}
    	}
    }

    /**
     * Each event received a closing tag.
     * @ See # org.xml.sax.ContentHandler endElement (java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
	public void endElement(String nameSpaceURI, String localName, String rawName) throws SAXException {
    	
    }

    /**
     * Event received every time the parser encounters characters (between tags).
     * @ Param ch the characters themselves.
     * @ Param start the rank of the first character to treat effectively.
     * @ Param end the position of the last character to deal effectively
     * @ See # org.xml.sax.ContentHandler characters (char [], int, int)
     */
    @Override
	public void characters(char[] ch, int start, int end) throws SAXException {
    	
    }

    /**
     * Received whenever space characters can be ignored for the purposes of XML. That is to say that this event is sent to several successive spaces, tabs, and carriage returns are succedants well as any combination of these three types of occurrence.
     * @ Param ch the characters themselves.
     * @ Param start the rank of the first character to treat effectively.
     * @ Param end the position of the last character to deal effectively
     * @ See # org.xml.sax.ContentHandler ignorableWhitespace (char [], int, int)
     */
    @Override
	public void ignorableWhitespace(char[] ch, int start, int end) throws SAXException {
//        System.err.println("espaces inutiles rencontres : ..." + new String(ch, start, end) +  "...");
    }

    /**
     * Encounters an operation instruction.
     * @ Param target the target of the operation command.
     * @ Param data values ​​associated with this target. In general, it is presented as a series of name / value pairs.
     * @ See # org.xml.sax.ContentHandler ProcessingInstruction (java.lang.String, java.lang.String)
     */
    @Override
	public void processingInstruction(String target, String data) throws SAXException {
//        System.err.println("Instruction de fonctionnement : " + target);
//        System.err.println("  dont les arguments sont : " + data);
    }

    /**
     * Received every time a tag is avoided in the treatment of a problem because not blocked by the parser. For my part I do not think you need it in your treatments.
     * @ See # org.xml.sax.ContentHandler skippedEntity (java.lang.String)
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
