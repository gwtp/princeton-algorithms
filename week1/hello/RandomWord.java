import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
  public static void main(String[] unused) {
    String champion = "";
    int i = 1;
    while (!StdIn.isEmpty()) {
      String word = StdIn.readString();
      if (StdRandom.bernoulli(1.0 / i)) {
        champion = word;
      }
      i++;
    }
    System.out.println(champion);
  }
}
