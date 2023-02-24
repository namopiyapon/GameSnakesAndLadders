
package com.mycompany.snakesandladders;
import java.awt.HeadlessException;
import java.awt.GridLayout;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class SnakesAndLadders extends JFrame{
    public SnakesAndLadders(){
        setTitle("ROBOT");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(1,1,0,0));
        start();
        setVisible(true);
    }
    
     public void start(){
        Index s =new Index();
        add(s);
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new SnakesAndLadders();
        
    }
}
