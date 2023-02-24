package com.mycompany.snakesandladders;

import java.awt.Color;
import java.awt.Graphics;

public class Bot {

    private int x, y, width, height;

    public Bot(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.width = size;
        this.height = size;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval(x * width + 50, y * height + 35, width - 50, height - 50);
        g.setColor(Color.RED);
        g.fillOval(x * width + 55, y * height + 40, width - 60, height - 60);

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
