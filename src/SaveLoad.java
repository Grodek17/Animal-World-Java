import java.io.*;

public class SaveLoad
{
    //save simulation state to a file
    public static void saveToFile(World world, File file)
    {
        //create new ObjectOutputStream to given file (classes that implements Serializable)
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file)))
        {
            //"download" to file world Class and all it's contents (ArrayList with all other classes)
            out.writeObject(world);
            world.log("Game saved. \uD83D\uDCBE");
        }
        catch (IOException saveError)
        {
            System.err.println("Save failed: " + saveError.getMessage());
        }
    }

    //restore previously saved simulation state
    public static World loadFromFile(File file)
    {
        //read objects saved in binary file
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file)))
        {
            //get World object with all it's saved "sub-Objects"
            return (World) in.readObject();
        }
        catch (IOException | ClassNotFoundException loadError)
        {
            System.err.println("Load failed: " + loadError.getMessage());
            return null;
        }
    }
}
