import java.io.Serializable;

public class Wolf extends Animal implements Serializable
{
    public Wolf (int x, int y, World world)
    {
        super(Constants.WOLF_STR, Constants.WOLF_INIT, x, y, Constants.WOLF_LOOK, world);
    }

    @Override
    public Organism createCopy(int newX, int newY)
    {
        return new Wolf(newX, newY, this.world);
    }

    @Override
    public int getColor()
    {
        return Constants.WOLF_COLOR;
    }
}
