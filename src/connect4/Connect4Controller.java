package connect4;

/**
 *
 * @author Stuart McKenzie 10077518
 */
public class Connect4Controller {
    private final Connect4Board board;
    private final ComputerPlayer cpuPlayer;
    private final String cpuColor;
    private final String humanColor;
    private String currentPlayer;
    private String lastPlayerToStart;
    private boolean playVsCpu;   
    private int redScore = 0;
    private int yellowScore = 0;
    
    
    public Connect4Controller(Connect4Board board) {
        this.board = board;
        playVsCpu = false;
        cpuColor = "Yellow";//needs to be random
        humanColor=(cpuColor.equals("Yellow") ? "Red" : "Yellow");
        currentPlayer = "Red";//needs to be random
        lastPlayerToStart = currentPlayer;
        cpuPlayer = new ComputerPlayer(this);
        this.board.addObserver(cpuPlayer);
    }
    
    public boolean getPlayVsCpu() {
        return playVsCpu;
    }
    
    public void setPlayVsCpu(boolean choice) {
        playVsCpu = choice;
    }
    
    public int getColumns() {
        return board.getColumns();
    }
    
    public int getRows() {
        return board.getRows();
    }
    
    public int getRedScore() {
        return redScore;
    }
    
    public void setRedScore(int redScore) {
        this.redScore = redScore;
    }
    
    public int getYellowScore() {
        return yellowScore;
    }
    
    public void setYellowScore(int yellowScore) {
        this.yellowScore = yellowScore;
    }
    
    public String getCpuColor() {
        return cpuColor;
    }
    
    public String getHumanColor() {
        return humanColor;
    }
    
    public String getWinner() {
        return board.getWinner();
    }
    
    public Connect4Board getBoard() {
        return board;
    }
    
    public String getCurrentPlayer() {
        return currentPlayer;
    }
      
    public boolean canInsert(int column) {
        return board.canInsert(column);
    }
    
    public void move(int column, String color) {
        if (color == null) { return; }
        if (canInsert(column) && color.equals(currentPlayer) && getWinner()==null) {
            currentPlayer=(currentPlayer.equals("Yellow") ? "Red" : "Yellow");
            board.insert(column,color);
        }
    }
    
    public boolean isFull() {
        return board.isFull();
    }
    
    public void newGame() {
        currentPlayer=(lastPlayerToStart.equals("Yellow") ? "Red" : "Yellow");
        lastPlayerToStart=currentPlayer;
        board.reset();
    }
    
    public void endGame() {
        currentPlayer = null;
        board.clearBoard();
    }
   
}
