package fi.utu.tech.graphs;

import java.util.ArrayList;

public class Node {
    private int placement;
    private int cost=0;
    private int[][] followers;

    //Dummy node
    public Node(int placement){
        this.placement = placement;
    }

    //Node with cost but no followers
    public Node(int placement, int cost){
        this.placement = placement;
        this.cost = cost;
    }

    //Node with followers and cost to move to said followers
    public Node(int placement, int[][] followers){
        this.placement = placement;
        this.followers = followers;
    }
}
