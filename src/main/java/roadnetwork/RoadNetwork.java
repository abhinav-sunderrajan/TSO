package roadnetwork;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class RoadNetwork {

    public Map<Integer, Node> allNodesMap;
    public Map<Integer, Road> allRoadsMap;

    public RoadNetwork(String filename) throws DocumentException {
	allNodesMap = new HashMap<Integer, Node>();
	allRoadsMap = new HashMap<Integer, Road>();
	loadRoadsAndNodes(filename);

    }

    private void loadRoadsAndNodes(String filename) throws DocumentException {
	SAXReader SAX_READER = new SAXReader();
	Document document = SAX_READER.read(filename);

	for (Iterator<?> i = document.getRootElement().elementIterator("edge"); i.hasNext();) {
	    Element edgeElem = (Element) i.next();
	    Integer roadlinkIId = Integer.parseInt(edgeElem.attributeValue("roadLinkIID"));
	    Integer beginNodeId = Integer.parseInt(edgeElem.element("fromNodeIID").getStringValue());
	    Integer endNodeId = Integer.parseInt(edgeElem.element("toNodeIID").getStringValue());
	    double length = Double.parseDouble(edgeElem.element("length").getStringValue());
	    Road road = new Road(roadlinkIId, beginNodeId, endNodeId);
	    road.setLength(length);
	    allRoadsMap.put(roadlinkIId, road);
	}

	for (Iterator<?> i = document.getRootElement().elementIterator("node"); i.hasNext();) {
	    Element nodeElem = (Element) i.next();
	    double x = Double.parseDouble(nodeElem.element("location").element("lon").getStringValue());
	    double y = Double.parseDouble(nodeElem.element("location").element("lat").getStringValue());
	    Integer nodeIId = Integer.parseInt(nodeElem.attributeValue("nodeIID"));
	    Node node = new Node(nodeIId, x, y);

	    for (Iterator<?> i1 = nodeElem.element("incoming").elementIterator("edgeIID"); i1.hasNext();) {
		Element edgeIId = (Element) i1.next();
		Integer roadIId = Integer.parseInt(edgeIId.getStringValue());
		Road incoming = allRoadsMap.get(roadIId);
		node.getInRoads().add(incoming);
	    }

	    for (Iterator<?> i1 = nodeElem.element("outgoing").elementIterator("edgeIID"); i1.hasNext();) {
		Element edgeIId = (Element) i1.next();
		Integer roadIId = Integer.parseInt(edgeIId.getStringValue());
		Road outGoing = allRoadsMap.get(roadIId);
		node.getOutRoads().add(outGoing);
	    }
	    allNodesMap.put(nodeIId, node);

	}

    }

    /**
     * @return the allNodes
     */
    public Map<Integer, Node> getAllNodes() {
	return allNodesMap;
    }

    public Map<Integer, Road> getAllRoadsMap() {
	return allRoadsMap;
    }

}
