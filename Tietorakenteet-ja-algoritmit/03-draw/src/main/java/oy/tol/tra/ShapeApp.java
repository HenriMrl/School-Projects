package oy.tol.tra;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class ShapeApp extends JPanel implements ShapesListener, ActionListener {

    private JFrame frame;
    private Shapes shapes;
    private static final int WINDOW_WIDTH = 1600;
    private static final int WINDOW_HEIGHT = 1000;
    private Random generator = new Random();

    public static void main(String[] args) {
        try {
            new ShapeApp().run();
        } catch (ShapeException e) {
            e.printStackTrace();
        }
    }

    private void run() throws ShapeException {
        frame = new JFrame("Shapes Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        JMenuBar mainMenu = new JMenuBar();
        JMenu shapesMenu = new JMenu("Shapes");
        // Maze size
        JMenuItem commandMenu = new JMenuItem("Create 18 shapes");
        commandMenu.setActionCommand("18-shapes");
        commandMenu.addActionListener(this);
        shapesMenu.add(commandMenu);
        commandMenu = new JMenuItem("Create 10 000 shapes");
        commandMenu.setActionCommand("some-shapes");
        commandMenu.addActionListener(this);
        shapesMenu.add(commandMenu);
        commandMenu = new JMenuItem("Create 100 000 shapes");
        commandMenu.setActionCommand("many-shapes");
        commandMenu.addActionListener(this);
        shapesMenu.add(commandMenu);
        shapesMenu.addSeparator();
        commandMenu = new JMenuItem("Remove 1 from middle");
        commandMenu.setActionCommand("remove-1");
        commandMenu.addActionListener(this);
        shapesMenu.add(commandMenu);
        shapesMenu.addSeparator();
        commandMenu = new JMenuItem("Remove gray shapes algo 1");
        commandMenu.setActionCommand("remove-gray-1");
        commandMenu.addActionListener(this);
        shapesMenu.add(commandMenu);
        commandMenu = new JMenuItem("Remove gray shapes algo 2");
        commandMenu.setActionCommand("remove-gray-2");
        commandMenu.addActionListener(this);
        shapesMenu.add(commandMenu);
        commandMenu = new JMenuItem("Remove gray shapes algo 3");
        commandMenu.setActionCommand("remove-gray-3");
        commandMenu.addActionListener(this);
        shapesMenu.add(commandMenu);
        commandMenu = new JMenuItem("Remove gray shapes algo 4");
        commandMenu.setActionCommand("remove-gray-4");
        commandMenu.addActionListener(this);
        shapesMenu.add(commandMenu);
        mainMenu.add(shapesMenu);
        frame.setJMenuBar(mainMenu);
        frame.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

        shapes = new Shapes();
        shapes.setListener(this);

        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void shapesChanged() {
        SwingUtilities.invokeLater(() -> {
            repaint();
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (shapes.count() < 20) {
            drawSmallShapesCollection(g);
        } else {
            drawLargeShapesCollection(g);
        }
    }

    private void drawLargeShapesCollection(Graphics g) {
        shapes.draw(g);
    }

    private void drawSmallShapesCollection(Graphics g) {
        int shapeCount = shapes.count();
        int capacity = shapes.capacity();

        int cellWidth = getSize().width / capacity;
        int cellHeight = 50;
        int totalWidth = getSize().width - 10;
        g.drawRect(10, 10, totalWidth, cellHeight);
        int cellRightEdge = 12;
        for (int shapeIndex = 0; shapeIndex < shapeCount; shapeIndex++) {
            Shape shape = shapes.getShape(shapeIndex);
            Color oldColor = g.getColor();
            g.drawRect(cellRightEdge, 12, cellWidth - 2, cellHeight - 2);
            if (null != shape) {
                g.setColor(shape.getLineColor());
                g.drawString(shape.toString(), cellRightEdge + 4, 12 + (cellHeight / 2));
                g.setColor(oldColor);
            }
            g.drawString(String.format("%d", shapeIndex), cellRightEdge + cellWidth / 2 - 2, 12 + cellHeight + 12);
            cellRightEdge += cellWidth;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String command = e.getActionCommand();
            if ("18-shapes".equals(command)) {
                shapes.removeAll();
                shapes.setListener(this);
                repaint();
                createSmallNumberOfShapes();
            } else if ("some-shapes".equals(command)) {
                shapes.setListener(null);
                shapes.removeAll();
                createLargeNumberOfShapes(10000);
                repaint();
            } else if ("many-shapes".equals(command)) {
                shapes.setListener(null);
                shapes.removeAll();
                createLargeNumberOfShapes(100000);
                repaint();
            } else if ("remove-1".equals(command)) {
                shapes.remove(shapes.count() / 2);
            } else if ("remove-gray-1".equals(command)) {
                shapes.select(shape -> shape.getLineColor() == Color.GRAY);
                shapes.removeSelected1();
                repaint();
            } else if ("remove-gray-2".equals(command)) {
                shapes.select(shape -> shape.getLineColor() == Color.GRAY);
                long start = System.currentTimeMillis();
                shapes.removeSelected2();
                System.out.println("removeSelected2 took " + (System.currentTimeMillis() - start) + " ms");
                repaint();
            } else if ("remove-gray-3".equals(command)) {
                shapes.select(shape -> shape.getLineColor() == Color.GRAY);
                long start = System.currentTimeMillis();
                shapes.removeSelected3();
                System.out.println("removeSelected3 took " + (System.currentTimeMillis() - start) + " ms");
                repaint();
            } else if ("remove-gray-4".equals(command)) {
                shapes.select(shape -> shape.getLineColor() == Color.GRAY);
                long start = System.currentTimeMillis();
                shapes.removeSelected4();
                System.out.println("removeSelected4 took " + (System.currentTimeMillis() - start) + " ms");
                repaint();
            }
        } catch (NullPointerException | ShapeException | ArrayIndexOutOfBoundsException | InterruptedException ex) {
            JOptionPane.showMessageDialog(frame,
                                          "Exception happened because: " + ex.getMessage(),
                                          "Something went wrong",
                                          JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        
    }

    private void createLargeNumberOfShapes(int shapeCount) throws ShapeException {
        for (int index = 0; index < shapeCount; index++) {
            int x = generator.nextInt(10, WINDOW_WIDTH - 20);
            int y = generator.nextInt(10, WINDOW_HEIGHT - 20);
            Shape newShape = ShapeFactory.createRandomShape(x, y);
            if (index % 2 == 0) {
                newShape.setLineColor(Color.GRAY);
            }
            shapes.add(newShape);
        }
    }

    private void createSmallNumberOfShapes() {
        Thread thread = new Thread(() -> {
            try {
                for (int index = 1; index <= 18; index++) {
                    int x = generator.nextInt(10, WINDOW_WIDTH - 20);
                    int y = generator.nextInt(10, WINDOW_HEIGHT - 20);
                    Shape newShape = ShapeFactory.createRandomShape(x, y);
                    newShape.setHeight(5);
                    newShape.setWidth(5);
                    shapes.add(newShape);
                    if (index % 2 == 0) {
                        newShape.setLineColor(Color.GRAY);
                    }
                    Thread.sleep(500);
                }
            } catch (Exception ex) {
               ex.printStackTrace();
            }
            });
        thread.start();
    }

    @Override
    public void exceptionHappened(String reason) {
        SwingUtilities.invokeLater(() -> 
            JOptionPane.showMessageDialog(frame, "Exception happened because: " + reason, "Something went wrong", JOptionPane.ERROR_MESSAGE)
        );
    }
}
