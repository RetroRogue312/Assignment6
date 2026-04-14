import java.util.ArrayList;
import java.util.Random;

public class EncodingSystem
{
    public Huffman h;
    public ArrayList<String> texts;
    public ArrayList<String> encoded;

    public EncodingSystem(ArrayList<String> texts)
    {
        this.texts = texts;
        this.encoded = new ArrayList<>();
        this.h = new Huffman();
    }

    public void encode()
    {
        for (String text : texts)
        {
            h.frequencyCount(text);
            h.buildHuffman();
            h.genCode();

            encoded.add(h.encode(text));
        }
    }

    public String highestCode()
    {
        String max = "";
        for (int i = 0; i < encoded.size(); i++)
        {
            if (encoded.get(i).length() > max.length())
                max = encoded.get(i);
        }
        return max;
    }

    public void shuffleCodes()
    {
        Random rng = new Random();
        for (int i = 0; i < encoded.size(); i++)
        {
            int rand = rng.nextInt(encoded.size());
            String temp = encoded.get(i);
            encoded.set(i,encoded.get(rand));
            encoded.set(rand, temp);
        }
    }

    public void printCodes()
    {
        for (int i = 0; i < encoded.size(); i++)
        {
            System.out.println(encoded.get(i));
        }
    }

    public void stats()
    {
        for (String text : texts)
        {
            h.frequencyCount(text);
            h.buildHuffman();
            h.genCode();
            h.printStats(text);
        }
    }

    public static void main(String[] args)
    {
        ArrayList<String> texts = new ArrayList<>();
        texts.add("marcus fenix is a gear");

        EncodingSystem system = new EncodingSystem(texts);
        system.encode();

        System.out.println("\nHighest code:");
        System.out.println(system.highestCode());

        System.out.println("\nAll Codes:");
        system.printCodes();

        System.out.println("\nStats:");
        system.stats();
    }
}
