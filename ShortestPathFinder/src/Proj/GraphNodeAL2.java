package Proj;

import java.util.ArrayList;
import java.util.List;

public class GraphNodeAL2<T> //T means we can pass it anything
{
    public T data; //landmark is held in data
    public int nodeValue = Integer.MAX_VALUE;
    private String name;
    private int x, y, culturalValue;
    private double mouseX, mouseY;


    //keeping a list of all the direct links to the landmarks/places (what landmarks are bordering this landmark)
    public List<GraphLink> adjList = new ArrayList<>(); //you can use any list implementation
    public GraphNodeAL2(T data) { this.data = data; }

    public GraphNodeAL2(String name, int x, int y)
    {
        setName(name);
        setX(x);
        setY(y);
    }


    public void connectToNodeDirected(GraphNodeAL2<T> destNode, int length)
    {
        adjList.add(new GraphLink(destNode, length));
    }

    public void connectToNodeUndirected(GraphNodeAL2<T> destNode, int length)
    {
        adjList.add(new GraphLink(destNode, length));
        destNode.adjList.add(new GraphLink(this, length));
    }

    public int getX() {
        return x;
    }


    public void setX(int x) {
        if (x < 0)
            return;

        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        if (y < 0)
            return;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() > 24)
            return;
        this.name = name;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<GraphLink> getAdjList() {
        return adjList;
    }


    @Override
    public String toString() {
        String connectingNodesNames = "";
        for(GraphLink edge : adjList)
            connectingNodesNames += edge.toString() + "\n";

        return "Name: " + name + ", Coordinates: (" + x + ", " + y + ") \n" +
                "Connected to: \n" +
                connectingNodesNames;
    }



}
