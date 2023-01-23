import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Program {
    static <K extends Comparable<K>> boolean isLEssThan(K a, K b) {
        return a.compareTo(b) < 0;
    }

    static class Node23<K extends Comparable<K>> {
        Node23<K> parent;
        List<K> keys;
        List<Node23<K>> children;

        public Node23( Node23<K> parent, K key) {
            this.parent = parent;
            this.keys = new ArrayList<>();
            this.children = new ArrayList<>();

        }

        public Node23(Node23<K> parent, K key, Node23<K> left, Node23<K> right) {
            this.parent = parent;
            this.keys = new ArrayList<>(Arrays.asList(key));
            this.children = new ArrayList<>(Arrays.asList(left, right));
        }
    }

    public static void main(String[] args) {
        Node23
    }
}
