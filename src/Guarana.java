import java.io.Serializable;

public class Guarana extends Plant implements Serializable {

    public Guarana(int x, int y, World world)
    {
        super(Constants.GUARANA_STR, x, y, Constants.GUARANA_LOOK, world);
    }

    @Override
    public Organism createCopy(int newX, int newY)
    {
        return new Guarana(newX, newY, this.world);
    }

    @Override
    public int getColor()
    {
        return Constants.GUARANA_COLOR;
    }

    @Override
    public void collision(Organism attacker)
    {
        world.log(attacker.getLook() + " tries to eat: " + getLook() +
                " at (" + getX() + "," + getY() + ")");

        if (getStrength() > attacker.getStrength()) {
            world.log(getLook() + " wins!");
            world.removeOrganism(attacker);
        }
        else
        {
            world.log(attacker.getLook() + " wins, +3STR boost!");
            int targetX = getX();
            int targetY = getY();
            world.removeOrganism(this);
            world.moveOrganism(attacker, targetX, targetY);
            attacker.setStrength(attacker.getStrength() + Constants.GUARANA_STR_BOOST);
        }
    }
}
