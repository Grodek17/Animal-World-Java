import java.io.Serializable;

public class BellaDonna extends Plant implements Serializable
{
    public BellaDonna (int x, int y, World world)
    {
        super(Constants.BELLADONNA_STR, x, y, Constants.BELLADONNA_LOOK, world);
    }

    @Override
    public Organism createCopy(int newX, int newY)
    {
        return new BellaDonna(newX, newY, this.world);
    }

    @Override
    public int getColor()
    {
        return Constants.BELLADONNA_COLOR;
    }

}
