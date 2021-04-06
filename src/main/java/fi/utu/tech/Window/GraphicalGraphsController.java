package fi.utu.tech.Window;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class GraphicalGraphsController {





    private HBox createNewConnection() throws IOException {
        FXMLLoader newConnectionGraphicLoader = new FXMLLoader(getClass().getResource("graphicalConnection.fxml"));
        HBox h = newConnectionGraphicLoader.load();
        Button b = new Button("X");
        b.setStyle("-fx-background-color: #FF0000");
        b.setOnAction((e) -> connections.getChildren().remove(h));
        h.getChildren().add(b);
        return h;
    }

    public void initialize(){
        numNodes.getItems().add("Random");
        addConnection.disableProperty().bind(random.selectedProperty());
        for(int i=1;i<101;i++){
            numNodes.getItems().add(i);
        }
        numNodes.setValue(1);

        //If I implement the "maximum number of connections per node" concept, then this code is ready to use
        /*
        numNodes.setOnAction((e) -> {
            if(numNodes.getValue() instanceof Integer){
                maxConnections.getItems().clear();
                maxConnections.getItems().add("Random");
                for(int i=1;i<(int)numNodes.getValue();i++){
                    maxConnections.getItems().add(i);
                }
                maxConnections.setValue(1);
            } else {
                maxConnections.getItems().clear();
                maxConnections.setValue("Random");
            }
        });
         */
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
    void clearConnections(ActionEvent e){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(() -> connections.getChildren().clear());
            }
        }).start();

    }

    @FXML
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
