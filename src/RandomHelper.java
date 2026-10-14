import java.util.Random;

public class RandomHelper
{
    private static final Random rand = new Random();

    public static boolean randChance(int chancePercent)
    {
        return rand.nextInt(100) < chancePercent;
    }

    public static int randInt(int min, int max)
    {
        return rand.nextInt(max - min + 1) + min;
    }
}
