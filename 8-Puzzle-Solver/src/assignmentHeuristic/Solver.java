package assignmentHeuristic;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Solver {                                   //This class is used to solve the puzzle
    ArrayList<Node> unexpanded = new ArrayList<Node>();     //A list of all the unexpanded nodes
    ArrayList<Node> expanded = new ArrayList<Node>();       //A list of all the expanded nodes
    Node rootNode;                  //The initial node

    public Solver(char[] initialBoard) {
        rootNode = new Node(new GameState(initialBoard));
    }       //The constructor method

    public void printSolution(Node n, PrintWriter output) {     //This method prints all the nodes from start to solution
        if (n.parent != null)printSolution(n.parent, output);
        output.println(n.state);
        output.println();
    }

    public void reportSolution(Node n, PrintWriter output) {        //This method prints the number of moves used to reach the solution, the number of expanded nodes, and the number of unexpanded nodes
        output.println("Solution found!");
        printSolution(n, output);
        output.println(n.getCost() + " Moves");
        output.println("Nodes expanded: " + this.expanded.size());
        output.println("Nodes unexpanded: " + this.unexpanded.size());
        output.println();
    }

    public int selectForExpansion(){            //This method is used to choose the next node for expansion from the unexpanded list of nodes.
        int min=Integer.MAX_VALUE;
        int index=-1;
        int i=0;
        for(Node n : unexpanded){                          //Since this method is used in A* search, we are going to find the node with the smallest sum of heuristic value and cost
            if(n.getCost()+n.getHeuristic()<min){
                min=n.getCost()+n.getHeuristic();
                index=i;
            }
            i++;
        }
        return index;
    }


    public void solveNonHeuristic(PrintWriter output){      //This method is used to solve the puzzle without the use of a heuristic. It uses Breadth First Search
        unexpanded.add(rootNode);               //Add the root node to the unexpanded list
        while(!unexpanded.isEmpty()){           //While there are unexpanded nodes keep the search going
            Node n = unexpanded.get(0);         //Select the first node in the list for expansion (Breadth First Search)
            unexpanded.remove(0);           //Remove the node from unexpanded
            expanded.add(n);                    //Add the node to the expanded list of nodes
            if(n.state.isGoal()){               //Check if the state is the goal
                reportSolution(n, output);      //If it is, output the solution and exit
                return;
            }else{                              //Otherwise expand the node
                ArrayList<GameState> moves = n.state.possibleMoves();       //Get all the states that are reachable in one move from the current state
                for(GameState gs : moves){                  //For all the possible states make sure they aren't already in the unexpanded or the expanded list
                    if(Node.findNodeWithState(unexpanded,gs)==null && Node.findNodeWithState(expanded,gs)==null){
                        unexpanded.add(new Node(gs,n,n.getCost()+1));           //If the state hasn't been found before and it isn't on the list of nodes to be expanded add it to the unexpanded list
                    }
                }
            }
        }
        output.println("No solution found");
    }


    public void solveHeuristic(PrintWriter output){             //This method is used to solve the puzzle with the use of a heuristic. It uses A* Search
        unexpanded.add(rootNode);                       //Add the root node to the unexpanded list
        while(!unexpanded.isEmpty()){               //While there are unexpanded nodes keep the search going
            int index = selectForExpansion();       //Get the index of the node selected for expansion
            Node n = unexpanded.get(index);      //Get the node selected for expansion
            unexpanded.remove(index);           //Remove the node from unexpanded
            expanded.add(n);                    //Add the node to the expanded list of nodes
            if(n.state.isGoal()){                   //Check if the state is the goal
                reportSolution(n, output);      //If it is, output the solution and exit
                return;
            }else{                          //Otherwise expand the node
            ArrayList<GameState> moves = n.state.possibleMoves(); //Get all the states that are reachable in one move from the current state
            for(GameState gs : moves){              //For all the possible states make sure they aren't already in the unexpanded or the expanded list
                if(Node.findNodeWithState(unexpanded,gs)==null && Node.findNodeWithState(expanded,gs)==null){
                    unexpanded.add(new Node(gs,n,n.getCost()+1)); //If the state hasn't been found before and it isn't on the list of nodes to be expanded add it to the unexpanded list
                }
            }
        }
        }
        output.println("No solution found");
    }

    public static void main(String[] args) throws Exception {       //This is the main method. It creates a Solver object and solves the puzzle
        Scanner input = new Scanner(System.in);
        int i=0;
        Solver s = new Solver(GameState.INITIAL_BOARD);  // Set up the problem to be solved.
        File outFile = new File("output.txt");                 // Create a file as the destination for output
        System.out.println("Chose the method to solve the puzzle: \n1)With a heuristic \n2)Without a heuristic (WARNING! This may take up to 15 minutes)");
        try{
            while (i!=1 && i!=2){
            i = input.nextInt();
            if(i!=1&&i!=2)
                System.out.println("Please choose one of the options");
            }
        }catch(Exception e){
            System.out.println("Invalid input, program has stopped");
        }
        PrintWriter output = new PrintWriter(outFile);         // Create a PrintWriter for that file
        if(i==1)
            s.solveHeuristic(output);                                 // Search for and print the solution
        else
            s.solveNonHeuristic(output);
        System.out.println("Completed!");
        output.close();                                        // Close the PrintWriter (to ensure output is produced).
    }
}
