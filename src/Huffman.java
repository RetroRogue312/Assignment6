import java.util.ArrayList;
import java.util.HashMap;

public class Huffman
{
    public HashMap<Character, Integer> freqMap;
    public HashMap<Character, String> codeMap;
    public HuffNode root;

    public void frequencyCount(String text)
    {
        freqMap = new HashMap<>();
        for(int i = 0; i < text.length(); i++)
        {
            char c = text.charAt(i);
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }
    }

    public void buildHuffman()
    {
        ArrayList<HuffNode> heap = new ArrayList<>();

        for (char c : freqMap.keySet())
        {
            HuffNode node = new HuffNode(c, freqMap.get(c));
            heap.add(node);
        }
        while (heap.size() > 1)
        {
            for (int i = 0; i < heap.size() - 1; i++)
            {
                for (int j = 0; j < heap.size() - i - 1; j++)
                {
                    if (heap.get(j).frequency > heap.get(j+1).frequency)
                    {
                        HuffNode temp = heap.get(j);
                        heap.set(j, heap.get(j + 1));
                        heap.set(j + 1, temp);
                    }
                }
            }

            HuffNode left = heap.remove(0);
            HuffNode right = heap.remove(0);
            HuffNode parent = new HuffNode(left, right);
            heap.add(parent);
        }
        root = heap.get(0);
    }

    public void genCode()
    {
        codeMap = new HashMap<>();
        generate(root, "");
    }

    public void generate(HuffNode node, String code)
    {
        if (node == null)
            return;

        if (node.left == null && node.right == null)
        {
            codeMap.put(node.letter, code);
            return;
        }

        generate(node.left, code + "0");
        generate(node.right, code + "1");
    }

    public String encode(String text)
    {
        String encoded = "";
        for (int i = 0; i < text.length(); i++)
        {
            encoded += codeMap.get(text.charAt(i));
        }

        return encoded;
    }

    public void printStats(String text)
    {
        System.out.println("Stats for " + text);

        System.out.println("Frequencies: ");
        for (char c: freqMap.keySet())
        {
            if (c == ' ')
                System.out.println("(space): " + freqMap.get(c));
            else
                System.out.println(c + ": " + freqMap.get(c));
        }
        System.out.println("Huffman Codes:");
        for (char c : codeMap.keySet())
        {
            if (c == ' ')
                System.out.println("(space): " + codeMap.get(c));
            else
                System.out.println(c + ": " + codeMap.get(c));
        }

        System.out.println("Encoded:");
        System.out.println(encode(text));
    }
}
