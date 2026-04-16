

public class EncryptedTree
{

    public boolean exists(int[] tree, int t)
    {
        TreeNode<Integer> root = TreeBuilder(tree, 0);
        if (root == null)
            return false;
        assignValues(root, 1);

        return search(root, t);
    }

    public TreeNode<Integer> TreeBuilder(int[] tree, int index)
    {
        if (index >= tree.length || tree[index] == -1)
            return null;

        TreeNode<Integer> root = new TreeNode<>(0, null, null);
        root.left = TreeBuilder(tree, 2 * index + 1);
        root.right = TreeBuilder(tree, 2 * index + 2);
        return root;
    }

    public void assignValues(TreeNode<Integer> root, int val)
    {
        if (root == null)
            return;
        root.val = val;

        if (root.left != null)
            assignValues(root.left, 3 * val + 1);
        if (root.right != null)
            assignValues(root.right, 2 * val + 5);
    }

    public boolean search(TreeNode<Integer> root, int t)
    {
        if (root == null)
            return false;
        if (root.val == t)
            return true;

        boolean leftFound = search(root.left, t);
        if (leftFound)
            return true;
        boolean rightFound = search(root.right,t);
        if (rightFound)
            return true;

        return true;
    }

    public static void main(String[] args)
    {
        int[] bt = {-2,-2,-1,-2,-1};
        int t = 1;
        EncryptedTree et = new EncryptedTree();
        System.out.println(et.exists(bt, t));
    }
}
