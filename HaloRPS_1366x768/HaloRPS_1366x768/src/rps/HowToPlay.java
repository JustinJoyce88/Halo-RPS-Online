package rps;

import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;


public class HowToPlay 
{
    public void HowToPlay() throws IOException
    {   
        String welcomeScreen = "The game - press [R] for Infantry, [P] for  a Tank or [S] for Air. Infantry beats Air, \n"
                +      "Tanks beats Infantry, and Air beats Tanks. It's that simple!\n\n" 
                +      "Offline VS CPU - In this mode you will play  first to 3 against the computer.\n\n" 
                +      "Offline VS Player - In this mode you will play against a friend locally first to 3.\n"
                +      "In order for this game to work properly offline, you must look away when your opponent\n"
                +      "is making their decision.\n\n"
                +      "Chat - In this mode you can chat to your opponent. Type /end to exit the program.\n\n"
                +      "Online Multiplayer - In this mode you will each see random messages. When a mesage says\n"
                + "/play, a game of rock paper scissors will start and display the results. When a message\n"
                + "says /end, the program will exit.\n\n";
        
             JTextArea text = new JTextArea();
             text.setEditable(false);
             text.setText(welcomeScreen);
             JOptionPane.showMessageDialog(null, text, "How To Play", JOptionPane.INFORMATION_MESSAGE);
     }
}
