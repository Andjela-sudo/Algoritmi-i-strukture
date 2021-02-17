package cs103.pz.pkg3972.andjelabojic;

import static cs103.pz.pkg3972.andjelabojic.HuffmanCode.getCharacterFrequency;
import static cs103.pz.pkg3972.andjelabojic.HuffmanCode.getCode;
import static cs103.pz.pkg3972.andjelabojic.HuffmanCode.getHuffmanTree;
import java.util.HashMap;
import java.util.Scanner;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        BST<Integer> treeBST = new BST<>();
        AVLTree<Integer> treeAVL = new AVLTree<>();
        BTView viewBST = new BTView(treeBST);
        BTView viewAVL = new BTView(treeAVL);

        Button btnAVL = new Button("AVL STABLO");
        Button btnBST = new Button("BST STABLO");
        Button btnHuffman = new Button("Huffman code && TREE");

        VBox dugmici = new VBox();
        dugmici.getChildren().addAll(new Label("Izaberi opciju: "), btnBST, btnAVL, btnHuffman);
        BorderPane paneMenu = new BorderPane();
        paneMenu.setCenter(dugmici);
        Scene menuScene = new Scene(paneMenu, 300, 300);
        menuScene.getStylesheets().add("resources/style.css");

        btnHuffman.setOnAction(event -> {
            BorderPane pane = new BorderPane();
            Button back = new Button("back");
            back.setOnAction(ev -> {
                primaryStage.setScene(menuScene); // Podesi scenu u stage-u
                primaryStage.show(); // Prikazi stage
            });
            pane.setTop(back);

            Button btPrikazi = new Button("Prikazi");
            TextField tfText = new TextField();
            tfText.setPrefColumnCount(12);
            tfText.setAlignment(Pos.BASELINE_RIGHT);

            HBox hBox = new HBox(5);
            hBox.getChildren().addAll(new Label("Text: "),
                    tfText, btPrikazi);
            hBox.setAlignment(Pos.CENTER);
            pane.setBottom(hBox);

            btPrikazi.setOnAction(e -> {

                String text = tfText.getText(); //input.nextLine();

                int[] counts = getCharacterFrequency(text); // Count frequency

                HuffView viewHuffman = new HuffView(counts);
                viewHuffman.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                pane.setCenter(viewHuffman);//view
                viewHuffman.displayTree();
                String output = "";
                HuffmanCode.Tree tree = getHuffmanTree(counts); // Create a Huffman tree
                String[] codes = getCode(tree.root); // Get codes
                  HashMap<String, String> slovoKod = new HashMap<String, String>();
                for (int i = 0; i < codes.length; i++) {
                    if (counts[i] != 0) // (char)i is not in text if counts[i] is 0
                    {
                        slovoKod.put((char)i+"",codes[i]);
                        output += i + "   " + (char) i + "   " + counts[i] + "   " + codes[i] + "\n";
                    }
                }
                viewHuffman.setStatus(output);

                String res = "";

              for(int i = 0 ; i< text.length();i++){
                  res+= slovoKod.get(text.charAt(i)+"");
              }
                viewHuffman.setRes(res);

            });

            Scene scene = new Scene(pane, 600, 400);
            scene.getStylesheets().add("resources/styleTree.css");
            primaryStage.setScene(scene); // Podesi scenu u stage-u
            primaryStage.show(); // Prikazi stage
        });

        btnBST.setOnAction(event -> {
            BorderPane pane = new BorderPane();
            Button back = new Button("back");
            back.setOnAction(ev -> {
                primaryStage.setScene(menuScene); // Podesi scenu u stage-u
                primaryStage.show(); // Prikazi stage
            });
            pane.setTop(back);
            pane.setCenter(viewBST);//view
            TextField tfKey = new TextField();
            tfKey.setPrefColumnCount(3);
            tfKey.setAlignment(Pos.BASELINE_RIGHT);
            Button btInsert = new Button("Insert");
            Button btDelete = new Button("Delete");
            Button btIzbalansiraj = new Button("Izbalansiraj");
            Button btPronadji = new Button("Pronadji");
            Button btPreorder = new Button("Preorder");
            Button btDodajKoraci = new Button("Dodaj Koraci");
            HBox hBox = new HBox(5);
            hBox.getChildren().addAll(new Label("Kljuc: "),
                    tfKey, btInsert, btDelete, btIzbalansiraj, btPronadji, btDodajKoraci, btPreorder);
            hBox.setAlignment(Pos.CENTER);
            pane.setBottom(hBox);
            btInsert.setOnAction(e -> {
                int key = Integer.parseInt(tfKey.getText());
                if (treeBST.search(key)) { // kljuc je vec u stablu
                    viewBST.displayTree();
                    viewBST.setStatus(key + " vec postoji u stablu");
                } else {
                    treeBST.insert(key); // Ubaci novi kljuc
                    viewBST.displayTree();
                    viewBST.setStatus(key + " je ubacen u stablo");
                }
            });
            btDelete.setOnAction(e -> {
                int key = Integer.parseInt(tfKey.getText());
                if (!treeBST.search(key)) { // kljuc nije u stablu
                    viewBST.displayTree();
                    viewBST.setStatus(key + " ne postoji u stablu");
                } else {
                    treeBST.delete(key); // Obrisi kljuc
                    viewBST.displayTree();
                    viewBST.setStatus(key + " je obrisan iz stabla");
                }
            });
            btIzbalansiraj.setOnAction(e -> {

                treeBST.root = treeBST.napraviBalansiranoStablo(treeBST.root);
                viewBST.displayTree();
                viewBST.setStatus(" Uspesno izbalansirano stablo ");

            });
            btPronadji.setOnAction(e -> {
                int key = Integer.parseInt(tfKey.getText());
                if (!treeBST.search(key)) { // kljuc nije u stablu
                    viewBST.displayTree();
                    viewBST.setStatus(key + " ne postoji u stablu");
                } else {

                    viewBST.displayTree();
                    viewBST.displaySearch(key);
                    viewBST.setStatus(key + " je pronadjen");
                }

            });
            btDodajKoraci.setOnAction(e -> {
                int key = Integer.parseInt(tfKey.getText());
                if (treeBST.search(key)) { // kljuc je vec u stablu
                    viewBST.displayTree();
                    viewBST.setStatus(key + " vec postoji u stablu");
                } else {

                    viewBST.displayTree();
                    viewBST.displayInsert(key);
                    //treeBST.insert(key); // Ubaci novi kljuc
                    viewBST.setStatus(key + " je ubacen u stablo");
                }

            });
            btPreorder.setOnAction(e -> {

                viewBST.preorderDisplay();
                viewBST.setStatus("");

            });
            Scene scene = new Scene(pane, 600, 400);
            scene.getStylesheets().add("resources/styleTree.css");
            primaryStage.setScene(scene); // Podesi scenu u stage-u
            primaryStage.show(); // Prikazi stage
        });

        btnAVL.setOnAction(event -> {
            BorderPane pane = new BorderPane();
            Button back = new Button("back");
            back.setOnAction(ev -> {
                primaryStage.setScene(menuScene); // Podesi scenu u stage-u
                primaryStage.show(); // Prikazi stage
            });
            pane.setTop(back);
            pane.setCenter(viewAVL);//view
            TextField tfKey = new TextField();
            tfKey.setPrefColumnCount(3);
            tfKey.setAlignment(Pos.BASELINE_RIGHT);
            Button btInsert = new Button("Insert");
            Button btDelete = new Button("Delete");
            Button btPronadji = new Button("Pronadji");
            HBox hBox = new HBox(5);
            hBox.getChildren().addAll(new Label("Kljuc: "),
                    tfKey, btInsert, btDelete, btPronadji);
            hBox.setAlignment(Pos.CENTER);
            pane.setBottom(hBox);
            btInsert.setOnAction(e -> {
                int key = Integer.parseInt(tfKey.getText());
                if (treeAVL.search(key)) { // kljuc je vec u stablu
                    viewAVL.displayTree();
                    viewAVL.setStatus(key + " vec postoji u stablu");
                } else {
                    treeAVL.insert(key); // Ubaci novi kljuc
                    viewAVL.displayTree();
                    viewAVL.setStatus(key + " je ubacen u stablo");
                }
            });
            btDelete.setOnAction(e -> {
                int key = Integer.parseInt(tfKey.getText());
                if (!treeAVL.search(key)) { // kljuc nije u stablu
                    viewAVL.displayTree();
                    viewAVL.setStatus(key + " nije u stablu");
                } else {
                    treeAVL.delete(key); // Obrisi kljuc
                    viewAVL.displayTree();
                    viewAVL.setStatus(key + " je obrisan iz stabla");
                }
            });
            btPronadji.setOnAction(e -> {

                int key = Integer.parseInt(tfKey.getText());
                if (!treeAVL.search(key)) { // kljuc nije u stablu
                    viewAVL.displayTree();
                    viewAVL.setStatus(key + " ne postoji u stablu");
                } else {

                    viewAVL.displayTree();
                    viewAVL.displaySearch(key);
                    viewAVL.setStatus(key + " je pronadjen");
                }

            });
            Scene scene = new Scene(pane, 600, 400);
            scene.getStylesheets().add("resources/styleTree.css");
            primaryStage.setScene(scene); // Podesi scenu u stage-u
            primaryStage.show(); // Prikazi stage
        });

        primaryStage.setTitle("Stabla");
        primaryStage.setScene(menuScene); // Podesi scenu u stage-u
        primaryStage.show(); // Prikazi stage
    }

    public static void main(String[] args) {
        launch(args);
    }

}
