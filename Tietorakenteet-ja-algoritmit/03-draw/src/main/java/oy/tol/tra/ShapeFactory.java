package oy.tol.tra;

import java.util.Random;

public class ShapeFactory {
    
    private static Random generator = new Random();

    private ShapeFactory() {
    }

    public static Shape createRandomShape(int xCoordinate, int yCoordinate) {
        final int SHAPE_TYPE_COUNT = 3;
        switch (generator.nextInt(SHAPE_TYPE_COUNT)) {
            case 0:
                return new Rectangle(xCoordinate, yCoordinate, 6, 6);
            case 1:
                return new Triangle(xCoordinate, yCoordinate, 6, 6);
            default:
                return new Ellipse(xCoordinate, yCoordinate, 6, 6);
        }
    }
}
