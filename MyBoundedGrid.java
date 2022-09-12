import java.util.ArrayList;

/**
 * MyBoundedGrid is the grid on which the game is played and on which the blocks exist.
 * It is a rectangular grid with a finite number of rows and columns.
 * 
 * @author  Dave Feinberg
 * @author  Richard Page
 * @author  Susan King
 * @author  Daniel Fields
 * @version 2 June 2020 
 * 
 * @param <E> the elements that may be put in the grid are any objects
 */
public class MyBoundedGrid<E>
{
    /**
     * The 2-D array that is used to store the grid's elements.
     */
    private Object[][] occupantArray; 

    /**
     * Constructs an empty MyBoundedGrid with the given dimensions.
     * 
     * @param rows  the grid's number of rows;  rows > 0 
     * @param cols  the grid's number of cols;  cols > 0
     */
    public MyBoundedGrid(int rows, int cols)
    {
        occupantArray = new Object[rows][cols];
    }

    /**
     * Retrieves the number of rows.
     * 
     * @return the grid's row count
     */
    public int getNumRows()
    {
        return occupantArray.length;
    }

    /**
     * Retrieves the number of columns.
     * 
     * @return the grid's columns count
     */
    public int getNumCols()
    {
        return occupantArray[0].length;
    }

    /**
     * Determines whether a location is valid.
     * 
     * @param  loc   the location in quesion.  loc != null
     * @return true  if loc is valid in this grid; otherwise, 
     *         false 
     */
    public boolean isValid(Location loc)
    {
        int row = loc.getRow();
        int col = loc.getCol();
        if (row>=getNumRows() || row<0)
        {
            return false;
        }
        if (col>=getNumCols() || col<0)
        {
            return false;
        }
        return true;
    }

    /**
     * Retrieves an element from this grid at a location, or
     * null if the location is unoccupied.
     * 
     * @param loc is a valid location in this grid
     * 
     * @return the object at location loc 
     *         or null if the location is unoccupied
     */
    public E get(Location loc)
    {
        int row = loc.getRow();
        int col = loc.getCol();
        E obj = (E)occupantArray[row][col];
        return obj;
    }

    /**
     * Puts an element at location loc on this grid.  Plus
     * returns the object previously at that location, or
     * null if the location is unoccupied.
     * 
     * @param loc is a valid location in this grid
     * @param obj  the object to put at location loc
     * 
     * @return the object at location loc 
     *         or null if the location is unoccupied
     */
    public E put(Location loc, E obj)
    {
        int row = loc.getRow();
        int col = loc.getCol();
        E element = get(loc);
        occupantArray[row][col] = obj;
        return element;
    }

    /**
     * Removes an element from this grid at a location. Plus
     * returns the object previously at that location, or
     * null if the location is unoccupied.
     * 
     * @param loc is a valid location in this grid
     * 
     * @return the object that was at location loc 
     *         or null if the location is unoccupied
     */
    public E remove(Location loc)
    {
        int row = loc.getRow();
        int col = loc.getCol();
        E element = get(loc);
        occupantArray[row][col] = null;
        return element;
    }

    /**
     * Returns all the occupied location in this grid.
     * 
     * @return all the occupied locations in an arry list; 
     *         0 <= list.size < getNumRows() * getNumCols()
     */
    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList<Location> locs = new ArrayList<Location>();
        for (int r=0; r<occupantArray.length; r++)
        {
            for (int c=0; c<occupantArray[r].length; c++)
            {
                Location loc = new Location(r, c);
                if (get(loc)!=null)
                {
                    locs.add(loc);
                }
            }
        }
        return locs;
    }

    /**
     * Returns all the unoccupied locations in this grid.
     * 
     * @return all the unoccupied locations in an arry list; 
     *         0 <= list.size < getNumRows() * getNumCols()
     */
    public ArrayList<Location> getUnOccupiedLocations()
    {
        ArrayList<Location> locs = new ArrayList<Location>();
        ArrayList<Location> occ = getOccupiedLocations();
        for (int r=0; r<occupantArray.length; r++)
        {
            for (int c=0; c<occupantArray[r].length; c++)
            {
                Location loc = new Location(r, c);
                if (!(hasLocation(occ, loc)))
                {
                    locs.add(loc);
                }
            }
        }
        return locs;
    }

    /**
     * Checks if a given Location array contains a given location
     * 
     * @param locs the given array of Locations 
     * @param loc the Location being searched for
     * 
     * @return true if the location is in the array;
     *          otherwise; return false
     */
    public boolean hasLocation(ArrayList<Location> locs, Location loc)
    {
        for(int i=0; i<locs.size(); i++)
        {
            Location curr = locs.get(i);
            if(curr==loc)
                return true;
        }
        return false;
    }
}