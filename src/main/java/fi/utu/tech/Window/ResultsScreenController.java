package fi.utu.tech.Window;

import fi.utu.tech.Dijkstran.Dijkstran;
import fi.utu.tech.graphs.*;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class ResultsScreenController {

    private Graph g;
    private MainWindow mw;
    private Dijkstran dijkstran;

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
        Circle circle = new Circle(5);
        circle.setLayoutX(10);
        circle.setLayoutY(10);
        circle.setFill(Color.RED);
        shapes.getChildren().add(circle);

        canvas.getGraphicsContext2D().strokeLine(circle.getLayoutX(),circle.getLayoutY(), canvas.getWidth(), canvas.getHeight());

    }


    /*
    Quick thoughts on how to continue:

    Divide the canvas/pane into 36x36 squares, distribute random coordinates within each square to the circles
    This way the graphs will probably look way better



     */
    private void drawResults(){
        Circle[] circles = new Circle[g.getNodes().length];
        for(int i=0;i<circles.length;i++){
            circles[i]=new Circle(5);
            shapes.getChildren().add(circles[i]);
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
}
