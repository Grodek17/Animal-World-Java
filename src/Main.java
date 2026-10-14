import javax.swing.*;

public class Main
{
    public static void main(String[] args)
    {
        //initialize world
        World world = new World(10, 10);

        //add X ammount of each organisms to the world (only one human)
        world.addEachOrganismAtRandom(2);

        //activate swing thread
        SwingUtilities.invokeLater(() ->
        {
            //create new GraphicalLayer
            new GraphicLayer(world);
        });
    }

}