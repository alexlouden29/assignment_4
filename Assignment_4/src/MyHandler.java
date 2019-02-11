import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyHandler extends DefaultHandler {
		
	private boolean print = false;
	private String name = "";
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(qName.contentEquals("serial") || qName.contentEquals("visible-string") || qName.contentEquals("unsigned")){
			print = true;
			name = qName;
		}
	}
	
	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		if(print) {
			System.out.println(name + ": " + new String(ch, start, length));
			print = false;
			name = "";
		}
	}
}
