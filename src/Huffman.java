import java.util.ArrayList;
import java.util.HashMap;

public class Huffman
{
    static HashMap<Character, Integer> freqMap;
    static HashMap<Character, String> codeMap;
    static HuffNode root;

    public static void frequencyCount(String text)
    {
        for(int i = 0; i < text.length(); i++)
        {
            char c = text.charAt(i);
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }
    }

    public static HuffNode buildHuffman()
    {
        ArrayList<HuffNode> Tree = new ArrayList<>();

        for (char c : freqMap.keySet())
        {
            HuffNode node = new HuffNode(c, freqMap.get(c));
            Tree.add(node);
        }
        while (Tree.size() > 1)
        {
            for (int i = 0; i < Tree.size() - 1; i++)
            {
                for (int j = 0; j < Tree.size() - i - 1; j++)
                {
                    if (Tree.get(j).frequency > Tree.get(j+1).frequency)
                    {
                        HuffNode temp = Tree.get(j);
                        Tree.set(j, Tree.get(j + 1));
                        Tree.set(j + 1, temp);
                    }
                }
            }

            HuffNode left = Tree.remove(0);
            HuffNode right = Tree.remove(0);
            HuffNode parent = new HuffNode(left, right);
            Tree.add(parent);
        }
        return Tree.get(0);
    }
}
