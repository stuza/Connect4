package connect4;
/**
 * @author Stuart McKenzie, 10077518
 */

public class Connect4 {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(
            new Runnable() {
                @Override
                public void run () {
                    createAndShowGUI();
                }
            }
        );
    }

    public static void createAndShowGUI() {
        Connect4Board board = new Connect4Board();
        Connect4Controller controller = new Connect4Controller(board);
        Connect4GUI gui = new Connect4GUI(controller);
    }
}
