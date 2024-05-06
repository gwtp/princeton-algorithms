import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {

        if (args.length != 1) {
            throw new IllegalArgumentException(
                    "Provide the number of permutations to print, for example: ./Permutation 3");
        }
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> q = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            q.enqueue(StdIn.readString());
        }

        for (int i = 0; i < k; i++) {
            StdOut.printf("%s\n", q.dequeue());
        }
    }
}