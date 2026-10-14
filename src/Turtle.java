import java.io.Serializable;

public class Turtle extends Animal implements Serializable
{
    public Turtle (int x, int y, World world)
    {
        super(Constants.TURTLE_STR, Constants.TURTLE_INIT, x, y, Constants.TURTLE_LOOK, world);
    }

    @Override
    public Organism createCopy(int newX, int newY)
    {
        return new Turtle(newX, newY, this.world);
    }

    @Override
    public int getColor()
    {
        return Constants.TURTLE_COLOR;
    }

    @Override
    public void move() {
        if (RandomHelper.randChance(75)) {
            world.log("Turtle stays in place at (" + getX() + "," + getY() + ")");
            return;
        }

        // default animal move
        super.move();
    }

    @Override
    public void collision(Organism attacker) {
        if (attacker.getStrength() < 5 && !attacker.getLook().equals(this.getLook()))
        {
            world.log("Turtle deflects attack at (" + getX() + "," + getY() + ")");
            return;
        }

        // default animal collision
        super.collision(attacker);
    }


}
