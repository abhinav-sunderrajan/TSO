package roadnetwork;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class IntersectionFeederEdges {

    private static RoadNetwork rnw;
    private static final String intersectionFileName = "file:///C:\\Users\\abhinav.sunderrajan\\Desktop\\subnw\\[03]intersection_control_static.xml";
    private static final OutputFormat format = OutputFormat.createPrettyPrint();

    public static void main(String args[]) {

	try {

	    rnw = new RoadNetwork("file:///C:\\Users\\abhinav.sunderrajan\\Desktop\\subnw\\[03]routing.xml");
	    SAXReader SAX_READER = new SAXReader();
	    Document document = SAX_READER.read(intersectionFileName);
	    for (Iterator<?> i = document.getRootElement().elementIterator("Intersection"); i.hasNext();) {
		Element intersectionElem = (Element) i.next();

		for (Iterator<?> i1 = intersectionElem.elementIterator("Phase"); i1.hasNext();) {
		    Element phaseElem = (Element) i1.next();

		    for (Iterator<?> i2 = phaseElem.elementIterator("link"); i2.hasNext();) {
			Element linkElem = (Element) i2.next();
			Integer phaseLinkIId = Integer.parseInt(linkElem.getStringValue());
			Road phaseRoad = rnw.getAllRoadsMap().get(phaseLinkIId);

			if (phaseRoad == null) {
			    System.out.println(phaseLinkIId + " does not exist..");
			    continue;
			}
			StringBuffer buf = new StringBuffer("");
			double sumLength = 0.0;
			while (sumLength <= 150.0) {
			    Node beginNode = rnw.getAllNodes().get(phaseRoad.getBeginNodeId());
			    Set<Road> incomingRoads = beginNode.getInRoads();
			    if (incomingRoads.size() > 0) {
				for (Road in : incomingRoads) {
				    sumLength += in.getLength();
				    buf.append(in.toString() + ",");
				    phaseRoad = in;
				    break;
				}
			    } else {
				break;
			    }
			}

			buf.deleteCharAt(buf.length() - 1);
			linkElem.addAttribute("feeders", buf.toString());
		    }
		}

	    }

	    FileOutputStream fos = new FileOutputStream("[03]intersection_control_dynamic.xml");
	    XMLWriter writer = new XMLWriter(fos, format);
	    writer.write(document);
	    writer.flush();

	} catch (DocumentException e) {
	    e.printStackTrace();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
