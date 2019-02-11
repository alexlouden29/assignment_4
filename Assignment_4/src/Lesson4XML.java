import java.io.File;
import java.util.List;

import javax.xml.parsers.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Test different parser types on a Job Result file.  File must have only one of each desired tagName to work without
 * redesign.
 * @author alexl
 *
 */

public class Lesson4XML {

	private static final File file = new File("resources/JobResult_UCSDExt.xml");
	private static final String serialStr = "serial";
	private static final String visibleStr = "visible-string";
	private static final String unsignedStr = "unsigned";
	
	public static void main(String[] args) throws Exception {

		System.out.println("Results of XML Parsing using DOM Parser:");
		testDOMParser(file);
		
		System.out.println("Results of XML Parsing using SAX Parser:");
		testSAXParser(file);
		
		System.out.println("Results of XML Parsing using XPath:");
		testXPATHParser(file);
		
		System.out.println("All Done!");
		
		

	}
	
	//Test using DOM Parser
	private static void testDOMParser(File file) throws Exception {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.parse(file);
		
		//Get Note Lists
		NodeList serialList = doc.getElementsByTagName(serialStr);
		NodeList visibleList = doc.getElementsByTagName(visibleStr);
		NodeList unsignedList = doc.getElementsByTagName(unsignedStr);
		
		//Get Values
		Node serial = (Element)serialList.item(0);
		Node visible = (Element)visibleList.item(0);
		Node unsigned =(Element)unsignedList.item(0);
		
		//Display
		Node[] values = {serial, visible, unsigned};
		for(Node val : values) {
			System.out.println(val.getNodeName() + ": " + ((Text)val.getFirstChild()).getData().trim() );
		}
		
	}
	
	private static void testSAXParser(File file) throws Exception {
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		parser.parse(file, new MyHandler());
	}
	
	private static void testXPATHParser(File file) throws Exception {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.parse(file);
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		System.out.println(serialStr + ": " + xpath.evaluate("/jobresult/serial",  doc));
		System.out.println(visibleStr + ": " + xpath.evaluate("/jobresult/data/visible-string",  doc));
		System.out.println(unsignedStr + ": " + xpath.evaluate("/jobresult/data/structure/unsigned",  doc));
		
	}
	
	


}
