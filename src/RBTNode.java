public class RBTNode
{
    public static enum Color {
        RED,
        BLACK;
    }
    public int key;
    public RBTNode parent;
    public RBTNode left;
    public RBTNode right;
    public Color color;

    public RBTNode(int nodeKey, RBTNode parentNode, boolean isRed) {
        this(nodeKey, parentNode, isRed, (RBTNode)null, (RBTNode)null);
    }

    public RBTNode(int nodeKey, RBTNode parentNode, boolean isRed, RBTNode leftChild, RBTNode rightChild) {
        this.key = nodeKey;
        this.parent = parentNode;
        this.left = leftChild;
        this.right = rightChild;
        this.color = isRed ? RBTNode.Color.RED : RBTNode.Color.BLACK;
    }

    public boolean areBothChildrenBlack() {
        if (this.left != null && this.left.isRed()) {
            return false;
        } else {
            return this.right == null || !this.right.isRed();
        }
    }



    public RBTNode getGrandparent() {
        return this.parent == null ? null : this.parent.parent;
    }

    public RBTNode getPredecessor() {
        RBTNode node;
        for(node = this.left; node.right != null; node = node.right) {
        }

        return node;
    }

    public RBTNode getSibling() {
        if (this.parent != null) {
            return this == this.parent.left ? this.parent.right : this.parent.left;
        } else {
            return null;
        }
    }

    public RBTNode getUncle() {
        RBTNode grandparent = this.getGrandparent();
        if (grandparent == null) {
            return null;
        } else {
            return grandparent.left == this.parent ? grandparent.right : grandparent.left;
        }
    }

    public boolean isBlack() {
        return this.color == RBTNode.Color.BLACK;
    }

    public boolean isRed() {
        return this.color == RBTNode.Color.RED;
    }

    public boolean replaceChild(RBTNode currentChild, RBTNode newChild) {
        if (this.left == currentChild) {
            this.setLeft(newChild);
            return true;
        } else if (this.right == currentChild) {
            this.setRight(newChild);
            return true;
        } else {
            return false;
        }
    }

    public void setLeft(RBTNode newChild) {
        this.left = newChild;
        if (this.left != null) {
            this.left.parent = this;
        }

    }

    public void setRight(RBTNode newChild) {
        this.right = newChild;
        if (this.right != null) {
            this.right.parent = this;
        }

    }

    public void setKey(int key) {
        this.key = key;
    }


}
