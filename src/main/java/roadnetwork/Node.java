package roadnetwork;

import java.util.HashSet;
import java.util.Set;

public class Node {
    private Integer nodeId;
    private double x;
    private double y;
    private Set<Road> outRoads = new HashSet<Road>();
    private Set<Road> inRoads = new HashSet<Road>();

    /**
     * A road node.
     * 
     * @param nodeId
     * @param x
     * @param y
     */
    public Node(int nodeId, double x, double y) {
	this.nodeId = nodeId;
	this.x = x;
	this.y = y;
    }

    /**
     * @return the x
     */
    public double getX() {
	return x;
    }

    /**
     * @return the y
     */
    public double getY() {
	return y;
    }

    /**
     * @return the outRoads
     */
    public Set<Road> getOutRoads() {
	return outRoads;
    }

    /**
     * @return the inRoads
     */
    public Set<Road> getInRoads() {
	return inRoads;
    }

    @Override
    public int hashCode() {
	return nodeId;
    }

    @Override
    public boolean equals(Object obj) {
	if (!(obj instanceof Node))
	    return false;
	if (obj == this)
	    return true;
	return this.nodeId.equals(((Node) obj).nodeId);

    }

    @Override
    public String toString() {
	return nodeId + "";
    }

    /**
     * @return the nodeId
     */
    public Integer getNodeId() {
	return nodeId;
    }

}
