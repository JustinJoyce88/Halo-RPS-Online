package multithreaded;

import java.awt.HeadlessException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.swing.JOptionPane;

public class ChatServer extends Thread 
{
    private String chatMessage;
    private static String userName;
    
    public ChatServer()
    {   
        setChatMessage("");
    }
    
    public void setUName(String userName1)
    {
        userName = userName1;
    }
    
    public String getUName()
    {
        return userName;
    }

    public void run()
    {      
        try
        {   int intServerPort = 0;
            while ((intServerPort <= 1023) || (intServerPort >= 65535)) 
            {   intServerPort = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Server Port:", 
                    "Your Server Port", JOptionPane.INFORMATION_MESSAGE));
            }
            DatagramSocket serverSocket = new DatagramSocket(intServerPort);

            while(true) 
            {
                byte[] receiveData = new byte[1024]; 
                byte[] sendData = new byte[1024]; 

                DatagramPacket receivePacket =  new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket); 


                String sentence = new String(receivePacket.getData()); 

                setChatMessage(sentence);
                System.out.println("Other User: " + getChatMessage());

                InetAddress IPAddress = receivePacket.getAddress(); 
                int port = receivePacket.getPort(); 

                sendData = getChatMessage().getBytes(); 

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port); 
                serverSocket.send(sendPacket);

                if(getChatMessage().equals("Thank you for chatting.\nExiting Program."))
                {
                    System.exit(0);
                }
            }
        }
        catch(HeadlessException | NumberFormatException | IOException E)
        {
            System.out.println(E);

        }
    }

    private String getChatMessage() 
    {
        return chatMessage;
    }

    private void setChatMessage(String cm) 
    {
        String temp = cm.trim();

        switch(temp.toUpperCase())
        {
            case "/END": chatMessage = "Thank you for chatting.\nExiting Program.";
                break;
            default: chatMessage = temp;
        }
    }
}


