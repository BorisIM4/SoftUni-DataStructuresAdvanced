import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tree23LiveDemo {
    static <K extends Comparable<K>> boolean isLessThan(K a, K b) {
        return a.compareTo(b) < 0;
    }

    static class Tree23<K extends Comparable<K>> {
        static class Node23<K extends Comparable<K>> {
            Node23<K> parent;
            List<K> keys;
            List<Node23<K>> children;

            public Node23(Node23<K> parent, K key) {
                this.parent = parent;
                this.keys = new ArrayList<>();
                this.children = new ArrayList<>();
            }

            public Node23(Node23<K> parent, K key, Node23<K> left, Node23<K> right) {
                this.parent = parent;
                this.keys = new ArrayList<>(Arrays.asList(key));
                this.children = new ArrayList<>(Arrays.asList(left, right));
                left.parent = this;
                right.parent = this;
            }
        }

        Node23<K> root;

        public void add(K key) {
            if (root == null) {
                root = new Node23<K>(null, key);
            } else {
                add(root, key);
            }
        }

        private void add(Node23<K> node, K key) {
            Node23<K> insertionNode = node;
            int insertionIndex = getInsertionIndex(insertionNode, key);

            if (node.children.isEmpty()) {
                insertionNode.keys.add(insertionIndex, key);

                while (insertionNode.keys.size() > 2) {
                    K midKey = insertionNode.keys.get(1);

                    Node23<K> leftPart;
                    Node23<K> rightPart;
                    if (insertionNode.children.isEmpty()) {
                        leftPart = new Node23<K>(insertionNode.parent, insertionNode.keys.get(0));
                        rightPart = new Node23<K>(insertionNode.parent, insertionNode.keys.get(2));
                    } else {
                        leftPart = new Node23<K>(insertionNode.parent, insertionNode.keys.get(0),
                                insertionNode.children.get(0), insertionNode.children.get(1));
                        rightPart = new Node23<K>(insertionNode.parent, insertionNode.keys.get(2),
                                insertionNode.children.get(0), insertionNode.children.get(1));
                    }

                    insertionNode = insertionNode.parent;
                    if (insertionNode != null) {
                        int midKeyInsertionIndex = getInsertionIndex(insertionNode, midKey);
                        insertionNode.keys.add(midKeyInsertionIndex, midKey);
                        insertionNode.children.add(midKeyInsertionIndex, leftPart);
                        insertionNode.children.set(midKeyInsertionIndex + 1, rightPart);
                    } else {
                        insertionNode = new Node23<K>(null, midKey, leftPart, rightPart);
                        root = insertionNode;
                    }
                }
            } else {
                add(node.children.get(insertionIndex), key);
            }
        }

        private int getInsertionIndex(Node23<K> node, K key) {
            int insertionIndex = -1;
            for (int i = 0; i < node.keys.size(); i++) {
                if (isLessThan(key, node.keys.get(i))) {
                    insertionIndex = i;
                }
            }

            if (insertionIndex == -1) {
                insertionIndex = node.keys.size();
            }

            return insertionIndex;
        }
    }

    public static void main(String[] args) {
    }
}
