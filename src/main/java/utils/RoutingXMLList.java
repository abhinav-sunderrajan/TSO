package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class RoutingXMLList {

    /**
     * @param args
     * @throws DocumentException
     * @throws IOException
     */
    public static void main(String[] args) throws DocumentException, IOException {
	SAXReader SAX_READER = new SAXReader();
	Document document = SAX_READER.read("file:///C:\\Users\\abhinav.sunderrajan\\Desktop\\subnw\\[03]routing.xml");
	BufferedWriter bw = new BufferedWriter(new FileWriter(new File("subnw.txt")));
	bw.write("roadlinkid\troadlinkiid\n");
	for (Iterator<?> i = document.getRootElement().elementIterator("edge"); i.hasNext();) {
	    Element edge = (Element) i.next();
	    bw.write(edge.attributeValue("roadLinkID") + "\t" + edge.attributeValue("roadLinkIID") + "\n");
	}
	System.out.println("finish");
	bw.flush();
	bw.close();

    }
}
