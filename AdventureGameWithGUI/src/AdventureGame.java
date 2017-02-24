import java.io.*; // for file
import java.util.* ; // for Scanner
import java.awt.*; // for BorderLayout
import java.awt.event.*; // for ActionListener
import javax.swing.*; // for JTextField, JTextArea and JScrollPane

/*****************************************************************************\
|*                                                                           *|
\*****************************************************************************/

public class AdventureGame implements ActionListener
{
    /************GUI VARIABLES******************/
    public JTextField userInputJTF;
    public JTextArea consoleJTA;
    
    /********COMMAND CONSTANTS****************/
    public static final String QUIT_COMMAND = "QUIT NOW!";
    public static final String ABOUT_COMMAND = "ABOUT";
    public static final String INSTRUCTION_COMMAND = "INSTRUCTION MANUEL";
    
   /**************************************************************************\
   |* Game Constants                                                         *|
   \**************************************************************************/
    
    public static final String QUIT_GAME = "Q" ;
    public static final String GET_LAMP = "L" ;
    public static final String GET_KEY = "K" ;
    public static final String OPEN_CHEST = "C" ;

    public static final String GO_NORTH = "N" ;
    public static final String GO_SOUTH = "S" ;
    public static final String GO_EAST = "E" ;
    public static final String GO_WEST = "W" ;


   /**************************************************************************\
   |* Game State                                                             *|
   \**************************************************************************/

    public Player player ;

    public Room room ;
    public Room lastRoom ;

    public boolean isGameOver ;
    public boolean isGrueLurking ;

   /**************************************************************************\
   |*                                                                        *|
   \**************************************************************************/

    public static void main(String[] arg)
    {
       AdventureGame ag = new AdventureGame() ;
    }

   /**************************************************************************\
   |*  Class's constructor                                                                      *|
   \**************************************************************************/

    public AdventureGame()
    {
        /**********TEXT FIELD LISTENER******/
        userInputJTF = new JTextField();
        userInputJTF.addActionListener(this);
        
        /******TEXT AREA IS NOT EDITABLE****/
        consoleJTA = new JTextArea();
        consoleJTA.setEditable(false);
        
        /******SCROLL PANE**************/
        JScrollPane sp = new JScrollPane(consoleJTA);
        
        JPanel cp = new JPanel(new BorderLayout());
        cp.add(sp, BorderLayout.CENTER);
        cp.add(userInputJTF, BorderLayout.SOUTH);
        
        JFrame f = new JFrame();
            f.setTitle("Adventure Game");
            f.setSize(800, 600);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setContentPane(cp);
            f.setJMenuBar(createMenuBar());
            f.setLocationRelativeTo(null);
            f.setVisible(true);
       
        init() ;                       // 1. Initialize Environment and Variables

        showSplashScreen() ;           // 2. Show SplashScreen

        showBoard() ;               // 3. Show or Render Board / Scene / Map

        showUserInputOptions() ; // 4. Show User Input Options

    }
   
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        String command = e.getActionCommand();
        
        /*********JMENU ACTION LISTNER********/
        if (command == QUIT_COMMAND)
        {
            isGameOver = true;
            
            userInputJTF.setEditable(false);
        }
        else if (command == ABOUT_COMMAND)
        {
            String message = "This game is for eductional purpose\n Author: Jose R. Torres Gomez";
            int type = JOptionPane.INFORMATION_MESSAGE;
            
            JOptionPane.showMessageDialog(null, message, "About", type);
        }
        else if (command == INSTRUCTION_COMMAND)
        {
            String instruction = "[" + QUIT_GAME + "] Quit Game | " +
                         "[" + GET_LAMP + "] Get Lamp | " +
                         "[" + GET_KEY + "] Get Key | " +
                         "[" + OPEN_CHEST + "] Open Chest | " +
                         "[" + GO_NORTH + "] Go North | " +
                         "[" + GO_SOUTH + "] Go South | " +
                         "[" + GO_EAST + "] Go East | " +
                         "[" + GO_WEST + "] Go West\n" +
                         "If you press quit game on file:quit game, the game ends," + 
                         "the game won't save if you quit the game.";
            int type = JOptionPane.INFORMATION_MESSAGE;
            JOptionPane.showMessageDialog(null, instruction, "Instruction Manuel", type);
        }
            
        String user = userInputJTF.getText();
        
        if (checkInput(user)) 
        {
            processUserInput(user);
            
            userInputJTF.setText("");
        }
        
        if (isGameOver == false) 
        {
            showBoard();
         
            showUserInputOptions();
         
            userInputJTF.setText("");
        
        }        
        else if (isGameOver == true) 
        {
            String message = "Game Over!!!!";
            int type = JOptionPane.INFORMATION_MESSAGE;
        
            JOptionPane.showMessageDialog(null, message, "Game over screen", type);
            
            showGameOverScreen();
            
            userInputJTF.setText("");
            userInputJTF.setEditable(false);  
        }           
    }
    
    /******Menu bar******/
    public JMenuBar createMenuBar()
    {
        JMenuBar mb = new JMenuBar();
        
        JMenu fileMN = (JMenu) mb.add(new JMenu("File"));
            fileMN.setMnemonic(KeyEvent.VK_F);
            
        JMenuItem exitMI = (JMenuItem) fileMN.add(new JMenuItem("Quit Game"));
            exitMI.setMnemonic(KeyEvent.VK_X);
            exitMI.setAccelerator(KeyStroke.getKeyStroke("alt F4"));
            exitMI.setActionCommand(QUIT_COMMAND);
            exitMI.addActionListener(this);
            exitMI.setToolTipText("Quit this program");
            
        JMenu helpMN = (JMenu) mb.add(new JMenu("Help"));
            helpMN.setMnemonic(KeyEvent.VK_H);
            
        JMenuItem aboutMI = (JMenuItem) helpMN.add(new JMenuItem("About"));
            aboutMI.setMnemonic(KeyEvent.VK_A);
            aboutMI.setAccelerator(null);
            aboutMI.setActionCommand(ABOUT_COMMAND);
            aboutMI.addActionListener(this);
            aboutMI.setToolTipText("Show info about this program");
            
        JMenuItem instructionMI = (JMenuItem) helpMN.add(new JMenuItem("Instruction"));
            instructionMI.setMnemonic(KeyEvent.VK_M);
            instructionMI.setAccelerator(null);
            instructionMI.setActionCommand(INSTRUCTION_COMMAND);
            instructionMI.addActionListener(this);
            instructionMI.setToolTipText("Game Instruction Manual");
            
        return mb;
    }

   /**************************************************************************\
   |*     Initialize Environment and Variables                               *|
   \**************************************************************************/

    public void init()
    {
       player = new Player() ;

       room = createDungeon() ;

       lastRoom = null ;

       isGameOver = false ;

       isGrueLurking = false ;

    }

   /**************************************************************************\
   |*  Welcome message                                                                      *|
   \**************************************************************************/

    public void showSplashScreen()
    {
        consoleJTA.setText("Welcome to Adventure Game! \n") ;
    }

   /**************************************************************************\
   |*   Show room details                                                                     *|
   \**************************************************************************/

    public void showBoard()
    {
        isGrueLurking = false ;

        if(player.hasLamp)
        {
            room.isLit = true ;
        }

        if(!room.isLit)
        {
            isGrueLurking = true ;
        }

        consoleJTA.append(room.toString() + "\n") ;
    }

   /**************************************************************************\
   |*  Player option message method                                                                      *|
   \**************************************************************************/

    public void showUserInputOptions()
    {
        String options = "[" + QUIT_GAME + "] Quit Game | " +
                         "[" + GET_LAMP + "] Get Lamp | " +
                         "[" + GET_KEY + "] Get Key | " +
                         "[" + OPEN_CHEST + "] Open Chest | " +
                         "[" + GO_NORTH + "] Go North | " +
                         "[" + GO_SOUTH + "] Go South | " +
                         "[" + GO_EAST + "] Go East | " +
                         "[" + GO_WEST + "] Go West \n\n" ;

        consoleJTA.append(options) ;
    }

   /**************************************************************************\
   |*  INPUT VEREFICATION                                                    *|
   \**************************************************************************/

    public boolean checkInput(String input)
    {
        return input.equalsIgnoreCase(QUIT_GAME) ||
               input.equalsIgnoreCase(GET_LAMP)  ||
               input.equalsIgnoreCase(GET_KEY)   ||
               input.equalsIgnoreCase(OPEN_CHEST)||
               input.equalsIgnoreCase(GO_NORTH)  ||
               input.equalsIgnoreCase(GO_SOUTH)  ||
               input.equalsIgnoreCase(GO_EAST)   ||
               input.equalsIgnoreCase(GO_WEST) ;
    }

   /**************************************************************************\
   |*  Input validation                                                                      *|
   \**************************************************************************/

    public void processUserInput(String input)
    {
            if(input.equalsIgnoreCase(QUIT_GAME)) { quitGame() ; }
       else if(input.equalsIgnoreCase(GET_LAMP)) { getLamp() ; }
       else if(input.equalsIgnoreCase(GET_KEY)) { getKey() ; }
       else if(input.equalsIgnoreCase(OPEN_CHEST)) { openChest() ; }
       else if(input.equalsIgnoreCase(GO_NORTH)) { goNorth() ; }
       else if(input.equalsIgnoreCase(GO_SOUTH)) { goSouth() ; }
       else if(input.equalsIgnoreCase(GO_EAST)) { goEast() ; }
       else if(input.equalsIgnoreCase(GO_WEST)) { goWest() ; }
   }

   /**************************************************************************\
   |*  Game Over Method                                                                     *|
   \**************************************************************************/

    public boolean isGameOver()
    {

        return isGameOver ;
    }

   /**************************************************************************\
   |*  Game Over Message                                                                      *|
   \**************************************************************************/

    public void showGameOverScreen()
    {
        consoleJTA.append("Game Over!") ;
    }

   /**************************************************************************\
   |*  Quit the Game Method                                                                      *|
   \**************************************************************************/

    public void quitGame()
    {
        isGameOver = true ;
    }

   /**************************************************************************\
   |*  Player get lamp validation method                                                                      *|
   \**************************************************************************/

    public void getLamp()
    {
        if(isGrueLurking)
        {
            consoleJTA.append("You have just gotten eaten alive!\n") ;
            isGameOver = true ;
        }
        else
        {
            if(room.hasLamp)
            {
                player.hasLamp = true ;
                room.hasLamp = false ;

                consoleJTA.append("You got the lamp!\n\n") ;
            }
            else
            {
                consoleJTA.append("There is no lamp in this room.\n\n") ;
            }
        }
    }

   /**************************************************************************\
   |*   Player get key validation method                                                                      *|
   \**************************************************************************/

    public void getKey()
    {
        if(isGrueLurking)
        {
            consoleJTA.append("You have just gotten eaten alive!\n") ;
            isGameOver = true ;
        }
        else
        {
            if(room.hasKey)
            {
                player.hasKey = true ;
                room.hasKey = false ;

                consoleJTA.append("You got the key!\n\n") ;
            }
            else
            {
                consoleJTA.append("There is no key in this room.\n\n") ;
            }
        }
    }

   /**************************************************************************\
   |*  Player open chest validation method                                                                       *|
   \**************************************************************************/

    public void openChest()
    {
        if(isGrueLurking)
        {
            consoleJTA.append("You have just gotten eaten alive!\n") ;
            isGameOver = true ;
        }
        else
        {
            if(room.hasChest)
            {
                if(player.hasKey)
                {
                    consoleJTA.append("You got the treasure!\n\n") ;
                    isGameOver = true ;
                }
                else
                {
                    consoleJTA.append("Dumbass! You don't have the key!\n\n") ;
                }
            }
            else
            {
                consoleJTA.append("There is no chest in this room.\n\n") ;
            }
        }
    }

   /**************************************************************************\
   |*  METHOD WERE THE DUNGEONS ARE CREATED USING A TEXT FILE                *|
   \**************************************************************************/

    public Room createDungeon()
    {
        try
        {
            Scanner s = new Scanner(new File("dungeon.txt"));
         
            int roomCount = s.nextInt();
         
            Room[] rooms = new Room[roomCount];
         
            for (int i = 0; i < roomCount; i++)
            {
                Room r = new Room();
             
                s.nextInt();
                s.nextLine();
             
                r.description = s.nextLine();
             
                r.isLit = s.nextBoolean();
                r.hasLamp = s.nextBoolean();
                r.hasKey = s.nextBoolean();
                r.hasChest = s.nextBoolean();
             
                rooms[i] = r;
            }
            for (int i = 0; i < roomCount; i++)
            {
                int index = s.nextInt();
             
                int n = s.nextInt();
                int so = s.nextInt();
                int e = s.nextInt();
                int w = s.nextInt();
             
                if (n != -1) { rooms[index].north = rooms[n]; }
                if (so != -1) { rooms[index].south = rooms[so]; }
                if (e != -1) { rooms[index].east = rooms[e]; }
                if (w != -1) { rooms[index].west = rooms[w]; }
            }
         
            return rooms[0];
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
     
        return null;
    }

   /**************************************************************************\
   |*  Player north direction option method                                                                     *|
   \**************************************************************************/

    public void goNorth()
    {
        if(isGrueLurking && lastRoom != room.north)
        {
           consoleJTA.append("You have just gotten eaten alive!\n") ;
           isGameOver = true ;
        }
        else
        {
            if(room.north != null)
            {
                lastRoom = room ;
                room = room.north ;
            }
            else
            {
                consoleJTA.append("You cannot go north!\n\n") ;
            }
        }
    }

   /**************************************************************************\
   |*  Player south direction option method                                                                         *|
   \**************************************************************************/

    public void goSouth()
    {
        if(isGrueLurking && lastRoom != room.south)
        {
            consoleJTA.append("You have just gotten eaten alive!\n") ;
            isGameOver = true ;
        }
        else
        {
            if(room.south != null)
            {
                lastRoom = room ;
                room = room.south ;
            }
            else
            {
                consoleJTA.append("You cannot go south!\n\n") ;
            }
        }
    }

   /**************************************************************************\
   |*  Player east direction option method                                                                         *|
   \**************************************************************************/

    public void goEast()
    {
        if(isGrueLurking && lastRoom != room.east)
        {
            consoleJTA.append("You have just gotten eaten alive!\n") ;
            isGameOver = true ;
        }
        else
        {
            if(room.east != null)
            {
                lastRoom = room ;
                room = room.east ;
            }
            else
            {
                consoleJTA.append("You cannot go east!\n\n") ;
            }
        }
    }

   /**************************************************************************\
   |*  Player west direction option method                                                                         *|
   \**************************************************************************/

    public void goWest()
    {
        if(isGrueLurking && lastRoom != room.west)
        {
            consoleJTA.append("You have just gotten eaten alive!\n") ;
            isGameOver = true ;
        }
        else
        {
            if(room.west != null)
            {
                lastRoom = room ;
                room = room.west ;
            }
            else
            {
                consoleJTA.append("You cannot go west!\n\n") ;
            }
        }
    }
}
