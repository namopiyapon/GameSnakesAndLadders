package com.mycompany.snakesandladders;

import java.awt.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Index extends JPanel implements Runnable {

    private BufferedImage image;
    private BufferedImage imageS;
    int dice;
    int dicebot;
    String Sdice;
    String Sdicebot;
    private boolean botwin = false;
    private boolean playerwin = false;
    int turn = 0; //กำหนดตาการเล่น
    boolean stop = false;
    boolean startbot = false;
    public static int WIDTH = 600;
    public static int HEIGHT = 600;
    private Thread th;
    private boolean running = true;
    //player
    private player p;
    private ArrayList<player> playerArr;
    private int xp = 5, yp = 4;//จุดที่บอทเริ่ม
    private int sizep = 1;//ขนาดช่อง
    int numplayer = 0;
    //BOT
    private Bot b;
    private ArrayList<Bot> botArr;
    private int xb = 5, yb = 4;
    private int sizeb = 1;
    int numbot = 0;

    //DICE
    private dice d;
    private ArrayList<dice> diceArr;

    private int count = 0;

    public Index() {
        try {
            image = ImageIO.read(new File("ladder.png"));
            imageS = ImageIO.read(new File("snake.png"));
        } catch (IOException ex) {
            // handle exception...
        }

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        playerArr = new ArrayList<>();
        botArr = new ArrayList<>();
        diceArr = new ArrayList<>();
        th = new Thread(this);
        th.start();
        addMouseListener(new MouseControl());
        setFocusable(true);

    }

    @Override
    public void run() {
        while (running) {
            tick();
            win();
            repaint();//การวาดใหม่
        }
    }

    private void tick() {

        if (playerArr.size() == 0) {
            p = new player(xp, yp, 100);
            playerArr.add(p);
        }
        if (botArr.size() == 0) {
            b = new Bot(xb, yb, 100);
            botArr.add(b);
        }
        if (diceArr.size() == 0) {
            d = new dice(dice);
            diceArr.add(d);
        }
        count++;
        if (count > 1000) {
            count = 0;
            move();

            p = new player(xp, yp, 100);
            playerArr.add(p);
            if (playerArr.size() > sizep) {
                playerArr.remove(0);
            }
            b = new Bot(xb, yb, 100);
            botArr.add(b);
            if (botArr.size() > sizep) {
                botArr.remove(0);
            }
            d = new dice(dice);
            diceArr.add(d);
            if (diceArr.size() > sizep) {
                diceArr.remove(0);
            }
        }
    }

    public void paint(Graphics g) {
        g.setColor(Color.pink);
        g.clearRect(0, 0, WIDTH, HEIGHT);
        //start
        g.setColor(Color.BLACK);
        g.setFont(new Font("Tahoma", Font.BOLD, 25));
        g.drawString("START ", 510, 465);
        //num
        int num = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                if (num > 0 && num < 29) {
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("Tahoma", Font.BOLD, 15));
                    g.drawString(" " + num + " ", 500 - (j * 100), 425 - (i * 100));
                }
                num++;
            }
        }
        //FINISH
        g.setColor(Color.BLACK);
        g.setFont(new Font("Tahoma", Font.BOLD, 25));
        g.drawString("FINISH ", 5, 60);
        //TABLE
        for (int i = 0; i < 600 / 100; i++) {
            g.drawLine(i * 100, 0, i * 100, 500);
        }
        for (int i = 0; i < 600 / 100; i++) {
            g.drawLine(0, i * 100, 600, i * 100);
        }
        //STAIRS UP
        g.drawImage(image, 500, 50, 100, 100, this);
        g.drawImage(image, 0, 350, 100, 100, this);
        g.drawImage(image, 400, 250, 100, 100, this);
        //STAIRS DOWN
        g.drawImage(imageS, 100, 50, 100, 100, this);
        g.drawImage(imageS, 200, 350, 100, 100, this);
        g.drawImage(imageS, 100, 250, 100, 100, this);

        //PLAYER
        for (int i = 0; i < playerArr.size(); i++) {
            playerArr.get(i).draw(g);
        }
        //BOT
        for (int i = 0; i < botArr.size(); i++) {
            botArr.get(i).draw(g);
        }
        //Dice
        for (int i = 0; i < diceArr.size(); i++) {
            diceArr.get(i).draw(g);
        }
        //PLAY AGAIN
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 525, 95, 40);
        g.setColor(Color.WHITE);
        g.fillRect(5, 530, 85, 30);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Tahoma", Font.BOLD, 13));
        g.drawString("PLAY AGAIN", 7, 550);

        //BUTTOM LOW
        g.setColor(Color.DARK_GRAY);
        g.fillRect(100, 525, 70, 40);
        g.setColor(Color.WHITE);
        g.fillRect(105, 530, 60, 30);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Tahoma", Font.BOLD, 15));
        g.drawString("LOW", 115, 550);

        //BUTTOM MIDDLE
        g.setColor(Color.DARK_GRAY);
        g.fillRect(200, 525, 75, 40);
        g.setColor(Color.WHITE);
        g.fillRect(205, 530, 65, 30);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Tahoma", Font.BOLD, 15));
        g.drawString("MIDDLE", 209, 550);

        //BUTTOM HIGH
        g.setColor(Color.DARK_GRAY);
        g.fillRect(300, 525, 70, 40);
        g.setColor(Color.WHITE);
        g.fillRect(305, 530, 60, 30);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Tahoma", Font.BOLD, 15));
        g.drawString("HIGH", 315, 550);
        //เลขที่ bot ทอย
        g.setColor(Color.BLACK);
        g.setFont(new Font("Tahoma", Font.BOLD, 15));
        g.drawString("BOT: " + Sdicebot, 480, 550);
        //เลขที่ ผู้เล่น ทอย
        g.setColor(Color.BLACK);
        g.setFont(new Font("Tahoma", Font.BOLD, 15));
        g.drawString("PLAY: " + Sdice, 480, 580);
        //เช็คการชนะ
        if (botwin) {
            g.setColor(Color.RED);
            g.setFont(new Font("Tahoma", Font.BOLD, 50));
            g.drawString("BOT WIN", 180, 280);
        }
        if (playerwin) {
            g.setColor(Color.RED);
            g.setFont(new Font("Tahoma", Font.BOLD, 50));
            g.drawString("PLAYER WIN", 150, 280);
        }

    }

    class MouseControl extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            int xMouse = e.getX();
            int yMouse = e.getY();
            int dLow[] = {1, 2, 3, 4};
            int dMiddle[] = {2, 3, 4, 5};
            int dHtgh[] = {3, 4, 5, 6};
            if ((100 < xMouse && 525 < yMouse) && (170 > xMouse && 565 > yMouse)) {
                int i = new Random().nextInt(dLow.length);
                dice = dLow[i];
                Sdice = "Low " + dice;
                stop = true;
                startbot = true;
                System.out.print(dice + " LOW\n");
            }
            if ((200 < xMouse && 525 < yMouse) && (270 > xMouse && 565 > yMouse)) {
                int i = new Random().nextInt(dMiddle.length);
                dice = dMiddle[i];
                Sdice = "Middle " + dice;
                stop = true;
                startbot = true;
                System.out.print(dice + " MIDDLE\n");
            }
            if ((300 < xMouse && 525 < yMouse) && (370 > xMouse && 565 > yMouse)) {
                int i = new Random().nextInt(dHtgh.length);
                dice = dHtgh[i];
                Sdice = "Htgh " + dice;
                stop = true;
                startbot = true;
                System.out.print(dice + " HIGH\n");
            }
            if ((0 < xMouse && 525 < yMouse) && (95 > xMouse && 565 > yMouse)) {

                xp = 5;
                yp = 4;
                xb = 5;
                yb = 4;
                numplayer = 0;
                numbot = 0;
                botwin = false;
                playerwin = false;
                Sdice = "";
                Sdicebot = "";
            }
            //System.out.print(n + "n\n");
        }
    }

    private void move() {
        if (stop) {

            if (turn % 2 == 0) {
                System.out.print(" trun Player-------------------------------------\n");
                numplayer += dice;
                int n[] = walk(numplayer);
                xp = n[0];
                yp = n[1];
                turn++;
                bot();
                //System.out.print(xp + " xp " + yp + " yp////////////////////////");
            }
            if (turn % 2 == 1) {
                System.out.print(" trun Bot----------------------------------------\n");
                numbot += dicebot;
                int n[] = walk(numbot);
                xb = n[0];
                yb = n[1];
                turn++;
                //System.out.print(xb + " xb " + yb + " yb////////////////////////");
            }
            stop = false;
        }
    }

    private int[] walk(int num) {
        int x = 0;
        int y = 0;
        if (num == 1) {
            x = 4;
            y = 4;
        }
        if (num == 2) {
            x = 3;
            y = 4;
        }
        if (num == 3 || num == 9) {
            x = 2;
            y = 4;
        }
        if (num == 4) {
            x = 1;
            y = 4;
        }
        if (num == 6) {
            x = 5;
            y = 3;
        }
        if (num == 8) {
            x = 3;
            y = 3;
        }
        if (num == 10 || num == 16) {
            x = 1;
            y = 3;
        }
        if (num == 11 || num == 5) {
            x = 0;
            y = 3;
        }
        if (num == 12) {
            x = 5;
            y = 2;
        }
        if (num == 13 || num == 7) {
            x = 4;
            y = 2;
        }
        if (num == 14) {
            x = 3;
            y = 2;
        }
        if (num == 15) {
            x = 2;
            y = 2;
        }
        if (num == 17) {
            x = 0;
            y = 2;
        }
        if (num == 19) {
            x = 4;
            y = 1;
        }
        if (num == 20) {
            x = 3;
            y = 1;
        }
        if (num == 21) {
            x = 2;
            y = 1;
        }
        if (num == 22 || num == 28) {
            x = 1;
            y = 1;
        }
        if (num == 23) {
            x = 0;
            y = 1;
        }
        if (num == 24 || num == 18) {
            x = 5;
            y = 0;
        }
        if (num == 25) {
            x = 4;
            y = 0;
        }
        if (num == 26) {
            x = 3;
            y = 0;
        }
        if (num == 27) {
            x = 2;
            y = 0;
        }
        if (num == 29) {
            x = 0;
            y = 0;
        }
        return new int[]{x, y};
    }

    private void bot() {
        int good[] = {5, 7, 18};
        int bad[] = {28, 16, 9};
        int sumgood[] = new int[3];
        int sumbad[] = new int[3];
        int l = 0, m = 0, h = 0;
        int max = 0;
        String smax = "";
        if (startbot) {
            for (int i = 0; i < good.length; i++) {
                sumgood[i] = Math.abs(numbot - good[i]);
                //System.out.print(good[i] + "-" + numbot + "=" + sumgood[i] + "\n");
            }
            for (int i = 0; i < sumgood.length; i++) {
                if (sumgood[i] >= 1 && sumgood[i] <= 4) {
                    l++;
                    if (l > max) {
                        max = l;
                        smax = "l";
                    }
                }
                if (sumgood[i] >= 2 && sumgood[i] <= 5) {
                    m++;
                    if (m > max) {
                        max = m;
                        smax = "m";
                    }
                }
                if (sumgood[i] >= 3 && sumgood[i] <= 6) {
                    h++;
                    if (h > max) {
                        max = h;
                        smax = "h";
                    }
                }
            }

            for (int i = 0; i < good.length; i++) {
                sumbad[i] = Math.abs(numbot - bad[i]);
                //System.out.print(bad[i] + "-" + numbot + "=" + sumbad[i] + "\n");
            }
            for (int i = 0; i < sumbad.length; i++) {
                if (sumbad[i] >= 1 && sumbad[i] <= 4) {
                    l--;
                    if (l > max) {
                        max = l;
                        smax = "l";
                    }
                    if (m > max) {
                        max = m;
                        smax = "m";
                    }
                    if (h > max) {
                        max = h;
                        smax = "h";
                    }
                }
                if (sumbad[i] >= 2 && sumbad[i] <= 5) {
                    m--;
                    if (l > max) {
                        max = l;
                        smax = "l";
                    }
                    if (m > max) {
                        max = m;
                        smax = "m";
                    }
                    if (h > max) {
                        max = h;
                        smax = "h";
                    }
                }
                if (sumbad[i] >= 3 && sumbad[i] <= 6) {
                    h--;
                    if (l > max) {
                        max = l;
                        smax = "l";
                    }
                    if (m > max) {
                        max = m;
                        smax = "m";
                    }
                    if (h > max) {
                        max = h;
                        smax = "h";
                    }
                }

            }
            //System.out.print(l + "=l " + m + "=m " + h + "=h\n");
        }
        int dLow[] = {1, 2, 3, 4};
        int dMiddle[] = {2, 3, 4, 5};
        int dHtgh[] = {3, 4, 5, 6};
        if (smax == "l") {
            int i = new Random().nextInt(dLow.length);
            dicebot = dLow[i];
            Sdicebot = "Low " + dicebot;
            //turn++;
            stop = true;
            System.out.print(dicebot + " dLow--->Bot\n");
            move();
        } else if (smax == "m") {
            int i = new Random().nextInt(dMiddle.length);
            dicebot = dMiddle[i];
            Sdicebot = "Middle " + dicebot;
            //turn++;
            stop = true;
            System.out.print(dicebot + " dMiddle--->Bot\n");
            move();
        } else if (smax == "h") {
            int i = new Random().nextInt(dHtgh.length);
            dicebot = dHtgh[i];
            Sdicebot = "Htgh " + dicebot;
            //turn++;
            stop = true;
            System.out.print(dicebot + " dHtgh--->Bot\n");
            move();
        }
        startbot = false;
    }

    private void win() {
        if (numplayer >= 29) {
            playerwin = true;
            startbot = false;
            stop = false;
            //botwin = false;
        }
        if (numbot >= 29) {
            botwin = true;
            startbot = false;
            stop = false;
            //playerwin = false;
        }

    }
}
