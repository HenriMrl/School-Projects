package oy.tol.tra;

import java.awt.Graphics;
import java.awt.Color;

/**
 * This class is a base class for all shapes in the drawing app.
 * 
 * @author Antti Juustila
 * @version 0.9
 */
public abstract class Shape {
    private static long runningId = 0;
    protected long id;
    protected int xCoordinate;
    protected int yCoordinate;
    protected int width;
    protected int height;
    protected boolean filled = false;
    protected Color lineColor = Color.BLACK;
    protected Color fillColor = null;
    protected boolean isSelected = false;

    /**
     * Constructs a shape providing the coordinates of the shape and the size of it.
     * @param x The x coordinate of the shape in pixels.
     * @param y The y coordinate of the shape in pixels.
     * @param w The width of the shape.
     * @param h The height of the shape.
     */
    protected Shape(int x, int y, int w, int h) {
        id = Shape.runningId++;
        xCoordinate = x;
        yCoordinate = y;
        width = w;
        height = h;
    }

    protected Shape(final Shape shape) {
        id = Shape.runningId++;
        xCoordinate = shape.xCoordinate;
        yCoordinate = shape.yCoordinate;
        width = shape.width;
        height = shape.height;
        filled = shape.filled;
        lineColor = shape.lineColor;
        fillColor = shape.fillColor;
    }

    @Override
    public String toString() {
        return (isSelected ? "*" : "") + String.format("%d", id);
    }

    public abstract void draw(Graphics graphics);

    /**
     * Get the x coordinate of the shape.
     * @return The x coordinate.
     */
    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}
