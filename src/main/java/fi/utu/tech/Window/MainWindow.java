package fi.utu.tech.Window;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainWindow extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        Stage primaryStage = stage;

        // Getting the reference to "class object of this class"
        var resourceRoot = getClass();


        //Add the corresponding FXML-files to the different scenes
        var form = "GraphicalGraphs.fxml";
        FXMLLoader initScreenLoader = new FXMLLoader(resourceRoot.getResource(form));
        Scene initScreen = new Scene(initScreenLoader.load());

        primaryStage.setTitle("Testing 123");
        primaryStage.setScene(initScreen);
        primaryStage.show();

    }
}
