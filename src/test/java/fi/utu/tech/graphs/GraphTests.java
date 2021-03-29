package fi.utu.tech.graphs;

import net.jqwik.api.*;

import java.util.*;

public class GraphTests {

    GraphGenerator gg = new GraphGenerator();
    Graph test = gg.createTotallyRandomGraph();



    @Provide
    Arbitrary<Integer> numNodes() {
        return Arbitraries.integers().between(0, test.getNodes().length-1);
    }

    /**
     *
     * @param i The number of nodes in our test graph
     * @return True if no node has duplicate followers
     * So node A cannot have two different direct connections to node B
     */
    @Property
    boolean every_node_has_unique_followers(@ForAll("numNodes") int i){

        ArrayList<Integer> l= new ArrayList<>();
            Node n = test.getNodes()[i];
            for(int k=0;k<n.getFollowers().length;k++){
                l.add(n.getFollowers()[k][0]);
            }
        Set<Integer> s = new HashSet<>(l);

        return !(s.size() < l.size());

    }

}
