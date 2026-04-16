import java.util.ArrayList;

public class BRTree
{
    private RBTNode root = null;

    public void deleteInRange(int a, int b) {
        ArrayList<Integer> keysToDelete = new ArrayList();
        this.collectRange(this.root, a, b, keysToDelete);

        for(int key : keysToDelete) {
            this.removeKey(key);
        }

    }

    public void collectRange(RBTNode node, int a, int b, ArrayList<Integer> keys) {
        if (node != null) {
            if (node.key > a) {
                this.collectRange(node.left, a, b, keys);
            }

            if (node.key >= a && node.key <= b) {
                keys.add(node.key);
            }

            if (node.key < b) {
                this.collectRange(node.right, a, b, keys);
            }

        }
    }

    public void TreeBuilder(int[] a) {
        for(int i = 0; i < a.length; ++i) {
            this.insertKey(a[i]);
        }

    }

    public void printInOrder(RBTNode node) {
        if (node != null) {
            this.printInOrder(node.left);
            System.out.print(node.key + " ");
            this.printInOrder(node.right);
        }
    }

    public boolean insertKey(int key) {
        if (this.contains(key)) {
            return false;
        } else {
            RBTNode newNode = new RBTNode(key, (RBTNode)null, true);
            this.insertNode(newNode);
            return true;
        }
    }

    private void insertNode(RBTNode node) {
        if (this.root == null) {
            this.root = node;
        } else {
            RBTNode currentNode = this.root;

            while(currentNode != null) {
                if (node.key < currentNode.key) {
                    if (currentNode.left == null) {
                        currentNode.setLeft(node);
                        break;
                    }

                    currentNode = currentNode.left;
                } else {
                    if (currentNode.right == null) {
                        currentNode.setRight(node);
                        break;
                    }

                    currentNode = currentNode.right;
                }
            }
        }

        node.color = RBTNode.Color.RED;
        this.insertionBalance(node);
    }

    private void insertionBalance(RBTNode node) {
        if (node.parent == null) {
            node.color = RBTNode.Color.BLACK;
        } else if (!node.parent.isBlack()) {
            RBTNode parent = node.parent;
            RBTNode grandparent = node.getGrandparent();
            RBTNode uncle = node.getUncle();
            if (uncle != null && uncle.isRed()) {
                parent.color = RBTNode.Color.BLACK;
                uncle.color = RBTNode.Color.BLACK;
                grandparent.color = RBTNode.Color.RED;
                this.insertionBalance(grandparent);
            } else {
                if (node == parent.right && parent == grandparent.left) {
                    this.rotateLeft(parent);
                    node = parent;
                    parent = parent.parent;
                } else if (node == parent.left && parent == grandparent.right) {
                    this.rotateRight(parent);
                    node = parent;
                    parent = parent.parent;
                }

                parent.color = RBTNode.Color.BLACK;
                grandparent.color = RBTNode.Color.RED;
                if (node == parent.left) {
                    this.rotateRight(grandparent);
                } else {
                    this.rotateLeft(grandparent);
                }

            }
        }
    }

    private void rotateLeft(RBTNode node) {
        RBTNode rightLeftChild = node.right.left;
        if (node.parent != null) {
            node.parent.replaceChild(node, node.right);
        } else {
            this.root = node.right;
            this.root.parent = null;
        }

        node.right.setLeft(node);
        node.setRight(rightLeftChild);
    }

    private void rotateRight(RBTNode node) {
        RBTNode leftRightChild = node.left.right;
        if (node.parent != null) {
            node.parent.replaceChild(node, node.left);
        } else {
            this.root = node.left;
            this.root.parent = null;
        }

        node.left.setRight(node);
        node.setLeft(leftRightChild);
    }

    private void bstRemove(int key) {
        RBTNode node = this.search(key);
        this.bstRemoveNode(node);
    }

    private void bstRemoveNode(RBTNode node) {
        if (node != null) {
            if (node.left != null && node.right != null) {
                RBTNode sucNode;
                for(sucNode = node.right; sucNode.left != null; sucNode = sucNode.left) {
                }

                int successorKey = sucNode.key;
                this.bstRemoveNode(sucNode);
                node.setKey(successorKey);
            } else if (node == this.root) {
                if (node.left != null) {
                    this.root = node.left;
                } else {
                    this.root = node.right;
                }

                if (this.root != null) {
                    this.root.parent = null;
                }
            } else if (node.left != null) {
                node.parent.replaceChild(node, node.left);
            } else {
                node.parent.replaceChild(node, node.right);
            }

        }
    }

    public boolean isNullOrBlack(RBTNode node) {
        return node == null ? true : node.isBlack();
    }

    public boolean isNotNullAndRed(RBTNode node) {
        return node == null ? false : node.isRed();
    }

    private void prepareForRemoval(RBTNode node) {
        if (!this.tryCase1(node)) {
            RBTNode sibling = node.getSibling();
            if (this.tryCase2(node, sibling)) {
                sibling = node.getSibling();
            }

            if (!this.tryCase3(node, sibling)) {
                if (!this.tryCase4(node, sibling)) {
                    if (this.tryCase5(node, sibling)) {
                        sibling = node.getSibling();
                    }

                    if (this.tryCase6(node, sibling)) {
                        sibling = node.getSibling();
                    }

                    sibling.color = node.parent.color;
                    node.parent.color = RBTNode.Color.BLACK;
                    if (node == node.parent.left) {
                        sibling.right.color = RBTNode.Color.BLACK;
                        this.rotateLeft(node.parent);
                    } else {
                        sibling.left.color = RBTNode.Color.BLACK;
                        this.rotateRight(node.parent);
                    }

                }
            }
        }
    }

    public boolean removeKey(int key) {
        RBTNode node = this.search(key);
        if (node != null) {
            this.removeNode(node);
            return true;
        } else {
            return false;
        }
    }

    private void removeNode(RBTNode node) {
        if (node.left != null && node.right != null) {
            RBTNode predNode = node.getPredecessor();
            int predecessorKey = predNode.key;
            this.removeNode(predNode);
            node.setKey(predecessorKey);
        } else {
            if (node.isBlack()) {
                this.prepareForRemoval(node);
            }

            this.bstRemove(node.key);
            if (this.root != null && this.root.isRed()) {
                this.root.color = RBTNode.Color.BLACK;
            }

        }
    }

    public boolean contains(int key) {
        return this.search(key) != null;
    }

    private RBTNode search(int target) {
        RBTNode currentNode = this.root;

        while(currentNode != null) {
            if (currentNode.key == target) {
                return currentNode;
            }

            if (target < currentNode.key) {
                currentNode = currentNode.left;
            } else {
                currentNode = currentNode.right;
            }
        }

        return null;
    }

    private boolean tryCase1(RBTNode node) {
        return node.isRed() || node.parent == null;
    }

    private boolean tryCase2(RBTNode node, RBTNode sibling) {
        if (sibling.isRed()) {
            node.parent.color = RBTNode.Color.RED;
            sibling.color = RBTNode.Color.BLACK;
            if (node == node.parent.left) {
                this.rotateLeft(node.parent);
            } else {
                this.rotateRight(node.parent);
            }

            return true;
        } else {
            return false;
        }
    }

    private boolean tryCase3(RBTNode node, RBTNode sibling) {
        if (node.parent.isBlack() && sibling.areBothChildrenBlack()) {
            sibling.color = RBTNode.Color.RED;
            this.prepareForRemoval(node.parent);
            return true;
        } else {
            return false;
        }
    }

    private boolean tryCase4(RBTNode node, RBTNode sibling) {
        if (node.parent.isRed() && sibling.areBothChildrenBlack()) {
            node.parent.color = RBTNode.Color.BLACK;
            sibling.color = RBTNode.Color.RED;
            return true;
        } else {
            return false;
        }
    }

    private boolean tryCase5(RBTNode node, RBTNode sibling) {
        if (this.isNotNullAndRed(sibling.left) && this.isNullOrBlack(sibling.right) && node == node.parent.left) {
            sibling.color = RBTNode.Color.RED;
            sibling.left.color = RBTNode.Color.BLACK;
            this.rotateRight(sibling);
            return true;
        } else {
            return false;
        }
    }

    private boolean tryCase6(RBTNode node, RBTNode sibling) {
        if (this.isNullOrBlack(sibling.left) && this.isNotNullAndRed(sibling.right) && node == node.parent.right) {
            sibling.color = RBTNode.Color.RED;
            sibling.right.color = RBTNode.Color.BLACK;
            this.rotateLeft(sibling);
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        int[] keys = new int[]{10, 19, 20, 30, 42, 55, 77};
        System.out.println("Tree before deletion: ");
        BRTree br = new BRTree();
        br.TreeBuilder(keys);
        System.out.println("Tree before deletion: ");
        br.printInOrder(br.root);
        br.deleteInRange(15, 20);
        System.out.println("\nTree after deletion: ");
        br.printInOrder(br.root);
    }
}
