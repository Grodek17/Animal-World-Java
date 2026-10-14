import java.awt.*;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


public abstract class Animal extends Organism implements Serializable
{

    public Animal(int str, int init, int x, int y, String look, World world)
    {
        super(str, init, x, y, look, world); // assuming superclass has such constructor
    }

    // create copy of organism for breeding
    public abstract Organism createCopy(int newX, int newY);

    @Override
    public void move()
    {
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        List<Integer> directions = new ArrayList<>(Arrays.asList(0, 1, 2, 3));

        Collections.shuffle(directions, new Random());

        for (int dir : directions)
        {
            int newX = getX() + dx[dir];
            int newY = getY() + dy[dir];

            if (!world.isInside(newX, newY))
            {
                continue;
            }

            Organism other = world.getOrganismAt(newX, newY);

            if (other == null)
            {
                world.moveOrganism(this, newX, newY);
                world.log(getLook() + " | moves to: (" + getX() + "," + getY() + ")"
                            + " | age: " + getAge()
                            + " | initiative: " + getInitiative());
                return;
            }
            else
            {
                other.collision(this);
                return;
            }
        }

        world.log(getLook() + " cannot move.");
    }

    @Override
    public void collision(Organism attacker) {
        // breeding: same species
        if (this.getLook().equals(attacker.getLook()))
        {
            Point spawn = world.findFreeNeighbor(getX(), getY());

            if (spawn != null)
            {
                Organism child = this.createCopy(spawn.x, spawn.y);
                world.addOrganism(child);
                world.log(getLook() + " breeds at: (" + spawn.x + "," + spawn.y + ")");
            } else {
                world.log(getLook() + " failed to breed.");
            }

        }
        else
        {
            // fight
            world.log("⚔\uFE0FFight: " + attacker.getLook() +
                    " (" + attacker.getStrength() + ")" +
                    " vs " + getLook() + " (" + getStrength() + ")" +
                    " at (" + getX() + "," + getY() + ")");

            if (getStrength() > attacker.getStrength())
            {
                world.log(getLook() + " wins fight");
                world.removeOrganism(attacker);
            }
            else
            {
                world.log(attacker.getLook() + " wins fight");
                int targetX = getX();
                int targetY = getY();
                world.removeOrganism(this);
                world.moveOrganism(attacker, targetX, targetY);
            }
        }
    }
}
