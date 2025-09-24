import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class Dungeon extends JPanel {

    int width = 500;
    int height = 500;
    int[] ARGB;
    double angle = 0;
    double xPos = 100;
    double yPos = 100;

    BufferedImage smiley_image = null;
    BufferedImage rgb_image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    public static void main(String[] args) throws IOException {
        System.out.print("Render.main( ");
        for (int i = 0; i < args.length; i++) {
            System.out.print(args[i] + ", ");
        }
        System.out.println(")");
        Dungeon me = new Dungeon();
        int w = me.width;
        int h = me.height;
        me.init(w, h);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(w, h);
        frame.add(me);
        frame.setVisible(true);

        Thread animThread = new Thread() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10);
                        frame.repaint();
                    } catch (Exception e) {

                    }
                }
            }
        };
        animThread.start();

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SPACE:

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

    }

    public Shape getCurShape() {
        try {
            return shapes.elementAt(curShape);
        } catch (Exception e) {
            System.out.println("Error fetching element at " + curShape + ": " + e);
            return null;
        }
    }

    public void init(int w, int h) {
        width = w;
        RenderSettings.get().width = width;
        height = h;
        ARGB = new int[width * height];
        RenderSettings.get().ARGB = ARGB;
        int i = 0;
        for (int y = 0; y < height; y++) {
            int red = (y * 255) / (height - 1);
            for (int x = 0; x < width; x++) {
                int green = (x * 255) / (width - 1);
                int blue = 128;
                ARGB[i++] = (red << 16) | (green << 8) | blue;
            }
        }

    }

    public void paint(Graphics g) {
        // clear int array
        for (int i = 0; i < ARGB.length; i++) {
            ARGB[i] = RenderSettings.get().backgroundColor;
        }

        move (0.5, g);
        turn (2);
        move (0.2, g);

        rgb_image.setRGB(0, 0, width, height, ARGB, 0, width);
        g.drawImage(rgb_image, 0, 0, null);
    }

    public void move (double length, Graphics g) {
        double tempX = xPos;
        double tempY = yPos;
        xPos += Math.cos(angle) * length;
        yPos += Math.sin(angle) * length;
        Painter.drawLine(tempX, tempY, xPos, yPos, renderSettings.get().foregroundColor, 0, g)
    }

    public void turn (double ang) {
        angle += ang;
        if (angle >= Math.PI * 2) {
            angle -= Math.PI * 2;
        } else if (angle <= Math.PI * -2) {
            angle += Math.PI * 2;
        }
    }
}
