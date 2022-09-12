import java.awt.*;
import java.util.*;
/**
 * The Apple class describes an apple, which is represented
 * by a red block.
 *
 * @author Daniel Fields
 * @version 2 June 2020
 */
public class Apple
{
    private Location randomLoc;
    private Block red;

    /**
     * Constructor for objects of class Apple
     * 
     * @param loc   the location that this apple will have
     */
    public Apple(Location loc)
    {
        randomLoc = loc;
        red = new Block(Color.RED);
    }
    
    /**
     * Puts the given apple in a location on the grid.
     * 
     * @param gr the grid that the apple is being put into
     */
    public void putInLocation(MyBoundedGrid<Block> gr)
    {
        red.putSelfInGrid(gr, randomLoc);
    }
}
