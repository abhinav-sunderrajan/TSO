package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class CreateUserEquilibriumItenary {

    private static final int NUM_OF_TRIPS = 38326;

    public static void main(String[] args) throws IOException {

	OutputFormat format = OutputFormat.createPrettyPrint();
	Document document = DocumentHelper.createDocument();
	Element root = document.addElement("Itineraries");
	root.addAttribute("version", "1.0");
	root.addAttribute("xsi:schemaLocation", "http://xenon.tum-create.edu.sg SEMSim_ItineraryData.xsd");
	root.add(new Namespace("xenon", "http://xenon.tum-create.edu.sg"));
	root.addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");

	// Read jordan's file for the trips.
	BufferedReader br = new BufferedReader(
		new FileReader(new File("C:/Users/abhinav.sunderrajan/Desktop/new_paths/new_paths_2.txt")));
	double incr = 0.0;
	while (br.ready()) {
	    String line = br.readLine();
	    String[] split = line.split(",");
	    StringBuffer buffer = new StringBuffer("");
	    int numOfLinks = 0;

	    for (String str : split) {
		if (str == "" || str == null)
		    break;
		buffer.append(str + ",");
		numOfLinks++;
	    }
	    if (numOfLinks > 5) {
		buffer.deleteCharAt(buffer.length() - 1);
		Element intersectionElem = root.addElement("itinerary");
		Element trip = intersectionElem.addElement("trip");
		trip.addText(buffer.toString());
		String startTime = 8 + String.format("%02d", Math.round(incr));
		if (startTime.equals("860")) {
		    startTime = "900";
		}
		trip.addAttribute("startTime", startTime);
	    }

	    incr += 60.0 / NUM_OF_TRIPS;

	}
	br.close();

	FileOutputStream fos = new FileOutputStream("user_equilibrium_itinerary-v2.xml");
	XMLWriter writer = new XMLWriter(fos, format);
	writer.write(document);
	writer.flush();

    }

}
