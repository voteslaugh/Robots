package robot.windows.game;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Visualizer {

    private static void fillCircle(Graphics g, int centerX, int centerY, int diameter) {
        g.fillArc(centerX - diameter / 2, centerY - diameter / 2, diameter, diameter, 0, 360);
    }

    private static void drawCircle(Graphics g, int centerX, int centerY, int diameter) {
        g.drawArc(centerX - diameter / 2, centerY - diameter / 2, diameter, diameter, 0, 360);
    }

    public void drawEnemy(Graphics2D g, Point position, double direction) {
        AffineTransform t = AffineTransform.getRotateInstance(direction, position.x, position.y);
        g.setTransform(t);
        g.setColor(Color.RED);
        fillCircle(g, position.x, position.y, 30);
        g.setColor(Color.BLACK);
        drawCircle(g, position.x, position.y, 30);
        g.setColor(Color.WHITE);
        fillCircle(g, position.x + 10, position.y, 5);
        g.setColor(Color.BLACK);
        drawCircle(g, position.x + 10, position.y, 5);
    }


    public void drawPlayer(Graphics2D g, Point position) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillCircle(g, position.x, position.y, 5);
        g.setColor(Color.BLACK);
        drawCircle(g, position.x, position.y, 5);
    }

}
