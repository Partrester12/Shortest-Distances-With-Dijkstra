# Shortest distances with Dijkstra


To install the program, run "mvn install" and to run the program, run "java -jar target/shortest-distanves-with-dijkstra-1.0.0.jar"

And yes, it's "distanves", not "distances"!

When starting the program, you're first presented with the initial screen, where you can create a graph which suites your needs.
Not that should you tick the "randomize" CheckBox, your already created connections will not be taken into account.

Also the button "CreateGraph" will not work should there be duplicate connections so make sure you don't leave two connections from node 0 to node 1.

The program is meant to be run as a 600x400 window, so keep that in mind.

After pressing the "CreateGraph" button, the program will switch scenes and draw the created graph onto the screen coupled with arrows representing connections between nodes. Note that the length of the arrows doesn't accurately represent the distance between two nodes and acts only as a visual aid. Also please keep in mind that for large graphs (20+ nodes) the screen will get cluttered so should there be a need for a clear visual aid, run the program only with small graphs.

The nodes (represented as blue circles) will get highlighted with the color red when hovered over, if the screen isn't currently showing a route.

To then get the shortest distances, first click on any node to select it. It should turn red. Then to get the shortest distance to another node, select the other node by clicking it. If the CheckBox "showRoute" has been checked, the screen will also show the shortest route by coloring the visited nodes purple. To then calculate another route, simply click on any node to select it.

If you click twice on the same node, the program will instead calculate the shortest distance between the selected node and all other nodes in the graph. The results will then be shown in the TextField on the right side of the screen. If the "showRoute" checkbox is ticked, the TextField will also show the shortest route below the distance.

Should you want to edit the graph (or even create a completely new one), press the "Edit Graph" button and you will be taken back to the initial screen with all your connections saved.
