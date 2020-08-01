package assignment;

import java.util.ArrayList;

class Position {
    int x;
    int y;

    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    boolean isAdj(Position p) {
        if (x == p.x && (y == p.y + 1 || y == p.y - 1))
            return true;
        if(y==p.y &&(x==p.x+1 || x==p.x-1))
            return true;
        return false;
    }
}

public class GameState {
    final char[]board;
    private int spacePos;
    static final char[] INITIAL_BOARD = {'8','7','6','5','4','3','2','1',' '};
    static final char[] GOAL_BOARD = {'1','2','3','4','5','6','7','8',' '};

    public GameState(char[] state){
        this.board=state;
        for(int i=0;i<board.length;i++){
            if(board[i] == ' '){
                spacePos=i;
                break;
            }
        }
    }


    public GameState clone(){
        char[] clonedBoard = new char[9];
        System.arraycopy(this.board, 0, clonedBoard, 0, 9);
        return new GameState(clonedBoard);
    }

    public int getSpacePos() {
        return spacePos;
    }

    public String toString() {
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

    public boolean isGoal() {
        for (int i = 0; i < 9; i++) {
            if (this.board[i] != GOAL_BOARD[i]) return false;
        }
        return true;
    }

    static boolean sameBoard (GameState state1, GameState state2) {
        for (int i = 0; i < 9; i++) {
            if (state1.board[i] != state2.board[i]) return false;
        }
        return true;
    }

    public boolean sameBoard (GameState state) {
        for (int i = 0; i < board.length; i++) {
            //System.out.println("Characters "+Integer.toString(i)+": "+this.board[i]+" "+state.board[i]);
            if (this.board[i] != state.board[i]) return false;
        }
        return true;
    }

    public ArrayList<Integer> legalMoves(GameState state){
        int index = state.spacePos;
        ArrayList<Integer> moves = new ArrayList<Integer>();
        Position posSpace = new Position(index%3,index/3);
        for(int i=0;i<9;i++)
            if(state.board[i]!=' ') {
                Position posIndex = new Position(i % 3, i / 3);
                if(posSpace.isAdj(posIndex)){
                    moves.add(i);
                }
            }
        return moves;
    }

    public ArrayList<GameState> possibleMoves() {
        ArrayList<GameState> states = new ArrayList<GameState>();
        int index = getSpacePos();
        ArrayList<Integer> moves = legalMoves(this);
        char temp;
        for(Integer i : moves){
            GameState copy = this.clone();
            temp = copy.board[i];
            copy.board[i]=copy.board[index];
            copy.board[index]=temp;
            copy.spacePos=i;
            states.add(copy);
        }
        return states;
    }
}
