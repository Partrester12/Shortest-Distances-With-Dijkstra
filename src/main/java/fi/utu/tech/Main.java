package fi.utu.tech;

import fi.utu.tech.Dijkstran.*;
import fi.utu.tech.Window.MainWindow;
import fi.utu.tech.graphs.*;
import javafx.application.Application;

public class Main {

    static Class<?> chooseMain(){
        return MainWindow.class;
    }

    public static void main(String[] args){

        var mainClass = chooseMain();

        Application.launch(mainClass.asSubclass(Application.class), args);

    }

    /*
    public static void main(String[] args){
        GraphGenerator gg = new GraphGenerator();
        Graph g = gg.createTestGraph();
        //System.out.println(g);
        Dijkstran dijkstran = new Dijkstran(g);

        for(int j=0;j<g.getNodes().length;j++) {
            for (int i : dijkstran.originNodesForShortestDistances(j)) {
                System.out.print(i + ", ");
            }
            System.out.println();
        }

        for(int i=0;i<dijkstran.shortestRouteFromNodeXToNodeY(0,4).length;i++){
            System.out.print(dijkstran.shortestRouteFromNodeXToNodeY(0,4)[i] + ", ");
        }
        System.out.println();
        for(int i=0;i<dijkstran.shortestRouteFromNodeXToNodeY(1,4).length;i++){
            System.out.print(dijkstran.shortestRouteFromNodeXToNodeY(1,4)[i] + ", ");
        }
        System.out.println();
    }

     */
}
