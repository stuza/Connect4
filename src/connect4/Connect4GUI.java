package connect4;
/**
 *
 * @author Stuart McKenzie, 10077518
 */

import java.awt.event.*;
import java.awt.*;
import java.util.Observer;
import javax.swing.*;

public class Connect4GUI implements Observer, MouseListener, ActionListener {
    
    private static final String TITLE = "Connect4";
    private static final Dimension PANEL_SIZE = new Dimension(350,300);
    private static final int DISC_SIZE = PANEL_SIZE.width/7;
    private final JTextField alertField = new JTextField(30);
    
    private JFrame gameFrame,menuFrame;
    private Connect4Panel gamePanel;
    private JPanel menuPanel;
    
    private JButton buttonNewGame,buttonResetScores,buttonEndGame,buttonVsCpu;
    private JTextField redScoreField,yellowScoreField;
    private JLabel redScoreLabel,yellowScoreLabel;
    
    private final Connect4Controller controller;
    
    public Connect4GUI(Connect4Controller controller) {
        this.controller = controller;
        controller.getBoard().addObserver(Connect4GUI.this);
        
        createControlFrame();
        createBoardFrame();
        controller.getBoard().init();
        update(controller.getBoard(),null);
    }
  
    private void createControlFrame() {
        menuFrame = new JFrame(TITLE);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
        Container contentPane = menuFrame.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        
        /*create alert field*/
        alertField.setHorizontalAlignment(JTextField.TRAILING);
        alertField.setEditable(false);
        alertField.setHorizontalAlignment(JTextField.CENTER);
        updateAlertField(controller.getCurrentPlayer() + " to go first");
        contentPane.add(alertField);
                
        /*create panel*/
        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(2,4));
        //score fields
        redScoreLabel = new JLabel("Red score:");
        yellowScoreLabel = new JLabel("Yellow score:");
        redScoreField = new JTextField(3);     
        redScoreField.setEditable(false);
        redScoreField.setText(Integer.toString(controller.getRedScore()));
        yellowScoreField = new JTextField(3);
        yellowScoreField.setEditable(false);
        yellowScoreField.setText(Integer.toString(controller.getYellowScore()));
        //buttons
        buttonNewGame = new JButton("New Game");
        buttonNewGame.addActionListener(Connect4GUI.this);
        buttonResetScores = new JButton("Reset Scores");
        buttonResetScores.addActionListener(Connect4GUI.this);
        buttonEndGame = new JButton("End Game");
        buttonEndGame.addActionListener(Connect4GUI.this);
        buttonVsCpu = new JButton("");
        if (controller.getPlayVsCpu()) {
            buttonVsCpu.setText("2 Player");
        }
        else {
            buttonVsCpu.setText("Play Vs CPU");
        }
        buttonVsCpu.addActionListener(Connect4GUI.this);
        //Add things to the panel
        menuPanel.add(redScoreLabel);
        menuPanel.add(redScoreField);
        menuPanel.add(yellowScoreLabel);
        menuPanel.add(yellowScoreField);
        menuPanel.add(buttonNewGame);
        menuPanel.add(buttonResetScores);
        menuPanel.add(buttonEndGame);
        menuPanel.add(buttonVsCpu);
        contentPane.add(menuPanel);
        
        menuFrame.pack();
        menuFrame.setResizable(false);
        menuFrame.setVisible(true);
    }
     
    private void createBoardFrame() {
        //create the frame itself
        gameFrame = new JFrame(TITLE);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = gameFrame.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));      
        //create a game panel
        gamePanel = new Connect4Panel(controller.getBoard());
        gamePanel.addMouseListener(Connect4GUI.this);
        contentPane.add(gamePanel);
        
        gameFrame.pack();
        gameFrame.setResizable(false);
        gameFrame.setVisible(true);
    }
    
    private void updateAlertField(String s) {
        alertField.setText(s);
    }
    
    private void clearScores() {
        controller.setRedScore(0);
        controller.setYellowScore(0);
        yellowScoreField.setText(Integer.toString(controller.getYellowScore()));
        redScoreField.setText(Integer.toString(controller.getRedScore()));
    }
    
    private void endGame() {
        controller.endGame();     
    }
    
    private void newGame() {
        controller.newGame();
    }
    
    private void switchMode() {
        if(controller.getPlayVsCpu()) {
            clearScores();
            controller.setPlayVsCpu(false);
            newGame();
            buttonVsCpu.setText("Play Vs CPU");
        }
        else {
            clearScores();
            controller.setPlayVsCpu(true);
            newGame();
            buttonVsCpu.setText("2 Player");
        }
    }
 
    
    @Override
     public final void update(java.util.Observable o, Object arg) {
        if (controller.isFull()) {//board full no winner
            updateAlertField("Game drawn");
        }
        else if (controller.getWinner()!=null) {//game has been won
            updateAlertField(controller.getWinner()+ " wins!");
            if (controller.getWinner().equals("Red")) {
                controller.setRedScore(controller.getRedScore()+1);
                redScoreField.setText(Integer.toString(controller.getRedScore()));
            }
            else {
                controller.setYellowScore(controller.getYellowScore()+1);
                yellowScoreField.setText(Integer.toString(controller.getYellowScore()));
            }
        }
        else if (controller.getCurrentPlayer() == null) {
            updateAlertField("Game Ended");
        }
        else{//no winner game in progress     
            updateAlertField(controller.getCurrentPlayer()+"'s turn");
        }
        gameFrame.repaint();
        menuFrame.repaint();
    }
  
     
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == buttonNewGame){
            newGame();
        }
        else if (event.getSource() == buttonResetScores) {
            clearScores();
        }
        else if (event.getSource() == buttonEndGame) {
            endGame();
        }
        else if (event.getSource() == buttonVsCpu) {
            switchMode();
        }
    }
   
    @Override
    public void mousePressed(MouseEvent e) {
        move((int)e.getX()/DISC_SIZE,controller.getCurrentPlayer());
    }
    
    @Override public void mouseExited(MouseEvent e){
    }
       
    @Override public void mouseEntered(MouseEvent e){
    }
       
    @Override public void mouseReleased(MouseEvent e){
    }
       
    @Override public void mouseClicked(MouseEvent e){
    }
    
    private void move(int column, String color) {
        if (controller.getPlayVsCpu()) {
            controller.move(column,controller.getHumanColor());
        }
        else { //We're in 2 player mode
            controller.move(column,color);
        }
    }
    
}
