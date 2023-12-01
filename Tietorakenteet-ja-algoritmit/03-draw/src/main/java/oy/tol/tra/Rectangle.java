package oy.tol.tra;

import java.awt.Graphics;
import java.awt.Color;

public class Rectangle extends Shape {

    Rectangle(int x, int y, int w, int h) {
        super(x, y, w, h);
    }
    public Rectangle(final Rectangle rectangle) {
        super(rectangle);
    }

    @Override
    public void draw(Graphics graphics) {
        Color previous = graphics.getColor();
        graphics.setColor(this.lineColor);
        graphics.drawRect(xCoordinate, yCoordinate, width, height);
        graphics.setColor(previous);
    }
    
}
