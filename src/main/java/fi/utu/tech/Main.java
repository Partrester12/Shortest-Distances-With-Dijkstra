package fi.utu.tech;

import fi.utu.tech.graphs.*;

public class Main {

    public static void main(String[] args){
        GraphGenerator gg = new GraphGenerator();
        System.out.println(gg.createTotallyRandomGraph());
    }
}
