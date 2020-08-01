package assignment;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Solver {
    ArrayList<Node> unexpanded = new ArrayList<Node>();
    ArrayList<Node> expanded = new ArrayList<Node>();
    Node rootNode;

    public Solver(char[] initialBoard) {
        rootNode = new Node(new GameState(initialBoard));
    }

    public void printSolution(Node n, PrintWriter output) {
        if (n.parent != null)printSolution(n.parent, output);
        output.println("Space at: "+n.state.getSpacePos());
        output.println(n.state);
        output.println();
    }

    public void reportSolution(Node n, PrintWriter output) {
        output.println("Solution found!");
        printSolution(n, output);
        output.println(n.getCost() + " Moves");
        output.println("Nodes expanded: " + this.expanded.size());
        output.println("Nodes unexpanded: " + this.unexpanded.size());
        output.println();
    }



    public void solveNonHeuristic(PrintWriter output){
        unexpanded.add(rootNode);
        while(!unexpanded.isEmpty()){
            Node n = unexpanded.get(0);
            unexpanded.remove(0);
            expanded.add(n);
            if(n.state.isGoal()){
                System.out.println("Found goal");
                reportSolution(n, output);
                return;
            }else{
            ArrayList<GameState> moves = n.state.possibleMoves();
            for(GameState gs : moves){
                if(Node.findNodeWithState(unexpanded,gs)==null && Node.findNodeWithState(expanded,gs)==null){
                    unexpanded.add(new Node(gs,n,n.getCost()+1));
                }
            }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Solver s = new Solver(GameState.INITIAL_BOARD);  // Set up the problem to be solved.
        File outFile = new File("output.txt");                 // Create a file as the destination for output
        PrintWriter output = new PrintWriter(outFile);         // Create a PrintWriter for that file
        s.solveNonHeuristic(output);                                 // Search for and print the solution
        output.close();                                        // Close the PrintWriter (to ensure output is produced).
        return;
    }
}
