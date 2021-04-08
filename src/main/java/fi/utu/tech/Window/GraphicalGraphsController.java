package fi.utu.tech.Window;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class GraphicalGraphsController {

    private IntegerProperty n;





    //Method which adds a new GUI representation of a connection the user wants to add to the graph
    //In simpler terms, adds an HBox which contains two ChoiceBoxes for which nodes wil be connected and a TextField where you write the distance
    private HBox createNewConnection() throws IOException {

        //Loading up the premade FXML-file containing the base for our HBox
        FXMLLoader newConnectionGraphicLoader = new FXMLLoader(getClass().getResource("graphicalConnection.fxml"));
        HBox h = newConnectionGraphicLoader.load();

        //Setting up the functionality on the ChoiceBoxes of the HBox
        ((ChoiceBox)h.getChildren().get(1)).setValue(0);
        ((ChoiceBox)h.getChildren().get(3)).setValue(1);
        ((ChoiceBox)h.getChildren().get(1)).setOnShowing((e) -> {
            ((ChoiceBox) h.getChildren().get(1)).getItems().clear();
            for(int i=0;i<n.getValue();i++){
                //Making sure that the user cannot accidentally create a connection from node A to node A
                if(i!=(Integer)((ChoiceBox)h.getChildren().get(3)).getValue()) {
                    ((ChoiceBox) h.getChildren().get(1)).getItems().add(i);
                }
            }
        });
        ((ChoiceBox)h.getChildren().get(3)).setOnShowing((e) -> {
            ((ChoiceBox) h.getChildren().get(3)).getItems().clear();
            for(int i=0;i<n.getValue();i++){
                if(i!=(Integer)((ChoiceBox)h.getChildren().get(1)).getValue()) {
                    ((ChoiceBox) h.getChildren().get(3)).getItems().add(i);
                }
            }
        });

        //Setting up a button to remove the connection
        Button b = new Button("X");
        b.setStyle("-fx-background-color: #FF0000");
        b.setOnAction((e) -> connections.getChildren().remove(h));
        h.getChildren().add(b);


        return h;
    }

    public void initialize(){

        //To keep track of the value of ChoiceBoc numNodes
        n= new SimpleIntegerProperty();

        //Adding the options to numNodes
        numNodes.getItems().add("Random");
        for(int i=1;i<101;i++){
            numNodes.getItems().add(i);
        }
        numNodes.setValue(1);

        //Setting up bindings
        n.bind(numNodes.valueProperty());
        addConnection.disableProperty().bind(random.selectedProperty().or(numNodes.valueProperty().isEqualTo("Random")));

    }

    @FXML
    private Button createGraph;

    @FXML
    private Button clearConnections;

    @FXML
    private Button addConnection;

    @FXML
    private CheckBox random;

    @FXML
    private ChoiceBox numNodes;

    @FXML
    private ChoiceBox maxConnections;

    @FXML
    private VBox connections;

    @FXML
    void createGraph(ActionEvent e){
    }

    @FXML
    //Clears all the connections
    void clearConnections(ActionEvent e){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(() -> connections.getChildren().clear());
            }
        }).start();

    }

    @FXML
    //Adds a new connection. The method *createNewConnection()* is written earlier on in this class
    void addConnection(ActionEvent e){
        new Thread(new Runnable() {
            @Override
            public void run() {
                    Platform.runLater(() -> {
                        try {
                            connections.getChildren().add(createNewConnection());
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    });
            }
        }).start();


    }
}
