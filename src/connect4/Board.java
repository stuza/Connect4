/*
 *  An interface designed to be convenient for the Controller and View to use. 
 */
package connect4;

public interface Board {
    
    /**
     * get board representing the game state.
     * @return the 2d array of strings representing the game state
     */
    String[][] getBoard();
    
    
     /**
     * get number of columns.
     * @return the number of columns in the board.
     */
    int getColumns();
    
    
    /**
     * get number of rows.
     * @return the number of rows in the board.
     */
    int getRows();
    
       
    /**
     * canInsert checks the top row in a column and returns true if it's empty
     * @param column
     * @return true if game_board[column][numRows-1] == null, false if game_board[column][numRows-1] != null
     */
    boolean canInsert(int column);
        
    
    /**
     * insert a disc of "color" into a given "column" on the board and notify
     * any observers of the change.     *
     * @param color of disc to insert
     * @param column into which we insert the disc
     * @return result true if insertion was successful, else return false
     */
    void insert(int column, String color);
    
    
    /**
     * getWinner() returns a string representing the winner. If there is no
     * winner yet, that string will be null.
     */
    String getWinner();
    
    
    /**
     * isFull returns true when the board is full
     * @return true if the board is full
     */
    boolean isFull();
    
    
    /**
     * reset the board to its default state
     */
    void reset();
}
