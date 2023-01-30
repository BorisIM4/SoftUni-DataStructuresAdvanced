public class AvlTree<T extends Comparable<T>>  {

    private Node<Integer> root;

    public Node<Integer> getRoot() {
        return this.root;
    }

    public void insert(Integer item) {
        this.root = this.insertNode(item, this.root);
    }

    public Node deleteNode(int key, Node node) {
        node = deleteNode(key, node);

        // Node is null if the tree doesn't contain the key
        if (node == null) {
            return null;
        }

        updateHeight(node);

        return rebalanced(node);
    }

    private Node insertNode(int key, Node node) {
        node = insertNode(key, node);

        updateHeight(node);

        return rebalanced(node);
    }

    private int height(Node node) {
        return node != null ? node.height : -1;
    }

    private void updateHeight(Node node) {
        int leftChildHeight = height(node.left);
        int rightChildHeight = height(node.right);
        node.height = Math.max(leftChildHeight, rightChildHeight) + 1;
    }

    private int balanceFactor(Node node) {
        return height(node.right) - height(node.left);
    }

    private Node rotateRight(Node node) {
        Node leftChild = node.left;

        node.left = leftChild.right;
        leftChild.right = node;

        updateHeight(node);
        updateHeight(leftChild);

        return leftChild;
    }

    private Node rotateLeft(Node node) {
        Node rightChild = node.right;

        node.right = rightChild.left;
        rightChild.left = node;

        updateHeight(node);
        updateHeight(rightChild);

        return rightChild;
    }

    private Node rebalanced(Node node) {
        int balanceFactor = balanceFactor(node);

        // Left-heavy?
        if (balanceFactor < -1) {
            if (balanceFactor(node.left) <= 0) {    // Case 1
                // Rotate right
                node = rotateRight(node);
            } else {                                // Case 2
                // Rotate left-right
                node.left = rotateLeft(node.left);
                node = rotateRight(node);
            }
        }

        // Right-heavy?
        if (balanceFactor > 1) {
            if (balanceFactor(node.right) >= 0) {    // Case 3
                // Rotate left
                node = rotateLeft(node);
            } else {                                 // Case 4
                // Rotate right-left
                node.right = rotateRight(node.right);
                node = rotateLeft(node);
            }
        }

        return node;
    }

}
