import java.io.Serializable;

public class SowThisle extends Plant implements Serializable
{

    public SowThisle(int x, int y, World world)
    {
        super(Constants.SOWTHISLE_STR, x, y, Constants.SOWTHISLE_LOOK, world);
    }

    @Override
    public Organism createCopy(int newX, int newY) {
        return new SowThisle(newX, newY, this.world);
    }

    @Override
    public int getColor() {
        return Constants.SOWTHISLE_COLOR;
    }

    @Override
    public void move()
    {
        // try sowing for three times
        for (int i = 0; i < 3; i++)
        {
            super.move();
        }
    }
}
