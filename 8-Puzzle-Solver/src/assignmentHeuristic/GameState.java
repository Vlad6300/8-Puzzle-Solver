package assignmentHeuristic;

import java.util.ArrayList;

class Position {            //This class is used to keep track of the position of a square in the puzzle
    int x;
    int y;

    Position(int x, int y) {        //Constructor for the class. The inputs are the x and y coordinates
        this.x = x;
        this.y = y;
    }

    boolean isAdj(Position p) {                    //This method verifies if two cubes are adjacent
        if (x == p.x && (y == p.y + 1 || y == p.y - 1))
            return true;
        if(y==p.y &&(x==p.x+1 || x==p.x-1))
            return true;
        return false;
    }
}

public class GameState {    //This class represents the state of the board and various actions related to the board
    final char[]board;      //This array holds the position of the cubes
    private int spacePos;   //This is the index of the empty space
    static final char[] INITIAL_BOARD = {'8','7','6','5','4','3','2','1',' '};  //This is the initial configuration
    static final char[] GOAL_BOARD = {'1','2','3','4','5','6','7','8',' '};     //This is the goal configuration

    public GameState(char[] state){         //This is the constructor for the class. The input is a char array that represents a configuration
        this.board=state;
        for(int i=0;i<board.length;i++){
            if(board[i] == ' '){
                spacePos=i;
                break;
            }
        }
    }


    public GameState clone(){           //This method creates and returns a new GameState object with the same characteristics as the original
        char[] clonedBoard = new char[9];
        System.arraycopy(this.board, 0, clonedBoard, 0, 9);
        return new GameState(clonedBoard);
    }

    public int getSpacePos() {
        return spacePos;
    }       //This method returns the index of the empty space

    public String toString() {              //This method outputs the character array in a 3-by-3 configuration
        String s = "";
        int i=0;
        for (char c : this.board) {
            i++;
            if(i>3){
                i=1;
                s+="\n";
            }
            s = s +" "+ c;
        }
        return s;
    }

    public boolean isGoal() {           //This method checks if the current board has the same configuration as the goal
        for (int i = 0; i < 9; i++) {
            if (this.board[i] != GOAL_BOARD[i]) return false;
        }
        return true;
    }

    public boolean sameBoard (GameState state) {    //This method is used to check if two boards are the same
        for (int i = 0; i < board.length; i++) {
            if (this.board[i] != state.board[i]) return false;
        }
        return true;
    }

    static boolean sameBoard (GameState state1, GameState state2) {     //This is the static version of the sameBoard method
        for (int i = 0; i < 9; i++) {
            if (state1.board[i] != state2.board[i]) return false;
        }
        return true;
    }
    //This method is used to figure out the heuristic score of a state.
    //The heuristic uses the sum of the Manhattan distances of the tiles from their goal.
    //I have found this heuristic online. I have attached a link to the pdf where I read about this heuristic.
    //https://www.cse.iitk.ac.in/users/cs365/2009/ppt/13jan_Aman.pdf
    public int heuristic(){
        int sum=0,temp;
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(this.board[i]==GameState.GOAL_BOARD[j]){
                    temp=Math.abs(i-j);
                    sum+=(temp/3)+(temp%3);             //By using div(/) and modulo(%) we can figure out what the distance is between the goal position and the current position
                }
            }
        }
        return sum;
    }



    public ArrayList<Integer> legalMoves(GameState state){          //Given a GameState, this method returns a list comprising of the inedices of the squares that can move into the empty space
        int index = state.spacePos;
        ArrayList<Integer> moves = new ArrayList<Integer>();
        Position posSpace = new Position(index%3,index/3);      //Keeps track of the position of the space
        for(int i=0;i<9;i++)                                        //This loop goes through each position on the board
            if(state.board[i]!=' ') {                               //Skip over the space position
                Position posIndex = new Position(i % 3, i / 3); //Get the position of the square
                if(posSpace.isAdj(posIndex)){                       //Check if the square is adjacent to the empty space
                    moves.add(i);                                   //If it's adjacent, add the index to the list
                }
            }
        return moves;
    }

    public ArrayList<GameState> possibleMoves() {               //This method returns a list of GameState objects comprising of all states that are reachable in one move from the current GameState
        ArrayList<GameState> states = new ArrayList<GameState>();
        int index = getSpacePos();
        ArrayList<Integer> moves = legalMoves(this);       //Get a list of all the moves that can be made
        char temp;
        for(Integer i : moves){                                //For every square that can be moved, create the GameState that would result from that move
            GameState copy = this.clone();
            temp = copy.board[i];
            copy.board[i]=copy.board[index];
            copy.board[index]=temp;
            copy.spacePos=i;
            states.add(copy);
        }
        return states;                                          //Return the list of possible states.
    }
}
