import java.io.Serializable;

public abstract class Organism implements Serializable
{
    protected int strength;
    protected int initiative;
    protected int x;
    protected int y;
    protected int age = 0;
    protected String look;
    protected World world;

    public Organism(int strength, int initiative, int x, int y, String look, World world)
    {
        this.strength = strength;
        this.initiative = initiative;
        this.x = x;
        this.y = y;
        this.look = look;
        this.world = world;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public void setPosition(int newX, int newY)
    {
        this.x = newX;
        this.y = newY;
    }

    public int getStrength() { return strength; }
    public void setStrength(int s) { this.strength = s; }

    public int getInitiative() { return initiative; }

    public int getAge() { return age; }
    public void incrementAge() { age++; }

    public String getLook() { return look; }
    public void changeLook(String l) { this.look = l; }

    public World getWorld() { return world; }

    public abstract void move();

    public abstract void collision(Organism other);

    public abstract int getColor(); // np. do GUI
}
