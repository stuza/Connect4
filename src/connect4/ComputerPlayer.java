package connect4;

import java.util.Arrays;
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
        humanColor = controller.getHumanColor();       
    }
    
    /**
     * This method calls various functions that go towards making a move
     */
    public void move() {
        Connect4Board copyBoard = new Connect4Board(controller.getBoard());//possibly a bit memory hungry but makes use of copy constructor
        if(tryToWin(copyBoard)) {return;}
        if(blockOpponent(copyBoard)) {return;}
        boolean[] badMoves = findBadMoves(copyBoard);
        pickBestColumn(copyBoard, badMoves);    
    }
    
    private boolean tryToWin(Connect4Board board) {
        for (int i=0; i<controller.getColumns();i++) {
            if(board.canInsert(i)) {
                board.insert(i,cpuColor);
                if(board.getWinner()!=null && board.getWinner().equals(cpuColor)) {
                    //System.out.println("trying tryToWin");
                    controller.move(i, cpuColor);
                    return true;
                }
                board.remove(i);
            }
        }
        return false;
    }
    
    private boolean blockOpponent(Connect4Board board) {
        for (int i=0; i<controller.getColumns();i++) {
            if(board.canInsert(i)) {
                board.insert(i,humanColor);
                if(board.getWinner()!=null && board.getWinner().equals(humanColor)) {
                    //System.out.println("trying blockOpponent");
                    controller.move(i, cpuColor);
                    return true;
                }
                board.remove(i);
            }
        }
        return false;
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
        if(!badMoves[bestColumn] && controller.canInsert(bestColumn)) {
            //System.out.println("trying bestColumn="+bestColumn+" highestSlotValue= "+highestSlotValue+" badMoves[]= "+Arrays.toString(badMoves));
            controller.move(bestColumn,cpuColor);
        }
        else if(!badMoves[secondBestColumn] && controller.canInsert(secondBestColumn)) {
            //System.out.println("trying secondBestColumn= "+secondBestColumn+" bestColumn= "+bestColumn+" badMoves[]= "+Arrays.toString(badMoves));
            controller.move(secondBestColumn,cpuColor);
        }
        else {//find first available non-bad slot 
            for (int j=0; j<controller.getColumns(); j++) {
                if(!badMoves[j] && controller.canInsert(j)) {
                    //System.out.println("trying first free non-bad column= "+j+" bestColumn= "+bestColumn+" secondBestColumn= "+secondBestColumn+" badMoves[]= "+Arrays.toString(badMoves));
                    controller.move(j, cpuColor);
                    return;
                }
            }//then just simply any free slot
            for (int k=0; k<controller.getColumns(); k++) {
                if(controller.canInsert(k)) {
                    //System.out.println("trying any free column= "+k+" badMoves[]= "+Arrays.toString(badMoves));
                    controller.move(k, cpuColor);
                    return;
                }
            }          
        }         
    }      
    
    @Override
    public final void update(java.util.Observable o, Object arg) {
        if (controller.getPlayVsCpu() && controller.getCurrentPlayer()!=null && 
                controller.getCurrentPlayer().equals(cpuColor) &&
                    controller.getWinner()==null) {
            move();
        }
    }
           
    
}
