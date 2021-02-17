package cs103.pz.pkg3972.andjelabojic;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class BTView extends Pane {

    private BST<Integer> tree = new BST<>();
    private double radius = 20; // Tree node radius
    private double vGap = 50; // Gap between two levels in a tree

    BTView(BST<Integer> tree) {
        this.tree = tree;
        setStatus("Prazno stablo");
    }

    public void setStatus(String msg) {
        getChildren().add(new Text(20, 20, msg));
    }

    public void displayTree() {
        this.getChildren().clear(); // Clear the pane
        if (tree.getRoot() != null) {
// Display tree recursively
            displayTree(tree.getRoot(), getWidth() / 2, vGap,
                    getWidth() / 4);
        }
    }

    /*
     * Display a subtree rooted at position (x, y)
     */
    private void displayTree(BST.TreeNode<Integer> root, double x, double y, double hGap) {
        if (root.left != null) {
// Draw a line to the left node
            getChildren().add(new Line(x - hGap, y + vGap, x, y));
// Draw the left subtree recursively
            displayTree(root.left, x - hGap, y + vGap, hGap / 2);
        }
        if (root.right != null) {
// Draw a line to the right node
            getChildren().add(new Line(x + hGap, y + vGap, x, y));
// Draw the right subtree recursively
            displayTree(root.right, x + hGap, y + vGap, hGap / 2);
        }

// Display a node
        Circle circle = new Circle(x, y, radius);
        circle.setFill(Color.RED);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(3);
        getChildren().addAll(circle,
                new Text(x - 4, y + 4, root.element + ""));
    }

    public void displaySearch(Integer e) {
        if (tree.getRoot() != null) {
// Display tree recursively
            search(tree.getRoot(), getWidth() / 2, vGap, getWidth() / 4, e);
        }
    }

    private void search(BST.TreeNode<Integer> root, double x, double y, double hGap, Integer e) {
        BST.TreeNode<Integer> current = root; // Start from the root
        while (current != null) {
            if (e.compareTo(current.element) < 0) {
                current = current.left;
                Line l = new Line(x - hGap, y + vGap, x, y);
                l.setStroke(Color.rgb(0, 255, 0, 0.2));
                l.setStrokeWidth(5);
                getChildren().add(l);
                x = x - hGap;
                y = y + vGap;
                hGap = hGap / 2;

            } else if (e.compareTo(current.element) > 0) {
                current = current.right;
                Line l = new Line(x + hGap, y + vGap, x, y);
                l.setStroke(Color.rgb(0, 255, 0, 0.2));
                l.setStrokeWidth(5);
                getChildren().add(l);

                x = x + hGap;
                y = y + vGap;
                hGap = hGap / 2;
            } else { // element matches current.element
                return; // Element is found
            }
        }

    }

    protected BST.TreeNode<Integer> createNewNode(Integer e, double x, double y) {
        Circle circle = new Circle(x, y, radius);
        circle.setFill(Color.RED);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(3);
        getChildren().addAll(circle,
                new Text(x - 4, y + 4, e + ""));
        return new BST.TreeNode<Integer>(e);
    }

    public void displayInsert(Integer e) {
        insert(tree.getRoot(), getWidth() / 2, vGap, getWidth() / 4, e);
    }

    private boolean insert(BST.TreeNode<Integer> root, double x, double y, double hGap, Integer e) {
        System.out.println("Zelim da ubacim : " + e);
        if (root == null) {
            root = createNewNode(e, x, y); // Create a new root
            System.out.println("Kreiran cvor u korenu");
        } else {
            System.out.println("Lociram parenta....");
            // Locate the parent node
            BST.TreeNode<Integer> parent = null;
            BST.TreeNode<Integer> current = root;
            while (current != null) {
                if (e.compareTo(current.element) < 0) {
                    parent = current;
                    current = current.left;
                    System.out.println("Idem levo");
                    Line l = new Line(x - hGap, y + vGap, x, y);
                    l.setStroke(Color.rgb(0, 255, 0, 0.2));
                    l.setStrokeWidth(5);
                    getChildren().add(l);
                    x = x - hGap;
                    y = y + vGap;
                    hGap = hGap / 2;
                } else if (e.compareTo(current.element) > 0) {
                    parent = current;
                    current = current.right;
                    System.out.println("Idem desno");
                    Line l = new Line(x + hGap, y + vGap, x, y);
                    l.setStroke(Color.rgb(0, 255, 0, 0.2));
                    l.setStrokeWidth(5);
                    getChildren().add(l);

                    x = x + hGap;
                    y = y + vGap;
                    hGap = hGap / 2;
                } else {
                    return false; // Duplicate node not inserted
                }// Create the new node and attach it to the parent node
            }
            if (e.compareTo(parent.element) < 0) {
                parent.left = createNewNode(e, x, y);
            } else {
                parent.right = createNewNode(e, x, y);
            }
        }

        return true; // Element inserted
    }

    public void preorderDisplay() {

        if (tree.getRoot() != null) {
// Display tree recursively
            preorder(tree.getRoot(), getWidth() / 2, vGap,
                    getWidth() / 4,0);
        }
    }

    protected void preorder(BST.TreeNode<Integer> root, double x, double y, double hGap,int count) {
        if (root == null) {
           
            return;
           
        }
        System.out.print(root.element + " ");
        Circle circle = new Circle(x, y, radius);
        circle.setFill(Color.rgb(0, 255, 0, 0.5));
        circle.setStroke(Color.GREEN);
        circle.setStrokeWidth(4);
           
        getChildren().addAll(circle, new Text(x - 4, y + 4, root.element + ""),new Text(x-20,y-4,count +""));
  count++;
     
         preorder(root.left, x - hGap, y + vGap, hGap / 2,count);
        
         preorder(root.right, x + hGap, y + vGap, hGap / 2,count);
    }

}
