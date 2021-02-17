package cs103.pz.pkg3972.andjelabojic;

public class AVLTree<E extends Comparable<E>> extends BST<E> {

    public AVLTree() {
    }

    public AVLTree(E[] objects) {
        super(objects);
    }
//Preklopi metod osnovne klase kako bi kreirali čvor AVL stabla

    @Override
    protected AVLTreeNode<E> createNewNode(E e) {
        return new AVLTreeNode<E>(e);
    }
//Dodaj element u stablo i rebalansiraj ukoliko je potrebno

    @Override
    public boolean insert(E e) {
        boolean successful = super.insert(e);
        if (!successful) {
            return false; // element e je već u stablu
        } else {
            balancePath(e); // Balansiranje stabla od čvora e do korena, ukoliko je potrebno
        }
        return true; // e je dodat
    }

    /*
* Ažuriraj visinu datog čvora
     */
    private void updateHeight(AVLTreeNode<E> node) {
        if (node.left == null && node.right == null) // čvor je list
        {
            node.height = 0;
        } else if (node.left == null) // čvor nema levo podstablo
        {
            node.height = 1 + ((AVLTreeNode<E>) (node.right)).height;
        } else if (node.right == null) // čvor nema desno podstablo
        {
            node.height = 1 + ((AVLTreeNode<E>) (node.left)).height;
        } else {
            node.height = 1 + Math.max(((AVLTreeNode<E>) (node.right)).height,
                    ((AVLTreeNode<E>) (node.left)).height);
        }
    }
//Balansiraj čvorove na putanji iz datog čvora do korena ukoliko je potrebno

    private void balancePath(E e) {
        java.util.ArrayList<TreeNode<E>> path = path(e);
        for (int i = path.size() - 1; i >= 0; i--) {
            AVLTreeNode<E> A = (AVLTreeNode<E>) (path.get(i));
            updateHeight(A);
            AVLTreeNode<E> parentOfA = (A == root) ? null : (AVLTreeNode<E>) (path.get(i - 1));
            switch (balanceFactor(A)) {
                case -2:
                    if (balanceFactor((AVLTreeNode<E>) A.left) <= 0) {
                        balanceLL(A, parentOfA); // Uradi LL rotaciju
                    } else {
                        balanceLR(A, parentOfA); // Uradi LR rotaciju
                    }
                    break;
                case +2:
                    if (balanceFactor((AVLTreeNode<E>) A.right) >= 0) {
                        balanceRR(A, parentOfA); // Uradi RR rotaciju
                    } else {
                        balanceRL(A, parentOfA); // Uradi RL rotaciju
                    }
            }
        }
    }
// Vrati balans faktor datog čvora

    private int balanceFactor(AVLTreeNode<E> node) {
// čvor nema desno podstablo
        if (node.right == null) {
            return -node.height;
        } // čvor nema levo podstablo
        else if (node.left == null) {
            return +node.height;
        } else {
            return ((AVLTreeNode<E>) node.right).height - ((AVLTreeNode<E>) node.left).height;
        }
    }
// LL balansiranje

    private void balanceLL(TreeNode<E> A, TreeNode<E> parentOfA) {
        TreeNode<E> B = A.left; // A je teže na levoj i B je teže na levoj strani
        if (A == root) {
            root = B;
        } else {
            if (parentOfA.left == A) {
                parentOfA.left = B;
            } else {
                parentOfA.right = B;
            }
        }
        A.left = B.right; // T2 postaje podstablo od A
        B.right = A; // A postaje levo dete od B
        updateHeight((AVLTreeNode<E>) A);
        updateHeight((AVLTreeNode<E>) B);
    }
//LR balansiranje

    private void balanceLR(TreeNode<E> A, TreeNode<E> parentOfA) {
        TreeNode<E> B = A.left; // A je teže na levoj strani
        TreeNode<E> C = B.right; // B je teže na desnoj strani
        if (A == root) {
            root = C;
        } else {
            if (parentOfA.left == A) {
                parentOfA.left = C;
            } else {
                parentOfA.right = C;
            }
        }
        A.left = C.right; // T3 postaje levo podstablo od A
        B.right = C.left; // T2 postaje desno podstablo od B
        C.left = B;
        C.right = A;
// Podesi visine
        updateHeight((AVLTreeNode<E>) A);
        updateHeight((AVLTreeNode<E>) B);
        updateHeight((AVLTreeNode<E>) C);
    }
//Balance RR

    private void balanceRR(TreeNode<E> A, TreeNode<E> parentOfA) {
        TreeNode<E> B = A.right; // A je teže na dasnoj i B je teže na desnoj strani
        if (A == root) {
            root = B;
        } else {
            if (parentOfA.left == A) {
                parentOfA.left = B;
            } else {
                parentOfA.right = B;
            }
        }
        A.right = B.left; // T2 postaje desno podstablo od A
        B.left = A;
        updateHeight((AVLTreeNode<E>) A);
        updateHeight((AVLTreeNode<E>) B);
    }
//Balance RL balansiranje

    private void balanceRL(TreeNode<E> A, TreeNode<E> parentOfA) {
        TreeNode<E> B = A.right; // A je teže na desnoj strani
        TreeNode<E> C = B.left; // B je teže na levoj strani
        if (A == root) {
            root = C;
        } else {
            if (parentOfA.left == A) {
                parentOfA.left = C;
            } else {
                parentOfA.right = C;
            }
        }
        A.right = C.left; // T2 postaje podstablo od A
        B.left = C.right; // T3 postaje podstablo od B
        C.left = A;
        C.right = B;
// Podešavanje visnia
        updateHeight((AVLTreeNode<E>) A);
        updateHeight((AVLTreeNode<E>) B);
        updateHeight((AVLTreeNode<E>) C);
    }

    @Override
    /* Brisanje elementa iz binarnog stabla
* Vrati true ukoliko je elemt uspešno obrisan,
* Vrati false ukoliko elemnt nije uspešno obrisan
     */
    public boolean delete(E element) {
        if (root == null) {
            return false; // Element se ne nalazi u stablu
        }
// Lociraj čvor koji treba obrisati, kao i njegovog roditelja
        TreeNode<E> parent = null;
        TreeNode<E> current = root;
        while (current != null) {
            if (element.compareTo(current.element) < 0) {
                parent = current;
                current = current.left;
            } else if (element.compareTo(current.element) > 0) {
                parent = current;
                current = current.right;
            } else {
                break;
            }
        }
        if (current == null) {
            return false; // Element se ne nalazi u stablu
        }
// Slučaj 1:
// Trenutnu čvor nema levo dete
        if (current.left == null) {
// Spoji roditelja sa desnim detetom trenutnog čvora
            if (parent == null) {
                root = current.right;
            } else {
                if (element.compareTo(parent.element) < 0) {
                    parent.left = current.right;
                } else {
                    parent.right = current.right;
                }
// Rebalansiraj stablu ukoliko je potrebno
                balancePath(parent.element);
            }
        } else {
// Slučaj 2: Trenutni čvor ima levo dete
// Lociraj najdešnji čvor i njegovog roditelja u levom podstablu trenutnog čvora
            TreeNode<E> parentOfRightMost = current;
            TreeNode<E> rightMost = current.left;
            while (rightMost.right != null) {
                parentOfRightMost = rightMost;
                rightMost = rightMost.right; // I dalje idi desno
            }
// Zameni element u trenutnom čvoru sa elementom u najdešnjem čvoru
            current.element = rightMost.element;
// Ukloni najdešnji čvor
            if (parentOfRightMost.right == rightMost) {
                parentOfRightMost.right = rightMost.left;
            } // Spvejlani slučaj: roditelj najdešnjeg čvora je trenutni element
            else {
                parentOfRightMost.left = rightMost.left;
            }
// Rebalansiraj stablu ukoliko je potrebno
            balancePath(parentOfRightMost.element);
        }
        size--;
        return true; // Element je obrisan
    }
// AVLTreeNode je TreeNode kome dodajemo visinu

    protected static class AVLTreeNode<E extends Comparable<E>> extends
            BST.TreeNode<E> {

        protected int height = 0;

        public AVLTreeNode(E o) {
            super(o);
        }
    }
}
