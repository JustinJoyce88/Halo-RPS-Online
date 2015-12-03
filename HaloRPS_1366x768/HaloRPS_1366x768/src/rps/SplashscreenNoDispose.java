package rps;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

public class SplashscreenNoDispose extends JWindow
{   public SplashscreenNoDispose(Frame f, String path, int loc) throws Exception
    {   super(f);
        try
        {   JLabel l = new JLabel(new ImageIcon(path));
            getContentPane().add(l, BorderLayout.CENTER);
            pack();
            Dimension screenSize =
            Toolkit.getDefaultToolkit().getScreenSize();
            Dimension labelSize = l.getPreferredSize();
            if(loc==1)
            {   setLocation(screenSize.width/2 - (labelSize.width/2),
                screenSize.height/2 - (labelSize.height/2));
            }
            else
            {   setLocation(screenSize.width - (labelSize.width),
                screenSize.height/2 - (labelSize.height/2));
            }
        }
        catch (Exception e)
        {   System.out.println(e);
        }        setVisible(true);       
    }
}
