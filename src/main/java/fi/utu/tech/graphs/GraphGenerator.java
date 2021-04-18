package fi.utu.tech.graphs;

import java.util.ArrayList;
import java.util.Random;

public class GraphGenerator {
    private boolean negativeValues;
    private Random random;

    /**
     * Standard constructor
     */
    public GraphGenerator(){
        negativeValues=false;
        random = new Random();
    }

    /**
     * Adjustable constructor which can allow negative values as distances
     * @param negativeValues Whether negative distances are allowed or not
     */
    public GraphGenerator(boolean negativeValues){
        this.negativeValues = negativeValues;
        random = new Random();
    }

    /**
     *  Create a graph of random number of nodes with random connections of random distances
     * @return Graph-object usable by the Dijkstran-class
     */

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

                    //Old debug
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

    /**
     * Create a graph with the specified number of nodes with random values
     * @param numNodes int specifying the number of nodes in the graph
     * @return Graph-object usable by the Dijkstran-class
     */
    public Graph createRandomValuedGraph(int numNodes){

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

    /**
     * A method for creating a totally custom graph. A connection within the graph consists of the ints located in the same index in each of the three arrays
     * @param nodeA Array containing the numbers of the nodes where a connection starts from
     * @param nodeB Array containing the numbers of the nodes where a connection ends
     * @param distances Array which contains the lengths of the connections
     * @param numNodes Number telling the total number of nodes in this graph
     * @return A Graph-object which is fully usable by the Dijkstran-class
     */
    public Graph createCustomGraph(int[] nodeA, int[] nodeB, int[] distances, int numNodes){


        Node[] nodes = new Node[numNodes];

        for(int i=0;i<numNodes;i++){

            int[][] followers;
            int h = 0;
            ArrayList<Integer> help = new ArrayList();

            for(int j=0;j<nodeA.length;j++){
                if(nodeA[j]==i){
                    h++;
                    help.add(j);
                }
            }

            if(h==0){
                followers = new int[1][2];
                followers[0][0]=-1;
                followers[0][1]=0;
            } else {
                followers = new int[help.size()][2];
                for(int j=0;j<help.size();j++){
                    followers[j][0]=nodeB[help.get(j)];
                    followers[j][1]=distances[help.get(j)];
                }
            }
            nodes[i] = new Node(i, followers);
        }

        return new Graph(nodes);
    }



    /**
     * Graph created contains 5 nodes, with each connection being bidirectional
     * Shortest route from 0 to 4 is 0-1-2-3-4 and distance is equal to 12
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

    /**
     *Method which returns an Integer ArrayList that contains ints up to the int i given as the parameter
     */
    private ArrayList<Integer> createHelper(int i){
        ArrayList<Integer> hjelp= new ArrayList<Integer>();
        for(int j=0;j<i;j++){
            hjelp.add(j);
        }
        return hjelp;
    }
}
