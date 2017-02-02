package roadnetwork;

public class Road {

    private int roadId;
    private Integer beginNodeId;
    private Integer endNodeId;
    private double length;

    /**
     * @param roadId
     * @param beginNode
     * @param endNode
     */
    public Road(int roadId, Integer beginNode, Integer endNode) {
	this.roadId = roadId;
	this.beginNodeId = beginNode;
	this.endNodeId = endNode;
    }

    @Override
    public boolean equals(Object obj) {
	if (!(obj instanceof Road))
	    return false;
	if (obj == this)
	    return true;

	// Return true if the id of the begin and end nodes are the same.
	return (this.beginNodeId.equals(((Road) obj).beginNodeId) && this.endNodeId.equals(((Road) obj).endNodeId));

    }

    @Override
    public String toString() {
	return this.roadId + "";
    }

    /**
     * @return the beginNode
     */
    public Integer getBeginNodeId() {
	return beginNodeId;
    }

    /**
     * @return the endNode
     */
    public Integer getEndNodeId() {
	return endNodeId;
    }

    /**
     * @return the length
     */
    public double getLength() {
	return length;
    }

    /**
     * @param length
     *            the length to set
     */
    public void setLength(double length) {
	this.length = length;
    }

}
