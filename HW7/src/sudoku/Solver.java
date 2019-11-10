package sudoku;

import java.util.*;


public class Solver 
{
	private Grid						problem;
	private ArrayList<Grid>				solutions;
	
	
	public Solver(Grid problem)
	{
		this.problem = problem;
	}
	
	
	public void solve()
	{
		solutions = new ArrayList<>();
		solveRecurse(problem);
	}

	// Standard backtracking recursive solver.
	//
	private void solveRecurse(Grid grid)
	{		
		Evaluation eval = evaluate(grid);
		
		if (eval == Evaluation.ABANDON)
		{
			return;
			// Abandon evaluation of this illegal board.
		}
		else if (eval == Evaluation.ACCEPT)
		{
			// A complete and legal solution. Adds it to solutions.
			solutions.add(grid);
		}
		else
		{
	        ArrayList<Grid> array = grid.next9Grids();
	        for (Grid grids: array)
	        {
	        	solveRecurse(grids);
	        }

			// Here if eval == Evaluation.CONTINUE, it generate all 9 possible next grids.
		}
	}
	
	// Returns Evaluation.ABANDON if the grid is illegal. 
	// Returns ACCEPT if the grid is legal and complete.
	// Returns CONTINUE if the grid is legal and incomplete.
	//
	public Evaluation evaluate(Grid grid)
	{
		if(!grid.isLegal())
				return Evaluation.ABANDON;		//if its not legal, it abandons
		else if(grid.isLegal() && grid.isFull())
			return Evaluation.ACCEPT;		//if its legal and the grid is full, it accepts
		else
			return Evaluation.CONTINUE;		//else continue

	}

	
	public ArrayList<Grid> getSolutions()
	{
		return solutions;
	}
	
	
	public static void main(String[] args)
	{
		Grid g = TestGridSupplier.getPuzzle1();		// or any other puzzle
		Solver solver = new Solver(g);
		System.out.println("Will solve\n" + g);
		solver.solve();
		System.out.println(solver.getSolutions());
		
	}
}
