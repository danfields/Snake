import java.awt.Color;
/**
 * Block class maintains information about a block.
 * This class is extremely flexible to multiple games;
 * such as Tile Game, Tetris, and Tic-Tac-Toe.
 * 
 * @author  Dave Feinberg
 * @author  Richard Page
 * @author  Susan King     Added documentation
 * 
 * @author Daniel Fields
 * @version 2 June 2020
 */
public class Block
{
    private MyBoundedGrid<Block> grid;
    private Location location;
    private Color color;
    private String text;

    /**
     * Constructs a blue block.
     * 
     * @param col   the color that the block
     *              will be set to
     */
    public Block(Color col)
    {
        color = col;
        grid = null;
        location = null;
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public void addToLocation(MyBoundedGrid<Block> gr, Location loc)
    {
        putSelfInGrid(gr, loc);
    }
    
    /**
     * Gets the color of this block.
     * 
     * @return the color of this block
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * Sets the color of this block to newColor.
     * 
     * @param newColor  the new color of this block
     */
    public void setColor(Color newColor)
    {
        color = newColor;
    }

    /**
     * Gets the grid of this block, or null if this block is not contained
     * in a grid.
     * 
     * @return the grid
     */
    public MyBoundedGrid<Block> getGrid()
    {
        return grid;
    }

    /**
     * Gets the location of this block, or null if this block is not contained
     * in a grid.
     * 
     * @return this block's location, or null if this block is not in the grid
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * Sets the location of this block to newLoc.
     * 
     * @param newLoc  the new location of this block
     */
    public void setLocation(Location newLoc)
    {
        location = newLoc;
    }
    
    /**
     * Removes this block from its grid.
     *
     * @precondition  this block is contained in a grid
     */
    public void removeSelfFromGrid()
    {
        grid.remove(location);
        location = null;
        grid = null;
    }

    /**
     * Puts this block into location loc of grid gr.
     * If there is another block at loc, it is removed.
     * 
     * @precondition  (1) this block is not contained in a grid
     *                (2) loc is valid in gr
     *               
     * @param gr  the grid to place this block
     * @param loc the location to place this block
     */
    public void putSelfInGrid(MyBoundedGrid<Block> gr, Location loc)
    {
        if (loc != null && gr != null)
        {
            Block element = gr.get(loc);
            if (element!=null)
            {
                element.removeSelfFromGrid();
            }
            gr.put(loc, this);
            grid = gr;
            location = loc;
        }
    }

    /**
     * Moves this block to newLocation.
     * If there is another block at newLocation, it is removed.
     *
     * @precondition  (1) this block is contained in a grid
     *                (2) newLocation is valid in the grid of this block
     *                
     * @param newLocation  the location that the block is to be moved
     */
    public void moveTo(Location newLocation)
    {
        MyBoundedGrid<Block> gridOne = grid;
        this.removeSelfFromGrid();
        this.putSelfInGrid(gridOne, newLocation);
    }

    /**
     * Returns a string with the location and color of this block.
     * 
     * @return location and color information about the block
     */
    public String toString()
    {
        return "Block[location=" + location + ",color=" + color + "]";
    }
}