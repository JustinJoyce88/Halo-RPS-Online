package rps;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

public class Splashscreen extends JWindow
{   public Splashscreen(Frame f, String path, int loc) throws Exception
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
            {   setLocation(screenSize.width/2 - (labelSize.width/2), 0);
            }
        }
        catch (Exception e)
        {   System.out.println(e);
        }
        
        addMouseListener(new MouseAdapter()
            {   @Override
                public void mousePressed(MouseEvent e)
                {   setVisible(false);
                    dispose();
                }
            }); 
        setVisible(true);       
    }
}
