import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class World implements Serializable
{

    private final int width;
    private final int height;
    private Human player;

    //list of Organisms in our simulation
    private ArrayList<Organism> organisms = new ArrayList<>();
    private int logCounter = 0;


    public World(int width, int height)
    {
        this.width = width;
        this.height = height;
        organisms = new ArrayList<>();
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public Human getPlayer()
    {
        return player;
    }

    public List<Organism> getOrganisms() {
        return organisms;
    }

    public boolean contains(Organism org)
    {
        return organisms.contains(org);
    }


    public boolean isInside(int x, int y)
    {
        return x >= 0 && x < width && y >= 0 && y < height;
    }


    public Organism getOrganismAt(int x, int y)
    {
        if (!isInside(x, y))
        {
            return null;
        }

        for (Organism org : organisms)
        {
            if (org != null && org.getX() == x && org.getY() == y)
            {
                return org;
            }
        }

        return null;
    }


    public void moveOrganism(Organism org, int newX, int newY)
    {
        org.setPosition(newX, newY);
    }

    public Point findFreeNeighbor(int x, int y)
    {
        int[] dx = { 1, -1, 0, 0 };
        int[] dy = { 0, 0, 1, -1 };

        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (isInside(nx, ny) && getOrganismAt(nx, ny) == null) {
                return new Point(nx, ny); // found
            }
        }

        return null; // no free neighbor
    }


    public Point findEmptyField()
    {
        while (true) {
            int helperX = RandomHelper.randInt(0, width - 1);
            int helperY = RandomHelper.randInt(0, height - 1);

            boolean occupied = false;

            for (Organism org : organisms) {
                if (org.getX() == helperX && org.getY() == helperY) {
                    occupied = true;
                    break;
                }
            }

            if (!occupied) {
                return new Point(helperX, helperY);
            }
        }
    }


    public void addOrganism(Organism org)
    {
        organisms.add(org);
    }


    public void log(String text)
    {
        if (GraphicLayer.activeInstance != null)
        {
            GraphicLayer.activeInstance.appendLog(text);
        }
        //System.out.println("[LOG]: " + text);
        logCounter++;
    }


    public void sortOrganisms()
    {
        organisms.sort((a, b) ->
        {
            if (a.getInitiative() != b.getInitiative())
            {
                return Integer.compare(b.getInitiative(), a.getInitiative()); // descending
            }
            else
            {
                return Integer.compare(b.getAge(), a.getAge()); // descending
            }
        });
    }


    public void removeOrganism(Organism org)
    {
        organisms.remove(org);
    }

    public void addEachOrganismAtRandom(int count)
    {
        Point coords;

        coords = findEmptyField();
        player = new Human(coords.x, coords.y, this);
        addOrganism(player);

        for (int i = 0; i < count; i++) {
            coords = findEmptyField();
            addOrganism(new Sheep(coords.x, coords.y, this));

            coords = findEmptyField();
            addOrganism(new Wolf(coords.x, coords.y, this));

            coords = findEmptyField();
            addOrganism(new Antelope(coords.x, coords.y, this));

            coords = findEmptyField();
            addOrganism(new Fox(coords.x, coords.y, this));

            coords = findEmptyField();
            addOrganism(new Turtle(coords.x, coords.y, this));

            coords = findEmptyField();
            addOrganism(new Grass(coords.x, coords.y, this));

            coords = findEmptyField();
            addOrganism(new SowThisle(coords.x, coords.y, this));

            coords = findEmptyField();
            addOrganism(new BellaDonna(coords.x, coords.y, this));

            coords = findEmptyField();
            addOrganism(new Guarana(coords.x, coords.y, this));

            coords = findEmptyField();
            addOrganism(new SosnowskyHogweed(coords.x, coords.y, this));
        }
    }
}
