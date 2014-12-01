package connect4;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Stuart McKenzie, 10077518
 */
public class Connect4BoardTest {
    
    public Connect4BoardTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Create a full board with no winning combination. Assert that
     * the board is full, and there is no winner. Clear the board and assert
     * that the board is not full.
     */
    @Test
    public void test1() {
        System.out.println("Test 1 entered");
        int column = 0;
        Connect4Board board = new Connect4Board();
        assertTrue(board.canInsert(column));
        board.insert(column,"Red");
        board.insert(column,"Red");
        board.insert(column,"Red");
        board.insert(column,"Yellow");
        board.insert(column,"Yellow");
        board.insert(column,"Yellow");
        column++;
        assertTrue(board.canInsert(column));
        board.insert(column,"Yellow");
        board.insert(column,"Yellow");
        board.insert(column,"Yellow");
        board.insert(column,"Red");
        board.insert(column,"Red");
        board.insert(column,"Red");
        column++;
        assertTrue(board.canInsert(column));
        board.insert(column,"Red");
        board.insert(column,"Red");
        board.insert(column,"Red");
        board.insert(column,"Yellow");
        board.insert(column,"Yellow");
        board.insert(column,"Yellow");
        column++;
        assertTrue(board.canInsert(column));
        board.insert(column,"Yellow");
        board.insert(column,"Yellow");
        board.insert(column,"Yellow");
        board.insert(column,"Red");
        board.insert(column,"Red");
        board.insert(column,"Red");
        column++;
        assertTrue(board.canInsert(column));
        board.insert(column,"Red");
        board.insert(column,"Red");
        board.insert(column,"Red");
        board.insert(column,"Yellow");
        board.insert(column,"Yellow");
        board.insert(column,"Yellow");
        column++;
        assertTrue(board.canInsert(column));
        board.insert(column,"Yellow");
        board.insert(column,"Yellow");
        board.insert(column,"Yellow");
        board.insert(column,"Red");
        board.insert(column,"Red");
        board.insert(column,"Red");
        column++;
        assertTrue(board.canInsert(column));
        board.insert(column,"Red");
        board.insert(column,"Red");
        board.insert(column,"Red");
        board.insert(column,"Yellow");
        board.insert(column,"Yellow");
        board.insert(column,"Yellow");
        
        column = 0;
        while (column < board.getColumns()-1) {
            assertFalse(board.canInsert(column));
            column++;
        }
        
        assertTrue(board.isFull());
        assertTrue(board.getWinner()==null);
        
        //clear board
        board.init();
        assertFalse(board.isFull());
        
        System.out.println("Test 1 completed");      
    }
    
    /**
     * Create a winning line of 4 Yellow discs. Then try to insert 3 more discs
     * into the same column and assert that column is still not full. This
     * demonstrates that once a win is detected, the insert function will not
     * insert any more discs.
     */
    @Test
    public void test2() {
        System.out.println("Test 2 entered");
        
        Connect4Board board = new Connect4Board();
        int column = 0;
        board.insert(column,"Yellow");
        board.insert(column,"Yellow");
        board.insert(column,"Yellow");
        board.insert(column,"Yellow");
        assertTrue(board.getWinner().equals("Yellow"));
        
        //Now there is a winner, insert should do nothing so even after
        //inserting another 3 discs, canInsert(column) should still return true.
        board.insert(column,"Yellow");
        board.insert(column,"Yellow");
        board.insert(column,"Yellow");
        //After inserting 7 discs, canInsert(column) would normally return false
        //but here we expect true. See above comment.
        assertTrue(board.canInsert(column));
        
        System.out.println("Test 2 completed");
    }
    
    /**
     * Create a row of 3 yellow discs along the bottom, an empty slot, then a
     * row of 3 red pieces. First insert a Yellow disc and assert that a
     * yellow winner has been detected. Then reset the board and try again with
     * a red disc and assert that a red winner has been detected.
     */
    @Test
    public void test3() {
        System.out.println("Test 3 entered");
        Connect4Board board = new Connect4Board();
        //set up board with a line of 3 reds and a line of 3 yellows
        int column = 0;
        assertTrue(board.canInsert(column));
        board.insert(column,"Yellow");
        column++;
        assertTrue(board.canInsert(column));
        board.insert(column,"Yellow");
        column++;
        assertTrue(board.canInsert(column));
        board.insert(column,"Yellow");
        column++;
        column++;
        assertTrue(board.canInsert(column));
        board.insert(column,"Red");
        column++;
        assertTrue(board.canInsert(column));
        board.insert(column,"Red");
        column++;
        assertTrue(board.canInsert(column));
        board.insert(column,"Red"); 
        assertTrue(board.getWinner() == null);
       
        //create and assert yellow winner
        assertTrue(board.canInsert(3));
        board.insert(3,"Yellow");
        assertTrue(board.getWinner().equals("Yellow"));
        
        //reset board
        board.init();  
        //set up board with a line of 3 reds and a line of 3 yellows
        column = 0;
        assertTrue(board.canInsert(column));
        board.insert(column,"Yellow");
        column++;
        assertTrue(board.canInsert(column));
        board.insert(column,"Yellow");
        column++;
        assertTrue(board.canInsert(column));
        board.insert(column,"Yellow");
        column++;
        column++;
        assertTrue(board.canInsert(column));
        board.insert(column,"Red");
        column++;
        assertTrue(board.canInsert(column));
        board.insert(column,"Red");
        column++;
        assertTrue(board.canInsert(column));
        board.insert(column,"Red"); 
        assertTrue(board.getWinner() == null);
        
        //create and assert red winner
        assertTrue(board.canInsert(3));
        board.insert(3,"Red");
        assertTrue(board.getWinner().equals("Red"));
        
        System.out.println("Test 3 completed");
    }
}