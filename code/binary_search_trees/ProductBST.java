public class ProductBST {
	
    private Node root;

    // Public API 
    public void insert(Product product) {
        root = insertRecursive(root, product);
    }

    // The logic: Left if smaller, Right if larger
    private Node insertRecursive(Node current, Product product) {
        if (current == null) {
            return new Node(product);
        }

        if (product.getId() < current.product.getId()) {
            current.left = insertRecursive(current.left, product);
        } else if (product.getId() > current.product.getId()) {
            current.right = insertRecursive(current.right, product);
        }
        // IDs are unique; we ignore duplicates for this example
        return current;
    }
    
    public Product search(int id) {
        System.out.println("Searching for ID " + id + ":");
        return searchRecursive(root, id, 1); // Start at step 1
    }

    private Product searchRecursive(Node current, int id, int step) {
        if (current == null) {
            System.out.println("  Step " + step + ": Hit a null branch. ID not found.");
            return null;
        }

        System.out.println("  Step " + step + ": Comparing with ID " + current.product.getId());

        if (id == current.product.getId()) {
            System.out.println("  Found! Total comparisons: " + step);
            return current.product;
        }

        if (id < current.product.getId()) {
            return searchRecursive(current.left, id, step + 1);
        } else {
            return searchRecursive(current.right, id, step + 1);
        }
    }
    
 // 1. In-Order: Left -> Root -> Right (The "Sorted" list)
    public void printInOrder() {
        System.out.print("In-Order (Sorted by ID): ");
        inOrderRecursive(root);
        System.out.println();
    }

    private void inOrderRecursive(Node node) {
        if (node != null) {
            inOrderRecursive(node.left);
            System.out.print(node.product.getId() + " ");
            inOrderRecursive(node.right);
        }
    }

    // 2. Pre-Order: Root -> Left -> Right (The "Copy" order)
    public void printPreOrder() {
        System.out.print("Pre-Order (Root First): ");
        preOrderRecursive(root);
        System.out.println();
    }

    private void preOrderRecursive(Node node) {
        if (node != null) {
            System.out.print(node.product.getId() + " ");
            preOrderRecursive(node.left);
            preOrderRecursive(node.right);
        }
    }

    // 3. Post-Order: Left -> Right -> Root (The "Cleanup" order)
    public void printPostOrder() {
        System.out.print("Post-Order (Leaves First): ");
        postOrderRecursive(root);
        System.out.println();
    }

    private void postOrderRecursive(Node node) {
        if (node != null) {
            postOrderRecursive(node.left);
            postOrderRecursive(node.right);
            System.out.print(node.product.getId() + " ");
        }
    }
    
 // 1. Min and Max: Prove the sorted structure
    public Product findMin() {
        if (root == null) return null;
        Node current = root;
        while (current.left != null) current = current.left; // Always go left
        return current.product;
    }

    public Product findMax() {
        if (root == null) return null;
        Node current = root;
        while (current.right != null) current = current.right; // Always go right
        return current.product;
    }

    // 2. Height: The longest path from root to leaf
    public int getHeight() {
        return calculateHeight(root);
    }

    private int calculateHeight(Node node) {
        if (node == null) return -1; // Standard: edge-based height. Use 0 for node-based.
        return 1 + Math.max(calculateHeight(node.left), calculateHeight(node.right));
    }

    // 3. Balance Check: Is the tree healthy?
    public boolean isBalanced() {
        return checkBalance(root);
    }

    private boolean checkBalance(Node node) {
        if (node == null) return true;
        int heightDiff = Math.abs(calculateHeight(node.left) - calculateHeight(node.right));
        return heightDiff <= 1 && checkBalance(node.left) && checkBalance(node.right);
    }
    
    public int size() {
        return calculateSize(root);
    }

    private int calculateSize(Node node) {
        if (node == null) return 0;
        return 1 + calculateSize(node.left) + calculateSize(node.right);
    }
    
    public int countLeaves() {
        return countLeavesRecursive(root);
    }

    private int countLeavesRecursive(Node node) {
        if (node == null) return 0;
        if (node.left == null && node.right == null) return 1;
        return countLeavesRecursive(node.left) + countLeavesRecursive(node.right);
    }
    
    public void delete(int id) {
        root = deleteRecursive(root, id);
    }

    private Node deleteRecursive(Node current, int id) {
        if (current == null) return null;

        // 1. Navigate the tree
        if (id < current.product.getId()) {
            current.left = deleteRecursive(current.left, id);
        } else if (id > current.product.getId()) {
            current.right = deleteRecursive(current.right, id);
        } 
        // 2. Found the node to delete!
        else {
            // Case A & B: Leaf or Single Child
            if (current.left == null) return current.right;
            if (current.right == null) return current.left;

            // Case C: Two Children
            // Find the smallest node in the right subtree (Successor)
            Node successor = findMinNode(current.right);
            // Replace current product with successor's product
            current.product = successor.product;
            // Delete the successor node from the right subtree
            current.right = deleteRecursive(current.right, successor.product.getId());
        }
        return current;
    }

    private Node findMinNode(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    // A nice way to visualize the hierarchy in the console
    public void printCatalog() {
        printRecursive(root, "", true);
    }

    private void printRecursive(Node node, String prefix, boolean isLeft) {
        if (node != null) {
            System.out.println(prefix + (isLeft ? "├── " : "└── ") + node.product);
            printRecursive(node.left, prefix + (isLeft ? "│   " : "    "), true);
            printRecursive(node.right, prefix + (isLeft ? "│   " : "    "), false);
        }
    }
}