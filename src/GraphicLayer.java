import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GraphicLayer extends JFrame
{
    //TODO: SPRAWDZIC CZY TO TRANSIENT JEST WGL POTRZEBNE
    private final World world;                                  //World object storing whole simulation logic
    private final int width;                                    //width of Simulation
    private final int height;                                   //height of simulation
    private final transient JLabel[][] gridLabels;              //2d array of labels for grid simulation
    private final transient JTextArea logArea;                  //text area for printing logs
    public static  GraphicLayer activeInstance;                 //acts like pointer to "active GUI"
    public static int roundCounter = 0;                         //counts simulation turns
    private final transient JLabel  abilityLabel;               //for textInfo about special ability


    //adding new organisms to the simulation when clicked on a cell
    private void handleCellClick(int x, int y)
    {
        //check if cell is occupied
        if (world.getOrganismAt(x, y) != null)
        {
            world.log("❌ Cell is not empty ❌");
            return;
        }

        //array of menu options
        String[] options = {"Wolf" + Constants.WOLF_LOOK, "Fox" + Constants.FOX_LOOK, "Sheep" + Constants.SHEEP_LOOK,
                            "Human" + Constants.HUMAN_LOOK, "Guarana" + Constants.GUARANA_LOOK,
                            "Hogweed" + Constants.SOSNOWSKY_LOOK, "Turtle" + Constants.TURTLE_LOOK,
                            "Antelope" + Constants.ANTELOPE_LOOK, "BellaDonna" + Constants.BELLADONNA_LOOK,
                            "Grass" + Constants.GRASS_LOOK, "SowThisle" + Constants.SOWTHISLE_LOOK};

        //display dropdown menu window with options to choose from
        String choice = (String) JOptionPane.showInputDialog(
                this,                           //connected to main window
                "Select organism to add:",                     //message inside
                "Add Organism at (" + x + "," + y + ")",       //window title
                JOptionPane.PLAIN_MESSAGE,                     //plain window type
                null,                                          //no icon
                options,                                       //options to choose from
                options[0]);                                   //default chosen option

        //if choice window is closed
        if (choice == null)
        {
            return;
        }

        //create new organism based on chosen option
        Organism newOrg = null;
        switch (choice)
        {
            case "Wolf" + Constants.WOLF_LOOK:
                 newOrg = new Wolf(x, y, world);
                 break;
            case "Fox" + Constants.FOX_LOOK:
                newOrg = new Fox(x, y, world);
                break;
            case "Sheep" + Constants.SHEEP_LOOK:
                newOrg = new Sheep(x, y, world);
                break;
            case "Human" + Constants.HUMAN_LOOK:
                newOrg = new Human(x, y, world);
                break;
            case "Guarana" + Constants.GUARANA_LOOK:
                newOrg = new Guarana(x, y, world);
                break;
            case "Hogweed" + Constants.SOSNOWSKY_LOOK:
                newOrg = new SosnowskyHogweed(x, y, world);
                break;
            case "Turtle" + Constants.TURTLE_LOOK:
                newOrg = new Turtle(x, y, world);
                break;
            case "Antelope" + Constants.ANTELOPE_LOOK:
                newOrg = new Antelope(x, y, world);
                break;
            case "BellaDonna" + Constants.BELLADONNA_LOOK:
                newOrg = new BellaDonna(x, y, world);
                break;
            case "Grass" + Constants.GRASS_LOOK:
                newOrg = new Grass(x, y, world);
                break;
            case "SowThisle" + Constants.SOWTHISLE_LOOK:
                newOrg = new SowThisle(x, y, world);
                break;
            default:
                break;
        };

        //if new organism is not empty, add it to the simulation
        if (newOrg != null)
        {
            world.addOrganism(newOrg);
            world.log("Added " + choice + " at (" + x + ", " + y + ")");
            refreshWindow();
        }
    }

    //create graphical layer
    public GraphicLayer(World world)
    {
        //update values from world-class, get activeInstance "gui pointer"
        this.world = world;
        this.width = world.getWidth();
        this.height = world.getHeight();
        this.gridLabels = new JLabel[height][width];
        activeInstance = this;


        //additional window details
        setTitle("Animal World JAVA");                                                   //set main window title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                                  //close window and end program upon clicking "X"
        setLayout(new BorderLayout());                                                   //create 5 "zones" in program [north, center, east...]
        setFocusable(true);                                                              //can be "focused" for keyboard input
        requestFocusInWindow();                                                          //window starts "focused"
        Font fontNormal = new Font("Noto Color Emoji", Font.PLAIN, 42);      //new font to use in program
        Font fontBOLD = new Font("Noto Color Emoji", Font.BOLD, 19);



        //====================================================================================================================//
        //========================================== GRID PANEL "MAP" ========================================================//
        //====================================================================================================================//

        JPanel gridPanel = new JPanel(new GridLayout(height, width));                    //create new Jpanel with grid layout

        //start grid label
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                //x,y for mouse listener
                final int fx = x;
                final int fy = y;
                //create new label
                JLabel label = new JLabel(" ", SwingConstants.CENTER);          //empty for now
                label.setFont(fontNormal);                                            //normal font
                label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));    //light gray border
                gridLabels[y][x] = label;                                             //fill grid with this label [stores data]

                //add mouse listening field for each grid part
                label.addMouseListener(new MouseAdapter()
                {
                    //when mouse click this field activate handleCellClick for this field
                    @Override
                    public void mouseClicked(MouseEvent e)
                    {
                        handleCellClick(fx, fy);
                    }
                });

                //add this label to grid panel  [for graphics]
                gridPanel.add(label);
            }
        }

        //====================================================================================================================//
        //========================================== LOG PANEL ====== ========================================================//
        //====================================================================================================================//

        logArea = new JTextArea();                                             //logArea as text area
        logArea.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));                                           //normal font
        logArea.setEditable(false);                                            //user cannot write anything to this field (read only)
        logArea.setLineWrap(true);                                             //too long text automatically goes to new line
        logArea.setPreferredSize(new Dimension(300, 500));       //set preferred size
        JScrollPane scrollPane = new JScrollPane(logArea);                     //add scrollbar

        //====================================================================================================================//
        //========================================== SAVE/LOAD BUTTONS =======================================================//
        //====================================================================================================================//

        //create button with "SAVE" label
        JButton saveButton = new JButton("Save");
        //add action listening for this button
        saveButton.addActionListener(e -> {
            SaveLoad.saveToFile(world, new File(Constants.FILE_NAME));
            requestFocusInWindow();                                         //window lose focus on saving
        });

        //create button with "LOAD" label
        JButton loadButton = new JButton("Load");
        //add action listening for this button
        loadButton.addActionListener(e -> {
            World loaded = SaveLoad.loadFromFile(new File(Constants.FILE_NAME));
            if (loaded != null)
            {
                //close and delete current graphical window
                dispose();
                //create new graphical window with loaded state
                new GraphicLayer(loaded);
            }
        });

        //create button panel, add save and load buttons to it
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);

        //====================================================================================================================//
        //========================================== ABILITY PANEL ===========================================================//
        //====================================================================================================================//
        abilityLabel = new JLabel("Ability: NOT ACTIVE", SwingConstants.CENTER);    //text at beginning, centered out in panel
        abilityLabel.setFont(fontBOLD);                                                  //bold font
        abilityLabel.setOpaque(true);                                                    //allow background coloring
        abilityLabel.setBackground(Color.LIGHT_GRAY);                                    //set background color
        abilityLabel.setForeground(Color.BLACK);                                         //set text color
        abilityLabel.setPreferredSize(new Dimension(300, 45));             //set preferred height wand width

        //====================================================================================================================//
        //========================================== BUILD MAIN WINDOW =======================================================//
        //====================================================================================================================//

        //set panel to the fields from borderLayout
        add(gridPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.EAST);
        add(abilityLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        setExtendedState(JFrame.MAXIMIZED_BOTH);        //window will be "fullscreen"
        setVisible(true);                               //window will be visible
        refreshWindow();                                //update window [grid] [ability]

        //====================================================================================================================//
        //===============================  KEY LISTENER [MAIN SIMULATION LOGIC] ==============================================//
        //====================================================================================================================//
        addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e) {
                //direction by default set to null
                Direction direction = null;

                //change direction based on keyboard input
                switch (e.getKeyCode())
                {
                    case KeyEvent.VK_UP:
                        direction = Direction.UP;
                        break;
                    case KeyEvent.VK_DOWN:
                        direction = Direction.DOWN;
                        break;
                    case KeyEvent.VK_LEFT:
                        direction = Direction.LEFT;
                        break;
                    case KeyEvent.VK_RIGHT:
                        direction = Direction.RIGHT;
                        break;
                    case KeyEvent.VK_A:
                        direction = Direction.ACTIVATE_ABILITY;
                        break;
                    default:
                        break;
                }
                //if input is correct and human is in simulation
                if (direction != null && world.getPlayer() != null)
                {
                    logArea.setText("");                                            //clears all text from logarea
                    world.getPlayer().setNextDirection(direction);                  //set direction in human class
                    world.sortOrganisms();                                          //sort all organisms by initiative and age
                    List<Organism> toMove = new ArrayList<>(world.getOrganisms());  //create copy of sorted arraylist

                    //iterate through all organisms, move them and increment their age
                    for (Organism org : toMove)
                    {
                            org.move();
                            org.incrementAge();
                    }

                    //update window, log
                    refreshWindow();
                    roundCounter++;
                    world.log("[End of round " + roundCounter + "]");
                }
            }
        });
    }



    //redraw map
    private void refreshWindow()
    {
        //redraw grid area
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++) {
                Organism org = world.getOrganismAt(x, y);
                JLabel label = gridLabels[y][x];

                if (org == null) {
                    label.setText(" ");
                    label.setForeground(Color.BLACK);
                } else {
                    label.setText(org.getLook());
                    label.setForeground(getColorFromCode(org.getColor()));
                }
            }
        }


        //check if player in world is human, then create local variable human for casting
        if (world.getPlayer() instanceof Human human)
        {
            //update abilityLabel depending on human ability status
            if (human.getAbilityFlag())
            {
                abilityLabel.setText("🛡 Immortality: ACTIVE (" + human.getAbilityDurationCounter() + "/5) 🛡");
                abilityLabel.setBackground(Color.blue);
                abilityLabel.setForeground(Color.white);
            }
            else if (human.getAbilityCooldownCounter() > 0)
            {
                abilityLabel.setText("⌛ Ability cooldown: " + human.getAbilityCooldownCounter() + " ⌛");
                abilityLabel.setBackground(Color.ORANGE);
                abilityLabel.setForeground(Color.BLACK);
            } else
            {
                abilityLabel.setText("Ability: NOT ACTIVE");
                abilityLabel.setBackground(Color.LIGHT_GRAY);
                abilityLabel.setForeground(Color.BLACK);
            }
        }
    }

    //returns color based on colorcode
    private Color getColorFromCode(int code)
    {
        Color c = null;
        switch (code)
        {
            case 1:
                c = Color.GRAY;
                break;
            case 2:
                c = new Color(34, 139, 34);
                break;
            case 3:
                c = Color.RED;
                break;
            case 4:
                c = Color.BLUE;
                break;
            case 5:
                c = Color.MAGENTA;
                break;
            case 6:
                c = Color.BLACK;
                break;
            case 7:
                c = Color.GREEN;
                break;
            case 8:
                c = new Color(239, 99, 3);
                break;
            case 9:
                c = new Color(202, 133, 26);
                break;
            case 10:
                c = new Color(255, 213, 0);
                break;
            default:
                c = Color.DARK_GRAY;
                break;
        }
        return c;
    }

    //print log in the new line
    public void appendLog(String message)
    {
        logArea.append(message + "\n");
    }


}
