package com.mycompany.snakesandladders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class dice {

    private BufferedImage dice1;
    private BufferedImage dice2;
    private BufferedImage dice3;
    private BufferedImage dice4;
    private BufferedImage dice5;
    private BufferedImage dice6;
    int dice ;

    public dice(int d) {
        dice = d;
    }

    public void draw(Graphics g) {
        try {

            dice1 = ImageIO.read(new File("dice1.png"));
            dice2 = ImageIO.read(new File("dice2.png"));
            dice3 = ImageIO.read(new File("dice3.png"));
            dice4 = ImageIO.read(new File("dice4.png"));
            dice5 = ImageIO.read(new File("dice5.png"));
            dice6 = ImageIO.read(new File("dice6.png"));

        } catch (IOException ex) {
            // handle exception...
        }
        if (dice == 1) {
            g.drawImage(dice1, 420, 520, 50, 50, null);
        }
        if (dice == 2) {
            g.drawImage(dice2, 420, 520, 50, 50, null);
        }
        if (dice == 3) {
            g.drawImage(dice3, 420, 520, 50, 50, null);
        }
        if (dice == 4) {
            g.drawImage(dice4, 420, 520, 50, 50, null);
        }
        if (dice == 5) {
            g.drawImage(dice5, 420, 520, 50, 50, null);
        }
        if (dice == 6) {
            g.drawImage(dice6, 420, 520, 50, 50, null);
        }
    }
}
