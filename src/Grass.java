import java.io.Serializable;

public class Grass extends Plant implements Serializable {

    public Grass(int x, int y, World world)
    {
        super(Constants.GRASS_STR, x, y, Constants.GRASS_LOOK, world); // strength = 0, initiative = 0
    }

    @Override
    public Organism createCopy(int newX, int newY)
    {
        return new Grass(newX, newY, this.world);
    }

    @Override
    public int getColor()
    {
        return Constants.GRASS_COLORCODE;
    }
}
