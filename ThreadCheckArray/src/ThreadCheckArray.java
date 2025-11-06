import java.util.ArrayList;

public class ThreadCheckArray implements Runnable {
    private boolean flag;
    private boolean[] winArray;
    SharedData sd;
    ArrayList<Integer> array;
    int b;

    public ThreadCheckArray(SharedData sd) {
        this.sd = sd;
        synchronized (sd) {
            array = sd.getArray();
            b = sd.getB();
        }
        winArray = new boolean[array.size()];
    }

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

        // כולל את האיבר n-1
        int val = array.get(n - 1);
        rec(n - 1, b - val);
        if (flag) winArray[n - 1] = true;

        synchronized (sd) {
            if (sd.getFlag()) return;
        }

        // בלי האיבר n-1
        rec(n - 1, b);
    }

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
