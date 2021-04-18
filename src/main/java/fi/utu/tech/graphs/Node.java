package fi.utu.tech.graphs;

public class Node {
    private int placement;
    private int cost=0;

    public int[][] getFollowers() {
        return followers;
    }

    private int[][] followers;

    /**
     * Dummy node
     * @param placement Contains only the placement id of the node
     */
    public Node(int placement){
        this.placement = placement;
    }


    /**
     * Node with followers and cost to move to said followers
     * @param placement Placement id of the node in the graph
     * @param followers Array consisting of the follower-nodes' ids and the distances
     */
    public Node(int placement, int[][] followers){
        this.placement = placement;
        this.followers = followers;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("This is node: "+placement+" with connections: ");

        for(int i=0;i< followers.length; i++){
            sb.append("("+followers[i][0]+","+followers[i][1]+")");
        }

        return sb.toString();
    }
}
