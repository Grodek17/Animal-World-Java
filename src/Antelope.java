import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;

public class Antelope extends Animal implements Serializable
{
    public Antelope (int x, int y, World world)
    {
        super(Constants.ANTELOPE_STR, Constants.ANTELOPE_INIT, x, y, Constants.ANTELOPE_LOOK, world);
    }

    @Override
    public Organism createCopy(int newX, int newY)
    {
        return new Antelope(newX, newY, this.world);
    }

    @Override
    public int getColor()
    {
        return Constants.ANTELOPE_COLOR;
    }

    @Override
    public void move() {
        int[] dx = {2, -2, 0, 0};
        int[] dy = {0, 0, 2, -2};
        List<Integer> directions = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
        Collections.shuffle(directions, new Random());

        for (int dir : directions) {
            int newX = getX() + dx[dir];
            int newY = getY() + dy[dir];

            if (!world.isInside(newX, newY)) continue;

            Organism other = world.getOrganismAt(newX, newY);

            if (other == null)
            {
                world.moveOrganism(this, newX, newY);
                world.log(getLook() +
                        " | moves to: (" + getX() + "," + getY() + ")" +
                        " | age: " + getAge() +
                        " | initiative: " + getInitiative());
                return;
            } else
            {
                other.collision(this);
                return;
            }
        }

        world.log("Entity: " + getLook() + " cannot move.");
    }

    @Override
    public void collision(Organism attacker)
    {
        // 50% chance of escape if different species
        if (!attacker.getLook().equals(this.getLook()) && RandomHelper.randChance(50))
        {
            Point escape = world.findFreeNeighbor(getX(), getY());

            if (escape != null && world.isInside(escape.x, escape.y))
            {
                // attacker takes this tile, antelope jumps to escape
                world.moveOrganism(attacker, getX(), getY());
                world.moveOrganism(this, escape.x, escape.y);

                world.log("Antelope escapes from: " + attacker.getLook() +
                        " to (" + escape.x + "," + escape.y + ")");
                return;
            }
        }

        // default fight logic
        super.collision(attacker);
    }
}
