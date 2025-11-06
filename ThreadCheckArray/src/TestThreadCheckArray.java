import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 */
public class TestThreadCheckArray {
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            Thread thread1, thread2;

            System.out.println("Enter array size");
            int n = input.nextInt();

            ArrayList<Integer> array = new ArrayList<>(n);
            System.out.println("Enter numbers for array");
            for (int i = 0; i < n; i++) {
                array.add(input.nextInt());
            }

            System.out.println("Enter number");
            int b = input.nextInt();

            SharedData sd = new SharedData(array, b);

            thread1 = new Thread(new ThreadCheckArray(sd), "thread1");
            thread2 = new Thread(new ThreadCheckArray(sd), "thread2");
            thread1.start();
            thread2.start();

            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!sd.getFlag()) {
                System.out.println("Sorry");
                return;
            }

            System.out.println("Solution for b : " + sd.getB() + ", n = " + sd.getArray().size());

            // Printing indexes
            System.out.print("I:    ");
            for (int i = 0; i < sd.getArray().size(); i++) {
                System.out.printf("%-5d", i);
            }
            System.out.println();

            // Printing values
            System.out.print("A:    ");
            for (int val : sd.getArray()) {
                System.out.printf("%-5d", val);
            }
            System.out.println();

            // Printing selections (winArray)
            System.out.print("C:    ");
            for (boolean chosen : sd.getWinArray()) {
                System.out.print(chosen ? "1    " : "0    ");
            }
            System.out.println();
        }
    }
}
