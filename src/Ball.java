import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Ball extends Rectangle{

    /* instance of random class */
    Random random;
    int xVelocity; /* how fast move X axis */
    int yVelocity; /* how fast move Y axis */
    int initialSpeed = 2;

    //parameter for constructor has to be the same with instance in gamepanel (x,y,width,height)
    Ball(int x, int y, int width, int height){
        //use super constructor bcs ball is subclass of rectangle superclass
        super(x,y,width,height);
        random = new Random(); //random direction ball is gonna head
        int randomXDirection = random.nextInt(2); //0 left 1 right
        if(randomXDirection == 0)
            randomXDirection--;
        //pass in a random X direction
        setXDirection(randomXDirection*initialSpeed);

        int randomYDirection = random.nextInt(2); //0 left 1 right
        if(randomYDirection == 0)
            randomYDirection--;
        //pass in a random Y direction
        setYDirection(randomYDirection*initialSpeed);

    }

    /* both X and Y direction because ball can go X and Y unlike paddle */
    public void setXDirection(int randomXDirection) { /* when create a ball, it goes random direction */
        xVelocity = randomXDirection;
    }
    public void setYDirection(int randomYDirection) {
        yVelocity = randomYDirection;
    }
    public void move() {
        x += xVelocity;
        y += yVelocity;
    }
    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillOval(x, y, height, width); //height & width will be the same
    }
}


