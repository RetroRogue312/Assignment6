import java.util.ArrayList;

public class AllLess
{
    public TreeNode TreeBuilder(String[] s, int i)
    {
        if (i >= s.length)
            return null;
        if (s[i] == null)
            return null;

        TreeNode root = new TreeNode(s[i], null, null);
        root.left = TreeBuilder(s, 2 * i + 1);
        root.right = TreeBuilder(s, 2 * i + 2);
        return root;
    }


    public ArrayList<String> allLess(String[] heap, int x)
    {
        TreeNode root = TreeBuilder(heap, 0);
        ArrayList<String> result = new ArrayList<>();
        search(root, x, result);
        return result;
    }

    public void search(TreeNode<String> root, int x, ArrayList<String> output)
    {
       if (root == null)
           return;
       if (root.val != null && root.val.length() < x && root.val.length() > 0)
           output.add(root.val);

       search(root.left, x, output);
       search(root.right, x, output);

    }

    public static void main(String[] args)
    {
        AllLess l = new AllLess();
        String[] input = {"zero","size","nutella","jojo","luna","isse","astor","as","entertien","","cal"};
        ArrayList<String> output = l.allLess(input, 5);
        for (int i = 0; i < output.size(); i++)
        {
            System.out.println(output.get(i));
        }
    }
}
