package snakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class gamePanel extends JPanel implements ActionListener , KeyListener {
    private int[] snakeXlength=new int[750];
    private int[] snakeYlength=new int[750];
    private  int lengthOfSnake=3;

    private int[] xPos= {25,50,75,100,125,150,175, 200, 225, 250, 275, 300, 325,350,375,400,425,450,475,500,525,550,575,600, 625,650,675,700,725,750, 775,800,825,850};

    private int[] yPos= {75,100,125,150, 175, 200, 225, 250, 275, 300, 325, 350,375,400,425, 450,475,500,525,550,575,600,625};
    private Random random=new Random();
    private int enemyX,enemyY;
    private boolean left=false;
    private boolean right=true;
    private boolean up=false;
    private boolean down=false;
     private int moves=0; // checking initial condition
    private int score=0;
    private boolean gameOver=false;

    private  ImageIcon snaketitle=new ImageIcon(getClass().getResource("snaketitle.jpg"));
   private ImageIcon leftmouth=new ImageIcon(getClass().getResource("leftmouth.png"));
    private ImageIcon rightmouth=new ImageIcon(getClass().getResource("rightmouth.png"));
    private ImageIcon upmouth=new ImageIcon(getClass().getResource("upmouth.png"));
    private ImageIcon downmouth=new ImageIcon(getClass().getResource("downmouth.png"));
    private ImageIcon snakeimage=new ImageIcon(getClass().getResource("snakeimage.png"));
    private ImageIcon enemy=new ImageIcon(getClass().getResource("enemy.png"));

    private Timer timer;
    private int delay=150;
    gamePanel()
    {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
       timer=new Timer(delay,this);
       timer.start();
       newEnemy();
    }

    private void newEnemy() {
        enemyX=xPos[random.nextInt(34)];
        enemyY=yPos[random.nextInt(23)];
        //enemy position not on snake body
        for(int i=lengthOfSnake-1;i>=0;i--)
        {
            if(snakeXlength[i]==enemyX && snakeYlength[i]==enemyY)
            {
                newEnemy();
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.WHITE);
        g.drawRect(24,10,851,55);

        g.drawRect(24,74,851,576);
        snaketitle.paintIcon(this,g,25,11);
        g.setColor(Color.black);
        g.fillRect(25,75,850,575);

        //draw snake
        if(moves==0)
        {
            snakeXlength[0]=100;
            snakeXlength[1]=75;
            snakeXlength[2]=50;
            snakeYlength[0]=100;
            snakeYlength[1]=100;
            snakeYlength[2]=100;
            //moves++;
        }
        if(left)
        {
            leftmouth.paintIcon(this,g,snakeXlength[0],snakeYlength[0]);
        }
        if(right)
        {
            rightmouth.paintIcon(this,g,snakeXlength[0],snakeYlength[0]);
        }
        if(up)
        {
            upmouth.paintIcon(this,g,snakeXlength[0],snakeYlength[0]);
        }
        if(down)
        {
            downmouth.paintIcon(this,g,snakeXlength[0],snakeYlength[0]);
        }
        for(int i=1;i<lengthOfSnake;i++)
        {
            snakeimage.paintIcon(this,g,snakeXlength[i],snakeYlength[i]);
        }
        enemy.paintIcon(this,g,enemyX,enemyY);

        if(gameOver)
        {
            g.setColor(Color.RED);

            g.setFont(new Font("Arial",Font.BOLD,20));
            g.drawString("GAME OVER",370,300);
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial",Font.PLAIN,20));
            g.drawString("PRESS 'SPACE' TO RESTART ",300,350);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",Font.PLAIN,14));
        g.drawString("Score : "+score ,750,30);
        g.drawString("Length : "+lengthOfSnake,750,50);

        g.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i=lengthOfSnake-1;i>0;i--)
        {
            snakeXlength[i]=snakeXlength[i-1];
            snakeYlength[i]=snakeYlength[i-1];
        }


        if(left)
        {
            snakeXlength[0]=snakeXlength[0]-25;
        }
        if(right)
        {
            snakeXlength[0]=snakeXlength[0]+25;
        }
        if(up)
        {
            snakeYlength[0]=snakeYlength[0]-25;
        }
        if(down)
        {
            snakeYlength[0]=snakeYlength[0]+25;
        }
        if(snakeXlength[0]>850)snakeXlength[0]=25;
        if(snakeXlength[0]<25)snakeXlength[0]=850;
        if(snakeYlength[0]>625)snakeYlength[0]=75;
        if(snakeYlength[0]<75)snakeYlength[0]=625;


        collideWithEnemy();
        collideWithBody();
        repaint();
    }

    private void collideWithBody() {
        for(int i=lengthOfSnake-1;i>0;i--)
        {
            if(snakeXlength[i]==snakeXlength[0] && snakeYlength[i]==snakeYlength[0])
            {
                timer.stop();
                gameOver=true;
            }
        }
    }

    private void collideWithEnemy() {
        if(snakeXlength[0]==enemyX && snakeYlength[0]==enemyY)
        {
            newEnemy();
            lengthOfSnake++;
            score++;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SPACE)
        {
            restart();
        }
       if(e.getKeyCode()==KeyEvent.VK_LEFT && (!right))
       {
           left = true;
           right=false;
           up=false;
           down=false;
           moves++;
       }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT && (!left))
        {
            left = false;
            right=true;
            up=false;
            down=false;
            moves++;

        }
        if(e.getKeyCode()==KeyEvent.VK_UP && (!down))
        {
            left = false;
            right=false;
            up=true;
            down=false;
            moves++;
        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN && (!up))
        {
            left = false;
            right=false;
            up=false;
            down=true;
            moves++;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    private void restart()
    {
        gameOver=false;
        moves=0;
        score=0;
        lengthOfSnake=3;
        left=false;
        right=true;
        up=false;
        down=false;
        timer.start();
        repaint();

    }
}
