import java.io.Serializable;

public class Sheep extends Animal implements Serializable
{
    public Sheep (int x, int y, World world)
    {
        super(Constants.SHEEP_STR, Constants.SHEEP_INIT, x, y, Constants.SHEEP_LOOK, world);
    }

    @Override
    public Organism createCopy(int newX, int newY)
    {
        return new Sheep(newX, newY, this.world);
    }

    @Override
    public int getColor()
    {
        return Constants.SHEEP_COLOR;
    }
}
