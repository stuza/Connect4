package connect4;
/**
 *
 * @author Stuart McKenzie, 10077518
 */
import javax.swing.*;
import java.awt.*;

public class Connect4Panel extends JPanel {
    
    private static final Dimension PANEL_SIZE = new Dimension(350,300);
    private static final int PANEL_WIDTH = PANEL_SIZE.width;
    private static final int PANEL_HEIGHT = PANEL_SIZE.height;
    private static final int DISC_SIZE = PANEL_WIDTH/7;
    private static final int DISC_Y = PANEL_HEIGHT-DISC_SIZE;
    private final Connect4Board board;
    
    
    /**
     * Connect4Panel constructor
     * @param board 
     */
    public Connect4Panel(Connect4Board board) {
        setBackground(Color.BLUE);
        this.board = board;
    }
    
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int numRows = board.getRows();
        int numColumns = board.getColumns();
        String game_board[][] = board.getBoard();
        
        int row, column;
        for (column=0;column<numColumns;column++) {
            for (row=0;row<numRows;row++) {
                if (game_board[column][row]==null) {
                    g.setColor(Color.BLACK);
                    g.fillOval(DISC_SIZE*column,DISC_Y-(DISC_SIZE*row),DISC_SIZE,DISC_SIZE);
                }
                else {
                    g.setColor(game_board[column][row].equals("Red") ? Color.RED : Color.YELLOW);
                    g.fillOval(DISC_SIZE*column,DISC_Y-(DISC_SIZE*row),DISC_SIZE,DISC_SIZE);
                }               
            }
        }
    }
    
    
    @Override
    public Dimension getPreferredSize() {
        return PANEL_SIZE;
    }
    
    @Override
    public void setPreferredSize(Dimension d) {
    }
    
    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }
}
