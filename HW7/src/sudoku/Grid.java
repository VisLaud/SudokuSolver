package sudoku;

import java.util.*;


public class Grid 
{
	private int[][]						values;
	
	// Constructs a Grid instance from a string[] as provided by TestGridSupplier.
	// See TestGridSupplier for examples of input.
	// Dots in input strings represent 0s in values[][].
	//
	public Grid(String[] rows)
	{
		values = new int[9][9];
		for (int j=0; j<9; j++)
		{
			String row = rows[j];
			char[] charray = row.toCharArray();
			for (int i=0; i<9; i++)
			{
				char ch = charray[i];
				if (ch != '.')
					values[j][i] = ch - '0';
			}
		}
	}
	
	
	public String toString()
	{
		String s = "";
		for (int j=0; j<9; j++)
		{
			for (int i=0; i<9; i++)
			{
				int n = values[j][i];
				if (n == 0)
					s += '.';
				else
					s += (char)('0' + n);
			}
			s += "\n";
		}
		return s;
	}


	// Copy ctor. Duplicates its source. You'll call this 9 times in next9Grids.
	//
	Grid(Grid src)
	{
		values = new int[9][9];
		for (int j=0; j<9; j++)
			for (int i=0; i<9; i++)
				values[j][i] = src.values[j][i];
	}
	
	
	// Finds an empty member of values[][]. Returns an array list of 9 grids that look like the current grid,
	// except the empty member contains 1, 2, 3 .... 9. Returns null if the current grid is full. Don't change
	// this grid. Build 9 new grids.
	// 
	//
	// Example: if this grid = 1........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//
	// Then the returned array list would contain:
	//
	// 11.......          12.......          13.......          14.......    and so on     19.......
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	//
	public ArrayList<Grid> next9Grids()
	{		
		int xEmpty = 0;
	    int yEmpty = 0;

	    // Find x,y of an empty cell.

	    find_first_empty_cell: for (int j = 0; j < 9; j++) 
	    {
	        for (int i = 0; i < 9; i++) { 	//this loop looks for the first empty cell
	            if (values[j][i] == 0) {
	                xEmpty = i;
	                yEmpty = j;
	                break find_first_empty_cell;	//this breaks the find_first_empty_cell for loop
	            }
	        }
	    }

	    //the second loop builds the grid sequentially
	    ArrayList<Grid> grids = new ArrayList<Grid>();

	    for (int i = 1; i <= 9; i++) {
	        Grid nextGrid = new Grid(this);
	        nextGrid.values[yEmpty][xEmpty] = i;
	        grids.add(nextGrid);
	    }

	 // Creates 9 new grids as described in the comments above. Add them to grids.
	    return grids;
	  }

		
	
	// Returns true if this grid is legal. A grid is legal if no row, column, or
	// 3x3 block contains a repeated 1, 2, 3, 4, 5, 6, 7, 8, or 9.
	//
	public boolean isLegal()
	{
		// Check every row. If you find an illegal row, return false.
		
		 HashSet<Integer> doubleCheck = new HashSet<Integer>();
	        for (int i = 0; i < 9; i++) {
	            for (int j = 0; j < 9; j++) {
	                if (values[i][j] != 0) {
	                    if (doubleCheck.contains(values[i][j])) {
	                        doubleCheck.clear();
	                        return false;
	                    } else {
	                        doubleCheck.add(values[i][j]);
	                    }
	                }
	            }
	            doubleCheck.clear();
	        }

	        // Check columns
	        for (int i = 0; i < 9; i++) {
	            for (int j = 0; j < 9; j++) {
	                if (values[j][i] != 0) {
	                    if (doubleCheck.contains(values[j][i])) {
	                        doubleCheck.clear();
	                        return false;
	                    } else {
	                        doubleCheck.add(values[j][i]);
	                    }
	                }
	            }
	            doubleCheck.clear();
	        }

	        // this loop checks 3x3 block 
	        for (int i = 0; i < 9; i += 3) {
	            for (int j = 0; j < 9; j += 3) {
	                for (int k = i; k < 3 + i; k++) {
	                    for (int l = j; l < 3 + j; l++) {
	                        int currentNum = values[k][l];
	                        if (currentNum != 0) {
	                            if (doubleCheck.contains(currentNum)) {
	                                doubleCheck.clear();
	                                return false;
	                            } else {
	                                doubleCheck.add(currentNum);
	                            }
	                        }
	                    }
	                }
	                doubleCheck.clear();
	            }
	        }

	        return true;
	    }

	
	// Returns true if every cell member of values[][] is a digit from 1-9.
	//
	public boolean isFull()
	{
        for (int j = 0; j < 9; j++) { 		//returns true if every cell memeber of the 2d arrya is a number 										
            for (int i = 0; i < 9; i++) {	// in between 1-9
                if (values[j][i] == 0) {
                    return false;
                }
            }
        }
        return true;
	}
	

	// Returns true if x is a Grid and, for every (i,j), 
	// x.values[i][j] == this.values[i][j].
	//
	public boolean equals(Object x)
	{
	    Grid that = (Grid)x;
	        for (int i = 0; i < values.length; i++)
	        {
	            for (int j = 0; j < values[0].length; j++)
	            {
	                if (that.values[i][j] != this.values[i][j])// Returns true if x is a Grid and, for every (i,j)
	                {
	                    return false;					// x.values[i][j] == this.values[i][j].
	                }
	            }
	        }
	    return true;
	}

}
