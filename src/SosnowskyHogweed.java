import java.io.Serializable;

public class SosnowskyHogweed extends Plant implements Serializable
{
    public SosnowskyHogweed (int x, int y, World world)
    {
        super(Constants.SOSNOWSKY_STR, x, y, Constants.SOSNOWSKY_LOOK, world);
    }

    @Override
    public Organism createCopy(int newX, int newY)
    {
        return new SosnowskyHogweed(newX, newY, this.world);
    }

    @Override
    public int getColor()
    {
        return Constants.SOSNOWSKY_COLOR;
    }

    @Override
    public void move()
    {
        int[] killX = {1, -1, 0, 0, 1, -1, -1, 1};
        int[] killY = {0, 0, 1, -1, 1, -1, 1, -1};

        for (int dir = 0; dir < killX.length; dir++)
        {
            int kX = getX() + killX[dir];
            int kY = getY() + killY[dir];

            if (!world.isInside(kX, kY))
            {
                continue;
            }

            Organism adjOrg = world.getOrganismAt(kX, kY);

            if (adjOrg == null || adjOrg.getInitiative() == 0)
            {
                continue; // plant or empty
            }


            // if it's Human with immortality active, skip
            if (adjOrg.getLook().equals(Constants.HUMAN_LOOK) && adjOrg instanceof Human) {
                if (((Human) adjOrg).getAbilityFlag()) {
                    continue;
                }
            }



            // kill organism
            world.log("Hogweed kills: " + adjOrg.getLook() + " at (" + kX + "," + kY + ")");
            world.removeOrganism(adjOrg);
        }

        // default sowing behavior
        super.move();
    }


}
