package Proj;

public class GraphLink {
    public GraphNodeAL2<?> destNode; //could also store source node of required
    public int length; //Other link attributes could be similarly stored

    public GraphLink(GraphNodeAL2<?> destNode, int length) {
        this.destNode = destNode;
        this.length = length;
    }

    public GraphNodeAL2<?> getDestinationNode() {
        return destNode;
    }

    public void setDestinationNode(GraphNodeAL2<?> destinationNode) {
        this.destNode = destinationNode;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        if (length < 0)
            length = 0;
        this.length = length;
    }

    public String toString() {
        return destNode.getName() + "(" + length + ")";
    }
}
