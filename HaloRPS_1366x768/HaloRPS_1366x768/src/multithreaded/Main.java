package multithreaded;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import rps.*;

public class Main 
{   static JFrame f = new JFrame();

    public static void main(String[] args) 
    {  
        try 
        {
            // initialize sound variables
            Sequence introSequence = MidiSystem.getSequence(new File("sounds/halo2.mid") );
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            
            // begin intro music
            sequencer.setSequence(introSequence);
            sequencer.start();
            
            // show splash
            introSplash();
            
            // end intro music
            sequencer.stop();

            // show game menu
            gameMenu(); 
            
            // end game menu music
            sequencer.stop();

        } // end of main method
        catch (InvalidMidiDataException ex) 
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Not a valid midi file...");
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No file was found silly...");
        } 
        catch (MidiUnavailableException ex) 
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("You computer can't even support MIDI!!! LOLOLLOLLLzzzz");
        }
    }// end of main method
    
    public static void introSplash()//start of splashscreen method
    {   
        SplashImagesCenter("images/gifIntro.gif");

    }//end of splashscreen method
    
    public static void gameMenu()
    {   
        try 
        {
            Sequence menuSequence = MidiSystem.getSequence(new File("sounds/midiMenu.mid") );
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();

            boolean hasPickedMain = false;  
            while(!hasPickedMain)
            {   
                // begin menu music
                sequencer.setSequence(menuSequence);
                sequencer.start();

                SplashImagesNoDispose("images/GameBG.png"); // Main Menu
                String playerChoice=null;
                while ((playerChoice == null) || (playerChoice.equals(""))) 
                {   playerChoice = JOptionPane.showInputDialog(null, "- Halo Themed RPS Game with Chat -\n\n[1] - Single Player\n[2] - Multi Player\n[3] - How to play\n[0] - Quit\n\n", 
                        "Main Menu", JOptionPane.QUESTION_MESSAGE);
                }
                switch(playerChoice)
                {
                    case "1": 
                        // end menu music
                        sequencer.stop();
                        singlePlayerOption();
                        break;
                    case "2": // Multiplayer Menu 
                        boolean hasPickedMultiplayer = false;
                        while(!hasPickedMultiplayer)
                        {
                            String playerChoseMP=null;
                            while ((playerChoseMP == null) || (playerChoseMP.equals(""))) 
                            {   playerChoseMP = JOptionPane.showInputDialog(null, "Offline or Online Multiplayer?\n\n[1] - Offline\n[2] - Online\n[0] - Back\n", 
                                    "Multiplayer", JOptionPane.QUESTION_MESSAGE);
                            }
                            switch(playerChoseMP)
                            {
                                case "1": 
                                    // end menu music
                                    sequencer.stop();
                                    offlineVSOption();
                                    hasPickedMultiplayer = true;
                                    break;
                                case "2": // Online Menu
                                    String chatOrRps=null;
                                    while ((chatOrRps == null) || (chatOrRps.equals("")) || !(chatOrRps.equals("1") || chatOrRps.equals("2"))) 
                                    {   chatOrRps = JOptionPane.showInputDialog(null, "Which mode?\n\n[1] - Chat Lobby\n[2] - RPS\n", 
                                            "Input required", JOptionPane.QUESTION_MESSAGE);
                                    }
                                        switch(chatOrRps)
                                        {
                                            case "1": 
                                                // end menu music
                                                sequencer.stop();   
                                                onlineVSChatOption();// CHAT
                                                return;
                                            case "2": 
                                                // end menu music
                                                sequencer.stop();   
                                                onlineVSGameOption();// RANDOM MESSAGE - RPS
                                                return;
                                            default: JOptionPane.showMessageDialog(null, "\nPlease type in a valid option noob!");
                                                     hasPickedMultiplayer = true;
                                        }
                                case "0": // Go Back
                                    hasPickedMultiplayer = true;
                                    hasPickedMain = false;
                                    break; 
                                default: // In case user types anything else other then the following options
                                    JOptionPane.showMessageDialog(null, "\nPlease type in a valid option noob!");
                                    hasPickedMultiplayer = false;   
                            }// end switch
                        }// end of while hasPickedMultiplayer
                        break;
                    case "3":   
                        sequencer.stop();
                        HowToPlay();
                        break;
                    case "0": // Quit the game
                        sequencer.stop();
                        quitGame();                   
                        break;
                    default: 
                        JOptionPane.showMessageDialog(null, "\nPlease type in a valid option noob!");
                }// end switch
            }// end of while hasPickedMain
        } // end of game menu method
        catch (InvalidMidiDataException | IOException | MidiUnavailableException ex) 
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// end of game menu method
        
    public static void singlePlayerOption()//start of singleplayer method
    {   
        try //start of singleplayer method
        {
            Sequence battleSequence = MidiSystem.getSequence(new File("sounds/midiGameplayBegin.mid") );
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();    

            // begin menu music
            sequencer.setSequence(battleSequence);
            sequencer.start();

            Player p1 = new Player(JOptionPane.showInputDialog(null, "What is your name?", "Single Player", JOptionPane.QUESTION_MESSAGE)); 
            if(p1.getName() == null)
            {
                p1.setName("Pancho");
            }
            Game singlePlayerGame = new Game(p1);
            System.out.println("This match will be...\n"+singlePlayerGame.getOne().getName() + " VS " + singlePlayerGame.getTwo().getName() + "!\n");

            while(!(singlePlayerGame.getOne().getWins() == 3 || singlePlayerGame.getOne().getLosses() == 3))
            {
                singlePlayerGame.playGame();
            }
            System.out.println(singlePlayerGame.getOne().getName() + "'s winning percentage is " + singlePlayerGame.getOne().calcWinPercent() + "%" 
                    + "\n WINS: " + singlePlayerGame.getOne().getWins()
                    + "\n LOSSES: " + singlePlayerGame.getOne().getLosses()
                    + "\n DRAWS: " + singlePlayerGame.getOne().getDraws() +"\n");
            JOptionPane.showMessageDialog(null,"Thank you for playing!\nExiting to Main Menu.");
            sequencer.stop();
        } //end of singleplayer method
        catch (InvalidMidiDataException | IOException | MidiUnavailableException ex) 
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }//end of singleplayer method
    
    public static void offlineVSOption()//start of offline VS method
    {   
        try //start of offline VS method
        {
            Sequence battleSequence = MidiSystem.getSequence(new File("sounds/midiGameplayBegin.mid") );
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();    

            // begin menu music
            sequencer.setSequence(battleSequence);
            sequencer.start();

            Player p1 = new Player(JOptionPane.showInputDialog(null, "You are player 1. What is your name?", "Offline Versus", JOptionPane.QUESTION_MESSAGE));
            Player p2 = new Player(JOptionPane.showInputDialog(null, "You are player 2. What is your name?", "Offline Versus", JOptionPane.QUESTION_MESSAGE));
            if(p1.getName() == null)
            {
                p1.setName("Juan Martinez");
            }
            if(p2.getName() == null)
            {
                p2.setName("Jeffrey");
            }

            Game multiPlayerGameOffline = new Game(p1,p2); 
            System.out.println("This match will be...\n" + multiPlayerGameOffline.getOne().getName() + " VS " + multiPlayerGameOffline.getTwo().getName() + "!\n");
            while(!(multiPlayerGameOffline.getOne().getWins() == 3 || multiPlayerGameOffline.getOne().getLosses() == 3))
            {
                multiPlayerGameOffline.playGame();
            }
            System.out.println(multiPlayerGameOffline.getOne().getName() + "'s winning percentage is " + multiPlayerGameOffline.getOne().calcWinPercent() + "%" 
                    + "\n WINS: " + multiPlayerGameOffline.getOne().getWins()
                    + "\n LOSSES: " + multiPlayerGameOffline.getOne().getLosses()
                    + "\n DRAWS: " + multiPlayerGameOffline.getOne().getDraws() +"\n");

            System.out.println(multiPlayerGameOffline.getTwo().getName() + "'s winning percentage is " + multiPlayerGameOffline.getTwo().calcWinPercent() + "%"
                        + "\n WINS: " + multiPlayerGameOffline.getTwo().getWins()
                        + "\n LOSSES: " + multiPlayerGameOffline.getTwo().getLosses()
                        + "\n DRAWS: " + multiPlayerGameOffline.getTwo().getDraws()+"\n");
            JOptionPane.showMessageDialog(null,"Thank you for playing!\nExiting to Main Menu.");

            sequencer.stop();
        } //end of offline VS method
        catch (InvalidMidiDataException | IOException | MidiUnavailableException ex) 
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//end of offline VS method
    
    public static void quitGame()//start of method for quit game option
    {
        try //start of method for quit game option
        {
            Sequence overSequence = MidiSystem.getSequence(new File("sounds/midiGameplayEnd.mid") );
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();    

            // begin menu music
            sequencer.setSequence(overSequence);
            sequencer.start();

            int n = JOptionPane.showConfirmDialog(null,"Are you sure you would like to quit?","Quit",JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION)
            {
                System.exit(0);
            } 
            else 
            {

            }    
            sequencer.stop();     
        } //start of method for quit game option
        catch (InvalidMidiDataException | IOException | MidiUnavailableException ex) 
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//start of method for quit game option

    private static void onlineVSChatOption() 
    {   
        try 
        {
            Sequence chatSequence = MidiSystem.getSequence(new File("sounds/midiChat.mid") );
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();  
            
            // begin menu music
            sequencer.setSequence(chatSequence);
            sequencer.start();
            
            ChatServer myChatServer = new ChatServer();
            ChatClient myChatClient = new ChatClient();

            myChatServer.start();
            System.out.println("Waiting for someone to connect...");
            JOptionPane.showMessageDialog(null, "Please wait for client to begin!");
            myChatClient.start();
            while(myChatServer.isAlive() || myChatClient.isAlive())
            {
                //do nothing. This keeps music running til threads die.
            }      
            sequencer.stop();
        } 
        catch (InvalidMidiDataException | IOException | MidiUnavailableException ex) 
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    private static void onlineVSGameOption() 
    {
        GameServer myGameServer = new GameServer();  
        GameClient myGameClient = new GameClient();
        
        try 
        {
            Sequence battleSequence = MidiSystem.getSequence(new File("sounds/midiGameplayBegin.mid") );
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();  
            
            // begin menu music
            sequencer.setSequence(battleSequence);
            sequencer.start();
            
            myGameServer.start();
            System.out.println("Waiting for someone to connect...");
            JOptionPane.showMessageDialog(null, "Please wait for client to begin!");
            myGameClient.start(); 
            while(myGameServer.isAlive() || myGameClient.isAlive())
            {
                //do nothing. This keeps music running til threads die.
            }
            sequencer.stop();
        } 
        catch (InvalidMidiDataException | IOException | MidiUnavailableException ex) 
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
          
    public static void SplashImagesCenter(String dir)
    {
        try
        {                      
            Splashscreen ss = new Splashscreen(f, dir, 1);
            ss.setVisible(true);
            while (ss.isVisible())
            {   
                Thread.sleep(0);
            }                                    
        }
        catch (Exception e)
        {   
            System.out.println("splash: " + e);
        }
    }
    
    public static void SplashImagesNoDispose(String dir)
    {
        try
        {                      
            SplashscreenNoDispose ss = new SplashscreenNoDispose(f, dir, 1);
            ss.setVisible(true);                               
        }
        catch (Exception e)
        {   
            System.out.println("splash: " + e);
        }
    }
    
    public static void HowToPlay()
    {
        try 
        {
            Sequence howSequence = MidiSystem.getSequence(new File("sounds/midiChat.mid") );
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();    

            // begin menu music
            sequencer.setSequence(howSequence);
            sequencer.start();

            HowToPlay htp = new HowToPlay();           
            htp.HowToPlay();

            sequencer.stop();
        } 
        catch (InvalidMidiDataException | IOException | MidiUnavailableException ex) 
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}// end of Main class
