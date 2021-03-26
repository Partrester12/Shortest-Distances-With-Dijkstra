package fi.utu.tech.graphs;

import java.util.ArrayList;
import java.util.Random;

public class GraphGenerator {
    private boolean negativeValues;
    private Random random;

    //Standard constructor
    public GraphGenerator(){
        negativeValues=false;
        random = new Random();
    }

    //Adjustable constructor
    public GraphGenerator(boolean negativeValues){
        this.negativeValues = negativeValues;
        random = new Random();
    }

    //Create a graph of random number of nodes and with random values
    public Graph createTotallyRandomGraph(){
        int numNodes = random.nextInt(100)+1;

        Node[] nodes = new Node[numNodes];

        for(int i=0;i<numNodes;i++){
            int numConnections = random.nextInt(numNodes);
            int[][] followers = new int[numConnections][numConnections];
            ArrayList<Integer> helper = createHelper(numNodes);
            for(int j=0;j<numConnections;j++){
                int follower = random.nextInt(helper.size());
                followers[j][0]=follower;
                helper.remove(follower);
                //If negative values are allowed, they can appear as costs
                if(negativeValues){
                    followers[j][1]= random.nextInt(201)-100;
                } else {
                    followers[j][1]= random.nextInt(101);
                }
            }
             nodes[i] = new Node(i, followers);
        }

        return new Graph(nodes);
    }

    //Create a graph with the specified number of nodes with random values
    public Graph createRandomValuedGraph(int numOfNodes){

        return null;
    }

    //Create a custom graph from scratch
    public Graph createCustomGraph(int numOfNodes){

        return null;
    }

    private ArrayList<Integer> createHelper(int i){
        ArrayList<Integer> hjelp= new ArrayList<Integer>();
        for(int j=0;j<i;j++){
            hjelp.add(j);
        }
        return hjelp;
    }
}
