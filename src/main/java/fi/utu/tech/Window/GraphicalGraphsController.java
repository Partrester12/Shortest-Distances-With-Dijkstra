package fi.utu.tech.Window;

import fi.utu.tech.graphs.*;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class GraphicalGraphsController {

    private IntegerProperty n;
    private GraphGenerator gg = new GraphGenerator();
    private MainWindow mw;





    /**
    Method which adds a new GUI representation of a connection the user wants to add to the graph
    In simpler terms, adds an HBox which contains two ChoiceBoxes for which nodes wil be connected and a TextField where you write the distance
     */
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

        //Setting constraints to the "distance" TextField so that the user can only input integers
        TextFormatter<String> textFormatter = new TextFormatter<String>(change -> {
           if(!change.isContentChange()){
               return change;
           }

           String text = change.getControlNewText();

           //If the new input matches the format of a positive Integer or would be empty, the change occurs
           if(text.matches("\\d+$")||text.equals("")){
               return change;
           }

           return null;
        });
        ((TextField)h.getChildren().get(5)).setTextFormatter(textFormatter);


        //Setting up a button to remove the connection
        Button b = new Button("X");
        b.setStyle("-fx-background-color: #FF0000");
        b.setOnAction((e) -> connections.getChildren().remove(h));
        h.getChildren().add(b);


        return h;
    }

    /**
     * Method for passing the MainWindow to this controller in order to switch scenes
     */
    public void setMainWindow(MainWindow mw){
        this.mw=mw;
    }

    /**
     * Initialize necessary properties, bindings and control-data
     */
    public void initialize(){

        //To keep track of the value of ChoiceBoc numNodes
        n= new SimpleIntegerProperty();

        //Adding the options to numNodes
        numNodes.getItems().add("Random");
        numNodes.getItems().add("Test");
        for(int i=2;i<101;i++){
            numNodes.getItems().add(i);
        }
        numNodes.setValue(2);

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
    private TextArea error;

    @FXML
    /**
     * Method for creating the graph based on the inputs from the user
     *
     * If the CheckBox random is checked and numNodes value is Random, creates a totally random graph
     * If only the CheckBox random is checked, creates a graph with the specified number of nodes with random connections
     * If no randomness is place, creates a graph according to the connections created by the user
     * In the last case we also perform some checks to make sure that the graph can be created and utilized by Dijkstra-algorithm
     */
    void createGraph(ActionEvent e){
        Graph g;
        if(numNodes.getValue().equals("Random") && random.isSelected()){
            //Creating graph, passing it onto the next scene and switching to the next scene
            g = gg.createTotallyRandomGraph();
            mw.passGraph(g);
            mw.switchScene(1);
        } else if(random.isSelected()){
            //Creating graph, passing it onto the next scene and switching to the next scene
            g = gg.createRandomValuedGraph((Integer)numNodes.getValue());
            mw.passGraph(g);
            mw.switchScene(1);
        } else if(numNodes.getValue().equals("Test")){
            g = gg.createTestGraph();
            mw.passGraph(g);
            mw.switchScene(1);
        } else {
            if ((connections.getChildren()).size()>(Integer)numNodes.getValue()*(((Integer)numNodes.getValue()-1))) {
                //Add some form of notification to the user
                error.setVisible(true);
            } else {
                int temp = (connections.getChildren()).size();

                int[] nodeA = new int[temp];
                int[] nodeB = new int[temp];
                int[] distances = new int[temp];

                for (int i = 0; i < temp; i++) {
                    nodeA[i] = (Integer) ((ChoiceBox) ((HBox) connections.getChildren().get(i)).getChildren().get(1)).getValue();
                    nodeB[i] = (Integer) ((ChoiceBox) ((HBox) connections.getChildren().get(i)).getChildren().get(3)).getValue();
                    distances[i] = Integer.parseInt(((TextField) ((HBox) connections.getChildren().get(i)).getChildren().get(5)).getText());
                }

                //Checks to make sure that there are no duplicate connections!
                boolean help = true;
                for(int i=0;i<nodeA.length;i++){
                    int x = 0;
                    int a = nodeA[i];
                    int b = nodeB[i];
                    for(int j=0;j<nodeA.length;j++){
                        if(a==nodeA[j]&&b==nodeB[j]){
                            x++;
                        }
                    }
                    help=x==1;
                }

                if(help) {
                    //Creating graph, passing it onto the next scene and switching to the next scene
                    g = gg.createCustomGraph(nodeA, nodeB, distances, (Integer) numNodes.getValue());
                    mw.passGraph(g);
                    mw.switchScene(1);
                } else {
                    //Add notification to the user
                    error.setVisible(true);
                }
            }
        }//End of graph creation
    }


    @FXML
            /**
    Clears all the connections
             */
    void clearConnections(ActionEvent e){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(() -> connections.getChildren().clear());
            }
        }).start();

    }

    @FXML
            /**
    Adds a new connection. The method *createNewConnection()* is written earlier on in this class
    */
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
