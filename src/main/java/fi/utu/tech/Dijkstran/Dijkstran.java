package fi.utu.tech.Dijkstran;

import fi.utu.tech.graphs.*;

import java.util.ArrayList;
import java.util.Collections;

public class Dijkstran {

    private Graph graph;
    private int[][] shortestDistances;
    private int[][] originNodes;

    /**
     *Constructor, which requires a Graph and immediately "solves" it
     * This is done so as to not waste resources solving the distances over and over again
     */
    public Dijkstran(Graph g){
        graph = g;
        shortestDistances = new int[graph.getNodes().length][graph.getNodes().length];
        originNodes = new int[graph.getNodes().length][graph.getNodes().length];
        for(int i=0;i<graph.getNodes().length;i++){
            shortestDistances[i]=shortestDistancesToAllNodesInGraph(i)[0];
            originNodes[i]=shortestDistancesToAllNodesInGraph(i)[1];
        }
    }


    /**
     *
     * @param n1 Index of node 1
     * @param n2 Index of node 2
     * @return Returns the shortest distance of two different nodes within the same graph
     */
    public int shortestDistanceFromNodeXToNodeY(int n1, int n2){
        return shortestDistances[n1][n2];
    }

    /**
     *
     * @param n1 Index of node 1
     * @param n2 Index of node 2
     * @return Returns an int[] containing all the nodes which are visited in the route which is the shortest from node A to node B
     */
    public int[] shortestRouteFromNodeXToNodeY(int n1, int n2){
        //If one of the nodes isn't connected to any other node
        if(n1==-1||n2==-1){
            return new int[]{n1, n2};
        }
        ArrayList<Integer> nodes = new ArrayList<Integer>();
        nodes.add(n2);

        int oN = originNode(n1, n2);
        while(oN != n1){
            //If it's impossible to reach node node n1 from node n2
            if(oN==-1){
                return new int[]{n1, n2};
            }
            nodes.add(oN);
            oN = originNode(n1, oN);
        }
        nodes.add(n1);
        Collections.reverse(nodes);

        int[] returnable = new int[nodes.size()];
        for(int i=0;i<nodes.size();i++){
            returnable[i]=nodes.get(i);
        }
        return returnable;

    }

    /**
     *
     * @param n1 Index of node 1
     * @param n2 Index of node 2
     * @return Returns the "origin" node for the given node n2 when counting distances from node n1
     *
     * In other terms the dijkstra-algorithm keeps track of what is the previous node
     * So for example if teh Graph g contains nodes n1, n2 and n3 AND there is a one-way connection from n1 to n2 and another from n2 to n3
     * The origin node for n3, when starting from node n1 is n2, as it precedes n3 on the shortest route from n1 to n3
     */
    private int originNode(int n1, int n2){
        return originNodes[n1][n2];
    }

    /**
     *
     * @param n1 Index of the node
     * @return Returns an int[] containing the shortest routes from node n1 to all the other nodes within the same graph
     */
    public int[] shortestDistancesFromNodeX(int n1){
        return shortestDistances[n1];
    }

    /**
     *
     * @param n1 Index of the node
     * @return Returns origin nodes for all nodes when starting from node n1
     */
    public int[] originNodesForShortestDistances(int n1){
        return originNodes[n1];
    }


    /**
     *
     * Currently uses the value 100000 to display "infinite distance".
     * Should the need arise, it can be easily changed
     *
     * Unfortunately since the Dijkstra-algorithm requires addition, we cannot straight up use INTEGER.MAX_VALUE as it would cause an overflow
     *
     *
     * @param n index of the node
     * Takes in an int as a parameter and runs the Dijkstra-algorithm for the corresponding node of a graph
     * @return
     * Returns two int-arrays
     * 1st one contains the shortest distances from node n to all the other nodes in the graph
     * 2nd one contains the origin node for the given distance, just as the Dijkstra-algorithm works
     */
    private int[][] shortestDistancesToAllNodesInGraph(int n){
        Node[] nodes = graph.getNodes();
        int[] distances = new int[nodes.length];
        int[] originNode = new int[nodes.length];
        ArrayList<Integer> nodesToVisit = new ArrayList();

        for(int i=0;i<distances.length;i++){
            nodesToVisit.add(i);
            originNode[i]=-1;
        }

        for(int i=0;i<distances.length;i++){
            if(i==n){
                distances[i]=0;
            } else {
                distances[i] = 100000;
            }
        }

        while(nodesToVisit.size()>0) {

            int p = nodesToVisit.get(0);
            for(int i=1;i<nodesToVisit.size();i++){
                if(distances[nodesToVisit.get(i)]<distances[p]){
                    p=nodesToVisit.get(i);
                }
            }

            int[][] currentFollowers = nodes[p].getFollowers();
            if (currentFollowers[0][0] == -1) {

            } else {
                for(int i=0;i<currentFollowers.length;i++){
                    int newDist = Math.min(distances[currentFollowers[i][0]], distances[p]+currentFollowers[i][1]);
                    if(distances[currentFollowers[i][0]]!=newDist){
                        distances[currentFollowers[i][0]] = Math.min(distances[currentFollowers[i][0]], distances[p]+currentFollowers[i][1]);
                        originNode[currentFollowers[i][0]] = p;
                    }
                }
            }
            nodesToVisit.remove(Integer.valueOf(p));
        }


        //Old debug
        /*
        for(int i=0;i<distances.length;i++){
            System.out.print(distances[i]+", ");
        }
        System.out.println();
        for(int i=0;i<distances.length;i++){
            System.out.print(originNode[i]+", ");
        }
        System.out.println();

         */

        int[][] returnable = new int[2][distances.length];
        returnable[0]=distances;
        returnable[1]=originNode;
        return returnable;
    }
}
