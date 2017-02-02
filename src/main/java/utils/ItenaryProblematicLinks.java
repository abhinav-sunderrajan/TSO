package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class ItenaryProblematicLinks {
    private static final int NUM_OF_TRIPS = 41320;

    public static void main(String[] args) throws IOException, DocumentException {

	OutputFormat format = OutputFormat.createPrettyPrint();
	Document document = DocumentHelper.createDocument();
	Element root = document.addElement("Itineraries");
	root.addAttribute("version", "1.0");
	root.addAttribute("xsi:schemaLocation", "http://xenon.tum-create.edu.sg SEMSim_ItineraryData.xsd");
	root.add(new Namespace("xenon", "http://xenon.tum-create.edu.sg"));
	root.addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");

	BufferedWriter bw = new BufferedWriter(new FileWriter(new File("lastLinks.txt")));
	bw.write("lastlinks");
	// Read jordan's file for the trips.
	BufferedReader br = new BufferedReader(
		new FileReader(new File("C:/Users/abhinav.sunderrajan/Desktop/new_paths/new_paths.csv")));
	double incr = 0.0;

	List<Integer> pl = identifyproblematicLinks();

	while (br.ready()) {
	    String line = br.readLine();
	    String[] split = line.split(",");
	    StringBuffer buffer = new StringBuffer("");

	    List<Integer> tripLinks = new ArrayList<>();
	    for (String str : split) {
		if (str == "" || str == null)
		    break;
		buffer.append(str + ",");
		tripLinks.add(Integer.parseInt(str));
	    }

	    List<Integer> inersection = intersection(pl, tripLinks);
	    if (inersection.size() > 0) {
		buffer.deleteCharAt(buffer.length() - 1);
		Element intersectionElem = root.addElement("itinerary");
		Element trip = intersectionElem.addElement("trip");
		trip.addText(buffer.toString());
		String startTime = 8 + String.format("%02d", Math.round(incr));
		if (startTime.equals("860")) {
		    startTime = "900";
		}
		trip.addAttribute("startTime", startTime);
		System.out.println("added trip");
	    }
	    incr += 60.0 / NUM_OF_TRIPS;

	}
	bw.close();
	br.close();

	FileOutputStream fos = new FileOutputStream("user_equilibrium_itinerary-problematic.xml");
	XMLWriter writer = new XMLWriter(fos, format);
	writer.write(document);
	writer.flush();

    }

    public static <T> List<T> intersection(List<T> list1, List<T> list2) {
	List<T> list = new ArrayList<T>();

	for (T t : list1) {
	    if (list2.contains(t)) {
		list.add(t);
	    }
	}

	return list;
    }

    public static List<Integer> identifyproblematicLinks() throws DocumentException {
	SAXReader reader = new SAXReader();
	Document document = reader.read(new File("[04]routing.xml"));
	List<Integer> problematicLinks = new ArrayList<Integer>();

	for (Iterator<?> i = document.getRootElement().elementIterator("edge"); i.hasNext();) {
	    Element edge = (Element) i.next();
	    int roadLinkIID = Integer.parseInt(edge.attributeValue("roadLinkIID"));
	    problematicLinks.add(roadLinkIID);
	}
	return problematicLinks;
    }

}
