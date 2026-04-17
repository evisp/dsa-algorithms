public class Main {
    public static void main(String[] args) {
        ProductBST catalog = new ProductBST();

        System.out.println("--- Building Product Catalog ---");
        
        // Inserting in an order that creates a relatively balanced tree
        catalog.insert(new Product(100, "Laptop", 1200.00));
        catalog.insert(new Product(50, "Keyboard", 80.00));
        catalog.insert(new Product(150, "Monitor", 300.00));
        catalog.insert(new Product(25, "Mouse", 25.00));
        catalog.insert(new Product(75, "Desk Lamp", 45.00));
        catalog.insert(new Product(125, "Webcam", 90.00));
        catalog.insert(new Product(175, "Headphones", 150.00));

        System.out.println("Visualizing Tree Structure:");
        catalog.printCatalog();
        
        /**
        System.out.println("\n--- Testing Efficiency ---");
        catalog.search(75);  
        catalog.search(999); 

        System.out.println("\n--- Testing Traversals ---");
        catalog.printInOrder();  
        catalog.printPreOrder(); 
        catalog.printPostOrder();
        

        System.out.println("\n--- Tree Analysis & Metrics ---");
        
        // 1. Min/Max - Proving the boundaries
        System.out.println("Cheapest ID: " + catalog.findMin());
        System.out.println("Most Expensive ID: " + catalog.findMax());

        // 2. Physical Properties
        System.out.println("Total Products in Catalog: " + catalog.size());
        System.out.println("Tree Height: " + catalog.getHeight()); 
        // Note: Height 2 means a perfectly packed tree for 7 nodes

        // 3. Health Check
        System.out.println("Is the tree balanced? " + catalog.isBalanced());

    
        System.out.println("\n--- Leaf Analysis ---");
        
        int totalNodes = catalog.size();
        int leaves = catalog.countLeaves();
        int internalNodes = totalNodes - leaves;

        System.out.println("Number of Leaf Nodes (Endpoints): " + leaves);
        System.out.println("Number of Internal Nodes (Parents): " + internalNodes);

        System.out.println("\n--- Testing Deletion ---");

        // Case 1: Delete a Leaf (No children)
        System.out.println("1. Deleting [ID 25] (Leaf)...");
        catalog.delete(25);
        catalog.printCatalog();

        // Case 2: Delete a node with one child
        // If we delete 150, 175 should take its place
        System.out.println("\n2. Deleting [ID 150] (One Child)...");
        catalog.delete(150);
        catalog.printCatalog();

        // Case 3: Delete the Root (Two children)
        // This is the complex one! Successor (125) should move up.
        System.out.println("\n3. Deleting [ID 100] (Root / Two Children)...");
        catalog.delete(100);
        catalog.printCatalog();

        System.out.println("\nFinal Catalog Stats:");
        System.out.println("Size: " + catalog.size());
        System.out.println("Balanced: " + catalog.isBalanced());
        */
    }
}