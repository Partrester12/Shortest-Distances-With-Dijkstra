package fi.utu.tech;

import fi.utu.tech.Window.MainWindow;
import javafx.application.Application;

/**
 * Regular Main-class launches the MainWindow JavaFX application
 */
public class Main {

    public static Class<?> chooseMain(){
        return MainWindow.class;
    }

    public static void main(String[] args){

        var mainClass = chooseMain();

        Application.launch(mainClass.asSubclass(Application.class), args);

    }
}
