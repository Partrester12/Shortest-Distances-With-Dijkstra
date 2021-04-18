package fi.utu.tech.graphs;


public class Graph {
    public Node[] getNodes() {
        return nodes;
    }

    private Node[] nodes;

    public Graph(Node [] nodes){
        this.nodes = nodes;
    }


    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("This graph"+" (length "+nodes.length+") "+" has the following nodes: \n");

        for(int i=0; i< nodes.length ; i++){
            sb.append(nodes[i].toString());
            sb.append("\n");
        }

        return sb.toString();
    }
}
