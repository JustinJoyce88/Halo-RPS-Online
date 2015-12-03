package multithreaded;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.swing.JOptionPane;

public class ChatClient extends Thread 
{   // variables

    public ChatClient()
    {   
        
    }
    public void run()
    {   
        boolean isDoneChatting = false;
        
        String theUserName=null;
        while ((theUserName == null) || (theUserName.equals(""))) 
        {   theUserName = JOptionPane.showInputDialog(null, "Enter a User Name:", 
                "User Name", JOptionPane.QUESTION_MESSAGE);
        }      
        int destPort = 0;
        while ((destPort <= 1023) || (destPort >= 65535)) 
        {   destPort = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Destination Port:", 
                "Your Friend's Port", JOptionPane.INFORMATION_MESSAGE));
        }
        String destIP=null;
        while ((destIP == null) || (destIP.equals(""))) 
        {   destIP = JOptionPane.showInputDialog(null, "Enter Destination IP:", 
                "Your Friend's IP", JOptionPane.QUESTION_MESSAGE);
        }
        
        JOptionPane.showMessageDialog(null, "You are ready to enter the lobby.");
        System.out.println("Welcome to the Halo RPS Chat Client!\n-------------------------------");
        
        // while loop
        while (!isDoneChatting)
        {   
            String input=null;
            while ((input == null) || (input.equals(""))) 
            {   input = JOptionPane.showInputDialog(null, "Enter your message:", 
                    "Chat", JOptionPane.INFORMATION_MESSAGE);
            }   
            // begin try
            try
            {
                DatagramSocket clientSocket = new DatagramSocket(); 
                // begin try    
                try
                {
                    InetAddress IPAddress = InetAddress.getByName(destIP);

                    byte[] sendData = new byte[1024]; 
                    byte[] receiveData = new byte[1024]; 

                    sendData = input.getBytes();   

                    // send packet
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, destPort);
                    clientSocket.send(sendPacket); 

                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    clientSocket.receive(receivePacket); 

                    String chatMessage = new String(receivePacket.getData());
                    
                    System.out.println(theUserName + ": " + chatMessage.trim());
                    // begin switch
                    switch(chatMessage.trim())
                    {
                        case "Thank you for chatting.\nExiting Program.": System.exit(0);
                            break;
                    }//end switch
                }// end try
                finally
                {
                    clientSocket.close();
                }// end finally
            }// end try
            catch(Exception E)
            {
                System.out.println(E);
            }// end catch
        }// end while loop    
    }// end of run
}// end of Client Thread class

