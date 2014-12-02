package connect4;

import java.util.Arrays;
import java.util.Observable;


/**
 *
 * @author Stuart McKenzie, 10077518
 */
public class Connect4Board extends Observable implements Board {
    private /*@ spec_public @*/ String winner;
    private /*@ spec_public @*/ /*@ non_null @*/ final static int numRows = 6;
    private /*@ spec_public @*/ /*@ non_null @*/ final static int numColumns = 7;
    private /*@ spec_public @*/ /*@ non_null @*/ String gameBoard[][];
    //@ invariant numRows==6;
    //@ invariant numColumns==7;
    //@ assignable gameBoard;
    //@ assignable winner;
    
    /**
     * Connect4Board constructor calls init() function to allow the board to easily be reset
     * to its default state
     */
    public Connect4Board() {
        init();
    }
    
    /**
     * Connect4Board copy constructor.
     * @param anotherBoard 
     */
    public Connect4Board(Connect4Board anotherBoard) {
        init();
        this.gameBoard = copy2dStringArray(anotherBoard.getBoard());     
    }
    
    
    /**
     * init() function called by constructor and used to reset board to default
     * state. Will notify observers of the change.
     * Declared as public final void to prevent being overridden.
     * Notifies observers
     */
    //@assignable gameBoard;
    //@assignable winner;
    //@ensures winner==null;
    //@ensures gameBoard != null;
    public final void init() {
        winner = null;
        gameBoard = new String[][]{
        {null,null,null,null,null,null,},
        {null,null,null,null,null,null,},
        {null,null,null,null,null,null,},
        {null,null,null,null,null,null,},
        {null,null,null,null,null,null,},
        {null,null,null,null,null,null,},
        {null,null,null,null,null,null,}
        };
        setChanged();
        notifyObservers();
    }
        
    /**
     * get board representing the game state.
     * @return the 2d array of strings representing the game state
     */
    //@ensures \result == gameBoard;
    @Override
    public String[][] getBoard() {
        return gameBoard;
    }
    

    //@ensures \result == numColumns;
    @Override
    public int getColumns() {
        return numColumns;
    }
    
    
    //@ensures \result == numRows;
    @Override
    public int getRows() {
        return numRows;
    }
    
    /**
     * canInsert checks the top row in a column and returns true if it's empty
     * @param column
     * @return true if gameBoard[column][numRows-1] == null, false if gameBoard[column][numRows-1] != null
     */
    //@requires column>=0 && column<numColumns-1;
    //@ensures gameBoard[column][numRows-1] == null ==> \result == true;
    //@ensures gameBoard[column][numRows-1] != null ==> \result == false;
    @Override
    public boolean canInsert(int column) {
        return (gameBoard[column][numRows-1] == null);
    }
    
    /**
     * insert a disc of "color" into a given "column" on the board and notify
     * any observers of the change.
     * @param color of disc to insert
     * @param column into which we insert the disc
     * Notifies Observers
     */
    //@assignable gameBoard;
    //@requires column>0 && column<numColumns-1;
    //@ensures gameBoard != \old(gameBoard);
    //@ensures (* observers are notified of change to board *)
    @Override
    public void insert(int column, String color) {
        //System.out.println("insert called");
        if(winner!=null) {
            return;
        }
        //find empty slot
        int row = 0;
        while(gameBoard[column][row] != null){
            row++;
        }
        //insert
        gameBoard[column][row] = color;
        winner=(isWinner(color,column,row) ? color : null);
        //Let the observers(the GUI) know of the change
        setChanged();
        notifyObservers();
    }
    
    /**
     * remove disc from column
     * @param column 
     */
    public void remove(int column) {
        int i;
	for (i=numRows-1; i>-1; i--) {
            if (gameBoard[column][i] != null) {
		gameBoard[column][i] = null;
		break;
            }
	}
        if (!isWinner("Yellow",column,i) && !isWinner("Red",column,i)) {
            winner=null;
        }
    }
    
    /**
     * isWinner checks if the game has been won and is called as part of the
     * insert() function each time a disc is inserted into the game board
     * @param color
     * @param column
     * @param row
     * @return true if there is a winner, false if there is not
     */
    //@requires color!=null;
    //@requires column>0 && column<numColumns-1;
    //@requires row>0 && row<numRows-1;
    //@ensures checkDirection(color,column,row,0,1) == true ==> \result == true;
    //@ensures checkDirection(color,column,row,1,0) == true ==> \result == true;
    //@ensures checkDirection(color,column,row,1,1) == true ==> \result == true;
    //@ensures checkDirection(color,column,row,-1,1) == true ==> \result == true;
    //@ensures checkDirection(color,column,row,0,1) == false ==> \result == false;
    //@ensures checkDirection(color,column,row,1,0) == false ==> \result == false;
    //@ensures checkDirection(color,column,row,1,1) == false ==> \result == false;
    //@ensures checkDirection(color,column,row,-1,1) == false ==> \result == false
    private boolean isWinner(String color, int column, int row) 
    {    
        return (
        checkDirection(color,column,row,0,1)|| //vertical
        checkDirection(color,column,row,1,0)|| //horizontal
        checkDirection(color,column,row,1,1)|| //positive diagonal
        checkDirection(color,column,row,-1,1)); //negative diagonal      
    }
    
    /**
     * check direction is called 4 times by the checkWinner() function. It takes
     * a starting point (column,row) and a vector(dx,dy) and will return true if
     * 4 or more discs of the same color are in a line along the vector from the
     * start point.
     * @param column
     * @param row
     * @param dx
     * @param dy
     * @return true if a line of 4 or more is detected, else return false
     */
    //@requires color!=null;
    //@requires column>0 && column<numColumns-1;
    //@requires row>0 && row<numRows-1;
    //@requires dx>= -1 && dx<=1;
    //@requires dy>= -1 && dy<=1;
    //@ensures (* sum >= 4 == true ==> \result == true *);
    //@ensures (* sum < 4 == false ==> \result == false *);
    private boolean checkDirection(String color, int column, int row, int dx, int dy) {
        int sum = 0; 
        int x = row;
        int y = column;           
        while(x>=0 && x<numRows && y>=0 && y<numColumns && gameBoard[y][x]!=null && gameBoard[y][x].equals(color)) {
            x += dx;
            y += dy;
            sum++;
        }        
        x = row - dx;
        y = column - dy;
        while(x>=0 && x<numRows && y>=0 && y<numColumns && gameBoard[y][x]!=null && gameBoard[y][x].equals(color)) {           
            x -= dx;
            y -= dy;
            sum++;
        }
        return (sum >= 4);	
    }
    
    
     /**
     * getWinner() returns a string representing the winner. If there is no
     * winner yet, that string will be null.
     * @return winner
     */
    //@ensures \result == winner;
    @Override
    public String getWinner() {
        return winner;
    }
    
    
    /**
     * isFull() tests if each column in the board can be inserted into. If a
     * column is found that can not be inserted into, return false. Else return
     * true
     */
    //@ensures (* board!=full ==> \result == true *);
    //@ensures (* board==full ==> \result == false *);
    @Override 
    public boolean isFull() {
        for (int column=0;column<numColumns;column++) {
            if (canInsert(column)) {
                return false;
            }
        }
        return true;
    }
    
        
    /**
     * reset the board to its default state
     */
    //ensures board != \old(board);
    @Override
    public void reset() {
        init();
    }
    
    
    /**
     * copy2dStringArray
     * @param source 2D String Array
     * @return copy of source
     */
    private String[][] copy2dStringArray(String[][] source) {
        String[][] copy = new String[source.length][];
        for(int i=0;i<source.length;i++) {
            copy[i] = Arrays.copyOf(source[i], source[i].length);
        }
        return copy;
    }
    
    /**
     * clearBoard sets all slots in the gameBoard to null.
     * Notifies observers.
     */
    public void clearBoard() {
        for (int i=0; i< numColumns-1; i++) {
            for (int j=0; j < numRows-1; j++) {
                gameBoard[i][j] = null;
            }
	}      
        setChanged();
        notifyObservers();
    }
        
}
