package fi.utu.tech.Window;

import fi.utu.tech.Dijkstran.Dijkstran;
import fi.utu.tech.graphs.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Random;


public class ResultsScreenController {

    private Graph g;
    private MainWindow mw;
    private Dijkstran dijkstran;
    private BooleanProperty nodeSelected;
    private IntegerProperty node1;

    //Method for passing the MainWindow to this controller in order to switch scenes
    public void setMainWindow(MainWindow mw){
        this.mw=mw;
    }

    public void setGraph(Graph g){
        this.g=g;
        dijkstran= new Dijkstran(g);
        drawResults();
    }

    public void initialize(){
        nodeSelected = new SimpleBooleanProperty(false);
        node1 = new SimpleIntegerProperty(-1);
    }


    /**
     * Method which draws the graph given to this object onto the screen
     *
     * First divides the whole drawable area into 100 different areas and then distributes random coordinates within each area to an ArrayList
     *
     * Then randomly selects coordinates for each node from this ArrayList so that each node has unique coordinates
     *
     * Finally draws a line from each node to its followers
     *
     */
    private void drawResults(){
        Random rand = new Random();
        ArrayList<int[]> help = new ArrayList();
        Circle[] circles = new Circle[g.getNodes().length];


        //Generating random coordinates in different areas
        for(int i=0;i<10;i++){
            int y = 10 + 36*i;
            for(int j=0;j<10;j++){
                int[] coords = new int[2];
                int x = 10 + 36*j;
                coords[0]=rand.nextInt(27)+x+5;
                coords[1]= rand.nextInt(27)+y+5;
                help.add(coords);
            }
        }

        //Adding the nodes to the window
        for(int i=0;i<circles.length;i++){
            int n=rand.nextInt(help.size());
            circles[i]=new Circle(7);
            circles[i].setId(String.valueOf(i));
            circles[i].setLayoutX(help.get(n)[0]);
            circles[i].setLayoutY(help.get(n)[1]);
            help.remove(n);
            circles[i].setFill(Color.BLUE);

            //Adding functionality to the nodes
            circles[i].setOnMouseEntered((e) -> {
                Circle target = (Circle)e.getTarget();
                test.setText("Node: "+target.getId());
                target.setFill(Color.RED);
            });

            circles[i].setOnMouseExited((e) -> {
                Circle target = (Circle)e.getTarget();
                test.setText("Node: -");
                if(!nodeSelected.getValue()||node1.getValue()!=Integer.parseInt(target.getId())){
                    target.setFill(Color.BLUE);
                }
            });

            circles[i].setOnMousePressed((e) -> {
                Circle target = (Circle)e.getTarget();
                if(nodeSelected.getValue()) {
                    if (node1.getValue() != Integer.parseInt(target.getId())) {
                        test.setText("Distance between nodes: "+node1.getValue()+"&"+target.getId()+" is "+dijkstran.shortestDistanceFromNodeXToNodeY(node1.getValue(), Integer.parseInt(target.getId())));
                        circles[node1.getValue()].setFill(Color.GREEN);
                        nodeSelected.setValue(false);
                    } else {
                        test.setText("Distances from node "+node1.getValue()+":");
                        nodeSelected.setValue(false);
                    }
                } else {
                    node1.setValue(Integer.parseInt(target.getId()));
                    nodeSelected.setValue(true);
                    target.setFill(Color.RED);
                }
            });

            shapes.getChildren().add(circles[i]);
        }

        for(int i=0;i<circles.length;i++){
            if(g.getNodes()[i].getFollowers()[0][0]!=-1){
                for(int j=0;j<g.getNodes()[i].getFollowers().length;j++){
                    int follower = g.getNodes()[i].getFollowers()[j][0];
                    double x1 = circles[i].getLayoutX();
                    double y1 = circles[i].getLayoutY();
                    double x2 = circles[follower].getLayoutX();
                    double y2 = circles[follower].getLayoutY();
                    canvas.getGraphicsContext2D().strokeLine(x1, y1, x2, y2);


                    //Create an arrow for pointing the direction of the connection
                    //Done with normalized vectors to get points in order to create a desirable triangle
                    double len1 = Math.sqrt(Math.pow((x1-x2), 2)+Math.pow((y1-y2), 2));
                    double[] v1 = new double[]{(x1-x2)/len1,(y1-y2)/len1};
                    double x3=x2+7*v1[0];
                    double y3=y2+7*v1[1];
                    double x4=x2+12*v1[0];
                    double y4=y2+12*v1[1];
                    double len2 = Math.sqrt(Math.pow((-v1[1]/v1[0]), 2)+Math.pow((1), 2));
                    double[] v2 = new double[]{-v1[1]/v1[0]/len2, 1/len2};
                    double x5=x4-5*v2[0];
                    double y5=y4-5*v2[1];
                    double x6=x4+5*v2[0];
                    double y6=y4+5*v2[1];

                    //Draw the arrowheads
                    canvas.getGraphicsContext2D().setFill(Color.YELLOW);
                    canvas.getGraphicsContext2D().fillPolygon(new double[]{x3,x5,x6}, new double[]{y3,y5,y6}, 3);
                    canvas.getGraphicsContext2D().strokePolygon(new double[]{x3,x5,x6}, new double[]{y3,y5,y6}, 3);

                }
            }
        }
    }

    @FXML
    private Pane shapes;

    @FXML
    private Label test;

    @FXML
    private Canvas canvas;

    @FXML
    private StackPane stack;

    @FXML
    private TextArea information;

    @FXML
    private Button editGraph;

    @FXML
    public void editGraph(ActionEvent e){

    }
}
