package fi.utu.tech;

import fi.utu.tech.Dijkstran.*;
import fi.utu.tech.graphs.*;

public class Main {

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
}
