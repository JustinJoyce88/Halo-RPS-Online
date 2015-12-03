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
//                  C L I E N T
//

// packagez
package multithreaded;

// importz
import javax.swing.JOptionPane;
import java.io.*; 
import java.net.*; 
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameClient extends Thread 
{
    // class variables   
    public GameClient()
    {   

    }// end Client constructor

    @Override
    public void run()
    {   
        // local variables
        int destPort = 0;
        while ((destPort <= 1023) || (destPort >= 65535)) 
        {   destPort = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Destination Port:", 
                "Your Friends' Port", JOptionPane.INFORMATION_MESSAGE));
        }
        String destIP=null;
        while ((destIP == null) || (destIP.equals(""))) 
        {   destIP = JOptionPane.showInputDialog(null, "Enter Destination IP:", 
                "Your Friend's IP", JOptionPane.QUESTION_MESSAGE);
        }
        boolean isDone = false;

        while (!isDone)
        {
            byte[] sendData = new byte[1024]; 
            byte[] receiveData = new byte[1024]; 
            
            try 
            {
                // SEND RANDOM NUMBER ( for message validation )
                
                //Scanner in = new Scanner(System.in);
                // variables
                Random r = new Random();                
                DatagramSocket clientSocket = new DatagramSocket();
                InetAddress IPAddress = InetAddress.getByName(destIP);

                String strRandomNumber = "" + r.nextInt(20);
                
                sendData = strRandomNumber.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, destPort); 
                clientSocket.send(sendPacket); 

                // waiting a bit before recevieving
                Thread.sleep(2001);
                
                // waiting to receive 
                // receive the random message and output it
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); 
                clientSocket.receive(receivePacket); 
                String randomSentence = new String(receivePacket.getData()); 
                
                System.out.println("Opponent: " + randomSentence.trim()); 
                                
                if(randomSentence.trim().equals("/PLAY"))
                {
                    // generate random choice and send
                    int randomChoice = r.nextInt(3);
                    String strRandomWeapon= "" + randomChoice;
                    
                    sendData = new byte[1024];   
                    sendData = strRandomWeapon.getBytes();
                    DatagramPacket sendWeaponPacket = new DatagramPacket(sendData, sendData.length, IPAddress, destPort); 
                    clientSocket.send(sendWeaponPacket); 
                    
                     // waiting a bit before recevieving...
                    
                    // receive data
                    receiveData = new byte[1024]; 
                    DatagramPacket receiveResultsPacket = new DatagramPacket(receiveData, receiveData.length); 
                    clientSocket.receive(receiveResultsPacket); 
                    String results = new String(receiveResultsPacket.getData()); 
                    System.out.println(results.trim());
                }// end if
                if(randomSentence.trim().equals("/END"))
                {
                    // Display message
                    System.out.println("Thanks for playing Halo RPS!");
                    
                    // sanitize buffers AND then proceed with shutdown process
                    sendData = new byte[1024]; 
                    receiveData = new byte[1024];
                    isDone = true;
                }//end if 
                
                // close socket
                clientSocket.close();  
                
            }// end try 
            catch (SocketException ex) 
            {
                Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
            } 
            catch (UnknownHostException ex) 
            {
                Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
            } 
            catch (IOException | InterruptedException ex) 
            {
                Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }// end while
        // end game or go back to main menu...
        System.exit(0);    
    }// end of run
}// end of client