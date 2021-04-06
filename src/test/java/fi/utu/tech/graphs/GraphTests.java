package fi.utu.tech.graphs;

import net.jqwik.api.*;

import java.util.*;

public class GraphTests {

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
     * @param g In total a 1000 randomly generated graphs
     * @return True if no node in any graph has duplicate followers
     * So node A cannot have two different direct connections to node B
     */
    @Property
    boolean multiple_every_node_has_unique_followers(@ForAll("multipleGraphs") Graph g){

        for(int i=0;i<g.getNodes().length-1;i++){
            ArrayList<Integer> l= new ArrayList<>();
            Node n = g.getNodes()[i];
            for(int k=0;k<n.getFollowers().length;k++){
                l.add(n.getFollowers()[k][0]);
            }
            Set<Integer> s = new HashSet<>(l);
            if(s.size() < l.size()){
                return false;
            }
        }

        return true;
    }

}
