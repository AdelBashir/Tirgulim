import java.util.ArrayList;

/**
 SharedData represents the shared resource used by multiple threads.
 * It stores the input number list, the target sum (b), a shared flag that
 * indicates whether a solution was found, and the result winArray.
 */
public class SharedData 
{
	private ArrayList<Integer> array;
	private boolean [] winArray;
	private boolean flag;
	private final int b;
	
	
	/**
	 * @param array
	 * @param b
	 * Creates a SharedData object that holds the integer list and target sum.
      Used as shared data between multiple threads.
	 */
	public SharedData(ArrayList<Integer> array, int b) {
		
		this.array = array;
		this.b = b;
	}

	/**
	 * @return
	 * Returns the array that marks which elements are part of the solution.
	 */
	public boolean[] getWinArray() 
	{
		return winArray;
	}

	/**
	 * @param winArray
	 * Sets the result array that indicates which elements belong to the solution.
	 */
	public void setWinArray(boolean [] winArray) 
	{
		this.winArray = winArray;
	}

	/**
	 * @return
	 * Returns the list of integers used for the subset-sum calculation.
	 */
	public ArrayList<Integer> getArray() 
	{
		return array;
	}

	/**
	 * @return
	 * Returns the target sum the threads are trying to reach.
	 */
	public int getB() 
	{
		return b;
	}

	/**
	 * @return
	 * Returns whether a solution has already been found by any thread.
	 */
	public boolean getFlag() 
	{
		return flag;
	}

	/**
	 * @param flag
	 * Updates the shared flag to indicate that a solution has been found.
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}
