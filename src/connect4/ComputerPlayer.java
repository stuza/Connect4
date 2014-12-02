package connect4;

import java.util.Observer;

/**
 *
 * @author Stuart McKenzie, 10077518
 */
public class ComputerPlayer implements Observer {    
    private final static int[][] evaluationTable = {{3, 4, 5, 5, 4, 3},
                                                    {4, 6, 8, 8, 6, 4},
                                                    {13, 8,11,11, 8, 5},
                                                    {14,14,12,12,10, 7},
                                                    {13, 8,11,11, 8, 5},
                                                    {4, 6, 8, 8, 6, 4},
                                                    {3, 4, 5, 5, 4, 3}};
    
    Connect4Controller controller;
    String cpuColor;
    String humanColor;
    
    public ComputerPlayer(Connect4Controller controller) {
        this.controller = controller;
        cpuColor = controller.getCpuColor();
        humanColor=controller.getHumanColor();       
    }
    
    /**
     * This method calls various functions that go towards making a move
     */
    public void move() {
        Connect4Board copyBoard = new Connect4Board(controller.getBoard());//possibly a bit memory hungry but makes use of copy constructor
        tryToWin(copyBoard);
        blockOpponent(copyBoard);
        boolean[] badMoves = findBadMoves(copyBoard);
        pickBestColumn(copyBoard, badMoves);    
    }
    
    private void tryToWin(Connect4Board board) {
        for (int i=0; i<controller.getColumns();i++) {
            if(board.canInsert(i)) {
                board.insert(i,cpuColor);
                if(board.getWinner()!=null && board.getWinner().equals(cpuColor)) {
                    controller.move(i, cpuColor);
                }
                board.remove(i);
            }
        }
    }
    
    private void blockOpponent(Connect4Board board) {
        for (int i=0; i<controller.getColumns();i++) {
            if(board.canInsert(i)) {
                board.insert(i,humanColor);
                if(board.getWinner()!=null && board.getWinner().equals(humanColor)) {
                    controller.move(i, cpuColor);
                }
                board.remove(i);
            }
        }
    }
    
    private boolean[] findBadMoves(Connect4Board board) {
        boolean[] badMoves = new boolean[board.getColumns()];
        for (int i=0; i<board.getColumns();i++) {
            badMoves[i] = false;
        }
        for (int j=0; j<board.getColumns();j++) {
            if(board.canInsert(j)) {
                board.insert(j,cpuColor);
                if (board.canInsert(j)) {
                    board.insert(j,humanColor);
                    if (board.getWinner()!=null && board.getWinner().equals(humanColor)) {
                        badMoves[j]= true;
                    }
                    board.remove(j);
                }
                board.remove(j);
            }
        }
        return badMoves;
    }
    
    private void pickBestColumn(Connect4Board board, boolean[] badMoves) {
        //check our evaluation table to find the "most valuable" free slot
        //that isn't a "bad move".
        int bestColumn = 0;
        int secondBestColumn = 0;
        int highestSlotValue = 0;
        int row;
        for (int i=0; i<board.getColumns(); i++) {
            row = 0;
            while(board.getBoard()[i][row]!=null && row<controller.getRows()-1){
                row++;
            }
            if(board.canInsert(i) && evaluationTable[i][row]>highestSlotValue) {
                highestSlotValue = evaluationTable[i][row];
                secondBestColumn = bestColumn;
                bestColumn = i;
            }
        }
        //try to insert in bestColumn. If thats a bad move the try second best
        //column. If that's a bad move, insert in the first column that isn't
        //bad. If all moves are bad, it doesn't matter what we do
        if(!badMoves[bestColumn]) {
            controller.move(bestColumn,cpuColor);
        }
        else if(!badMoves[secondBestColumn]) {
            controller.move(secondBestColumn,cpuColor);
        }
        else {//find first available non-bad slot then just simply any free slot
            for (int j=0; j<badMoves.length; j++) {
                if(!badMoves[j] && board.canInsert(j)) {
                    controller.move(j, cpuColor);
                    return;
                }
            }
            for (int k=0; k<board.getColumns(); k++) {
                if(board.canInsert(k)) {
                    controller.move(k, cpuColor);
                }
            }          
        }         
    }      
    
    @Override
    public final void update(java.util.Observable o, Object arg) {
         if (controller.getPlayVsCpu() && controller.getCurrentPlayer().equals(cpuColor)) {
            move();
         }
    }
           
    
}
