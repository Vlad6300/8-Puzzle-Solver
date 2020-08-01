package assignmentHeuristic;

import java.util.ArrayList;

public class Node{                  //Node class that keeps track of the GameState, the parent Node, the cost of reaching this node and the heuristic value of the GameState
    GameState state;
    Node parent;
    private int cost;
    private int heuristic;

    public Node(GameState state, Node parent, int cost) {       //Constructor method for a new node
        this.state = state;
        this.parent = parent;
        this.cost = cost;
        this.heuristic=state.heuristic();           //Gets the heuristic value
    }

    public Node(GameState state){           //Constructor for the rootNode
        this.state=state;
        this.parent=null;
        this.cost=0;
        this.heuristic=state.heuristic();
    }

    public int getHeuristic(){
        return this.heuristic;
    } //This method returns the heuristic value of a node
    public int getCost(){
        return this.cost;
    }       //This method returns the cost of reaching this node from the root node

    public String toString() {
        return "Node:\n" + state.toString();
    }   //This method turns the Node into a string for output

    public static Node findNodeWithState(ArrayList<Node> nodeList, GameState gs) {      //Given a GameState and a list of nodes, this method finds a node with the same GameState in the list or returns null
        for (Node n : nodeList) {
            if (gs.sameBoard(n.state)) return n;
        }
        return null;
    }

}
