package fi.utu.tech.Window;

import fi.utu.tech.graphs.Graph;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainWindow extends Application {
    private Stage primary;
    private Scene initScreen;
    private Scene resultScreen;
    private ResultsScreenController rsc;
    private GraphicalGraphsController ggc;

    @Override
    public void start(Stage stage) throws Exception {

        Stage primaryStage = stage;
        primary=stage;

        // Getting the reference to "class object of this class"
        var resourceRoot = getClass();


        //Add the corresponding FXML-files to the different scenes
        var form = "GraphicalGraphs.fxml";
        FXMLLoader initScreenLoader = new FXMLLoader(resourceRoot.getResource(form));
        initScreen = new Scene(initScreenLoader.load());
        ggc = initScreenLoader.getController();
        ggc.setMainWindow(this);
        form = "ResultsScreen.fxml";
        FXMLLoader resultScreenLoader = new FXMLLoader(resourceRoot.getResource(form));
        resultScreen = new Scene(resultScreenLoader.load());
        rsc = resultScreenLoader.getController();
        rsc.setMainWindow(this);


        primaryStage.setTitle("Testing 123");
        primaryStage.setScene(initScreen);
        primaryStage.show();

    }

    public void switchScene(int n){
        switch(n){
            case(0):primary.setScene(initScreen);
            break;
            case(1):primary.setScene(resultScreen);
            break;
        }
    }

    public void passGraph(Graph g){
        rsc.setGraph(g);
    }
}