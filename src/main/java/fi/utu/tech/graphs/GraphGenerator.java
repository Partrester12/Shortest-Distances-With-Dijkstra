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
            int[][] followers;

            //If the node has no followers, we create a dummy follower
            if(numConnections==0){
                followers = new int[1][2];
                followers[0][0]=-1;
                followers[0][1]=0;
            } else {
                followers = new int[numConnections][2];
                ArrayList<Integer> helper = createHelper(numNodes);
                //Remove the node itself from the list of possible followers
                helper.remove(Integer.valueOf(i));
                for (int j = 0; j < numConnections; j++) {
                    int follower = helper.get(random.nextInt(helper.size()));
                    followers[j][0] = follower;

                    //Debug
                /*
                for(int k=0;k<helper.size();k++){
                    System.out.print(helper.get(k)+", ");
                }
                System.out.println();
                 */

                    helper.remove(Integer.valueOf(follower));
                    //If negative values are allowed, they can appear as costs
                    if (negativeValues) {
                        followers[j][1] = random.nextInt(201) - 100;
                    } else {
                        followers[j][1] = random.nextInt(101);
                    }
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

    /**
     * Graph created contains 5 nodes, with each connection being bidirectional
     * Shortest route from 0 to 4 is 0-1-2-3-4 and is equal to 12
     *
     * This graph has been solved by hand so the results above are always accurate
     *
     * @return A premade graph for testing purposes
     */
    public Graph createTestGraph(){
        Node[] nodes = new Node[5];
        int[][] yeet = new int[2][2];
        yeet[0][0]=1;
        yeet[0][1]=4;
        yeet[1][0]=2;
        yeet[1][1]=8;
        nodes[0]=new Node(0, yeet);
        yeet = new int[3][2];
        yeet[0][0]=0;
        yeet[0][1]=4;
        yeet[1][0]=2;
        yeet[1][1]=2;
        yeet[2][0]=4;
        yeet[2][1]=10;
        nodes[1]=new Node(1, yeet);
        yeet = new int[4][2];
        yeet[0][0]=0;
        yeet[0][1]=8;
        yeet[1][0]=1;
        yeet[1][1]=2;
        yeet[2][0]=3;
        yeet[2][1]=4;
        yeet[3][0]=4;
        yeet[3][1]=8;
        nodes[2]=new Node(2, yeet);
        yeet = new int[2][2];
        yeet[0][0]=2;
        yeet[0][1]=4;
        yeet[1][0]=4;
        yeet[1][1]=2;
        nodes[3]=new Node(3, yeet);
        yeet = new int[3][2];
        yeet[0][0]=1;
        yeet[0][1]=10;
        yeet[1][0]=2;
        yeet[1][1]=8;
        yeet[2][0]=3;
        yeet[2][1]=2;
        nodes[4]=new Node(4, yeet);
        return new Graph(nodes);
    }

    private ArrayList<Integer> createHelper(int i){
        ArrayList<Integer> hjelp= new ArrayList<Integer>();
        for(int j=0;j<i;j++){
            hjelp.add(j);
        }
        return hjelp;
    }
}
