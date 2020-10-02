package Proj;

import java.util.*;

public class CostedPath<T>
{
    //cost in peters notes = length here
    //GraphLinkAL in peters notes = GraphLink here

    public int pathCost =0;
    public List<GraphNodeAL2<?>> pathList = new ArrayList<>();

    private GraphNodeAL2<T> start;
    private GraphNodeAL2<T> end;
    private List<GraphNodeAL2<T>> path; //list of nodes connecting to make the path

    public CostedPath(GraphNodeAL2<T> start, GraphNodeAL2<T> end) {
        this.start = start;
        this.end = end;
    }


//    public static <T> CostedPath findCheapestPathDijkstra(GraphNodeAL2<?> startNode, T lookingfor)
//    {
//        CostedPath cp = new CostedPath; //creates result object for cheapest path
//        List<GraphNodeAL2 <?>> encountered = new ArrayList<>(), unencountered = new ArrayList<>(); //creates encountered and unencountered lists
//        startNode.nodeValue = 0; //sets the start node value to 0
//        unencountered.add(startNode); //adds the starting node as the only value in the unencoutered list to start
//        GraphNodeAL2<?> currentNode;
//
//
//        do { //Loop until unencountered list is empty
//            currentNode = unencountered.remove(0); //get the first unencountered node (sorted list, so will have lower value)
//            encountered.add(currentNode); //Record current node in encountered list
//
//            if(currentNode.equals(lookingfor)) //Found goal - assemble path list back to start and return it
//            {
//                cp.pathList.add(currentNode); //Add the current (goal) node to the result list (only element)
//                cp.pathCost = currentNode.nodeValue; //The total cheapest path cost is the node value of the current/goal node
//
//            while (currentNode != startNode) //while we're not back to the start node...
//            {
//                boolean foundPrevPathNode = false; //use a flag to identify when the previous path node is identified
//                for(GraphNodeAL2<?> n : encountered) //for each node in the encountered list
//                {
//                    for (GraphLink e : n.adjList) //for each edge from that node
//                    {
//                        if(e.destNode == currentNode && currentNode.nodeValue - e.length == n.nodeValue)
//                        {//if that edge links to the current node and the difference in node values is the cost of the edge -> found path!
//                            cp.pathList.add(0, n); //add the identified path node to the front of the result list
//                            currentNode = n; //move the currentNode reference back to the identified path node
//                            foundPrevPathNode = true; //set the flag to break the outer loop
//                            break; //We've found the correct previous path node and moved the currentNode reference back to it so break the inner loop
//                        }
//
//                        if(foundPrevPathNode)
//                            break; //we've identified the previous path node so break the inner loop to continue
//                    }
//                }
//            }
//
//            //reset the node values for all nodes to (effectively) infinity se we can search again (leave no footprint!)
//            for(GraphNodeAL2<?> n : encountered) n.nodeValue = Integer.MAX_VALUE;
//            for(GraphNodeAL2<?> n : unencountered) n.nodeValue = Integer.MAX_VALUE;
//
//            return cp; //The costed (cheapest) path has been assembled, so return it!
//        }
//
//            //we're not at the goal node yet, so...
//            for (GraphLink e : currentNode.adjList) //for each edge/link from the current node...
//            {
//                if(!encountered.contains(e.destNode)) //if the node it leads has not yet been encountered (i.e proceed)
//                {
//                    //update the node value at the end of the edge to the minimum of ts current value or the total of
//                    //the current node's value plus the cost of the edge
//                    e.destNode.nodeValue = Integer.min(e.destNode.nodeValue, currentNode.nodeValue+e.length);
//                    unencountered.add(e.destNode);
//                }
//            }
//            Collections.sort(unencountered, (n1, n2)-> n1.nodeValue - n2.nodeValue); //sort in ascending node value order
//        }
//        while (!unencountered.isEmpty());
//        return null; //no path found, so return null
//    }



    public void breadthFirstSearchAlg() {
    if (start == null || end == null)
    {
        return;
    }

    Queue<GraphNodeAL2<T>> nodeList = new LinkedList<>();//nodes you'll look from
    Set<GraphNodeAL2<T>> encountered = new HashSet<>();

    Map<GraphNodeAL2<T>, GraphNodeAL2<T>> hashmap = new HashMap<>();

    nodeList.add(start);
    while (!nodeList.isEmpty())
    {
        GraphNodeAL2<T> currentNode = nodeList.poll(); //current node = node beginning of node list
        if (currentNode.equals(end)) {
            break;
        }

        //
        for (GraphLink edge : currentNode.getAdjList()) {
            GraphNodeAL2<T> nextNode = (GraphNodeAL2<T>) edge.getDestinationNode();
            if (!encountered.contains(nextNode)) {
                encountered.add(nextNode);
                nodeList.add(nextNode);
                hashmap.put(nextNode, currentNode);
            }
        }
    }

    // creates path
    path = getListOfNodes(hashmap, start, end);
}

    //shortest path
    public void dijkstrasAlg(GraphNodeAL2<T>[] graph)
    {
        Queue<GraphNodeAL2<T>> list = new LinkedList<>();
        Map<GraphNodeAL2<T>, GraphNodeAL2<T>> nodeMap = new HashMap<>(); //to show which nodes are connected

        //this is to get the total length of the paths
        Map<GraphNodeAL2<T>, Integer> length = new HashMap<>();

        for (GraphNodeAL2<T> node : graph)
        {
            //sets nodes length to infinite
            length.put(node, Integer.MAX_VALUE);
        }

        length.put(start, 0);

        //add the start node to list
        list.add(start);

        while (list.size() != 0)
        {
            //removes head
            GraphNodeAL2<T> current = list.poll();

            // go through each edge and test
            for (GraphLink edge : current.getAdjList())
            {
                GraphNodeAL2 nextNode = edge.getDestinationNode();
                int nextLength = edge.getLength();

                if (length.get(current) + nextLength < length.get(nextNode))
                {//if its shorter then set the lenth of the new node
                    // set the new cost of the node
                    length.put(nextNode, length.get(current) + nextLength);
                    nodeMap.put(nextNode, current);
                    list.add(nextNode);
                }
            }
        }

        // highlight the path
        path = getListOfNodes(nodeMap, start, end);
    }

    private List<GraphNodeAL2<T>> getListOfNodes(Map<GraphNodeAL2<T>, GraphNodeAL2<T>> map, GraphNodeAL2<T> start, GraphNodeAL2<T> end) {
        GraphNodeAL2<T> currentNode = end;
        List<GraphNodeAL2<T>> listOfNodes = new ArrayList<>();
        while (!currentNode.equals(start))
        {
            listOfNodes.add(currentNode);
            currentNode = map.get(currentNode);
        }
        return listOfNodes;
    }

    public static List<GraphNodeAL2<?>> addPathsTogether(List<List<GraphNodeAL2<?>>> paths) {
        List<GraphNodeAL2<?>> finalPath = new ArrayList<>(paths.get(0));

        for (int i = 1; i < paths.size(); i++)
        {
            finalPath.addAll(paths.get(i));
        }
        return finalPath;
    }

    //Getters and Setters
    public GraphNodeAL2<T> getStart() {
        return start;
    }

    public void setStart(GraphNodeAL2<T> start) {
        this.start = start;
    }

    public GraphNodeAL2<T> getEnd() {
        return end;
    }

    public void setEnd(GraphNodeAL2<T> end) {
        this.end = end;
    }

    public List<GraphNodeAL2<T>> getPath() {
        return path;
    }


}

