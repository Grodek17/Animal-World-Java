import java.awt.*;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public abstract class Plant extends Organism implements Serializable
{

    public Plant(int str, int x, int y, String look, World world)
    {
        super(str, Constants.PLANT_INITIATIVE, x, y, look, world);
    }

    @Override
    public void move()
    {
        // small chance for sowing
        if (RandomHelper.randChance(Constants.NOT_SOWING_CHANCE_PERCENT))
        {
            return;
        }

        int newX = getX();
        int newY = getY();

        // find free neighbour
        Point freePos = world.findFreeNeighbor(getX(), getY());

        if (freePos != null)
        {
            Organism child = createCopy(freePos.x, freePos.y);
            world.addOrganism(child);
            world.log(getLook() + " sows at: (" + freePos.x + "," + freePos.y + ")");
        }
        else
        {
            world.log(getLook() + " failed to sow.");
        }
    }



    @Override
    public void collision(Organism attacker)
    {
        // log eating attempt
        world.log(attacker.getLook() + " tries to eat: " + getLook() + " at (" +
                getX() + "," + getY() + ")");

        if (getStrength() > attacker.getStrength())
        {
            world.log(getLook() + " wins!");
            world.removeOrganism(attacker);
        } else {
            world.log(attacker.getLook() + " wins!");
            int targetX = getX();
            int targetY = getY();
            world.removeOrganism(this);
            world.moveOrganism(attacker, targetX, targetY);
        }
    }


    public abstract Organism createCopy(int newX, int newY);

}
