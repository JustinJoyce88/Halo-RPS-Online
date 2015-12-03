//
//    8 8888      88 8 888888888o.      8 888888888o   
//    8 8888      88 8 8888    `^888.   8 8888    `88. 
//    8 8888      88 8 8888        `88. 8 8888     `88 
//    8 8888      88 8 8888         `88 8 8888     ,88 
//    8 8888      88 8 8888          88 8 8888.   ,88' 
//    8 8888      88 8 8888          88 8 888888888P'  
//    8 8888      88 8 8888         ,88 8 8888         
//    ` 8888     ,8P 8 8888        ,88' 8 8888         
//      8888   ,d8P  8 8888    ,o88P'   8 8888         
//       `Y88888P'   8 888888888P'      8 8888         
//
//                  S E R V E R 
//

// packagez
package multithreaded;

// importz
import javax.swing.JOptionPane;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import rps.Splashscreen;

public class GameServer extends Thread
{
    // variables
    private ArrayList<String> botArray;
    JFrame f = new JFrame();
    
    public GameServer()
    {
        initializeArray(); // fill Array with data, use new File(... for extra credit
    }

    @Override
    public void run()
    {
        try
        {   
            // local variables
            String theUserName=null;
            while ((theUserName == null) || (theUserName.equals(""))) 
            {   theUserName = JOptionPane.showInputDialog(null, "Enter a User Name:", 
                    "User Name", JOptionPane.QUESTION_MESSAGE);
            }
            
            int intServerPort = 0;
            while ((intServerPort <= 1023) || (intServerPort >= 65535)) 
            {   intServerPort = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Server Port:", 
                    "Your Server Port", JOptionPane.INFORMATION_MESSAGE));
            }
            DatagramSocket serverSocket = new DatagramSocket(intServerPort);
            boolean isDone = false;

            while(!isDone)
            {
                byte[] receiveData = new byte[1024]; 
                byte[] sendData  = new byte[1024]; 
                
                // prepare a receiving packet and receive the packet from the client
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); 
                serverSocket.receive(receivePacket); 
                String strRandomNumber = new String(receivePacket.getData()); 
                
                // get the trimmed String and then convert to Integer... to be able to pull the ArrayList index
                String strBotMessage = getBotArray().get(Integer.parseInt(strRandomNumber.trim()));
                
                // wait and sleep before responding
                Thread.sleep(2002);
                InetAddress IPAddress = receivePacket.getAddress(); 
                int port = receivePacket.getPort();
                sendData = strBotMessage.getBytes(); 
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port); 
                serverSocket.send(sendPacket); 
                System.out.println(theUserName + ": "+strBotMessage);
                
                if(strBotMessage.equals("/PLAY"))
                {
                        // get CLIENT choice
                        receiveData = new byte[1024];
                        serverSocket.receive(receivePacket);
                        String clientChoiceNumber = new String(receivePacket.getData());
                        int intClientChoice = Integer.parseInt(clientChoiceNumber.trim());
                        String clientWeaponChoice = getWeaponChoice(intClientChoice);
                        // get SERVER choice
                        Random r = new Random();
                        int intServerChoice = r.nextInt(3);
                        String serverWeaponChoice = getWeaponChoice(intServerChoice);
                        System.out.println("You chose " + serverWeaponChoice + " and your opponent chose " + clientWeaponChoice);
                        // figure out who wins
                        String serverResults = "";
                        String clientResults = "";
                        
                        switch(clientWeaponChoice)
                        {
                            case "Infantry": 
                                if(serverWeaponChoice.equals("Infantry"))
                                {
                                    clientResults = "You both TIE!";
                                    serverResults = "You both TIE!";
                                }
                                if(serverWeaponChoice.equals("Tank"))
                                {
                                    clientResults = "You LOSE!";
                                    serverResults = "You WIN!";
                                }
                                if(serverWeaponChoice.equals("Air"))
                                {
                                    clientResults = "You WIN!";
                                    serverResults = "You LOSE!";
                                }
                                break;
                            case "Tank":
                                if(serverWeaponChoice.equals("Infantry"))
                                {
                                    clientResults = "You WIN!";
                                    serverResults = "You LOSE!";
                                }
                                if(serverWeaponChoice.equals("Tank"))
                                {
                                    clientResults = "You both TIE!";
                                    serverResults = "You both TIE!";
                                }
                                if(serverWeaponChoice.equals("Air"))
                                {
                                    clientResults = "You LOSE!";
                                    serverResults = "You WIN!";
                                }
                                break;
                            default: 
                                if(serverWeaponChoice.equals("Infantry"))
                                {
                                    clientResults = "You LOSE!";
                                    serverResults = "You WIN!";
                                }
                                if(serverWeaponChoice.equals("Tank"))
                                {
                                    clientResults = "You WIN!";
                                    serverResults = "You LOSE!";
                                }
                                if(serverWeaponChoice.equals("Air"))
                                {
                                    clientResults = "You both TIE!";
                                    serverResults = "You both TIE!";
                                }
                        }// end switch
                        
                        // display and send results of the match
                        
                        System.out.println(serverResults);
                        sendData = new byte[1024];
                        String output = "You chose " +clientWeaponChoice + " and your opponent chose " + serverWeaponChoice + "\n";
                        output += clientResults;
                        sendData = output.getBytes();
                        // sleep really any time before sending packet
                        DatagramPacket sendResultsPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                        serverSocket.send(sendResultsPacket);
                }// end if
                if(strBotMessage.equals("/END"))
                {
                        // Display message
                        System.out.println("Thanks for playing Halo RPS!");
                        // sanitize buffers AND then proceed with shutdown process
                        sendData = new byte[1024];
                        receiveData = new byte[1024];
                        isDone = true;
                }// end if
            }// end while loop
            
            // close server socket and then exit program... or go to main menu...
            serverSocket.close();
            System.exit(0);
            
        }// end try
        catch (SocketException ex) 
        {
            Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException | InterruptedException ex) 
        {
            Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// end of run
    
    private void initializeArray()
    {
        // try to get the file
        try
        {
            botArray = new ArrayList<>();
            Scanner fileScanner = new Scanner(new File("build/classes/Data/messageList.data"));
            // while list still has next line...
            while (fileScanner.hasNext()) 
            {
                botArray.add(fileScanner.next());
            }
        }
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("File could not be found");
        }
    }// end of initializeArray

    public ArrayList<String> getBotArray() 
    {
        return botArray;
    }// end of getBotArray

    public void setBotArray(ArrayList<String> botArray) 
    {
        this.botArray = botArray;
    }// end of setBotArray
    
    public String getWeaponChoice(int num)
    {
        if (num == 0)
        {
           return "Infantry";
        }
       else if(num == 1)
       {
           return "Tank";
       }
       else
       {
           return "Air";
       }     
    }// end of getWeaponChoice
}// end of Server
