import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int count = Integer.parseInt(args[0]);
        RandomizedQueue<String> rqueue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            rqueue.enqueue(StdIn.readString());
        }
        for (int i = 0; i < count; i++) {
            System.out.println(rqueue.dequeue());
        }
    }
}
