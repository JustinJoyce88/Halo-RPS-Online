package rps;

import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Game 
{
    // variables
    private static Player one, two;
    boolean isPlayerTwoCPU;
    JFrame f = new JFrame(); 
    
    public Game(Player uno)
    {
        one = uno;
        two = new Computer("CPU");
        isPlayerTwoCPU = true;
    }
    
    public Game(Player uno, Player dos)
    {
        one = uno;
        two = dos;
        isPlayerTwoCPU = false;
    }
    
    public boolean isPlayerTwoCPU()
    {
        return isPlayerTwoCPU;
    }
    
    public String generateRandomMessage()
    {
        Random r = new Random();
        switch(r.nextInt(3) + 1)
        {
            case 1: // if 1 make Rock
                return "R";
            case 2: // if 2 make Scissors
                return "S";
            default: // if 3 make Paper
                return "P";
        }// end of switch
    }// end of generateRandomMessage

    public Player getOne() 
    {
        return one;
    }

    public void setOne(Player one) 
    {
        this.one = one;
    }

    public Player getTwo() 
    {
        return two;
    }

    public void setTwo(Player two) 
    {
        this.two = two;
    }
    
    public void playGame()
    {    
        String p1 = null;
        String p2 = null;
        // local variables   
        // intro BATTLE VS screen
        
        while((p1 == null) || (p1.equals("")) || !( p1.equalsIgnoreCase("R") ||  p1.equalsIgnoreCase("P") ||  p1.equalsIgnoreCase("S") ))
        {
            p1 = JOptionPane.showInputDialog(null, "Please choose your weapon "+getOne().getName()+": Infantry(R) Tank(P) Air(S)?", "Choose Wisely", JOptionPane.QUESTION_MESSAGE);
        }// end while
        // if player 2 is a computer
        if (!isPlayerTwoCPU())
        {
            while(p2 == null || p2.equals("") || !( p2.equalsIgnoreCase("R") ||  p2.equalsIgnoreCase("P") ||  p2.equalsIgnoreCase("S") ))
            {
                p2 = JOptionPane.showInputDialog(null, "Please choose your weapon "+getTwo().getName()+": Infantry(R) Tank(P) Air(S)?", "Choose Wisely", JOptionPane.QUESTION_MESSAGE);

             }// end while
        }// end if
        else
        {
            p2 = generateRandomMessage();
            System.out.println(getTwo().getName() + " chose " + p2 + "!");
        }// end else
        switch(p1.toUpperCase())
        {
            case "R": // if user input equal Rrrzz
                if(p2.equalsIgnoreCase("S")) 
                {
                    one.setWins(one.getWins()+1); 
                    two.setLosses(two.getLosses()+1);
                    System.out.println(one.getName() + " wins!\n");
                    SplashImagesTop("images/InfantryVsAir.jpg");
                }
                if (p2.equalsIgnoreCase("R"))
                {
                    one.setDraws(one.getDraws()+1);
                    two.setDraws(two.getDraws()+1);
                    System.out.println("TIE\n");
                    SplashImagesTop("images/InfantryDraw.jpg");
                }
                if (p2.equalsIgnoreCase("P"))
                {
                    one.setLosses(one.getLosses()+1); 
                    two.setWins(two.getWins()+1);
                    System.out.println(two.getName() + " wins!\n");
                    SplashImagesTop("images/InfantryVsTank.jpg");
                }
                one.setTotalGames(one.getTotalGames()+1);
                two.setTotalGames(two.getTotalGames()+1);
                break;
            case "P": // if user input equal Pee! Ewwww
                if(p2.equalsIgnoreCase("R")) 
                {
                    one.setWins(one.getWins()+1); 
                    two.setLosses(two.getLosses()+1);
                    System.out.println(one.getName() + " wins!\n");
                    SplashImagesTop("images/TankVsInfantry.jpg");
                }
                if (p2.equalsIgnoreCase("P"))
                {
                    one.setDraws(one.getDraws()+1);
                    two.setDraws(two.getDraws()+1);
                    System.out.println("TIE\n");
                    SplashImagesTop("images/TankDraw.jpg");
                }
                if (p2.equalsIgnoreCase("S"))
                {
                    one.setLosses(one.getLosses()+1); 
                    two.setWins(two.getWins()+1);
                    System.out.println(two.getName() + " wins!\n");
                    SplashImagesTop("images/TankVsAir.jpg");
                }
                one.setTotalGames(one.getTotalGames()+1);
                two.setTotalGames(two.getTotalGames()+1);
                break;
            case "S": // if user input equal Rrrzz
                if(p2.equalsIgnoreCase("P")) // when win
                {
                    one.setWins(one.getWins()+1); 
                    two.setLosses(two.getLosses()+1);
                    System.out.println(one.getName() + " wins!\n");                   
                    SplashImagesTop("images/AirVsTank.jpg");
                }
                if (p2.equalsIgnoreCase("S")) // when draw
                {
                    one.setDraws(one.getDraws()+1);
                    two.setDraws(two.getDraws()+1);
                    System.out.println("TIE\n");
                    SplashImagesTop("images/AirDraw.jpg");
                }
                if (p2.equalsIgnoreCase("R")) // when lost
                {
                    one.setLosses(one.getLosses()+1); 
                    two.setWins(two.getWins()+1);
                    System.out.println(two.getName() + " wins!\n");
                    SplashImagesTop("images/AirVsInfantry.jpg");
                }
                one.setTotalGames(one.getTotalGames()+1);
                two.setTotalGames(two.getTotalGames()+1);
                break;
        }// end of switch
    }// play Game
    
    public void SplashImagesTop(String dir)
    {
        try
        {                      
            Splashscreen ss = new Splashscreen(f, dir, 0);
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
 }// end of game class
