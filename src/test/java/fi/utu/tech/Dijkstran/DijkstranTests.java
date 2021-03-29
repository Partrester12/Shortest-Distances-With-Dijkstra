package fi.utu.tech.Dijkstran;

import fi.utu.tech.graphs.*;
import net.jqwik.api.*;

import java.util.ArrayList;
import java.util.List;

public class DijkstranTests {

    //Initialize GraphGenerator for use
    GraphGenerator gg = new GraphGenerator();

    /**
     *
     * @return Arbitrary with a 1000 randomly generated graphs
     */
    @Provide
    Arbitrary<Graph> multipleGraphs(){
        List<Graph> l = new ArrayList<>();
        for(int i=0;i<1000;i++){
            l.add(gg.createTotallyRandomGraph());
        }
        return Arbitraries.of(l);
    }

    /**
     *
     * @param g Takes in a 1000 randomly generated graphs
     * @return Returns true if every single graph was calculated without errors and if the distance of each node from itself is 0
     */
    @Property
    boolean every_graph_successfully_iterated_through(@ForAll("multipleGraphs")Graph g){
        Dijkstran d = new Dijkstran(g);
        boolean b = true;
        for(int i=0;i<g.getNodes().length;i++){
            b=d.shortestDistanceFromNodeXToNodeY(i,i)==0;
        }
        return  b;
    }

    /**
     *
     * @param g Takes in a 1000 randomly generated graphs
     * @return Returns true if shortest distance from node A to its followers is shorter or equal to the original distance between node A and its followers
     */
    @Property
    boolean every_distance_leq_than_original_distances_from_node_to_node(@ForAll("multipleGraphs")Graph g) {
        Dijkstran d = new Dijkstran(g);
        boolean b = true;
        for (int i = 0; i < g.getNodes().length; i++) {
            for (int k = 0; k < g.getNodes()[i].getFollowers().length; k++) {
                if (g.getNodes()[i].getFollowers()[0][0] != -1) {
                    b = g.getNodes()[i].getFollowers()[k][1] >= d.shortestDistanceFromNodeXToNodeY(i, g.getNodes()[i].getFollowers()[k][0]);
                }
            }

        }
        return b;
    }

    /**
     *
     * @param g Takes in a 1000 randomly generated graphs
     * @return
     * Returns true only if for every single shortest path returned by the Dijkstran object
     * Is indeed the shortest path from node A to node B
     *
     * So for example shortest route from node A to node B is A-D-G-B.
     * Now this method tests whether the shortest distance between A nd B is equal to
     * the sum of the shortest distances between the nodes A-D, D-G, G-B
     */
    @Property
    boolean every_origin_node_correct(@ForAll("multipleGraphs") Graph g){
        Dijkstran d = new Dijkstran(g);
        boolean b = true;
        for(int i=0;i<g.getNodes().length;i++){
            //No point in testing if this node doesn't have any followers
            if(g.getNodes()[i].getFollowers()[0][0]!=-1) {
                for (int k = 0; k < g.getNodes().length; k++) {
                    if (i != k) {
                        int a = d.shortestDistanceFromNodeXToNodeY(i, k);
                        int c = 0;
                        int[] help = d.shortestRouteFromNodeXToNodeY(i, k);

                        for (int o = 0; o < help.length - 1; o++) {
                            c = c + d.shortestDistanceFromNodeXToNodeY(help[o], help[o + 1]);
                        }
                        b = a == c;
                    }
                }
            }
        }

        return b;
    }
}
