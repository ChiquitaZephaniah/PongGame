import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/* game panel is the canvas on which we're painting
 * game panel will be surrounded with the frame */

public class GamePanel extends JPanel implements Runnable{ /* runnable interface so can run on a thread */

    static final int GAME_WIDTH = 1000;
    /* static bcs reason we had multiple instances of game panel class, they'd all share 1 variable
     * and wouldn't each have their own individual game width. they'll share the same one */
    /* final bcs it prohibits from accidentally modifying our game with later on in the program
     * also it makes this run a little bit faster*/
    static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555)); /* use pingpong table ratio (ratio 5/9)
    ratio: put 5 divided by 9 value because if put "5/9", integer division will round up to 0*/
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
    static final int BALL_DIAMETER = 20; /* high number, high ball diameter */
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;
    /* declare few instances, but not initialize them yet */
    Thread gameThread; /* thread bcs we're implementing runnable interface */
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1; /* player 1 */
    Paddle paddle2;
    Ball ball;
    Score score;

    GamePanel(){
        /* call new paddles and ball method */
        newPaddles();
        newBall();
        score = new Score(GAME_WIDTH,GAME_HEIGHT); /* instanciate score class and pass game width & height into constructors */
        this.setFocusable(true); /* when press any keys, it's going to have have focus */
        this.addKeyListener(new AL()); /* add action listener(AL)(extends keyAdapter class) so this will respond to keystrokes */
        this.setPreferredSize(SCREEN_SIZE);

        gameThread = new Thread(this); /* pass 'this' since we're implementing runnable interface */
        gameThread.start(); /* start the thread */
    }

    public void newBall() { /* method. So whenever we call this method 
        will create new ball on the screen */
        //random = new Random(); //uncomment this if want ball start random ydirection 
        //want the ball to start at the center of the window
        // details: new Ball(x, y, width, height)
        ball = new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2),(GAME_HEIGHT/2)-(BALL_DIAMETER/2),BALL_DIAMETER,BALL_DIAMETER);
        //use this if want the ball to start from random y direction
        //ball = new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2),random.nextInt(GAME_HEIGHT-BALL_DIAMETER),BALL_DIAMETER,BALL_DIAMETER);
        //uncomment this^ if want ball start random ydirection 
    }
    public void newPaddles() { /* call this method to reset a level or a game */
        paddle1 = new Paddle(0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,1); //where we want the paddle to be in x&y axis
        // x=0 means very left of the window
        // right in the middle of y axis, also subtract half of paddle height so right in the middle
        // then paddle width and paddle height, unique id number (1)
        paddle2 = new Paddle(GAME_WIDTH-PADDLE_WIDTH,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,2);
    }
    public void paint(Graphics g) { /* so we can paint stuff on the screen, one parameter graphics */
        image = createImage(getWidth(), getHeight());/* create image with dimention width&height of game panel */
        graphics = image.getGraphics();
        /* call draw method to draw all components */
        draw(graphics); /* pass in the graphic that is created from image */
        g.drawImage(image,0,0,this);/* pass image, coordinates x0 y0 top left corner, this Jpanel called game panel */
    }
    public void draw(Graphics g) { //actually draw the rectangle
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);
    }
    public void move() { /* move things after each iteration of game loop */
        //to make the move smoother
        paddle1.move(); //why can't stop the movement?
        paddle2.move(); //why can't stop the movement?
        ball.move();
    }
    public void checkCollision() {
        //bounce ball off top & bottom window edges
        if(ball.y <=0) {
            ball.setYDirection(-ball.yVelocity); //minus so it's reverse velocity, go opposite direction
        }
        if(ball.y >= GAME_HEIGHT-BALL_DIAMETER) {
            ball.setYDirection(-ball.yVelocity);
        }
        //bounces ball off paddles
        if(ball.intersects(paddle1)) {//compare ball and paddle if there's any collition
            ball.xVelocity = Math.abs(ball.xVelocity); //reverse the direction, can use the -ball.xVelocity too
            ball.xVelocity++; //if want to increase speed after touching paddle
            if(ball.yVelocity>0)
                ball.yVelocity++; //optional for more difficulty
            else
                ball.yVelocity--; //if yVelocity negative, going upwards
            //set x & y direction with these new values
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
        if(ball.intersects(paddle2)) {//compare ball and paddle if there's any collition
            ball.xVelocity = Math.abs(ball.xVelocity); //reverse the direction, can use the -ball.xVelocity too
            ball.xVelocity++; //if want to increase speed after touching paddle
            if(ball.yVelocity>0)
                ball.yVelocity++; //optional for more difficulty
            else
                ball.yVelocity--; //if yVelocity negative, going upwards
            //set x & y direction with these new values
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
        //stops paddles at window edges
        if(paddle1.y<=0)
            paddle1.y=0;
        if(paddle1.y >= (GAME_HEIGHT-PADDLE_HEIGHT)) //if moving down
            paddle1.y = GAME_HEIGHT-PADDLE_HEIGHT;
        if(paddle2.y<=0)
            paddle2.y=0;
        if(paddle2.y >= (GAME_HEIGHT-PADDLE_HEIGHT)) //if moving down
            paddle2.y = GAME_HEIGHT-PADDLE_HEIGHT;

        //give a player 1 point and creates new paddles & ball
        if(ball.x <=0) { //player 2 scored, ball goes to the left of the screen
            score.player2++;
            newPaddles();
            newBall();
            System.out.println("Player 2: " + score.player2);
        }
        if(ball.x >= GAME_WIDTH-BALL_DIAMETER) { //player 2 scored, ball goes to the left of the screen
            score.player1++;
            newPaddles();
            newBall();
            System.out.println("Player 1: " + score.player1);
        }
    }
    public void run() {
        //game loop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks; //nano seconds
        double delta = 0;
        while(true) {
            long now = System.nanoTime();
            delta += (now - lastTime)/ns;
            lastTime = now;
            if(delta >=1) {
                move(); //move all of the components
                checkCollision();
                repaint();
                delta--; //subtract 1 from delta
            }
        }
    }
    public class AL extends KeyAdapter{ /* inner class action listener */
        public void keyPressed(KeyEvent e) { //so paddles move up down when type in certain keystrokes
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }
        public void keyReleased(KeyEvent e) {
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }
    }
}
