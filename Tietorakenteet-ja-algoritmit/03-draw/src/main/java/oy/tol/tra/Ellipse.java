package oy.tol.tra;

import java.awt.Graphics;
import java.awt.Color;

public class Ellipse extends Shape {

    public Ellipse(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public Ellipse(final Ellipse ellipse) {
        super(ellipse);
    }

    @Override
    public void draw(Graphics graphics) {
        Color previous = graphics.getColor();
        graphics.setColor(this.lineColor);
        graphics.drawOval(xCoordinate, yCoordinate, width, height);
        graphics.setColor(previous);
    }
    
}
