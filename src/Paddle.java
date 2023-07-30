import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Paddle extends Rectangle{ //Paddle is subclass of Rectangle superclass

    /* 2 variables to declare before constructor*/
    int id; /* 1 for player 1, 2 for paddle 2 */
    int yVelocity; /* how fast paddle move up down when press button*/
    int speed = 10; //speed of the paddle movement

    Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id){ //same with paddle construction in gamepanel
         //bcs line 6 paddle subclass of rectangle superclass, call super constructor to assign arguments
        super(x,y,PADDLE_WIDTH,PADDLE_HEIGHT);
        this.id=id;
    }

    public void keyPressed(KeyEvent e) {
        switch(id) { //examine contents of ID variable
        case 1: //paddle 1
            if(e.getKeyCode()==KeyEvent.VK_W) { //if type 'W', will execute:
                setYDirection(-speed); //move up on Y axis
                move();
            }
            if(e.getKeyCode()==KeyEvent.VK_S) {
                setYDirection(speed);
                move();
            }
            break;
        case 2: //paddle 2
            if(e.getKeyCode()==KeyEvent.VK_UP) { //if type up arrow, will execute:
                setYDirection(-speed); //move up on Y axis
                move();
            }
            if(e.getKeyCode()==KeyEvent.VK_DOWN) {
                setYDirection(speed);
                move();
            }
            break;
        }
    }
    public void keyReleased(KeyEvent e) {
        switch(id) { //examine contents of ID variable
        case 1: //paddle 1
            if(e.getKeyCode()==KeyEvent.VK_W) { //if type 'W', will execute:
                setYDirection(0); //stop moving (0 speed)
                move();
            }
            if(e.getKeyCode()==KeyEvent.VK_S) {
                setYDirection(0);
                move();
            }
            break;
        case 2: //paddle 2
            if(e.getKeyCode()==KeyEvent.VK_UP) { //if type up arrow , will execute:
                setYDirection(0); //stop moving
                move();
            }
            if(e.getKeyCode()==KeyEvent.VK_DOWN) {
                setYDirection(0);
                move();
            }
            break;
        }
    }
    public void setYDirection(int yDirection) { /* paddle only move up down */
        yVelocity = yDirection;
    }
    public void move() {
        y = y + yVelocity;
    }
    public void draw(Graphics g) { //draw rectangle for paddle
        if(id==1)
            g.setColor(Color.cyan); //set color for player 1
        else //set color for player 2
            g.setColor(Color.red);
        g.fillRect(x, y, width, height);
    }
}
