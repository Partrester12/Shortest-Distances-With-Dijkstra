package fi.utu.tech.graphs;

import net.jqwik.api.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphTests {

    GraphGenerator gg = new GraphGenerator();
    Graph test = gg.createTotallyRandomGraph();

    @Provide
    Arbitrary<Integer> numNodes() {
        return Arbitraries.integers().between(0, test.getNodes().length-1);
    }

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
