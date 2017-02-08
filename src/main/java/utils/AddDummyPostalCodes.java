package utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * Add dummy postal codes to lanes which have no postal codes.
 * 
 * @author abhinav.sunderrajan
 * 
 *         This a change I have made in branch testing.
 *
 */
public class AddDummyPostalCodes {

    private static SAXReader SAX_READER = new SAXReader();
    private static int dummyPostalCode = 1000000;

    public static void main(String[] args) throws DocumentException, IOException {

	Document originalRoadNetwork = SAX_READER
		.read("file:///C:/Users/abhinav.sunderrajan/Desktop/subnw/[03]road_network.xml");

	// The new road network which adds dummy postal codes to lanes with no
	// PC.
	OutputFormat format = OutputFormat.createPrettyPrint();

	for (Iterator<?> i = originalRoadNetwork.getRootElement().elementIterator("lane"); i.hasNext();) {
	    Element lane = (Element) i.next();
	    Element tag = lane.element("tag");
	    if (tag.elements().size() == 0) {
		tag.addElement("pc").addText(String.valueOf(dummyPostalCode));
		dummyPostalCode++;
	    }

	}

	// This is the fix1 I have added for branch hotfix3
	System.out.println("Finished adding postal codes to all lanes");
	FileOutputStream fos = new FileOutputStream("[03]road_network_pc.xml");
	XMLWriter writer = new XMLWriter(fos, format);
	writer.write(originalRoadNetwork);
	writer.flush();
	// This is the fix2 I have added for branch hotfix3

    }

}
