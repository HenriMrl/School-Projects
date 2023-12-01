package oy.tol.tra;

import java.awt.Color;
import java.awt.Graphics;

public class Triangle extends Shape {

    protected Triangle(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public Triangle(Shape shape) {
        super(shape);
    }

    @Override
    public void draw(Graphics graphics) {
        Color previous = graphics.getColor();
        graphics.setColor(this.lineColor);
        int [] xPoints = {xCoordinate, xCoordinate+(width/2), xCoordinate+width, xCoordinate};
        int [] yPoints = {yCoordinate+height, yCoordinate, yCoordinate+height, yCoordinate+height};
        graphics.drawPolygon(xPoints, yPoints, 4);
        graphics.setColor(previous);
    }
    
}
