package cs103.pz.pkg3972.andjelabojic;

import cs103.pz.pkg3972.andjelabojic.HuffmanCode.Tree.Node;
import static cs103.pz.pkg3972.andjelabojic.HuffmanCode.getCode;
import static cs103.pz.pkg3972.andjelabojic.HuffmanCode.getHuffmanTree;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class HuffView extends Pane {

    private HuffmanCode.Tree tree;
    String[] codes;
    
    private double radius = 20; // Tree node radius
    private double vGap = 60; // Gap between two levels in a tree

    HuffView(int[] counts) {
        this.tree = getHuffmanTree(counts);
        this.codes = getCode(this.tree.root); // Get codes
        this.setWidth(600);
        this.setHeight(400);
        setStatus("Prazno stablo");
    }

    public void setStatus(String msg) {
        getChildren().add(new Text(20, 20, msg));
    }
    public void setRes(String msg){
        getChildren().add(new Text(240, 290, msg));
    }

    public void displayTree() {
        this.getChildren().clear(); // Clear the pane
        if (tree.root != null) { // Display tree recursively
            System.out.println(this.getWidth()/2 + "...."+this.getHeight()/2);
            displayTree(tree.root, this.getWidth() / 2, vGap, this.getWidth() / 4);
        }
    }

    /*
     * Display a subtree rooted at position (x, y)
     */
    private void displayTree(Node root, double x, double y, double hGap) {
        
        if (root.left != null) {
// Draw a line to the left node
            getChildren().addAll(new Line(x - hGap, y + vGap, x, y),new Text(x-hGap/2,y+vGap/2,"0"));
// Draw the left subtree recursively
            displayTree(root.left, x - hGap, y + vGap, hGap / 2);
        }
        if (root.right != null) {
// Draw a line to the right node
            getChildren().addAll(new Line(x + hGap, y + vGap, x, y),new Text(x+hGap/2,y+vGap/2,"1"));
// Draw the right subtree recursively
            displayTree(root.right, x + hGap, y + vGap, hGap / 2);
        }

// Display a node
        Circle circle = new Circle(x, y, radius);
        circle.setFill(Color.RED);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(3);
        getChildren().addAll(circle, new Text(x - 4, y + 4, root.element+","+ root.weight));
    }

}
