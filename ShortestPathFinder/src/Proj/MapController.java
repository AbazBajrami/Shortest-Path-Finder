package Proj;

import com.sun.javafx.scene.paint.GradientUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.ParsedValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MapController
{
    //INSTANCES
    //map
     @FXML private ImageView imageView;
     @FXML private Image image;
     private Image resetImage;

    //menu
    @FXML private MenuBar menuBar;
    @FXML private Menu file, help, edit;
    @FXML private MenuItem openImage;

    //choice boxes
    @FXML private ComboBox<GraphNodeAL2<Landmark>> startingPoint, endPoint;
    @FXML private TableColumn<Landmark, String> startingPointName, destinationName;

    //comment 1: You should add in an array to hold your GraphNodes, this will be used now and when you create your file
    static ArrayList<GraphNodeAL2<String>> listOfLandmarks = new ArrayList<>();
    //private ArrayList<Landmark> landMList = new ArrayList<>();


    //buttons
    @FXML private Button print, circle, save, delete, load, addLandmark, resetPoints, findPath, find;

    //Text
    @FXML private TextField landmarkName, culturalValue;

    //Marker
    private ArrayList<Integer> coordList = new ArrayList<>(4);
    private int numOfClick = 0;
    private int totalClicks =10;
    private Circle redCircle1, redCircle2;

    //creates list for nodes
    GraphNodeAL2<Color>[] nodes;



    //INIT
    public void initialize()
    {

        listOfLandmarks =  Main.createNodes();

        ObservableList oLandmarkList = FXCollections.observableList((listOfLandmarks));
        startingPoint.setItems(oLandmarkList);
        endPoint.setItems(oLandmarkList);

//        imageView.setOnMouseClicked(e ->
//        {
//            PixelReader pixelReader = imageView.getImage().getPixelReader();
//
//            int x =(int) e.g;
//            int y = (int) e.getY();
//
////            System.out.println("x = " +x );
////            System.out.println("y = " + y);
//
//            // if the start and end points have not been placed yet
//            if (coordList.size()/2 < totalClicks && pixelReader.getColor(x, y).equals(Color.WHITE))
//            {
//                coordList.add(x);
//                coordList.add(y);
//
//                redCircle = new Circle(x, y, 4, Color.RED);
//                redCircle.setTranslateX(x -4);
//                redCircle.setTranslateY(y-4);
//                ((Pane) imageView.getParent()).getChildren().add(redCircle);
//
//                GraphNodeAL2<?> temp = new GraphNodeAL2<>(totalClicks == 0 ? "Start" : "End", x, y);
//                agendaList.add(temp);
//
//            }
//            else if(coordList.size()>=totalClicks)
//            {
//                System.out.println("Total Number of markers exceeded.");
//            }
//            else
//            {
//                System.out.println("Place makrer on a white tiles");
//            }
//        });





//            if (numOfClick == 0) {
//                redCircle = new Circle(e.getX(), e.getY(), 4, Color.RED);
//                //Circle circle = new Circle(e.getSceneX() -15, e.getSceneY() -30, 4, Color.RED);
//                redCircle.setTranslateX(imageView.getLayoutX()); //getting correct values for the image
//                redCircle.setTranslateY(imageView.getLayoutY()); //finding where the image is on the screen
//                Waypoint start = new Waypoint(e.getX(), e.getY());
//                ((Pane) imageView.getParent()).getChildren().addAll(redCircle); //add the circle and text on top of the image
//                System.out.println("Start: " + start.getX() + " : " + start.getY());
//                numOfClick = numOfClick + 1;
//
    }


    //METHODS

    @FXML
    public void reset(ActionEvent e)
    {
        ((Pane) imageView.getParent()).getChildren().removeAll(redCircle1);
        ((Pane) imageView.getParent()).getChildren().remove(redCircle1);


        imageView.setImage(resetImage);
        coordList = new ArrayList<>(4);
    }

    @FXML
    public void makeMarker(MouseEvent e)
    {
        PixelReader pixelReader = imageView.getImage().getPixelReader();

        int x =(int) e.getSceneX();
        int y = (int) e.getSceneY();

        System.out.println("x = " +x );
        System.out.println("y = " + y);

        // if the start and end points have not been placed yet
        if (coordList.size()/2 < totalClicks && pixelReader.getColor(x, y).equals(Color.WHITE))
        {
            redCircle1 = new Circle(x, y, 4, Color.RED);

            //redCircle.setTranslateX(imageView.getX());
            //redCircle.setTranslateY(imageView.getY());

            coordList.add(x);
            coordList.add(y);

            ((Pane) imageView.getParent()).getChildren().add(redCircle1);


        }
        else if(coordList.size()>=totalClicks)
        {
            System.out.println("Total Number of markers exceeded.");
        }
        else
        {
            System.out.println("Place makrer on a white tiles");
        }
    }
    public GraphNodeAL2<Color> getNodesFromMousePosition(Image image, int x, int y, GraphNodeAL2<Color>[] nodesPos)
    {
        // gets the node that should have that x and y
        return nodesPos[(int) ((y * image.getWidth()) + x)];

    }

    public void findPathBetweenSelectedPoints() {

        Points pointX = new Points(-13);
        Points pointY = new Points(-33);
        try {

            // list of all the paths found based on the points selected
            nodes = createGraphNodes(imageView.getImage());
            List<List<GraphNodeAL2<?>>> bfsList = new ArrayList<>();
            for (int i = 0; i < coordList.size(); i += 2) {

                if (i + 2 >= coordList.size()) {
                    continue;
                }


                GraphNodeAL2<Color> start = getNodesFromMousePosition(imageView.getImage(), coordList.get(i), coordList.get(i + 1), nodes);
                GraphNodeAL2<Color> end = getNodesFromMousePosition(imageView.getImage(), coordList.get(i + 2) , coordList.get(i + 3), nodes);

                CostedPath searching = new CostedPath(start, end);

                if (true)
                {
                    searching.breadthFirstSearchAlg();
                } else {

                    searching.dijkstrasAlg(nodes);
                }


                bfsList.add(searching.getPath());
            }

            //adds the individual paths
            List<GraphNodeAL2<?>> totalPath = CostedPath.addPathsTogether(bfsList);
            System.out.println((getLength(totalPath)));

            // perform the search
            imageView.setImage(makePath(imageView.getImage(), totalPath, Color.RED, false));

        }
        catch (Exception e)
        {
            if (e.getMessage() == null)
                System.out.println("The path cannot be found, Try again Later");
            else if (e.getMessage().equals("Invalid color specification"))
                System.out.println("Try a new colour like: #00ff00");
            else
                System.out.println("Oops... Something went wrong");

        }
    }

//    @FXML
//    public void  useDJA(ActionEvent e)
//    {
//        GraphNodeAL2 landmark1 = startingPoint.getSelectionModel().getSelectedItem();
//        GraphNodeAL2 landmark2 = endPoint.getSelectionModel().getSelectedItem();


//        for(int i=0; i<listOfLandmarks.size(); i++)
//        {
//            if(String.valueOf(startingPoint.getValue()) == listOfLandmarks.get(i).data.getName())
//            {
//
//                landmark1 = new GraphNodeAL2<>(new Landmark( listOfLandmarks.get(i).data.getName(),
//                        listOfLandmarks.get(i).data.getxCoord(),
//                        listOfLandmarks.get(i).data.getyCoord(),
//                        listOfLandmarks.get(i).data.getCultureVA1()));
//                cheapestPathList.add(landmark1);
//            }

//            if (endPoint.getValue().equals(listOfLandmarks.get(i).data.getName()))
//            {
//                landmark2 = new GraphNodeAL2<>(new Landmark( listOfLandmarks.get(i).data.getName(),
//                        listOfLandmarks.get(i).data.getxCoord(),
//                        listOfLandmarks.get(i).data.getyCoord(),
//                        listOfLandmarks.get(i).data.getCultureVA1()));
//                cheapestPathList.add(landmark2);
//            }
//        }

//        CostedPath cpa = CostedPath.findCheapestPathDijkstra(landmark1, landmark2);
//        for(GraphNodeAL2<?> n : cpa.pathList)
//            System.out.println(n);
//            System.out.println("\nThe total path cost is: " + cpa.pathCost);
//
//        for(GraphNodeAL2<?> n : cpa.pathList)
//        System.out.println(n.data);
//        System.out.println("\nThe total path cost is: " + cpa.pathCost);
  //  }

//    @FXML
//    public void addLandmark(ActionEvent e)
//    {
//        GraphNodeAL2<String> landmark = new GraphNodeAL2<>(landmarkName.getText(),landmarkName.getText(),redCircle.getCenterX(), redCircle.getCenterY(), Integer.parseInt(culturalValue.getText()));
//        System.out.println(landmark.getName() + " : " +
//                "\n x coord = " + landmark.getX() +
//                "\n y coord = " + landmark.getY());
//        listOfLandmarks.add(landmark);
//
//        startingPoint.getItems().clear();
//        endPoint.getItems().clear();
////        for(GraphNodeAL2 theNode : listOfLandmarks)
////        {
////            startingPoint.getItems().add(((Landmark) theNode.data).name);
////            endPoint.getItems().add(((Landmark) theNode.data).name);
////        }
//    }


//    public void printName()
//    {
//        int startInt = startingPoint.getSelectionModel().getSelectedIndex(); //get the index chosen from starting point
//        GraphNodeAL2<String> start = listOfLandmarks.get(startInt);
//        int endInt = endPoint.getSelectionModel().getSelectedIndex();
//        GraphNodeAL2<String> end = listOfLandmarks.get(endInt);
//
//        CostedPath cpa = CostedPath.findCheapestPathDijkstra(start, end);
//        System.out.println("The cheapest path from " + start.getName() + " to " + end.getName());
//        System.out.println("using Dijkstra's algorithm:");
//        System.out.println("-------------------------------------");
//        System.out.println("\nThe total path cost is: " + cpa.pathCost);
//        System.out.println("Starting point: " + listOfLandmarks.get(startInt).getName()); //printout the name of the Landmark at that location
//        System.out.println("Ending point: " + listOfLandmarks.get(endInt).getName());
//
//    }


    //open image
    @FXML
    private void openTheImage(ActionEvent e)
    {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Image Files", "*.JPG", "*.jpg", "*.PNG", "*.png");
        fileChooser.getExtensionFilters().add(filter);


        File file = fileChooser.showOpenDialog(null);
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            image = SwingFXUtils.toFXImage(bufferedImage, null);


            //height width
            int width = (int) image.getWidth();
            int height = (int) image.getHeight();

            WritableImage blackAndWhiteImage = new WritableImage(width, height);
            //pixel reader
            PixelReader pixelReader = image.getPixelReader();
            PixelWriter pixelWriter = blackAndWhiteImage.getPixelWriter();


            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    Color color = pixelReader.getColor(x, y);
                    int argb = pixelReader.getArgb(x,y);
                    double r = color.getRed();
                    double g = color.getGreen();
                    double b =  color.getBlue();
                    //initialize hue and saturation
                    double saturation = pixelReader.getColor(x,y).getSaturation();
                    double hue = pixelReader.getColor(x,y).getHue();

                    //set colors to black and white
                    if (r+g+b >  2.6)
                    {
                        pixelWriter.setColor(x,y,Color.WHITE);

                           // !(pixelReader.getColor(x,y).grayscale().equals(125){
                        //blackAndWhiteImage.getPixelWriter().setColor(x, y, Color.BLACK);
                    }
                    else
                    {
                        pixelWriter.setColor(x,y,Color.BLACK);
                    }
                }
            }

            imageView.setImage(blackAndWhiteImage);
            createGraphNodes(blackAndWhiteImage);
            resetImage = blackAndWhiteImage;


        }
        catch (IOException ioe)
        {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ioe);
        }

    }



    public  GraphNodeAL2<Color>[] createGraphNodes (Image image)
    {
        nodes = new GraphNodeAL2[(int) (image.getWidth() * image.getHeight())];
        PixelReader pixelReader = imageView.getImage().getPixelReader();

        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if(pixelReader.getColor(x, y).equals(Color.BLACK))
                {
                    continue;
                }
                GraphNodeAL2<Color> node = new GraphNodeAL2<>("PATH@" + x  + ":" + y, x, y);
                node.setData(pixelReader.getColor(node.getX(), node.getY()));
                nodes[ ((y * width) + x)] = node;
            }
        }
        return createEdges(image, nodes);

    }

    public GraphNodeAL2[] createEdges(Image image, GraphNodeAL2[] nodeEdge) {
        for (int i = 0; i < nodeEdge.length; i++)
        {
            if (nodeEdge[i] == null)
            {
                continue;
            }

            if ((i + 1) % (int) image.getWidth() != 0)
            {
                if (i + 1 < nodeEdge.length) {
                    if (nodeEdge[i + 1] != null) {
                        nodeEdge[i].connectToNodeUndirected(nodeEdge[i + 1], 1);
                    }
                }
            }

                    if (!(i + image.getWidth() >= nodeEdge.length)) {
                        if (nodeEdge[i + (int) image.getWidth()] != null) { // make sure its not black
                            nodeEdge[i].connectToNodeUndirected(nodeEdge[i + (int) image.getWidth()], 1);

                        }
                    }
                }
                return nodeEdge;
            }



    public Image makePath(Image image, List<GraphNodeAL2<?>> nodePath, Color colour, boolean bool) {
        WritableImage writableImage = new WritableImage(image.getPixelReader() ,(int)image.getWidth(), (int)image.getHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();


        if(bool) {
            for(GraphNodeAL2 node : nodePath) {
                colour = colour.deriveColor(1.1, 1, 1, 1);
                pixelWriter.setColor(node.getX(), node.getY(), Color.RED);
            }
        } else {
            for(GraphNodeAL2 node : nodePath) {
                pixelWriter.setColor(node.getX(), node.getY(), Color.RED);
            }
        }

        return writableImage;
    }


    public int getLength(List<GraphNodeAL2<?>> path) {

        if(path.size() <= 1) return 0;

        int length = 0;

        for(int i = 0; i < path.size() - 1; i++) {
            GraphNodeAL2<?> currentNode = path.get(i);
            GraphNodeAL2<?> nextNode = path.get(i + 1);

            for(GraphLink adjEdge : currentNode.getAdjList()){
                if(adjEdge.getDestinationNode().equals(nextNode))
                    length += adjEdge.getLength();
            }

        }

        return length;
    }


//    public void drawCircles()
//    {
////  comment 7: creating another method, this will be called for each of your Landmark GraphNodes (actually creating the circles)
//        //drawing a circle for each of the graphlink nodes (landmarks)
//        for(GraphNodeAL2 theNOde:listOfLandmarks){ //going through list 1 by 1 theNode = name of each node
//            int x = (int)(theNOde.getX()); //for theNode pull out the data and we're telling it its a landmark
//            int y = (int)(theNOde.getY());
//            String landmarkName = (theNOde.getName());
//            drawCircle(x, y, landmarkName);
//        }
//
//    }

//    //places a circle in the image
//    public void drawCircle(int x, int y, String landmarkName)
//    {
//        Circle circle = new Circle(x, y, 8);
//        circle.setTranslateX(imageView.getLayoutX()); //getting correct values for the image
//        circle.setTranslateY(imageView.getLayoutY()); //finding where the image is on the screen
//
//        Text text = new Text(x + 12, y, landmarkName); //adding text for the name 12 = 4 pixels outside the circle
//        text.setTranslateX(imageView.getLayoutX());
//        text.setTranslateY(imageView.getTranslateY());
//
//        ((Pane) imageView.getParent()).getChildren().addAll(circle, text); //add the circle and text on top of the image
//    }


    //SAVE
    @FXML
    private void saveAll(ActionEvent e) throws IOException
    {
        try
        {
            Main.save();
            System.out.println("The data has been saved.");
        }
        catch (Exception f)
        {
            System.err.println("Error writing to file: " + f);
        }
    }

    //LOAD
    @FXML
    private void loadAll()
    {
        try
        {
            //Main.load();
            System.out.println("The data has been loaded.");
        }
        catch (Exception f)
        {
            System.err.println("Error writing to file: " + f);
        }
    }


}
