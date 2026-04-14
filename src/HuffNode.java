public class HuffNode
{
    public char letter;
    public int frequency;
    public HuffNode left;
    public HuffNode right;

    public HuffNode(char c, int freq)
    {
        this.letter = c;
        this.frequency = freq;
        this.left = null;
        this.right = null;
    }
    public HuffNode(HuffNode left, HuffNode right)
    {
        this.letter = 0;
        this.left = left;
        this.right = right;
        this.frequency = left.frequency + right.frequency;
    }
}
