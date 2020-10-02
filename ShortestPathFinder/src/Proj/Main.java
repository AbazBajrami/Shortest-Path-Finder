package Proj;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static javafx.application.Application.launch;

public class Main extends Application
{
    public static Stage ps;


    //START METHOD
    @Override
    public void start(Stage primaryStage) throws Exception{
        createNodes();
        Parent root = FXMLLoader.load(getClass().getResource("../Proj/Map.fxml")); //Open Login Page
        primaryStage.setScene(new Scene(root)); //Set the Scene
        primaryStage.show(); //Show Scene
        ps = primaryStage;
        //comment 3 : just calling createNodes, so that the nodes are created

        //added this
        createLandmarks();


        //comment 4: at this point you will have a list of GraphNodes called listOfLandmarks populated with the GraphNodes
    }

    //Main
    public static void main(String[] args)
    {
        launch(args);


    }


    //creates Landmarks i need to add graphics on the map to show these
    public void createLandmarks() {
        // Landmark(String name, float xCoord, float yCoord, int cultureVal)
        new Landmark("Vatican City", 154, 215, 8);
        new Landmark("Pantheon", 924, 509, 4);
        new Landmark("Colloseum", 1399, 856, 8);
        new Landmark("Castel St. Angelo", 535, 296, 7);
        new Landmark("Train Station", 1731, 396, 1);


    }

    public static ArrayList<GraphNodeAL2<String>> createNodes() {
        GraphNodeAL2<String> vat = new GraphNodeAL2<>("Vatican City", 154, 215);
        GraphNodeAL2<String> col = new GraphNodeAL2<>("Colosseum", 399, 156);
        GraphNodeAL2<String> pan = new GraphNodeAL2<>("Pantheon", 424, 209);
        GraphNodeAL2<String> cas = new GraphNodeAL2<>("Castel St. Angelo", 535, 296);
        GraphNodeAL2<String> tra = new GraphNodeAL2<>("Train Station", 231, 396);
        col.connectToNodeUndirected(pan, 2000);
        col.connectToNodeUndirected(vat, 4200);
        col.connectToNodeUndirected(cas, 3000);
        vat.connectToNodeUndirected(cas, 1500);
        cas.connectToNodeUndirected(tra, 3600);

        ArrayList<GraphNodeAL2<String>> nodes = new ArrayList();
        nodes.add(vat);
        nodes.add(col);
        nodes.add(pan);
        nodes.add(cas);
        nodes.add(tra);

        return nodes;
//................this works .....................................................
//        System.out.println("The cheapest path from Vatican City to Colosseum");
//        System.out.println("using Dijkstra's algorithm:");
//        System.out.println("-------------------------------------");
//        CostedPath cpa = CostedPath.findCheapestPathDijkstra(vat, col.data);
//
//        for(GraphNodeAL2<?> n : cpa.pathList)
//        System.out.println(n.data);
//        System.out.println("\nThe total path cost is: " + cpa.pathCost);
        //..................................................................

//           CostedPath cpa = CostedPath.findCheapestPathDijkstra(GraphNodeAL2(start), end); //have to pass user input to var start and end
//            System.out.println("The cheapest path from" + start + " to" + end);
//            System.out.println("using Dijkstra's algorithm:");
//            System.out.println("-------------------------------------");
//
//            for(GraphNodeAL2<?> n : cpa.pathList)
//            System.out.println(n.data);
//            System.out.println("\nThe total path cost is: " + cpa.pathCost);

    }

    //SAVE
    @SuppressWarnings("unchecked")
    public static void save() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter("landmarks.xml"));
        out.writeObject(MapController.listOfLandmarks);
        out.close();
    }

    //LOAD
    @SuppressWarnings("unchecked")
    public static void load() throws Exception
    {
        XStream xstream = new XStream(new DomDriver());

        ObjectInputStream is = xstream.createObjectInputStream(new FileReader("landmarks.xml"));
        is.close();

    }
}











