import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Score extends Rectangle{

    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    int player1; /* hold current score of player 1 */
    int player2;
    
    Score(int GAME_WIDTH, int GAME_HEIGHT){
        //pass in game width & game height
        //game width& height static, so type class name instead of instance 
        Score.GAME_WIDTH = GAME_WIDTH;
        Score.GAME_HEIGHT = GAME_HEIGHT;
    }
    /* we only need to draw a score, don't need to move or anything */
    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Consolas", Font.PLAIN, 60));
        //drawline(x1, y1, x2, y2) x1y1 = start position, x2y2 = end position
        //start in the center
        g.drawLine(GAME_WIDTH/2, 0, GAME_WIDTH/2, GAME_HEIGHT);

        //have 2 digits of score
        g.drawString(String.valueOf(player1/10)+String.valueOf(player1%10), (GAME_WIDTH/2)-85, 50); //convert player 1 score to string
        g.drawString(String.valueOf(player2/10)+String.valueOf(player2%10), (GAME_WIDTH/2)+20, 50); //convert player 1 score to string
    }
}