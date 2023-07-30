import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
/* game frame is the frame around the painting */
/* GameFrame is window frame with minimize, maximize, X button */
/* extends JFrame so we can treat GameFrame as JFrame */

public class GameFrame extends JFrame { 

    /* create instance of the GamePanel */
    GamePanel panel;
    
    GameFrame(){ /* constructor */
        panel = new GamePanel();
        this.add(panel); /* add game panel to frame */
        this.setTitle("Pong Game"); /* add title */
        this.setResizable(false); /* don't want people to resize it */
        this.setBackground(Color.black);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); /* hit X button to close app */
        this.pack(); /* use pack method to make game frame class fit snugly around game panel
        so J frame can accommodate size of game panel*/
        this.setVisible(true);
        this.setLocationRelativeTo(null); /* so it appears in the middle of the screen */

    }
}
