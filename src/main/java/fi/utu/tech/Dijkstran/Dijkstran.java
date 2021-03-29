package fi.utu.tech.Dijkstran;

import fi.utu.tech.graphs.*;

import java.util.ArrayList;
import java.util.Collections;

public class Dijkstran {

    private Graph graph;
    private int[][] shortestDistances;
    private int[][] originNodes;

    public Dijkstran(Graph g){
        graph = g;
        shortestDistances = new int[graph.getNodes().length][graph.getNodes().length];
        originNodes = new int[graph.getNodes().length][graph.getNodes().length];
        for(int i=0;i<graph.getNodes().length;i++){
            shortestDistances[i]=shortestDistancesToAllNodesInGraph(i)[0];
            originNodes[i]=shortestDistancesToAllNodesInGraph(i)[1];
        }
    }




    public int shortestDistanceFromNodeXToNodeY(int n1, int n2){
        return shortestDistances[n1][n2];
    }

    public int[] shortestRouteFromNodeXToNodeY(int n1, int n2){
        //If one of the nodes isn't connected to any other node
        if(n1==-1||n2==-1){
            return new int[]{n1, n2};
        }
        ArrayList<Integer> nodes = new ArrayList();
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

    private int originNode(int n1, int n2){
        return originNodes[n1][n2];
    }

    public int[] shortestDistancesFromNodeX(int n1){
        return shortestDistances[n1];
    }

    public int[] originNodesForShortestDistances(int n1){
        return originNodes[n1];
    }

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
                distances[i] = 10000;
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
