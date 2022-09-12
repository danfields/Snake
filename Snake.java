import java.awt.Color;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Creates a Snake Window
 *
 * @author Daniel Fields
 * @version 2 June 2020
 */
public class Snake implements ArrowListener
{
    private MyBoundedGrid<Block> grid;
    private BlockDisplay display;

    private Block b;
    //Keeps track of the blocks that make up the Snake.
    private ArrayList<Block> snake;

    private int score = 0;

    private boolean gameOver = false;

    private Color col;
    private int level;

    //Keep track of the Snake's direction.
    private boolean up = false;
    private boolean right = true;
    private boolean left = false;
    private boolean down = false;

    /**
     * Creates a new Snake object
     * 
     * @param col   the color of the Snake
     * @param level the level of the Snake
     */
    public Snake(Color col, int level)
    {
        this.col = col;
        this.level = level;

        grid = new MyBoundedGrid(20,20);
        display = new BlockDisplay(grid);
        display.setTitle("Snake");
        display.setArrowListener(this);

        //Head block
        b = new Block(col);
        Location location = new Location(10, 10);
        b.setLocation(location);
        snake = new ArrayList<Block>();
        snake.add(b);
        b.putSelfInGrid(grid, location);

        //Body Block 1
        Block c = new Block(col);
        Location locationTwo = new Location(10, 9);
        c.setLocation(locationTwo);
        snake.add(c);
        c.putSelfInGrid(grid, locationTwo);

        //Body Block 2
        Block d = new Block(col);
        Location locationThree = new Location(10, 8);
        d.setLocation(locationThree);
        snake.add(d);
        d.putSelfInGrid(grid, locationThree);
    }

    /**
     * Changes the Snake's direction to up.
     */
    public void upPressed()
    {
        if(down == true)
        {
            downPressed();
            return;
        }
        up = true;
        left = false;
        right = false;
        down = false;
    }

    /**
     * Changes the snake's direction to down.
     */
    public void downPressed()
    {
        if (up==true)
        {
            upPressed();
            return;
        }
        down = true;
        left = false;
        right = false;
        up = false;
    }

    /**
     * Changes the Snake's direction to left.
     */
    public void leftPressed()
    {
        if(right==true)
        {
            rightPressed();
            return;
        }
        left = true;
        up = false;
        down = false;
        right = false;
    }

    /**
     * Changes the Snake's direction to right.
     */
    public void rightPressed()
    {
        if(left==true)
        {
            leftPressed();
            return;
        }
        right = true;
        up = false;
        down = false;
        left = false;
    }

    /**
     * Plays for the Snake game until the game is over.
     */
    public void play()
    {
        while (!gameOver)
        {
            display.setTitle("SNAKE" + "     Score: " + score);
            try 
            {
                Thread.sleep(level);
            }
            catch(InterruptedException e)
            {
                //ignore 
            }
            
            moveInDirection();
            display.showBlocks(); 
            if(!isApple())
            {
                putApple();
            }
            display.showBlocks();
        }
        if(gameOver)
        {
            display.setTitle("Game Over!" + "    Your score was: " + score);
            display.setArrowListener(null);
        }
    }

    /**
     * Checks if there is an apple on the grid. Returns true
     * if there is an apple present; otherwise, returns false.
     * 
     * @return true if there is an apple present; otherwise,
     *  return false.
     */
    public boolean isApple()
    {
        for(int r=0; r<=19; r++)
        {
            for(int c=0; c<=19; c++)
            {
                Location loc = new Location(r, c);
                if(grid.get(loc)!=null 
                    && grid.get(loc).getColor() == Color.RED)
                    return true;
            }
        }
        return false;
    }

    /**
     * Makes one move for the snake in a certain direction 
     * depending on the current direction of the Snake.
     */
    public void moveInDirection()
    {
        if(up)
        {
            Block head = snake.get(0);
            Block tail = snake.get(snake.size()-1);
            Location headLoc = head.getLocation();
            if(!grid.isValid(headLoc.getAdjacentLocation(0)))
            {
                gameOver=true;
                return;
            }
            Location adjLocation = headLoc.getAdjacentLocation(0);
            if(grid.get(adjLocation)!= null 
              && grid.get(adjLocation).getColor() == Color.RED)
            {
                Location tailLoc = tail.getLocation();
                Block newTail = new Block(col);
                Location headLocation = head.getLocation();
                int headRow = headLocation.getRow();
                int headCol = headLocation.getCol();
                tail.moveTo(new Location(headRow-1,headCol));
                snake.add(0, snake.remove(snake.size()-1));
                head = snake.get(0);
                newTail.putSelfInGrid(grid, tailLoc);
                snake.add(newTail);
                display.showBlocks();
                score++;
                return;
            }
            if(grid.get(adjLocation)!=null)
            {
                gameOver=true;
                return;
            }
            tail.moveTo(new Location(headLoc.getRow()-1,headLoc.getCol()));
            snake.add(0, snake.remove(snake.size()-1));
            display.showBlocks();
        }
        if(down)
        {
            Block head = snake.get(0);
            Block tail = snake.get(snake.size()-1);
            Location headLoc = head.getLocation();
            if(!grid.isValid(headLoc.getAdjacentLocation(180)))
            {
                gameOver=true;
                return;
            }
            Location adjLocation = headLoc.getAdjacentLocation(180);
            if(grid.get(adjLocation)!= null 
              && grid.get(adjLocation).getColor() == Color.RED)
            {
                Location tailLoc = tail.getLocation();
                Block newTail = new Block(col);
                Location headLocation = head.getLocation();
                int headRow = headLocation.getRow();
                int headCol = headLocation.getCol();
                tail.moveTo(new Location(headRow+1,headCol));
                snake.add(0, snake.remove(snake.size()-1));
                head = snake.get(0);
                newTail.putSelfInGrid(grid, tailLoc);
                snake.add(newTail);
                display.showBlocks();
                score++;
                return;
            }
            if(grid.get(adjLocation)!=null)
            {
                gameOver=true;
                return;
            }
            tail.moveTo(new Location(headLoc.getRow()+1,headLoc.getCol()));
            snake.add(0, snake.remove(snake.size()-1));
            display.showBlocks();
        }
        if(left)
        {
            Block head = snake.get(0);
            Block tail = snake.get(snake.size()-1);
            Location headLoc = head.getLocation();
            if(!grid.isValid(headLoc.getAdjacentLocation(-90)))
            {
                gameOver=true;
                return;
            }
            Location adjLocation = headLoc.getAdjacentLocation(-90);
            if(grid.get(adjLocation)!= null 
              && grid.get(adjLocation).getColor() == Color.RED)
            {
                Location tailLoc = tail.getLocation();
                Block newTail = new Block(col);
                Location headLocation = head.getLocation();
                int headRow = headLocation.getRow();
                int headCol = headLocation.getCol();
                tail.moveTo(new Location(headRow,headCol-1));
                snake.add(0, snake.remove(snake.size()-1));
                head = snake.get(0);
                newTail.putSelfInGrid(grid, tailLoc);
                snake.add(newTail);
                display.showBlocks();
                score++;
                return;
            }
            if(grid.get(adjLocation)!=null)
            {
                gameOver=true;
                return;
            }
            tail.moveTo(new Location(headLoc.getRow(),headLoc.getCol()-1));
            snake.add(0, snake.remove(snake.size()-1));
            display.showBlocks();
        }
        if(right)
        {
            Block head = snake.get(0);
            Block tail = snake.get(snake.size()-1);
            Location headLoc = head.getLocation();
            if(!grid.isValid(headLoc.getAdjacentLocation(90)))
            {
                gameOver=true;
                return;
            }
            Location adjLocation = headLoc.getAdjacentLocation(90);
            if(grid.get(adjLocation)!= null 
              && grid.get(adjLocation).getColor() == Color.RED)
            {
                Location tailLoc = tail.getLocation();
                Block newTail = new Block(col);
                Location headLocation = head.getLocation();
                int headRow = headLocation.getRow();
                int headCol = headLocation.getCol();
                tail.moveTo(new Location(headRow,headCol+1));
                snake.add(0, snake.remove(snake.size()-1));
                head = snake.get(0);
                newTail.putSelfInGrid(grid, tailLoc);
                snake.add(newTail);
                display.showBlocks();
                score++;
                return;
            }
            if(grid.get(adjLocation)!=null)
            {
                gameOver=true;
                return;
            }
            tail.moveTo(new Location(headLoc.getRow(),headLoc.getCol()+1));
            snake.add(0, snake.remove(snake.size()-1));
            display.showBlocks();
        }
    }

    /**
     * Puts an apple into the grid in a random location.
     */
    public void putApple()
    {
        ArrayList<Location> unOcc = grid.getUnOccupiedLocations();
        int size = unOcc.size();
        int randIndex = (int)(Math.random()*(size));
        Apple apple = new Apple(unOcc.get(randIndex));
        apple.putInLocation(grid);
    }

    /**
     * Main method - plays the game after asking for user input
     *               for Snake customization.
     *               
     * @param args information from the command line
     */
    public static void main(String[]args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose the color of your Snake!");
        System.out.println("Type G for Green, O for Orange, and B for Blue.");
        System.out.println("If you choose to type anything else,");
        System.out.println("the default color will be Green.");
        String input = sc.nextLine();
        Color col;
        if(input.equals("B"))
            col = Color.BLUE;
        else if(input.equals("O"))
            col = Color.ORANGE;
        else if(input.equals("G"))
            col = Color.GREEN;
        else
            col = Color.GREEN;
        System.out.println("Choose the level of the game!");
        System.out.println("The harder the level, the faster the snake will move.");
        System.out.println("Type E for Easy, M for Medium, and H for Hard.");
        System.out.println("If you choose to type anything else,");
        System.out.println("the default level will be Easy.");
        String inputTwo = sc.nextLine();
        int level;
        if(inputTwo.equals("E"))
            level = 290;
        else if(inputTwo.equals("M"))
            level = 170;
        else if(inputTwo.equals("H"))
            level = 100;
        else
            level = 290;
        Snake game = new Snake(col, level);
        try 
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException e)
        {
            //ignore 
        }
        System.out.println("Your game will start in:");
        try 
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException e)
        {
            //ignore 
        }
        System.out.println("3");
        try 
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException e)
        {
            //ignore 
        }
        System.out.println("2");
        try 
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException e)
        {
            //ignore 
        }
        System.out.println("1");  
        try 
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException e)
        {
            //ignore 
        }
        System.out.println("GO!");  
        try 
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException e)
        {
            //ignore 
        }
        game.play();
    }
}
