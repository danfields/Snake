import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * BlockDisplay is used to display the contents of the
 * game board.
 * 
 * @author Adheet Gahesh
 * @version Feb 22, 2019
 * Changed to add code so the window is the right size and can be resized on PC.
 *
 * @author Anu Datar
 * @version Feb 20, 2016
 * Changed block size and added a split panel display for next block and Score.
 *
 * @author Ryan Adolf
 * @version Feb 16, 2016
 * Fixed the lag issue with block rendering.
 * Removed the JPanel
 * 
 * @author  Dave Feinberg
 * @author  Richard Page
 * @author  Susan King     Added documentation
 * @version May 13, 2015
 */
// Used to display the contents of a game board
public class BlockDisplay extends JComponent implements KeyListener
{
    private static final Color BACKGROUND = Color.WHITE;
    private static final Color BORDER = Color.BLACK;

    private static final int OUTLINE = 2;
    private static int blockWidth = 30;
    private static int blockHeight = 30;

    private MyBoundedGrid<Block> board;
    private JFrame frame;
    private ArrowListener listener;


    /**
     * Constructs a new display for displaying the given board.
     * 
     * @param board   the grid on which the game is to be played
     */
    public BlockDisplay(MyBoundedGrid<Block> board)
    {
        this.board = board;

        /* 
         * Schedules a job for the event-dispatching thread, which
         * creates and shows this application's GUI.
         */
        SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    createAndShowGUI();
                }
            });

        /*
         * Waits until display has been drawn.
         */
        try
        {
            while (frame == null || !frame.isVisible())
                Thread.sleep(1);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI()
    {
        //Create and set up the window.
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.addKeyListener(this);

        //Display the window.
        this.setPreferredSize(new Dimension(
                blockWidth * board.getNumCols(),
                blockHeight * board.getNumRows()));

        frame.pack();
        frame.setVisible(true);
    }

    /**
     *  Redraws the board to include the pieces and border colors
     *  Uses fillRect instead of drawing JButton as earlier to 
     *  render each block cleanly and quickly.
     *  @param g the graphics object that lets you repaint the screen 
     */
    public void paintComponent(Graphics g)
    {
        adjustBlockSize();
        g.setColor(BACKGROUND);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(BORDER);
        g.fillRect(0, 0, blockWidth * board.getNumCols() + 
                   OUTLINE, blockHeight * board.getNumRows());
        for (int row = 0; row < board.getNumRows(); row++)
            for (int col = 0; col < board.getNumCols(); col++)
            {
                Location loc = new Location(row, col);

                Block square = board.get(loc);

                if (square == null)
                    g.setColor(BACKGROUND);
                else
                    g.setColor(square.getColor());

                g.fillRect(col * blockWidth + OUTLINE/2, row * blockHeight + OUTLINE/2,
                    blockWidth - OUTLINE, blockHeight - OUTLINE);
            }

    }

    /**
     * Redraws the board to include the pieces and border colors.
     */
    public void showBlocks()
    {
        repaint();
    }
    
    /**
     * Adjusts the block size.
     */
    public void adjustBlockSize()
    {
        blockHeight = getHeight()/board.getNumRows();
        blockWidth = getWidth()/board.getNumCols();
    }
    
    /**
     * Sets the title of the window.
     * 
     * @param title  the information to be placed at the
     *               top of the window
     */
    public void setTitle(String title)
    {
        frame.setTitle(title);
    }

    /**
     * Creates a skeleton method to respond when a key is typed in.
     * This event occurs when a key press is followed by a key release.
     * 
     * @param e an event which indicates that a keystroke occurred 
     *          in a component (such as the grid)
     */
    public void keyTyped(KeyEvent e)
    {
    }

    /**
     * Creates a skeleton method to respond when a
     * keyboard key is released.
     * 
     * @param e an event which indicates that a keystroke occurred 
     *          in a component (such as the grid)
     */
    public void keyReleased(KeyEvent e)
    {
    }

    /**
     * Sets up the action when a key is pressed
     * on the keyboard.
     * 
     * @param e an event which indicates that a keystroke occurred 
     *          in a component (such as the grid)
     */
    public void keyPressed(KeyEvent e)
    {
        if (listener == null)
            return;
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT)
            listener.leftPressed();
        else if (code == KeyEvent.VK_RIGHT)
            listener.rightPressed();
        else if (code == KeyEvent.VK_DOWN)
            listener.downPressed();
        else if (code == KeyEvent.VK_UP)
            listener.upPressed();
        //else if (code == KeyEvent.VK_SPACE)
            //listener.spacePressed();
    }
    
    /**
     * Establishes the class that is the ArrowListener.
     * 
     * @param listenerA   the class that is assigned the task of
     *                  being the ArrowListener
     */
    public void setArrowListener(ArrowListener listenerA)
    {
        this.listener = listenerA;
    }
}
