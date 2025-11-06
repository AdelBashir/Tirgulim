import java.util.ArrayList;

/**
 * ThreadCheckArray is a worker thread that performs a recursive subset-sum search.
 * Each thread explores a different branch of the search space and stops early if
 * another thread has already found a valid solution. When a solution is found,
 * the thread updates the shared data with the result and marks which elements
 * form the successful subset.
 */
public class ThreadCheckArray implements Runnable {
    private boolean flag;
    private boolean[] winArray;
    SharedData sd;
    ArrayList<Integer> array;
    int b;

    /**
     * 
     * @param sd
     * Initializes the thread with shared data and prepares a local winArray.
     */
    public ThreadCheckArray(SharedData sd) {
        this.sd = sd;
        synchronized (sd) {
            array = sd.getArray();
            b = sd.getB();
        }
        winArray = new boolean[array.size()];
    }
    
    

    /**
     * @param n
     * @param b
     * Recursively checks combinations of the array to find a subset whose sum equals b.
      If a solution is found, it updates the shared flag and marks elements in winArray.
     */
    void rec(int n, int b) {
        synchronized (sd) {
            if (sd.getFlag()) return;
        }

        if (n == 1) {
            int last = array.get(0);
            if (b == 0 || b == last) {
                flag = true;
                synchronized (sd) { sd.setFlag(true); }
            }
            if (b == last) winArray[0] = true;
            return;
        }

        // Includes the term n-1
        int val = array.get(n - 1);
        rec(n - 1, b - val);
        if (flag) winArray[n - 1] = true;

        synchronized (sd) {
            if (sd.getFlag()) return;
        }

        // Without the term n-1
        rec(n - 1, b);
    }

    /**
     Runs the subset-sum checking process for this thread.
     * Each thread explores a different search branch and updates shared data if a solution is found.
     */
    @Override
    public void run() {
        int size = array.size();
        if (size != 1) {
            int last = array.get(size - 1);
            if (Thread.currentThread().getName().equals("thread1")) {
                rec(size - 1, b - last);
            } else {
                rec(size - 1, b);
            }
        } else {
            if (b == array.get(0) && !flag) {
                winArray[0] = true;
                flag = true;
                synchronized (sd) { sd.setFlag(true); }
            }
        }

        if (flag) {
            if (Thread.currentThread().getName().equals("thread1") && array.size() > 0) {
                winArray[array.size() - 1] = true; // הענף של thread1 כולל את האחרון
            }
            synchronized (sd) {
                sd.setWinArray(winArray);
            }
        }
    }
}
