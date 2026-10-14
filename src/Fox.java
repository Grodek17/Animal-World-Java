import java.io.Serializable;
import java.util.*;

public class Fox extends Animal implements Serializable
{
    public Fox (int x, int y, World world)
    {
        super(Constants.FOX_STR, Constants.FOX_INIT, x, y, Constants.FOX_LOOK, world);
    }

    @Override
    public Organism createCopy(int newX, int newY)
    {
        return new Fox(newX, newY, this.world);
    }

    @Override
    public int getColor()
    {
        return Constants.FOX_COLOR;
    }

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
            }
            else
            {
                if (other.getStrength() > Constants.FOX_STR)
                {
                    continue; // too strong, don't attack
                }
                // attack
                other.collision(this);
                return;
            }
        }
        world.log("Entity: " + getLook() + " cannot move.");
    }
}
