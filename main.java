package snakeGame;

import javax.swing.*;
import java.awt.*;

public class main {
    public static void main(String[] args) {
        JFrame frame=new JFrame("Snake Game");
        frame.setBounds(10,10,905,700);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel panel=new gamePanel();
        panel.setBackground(Color.DARK_GRAY);
        frame.add(panel);

        frame.setVisible(true);
    }
}
