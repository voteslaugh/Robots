package robot.windows.game;

import robot.windows.game.world.Bullet;
import robot.windows.game.world.Character;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Visualizer {

    private static void fillCircle(Graphics g, int centerX, int centerY, int diameter) {
        g.fillArc(centerX - diameter / 2, centerY - diameter / 2, diameter, diameter, 0, 360);
    }

    private static void drawCircle(Graphics g, int centerX, int centerY, int diameter) {
        g.drawArc(centerX - diameter / 2, centerY - diameter / 2, diameter, diameter, 0, 360);
    }

    public void drawEnemies(Graphics2D g, HashSet<Character> enemies) {
        for (Character enemy: enemies) {
            int posX = enemy.getPosition().x;
            int posY = enemy.getPosition().y;
            AffineTransform t = AffineTransform.getRotateInstance(enemy.getDirection(), posX, posY);
            g.setTransform(t);
            g.setColor(Color.RED);
            fillCircle(g, posX, posY, 30);
            g.setColor(Color.BLACK);
            drawCircle(g, posX, posY, 30);
            g.setColor(Color.WHITE);
            fillCircle(g, posX + 10, posY, 5);
            g.setColor(Color.BLACK);
            drawCircle(g, posX + 10, posY, 5);
        }
    }


    public void drawPlayer(Graphics2D g, Point position) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillCircle(g, position.x, position.y, 5);
        g.setColor(Color.BLACK);
        drawCircle(g, position.x, position.y, 5);
    }

    public void drawObstacles(Graphics2D g, HashSet<Shape> obstacles) {
        for (Shape obstacle: obstacles) {
            g.draw((obstacle));
            g.setColor(Color.GRAY);
            g.fill(obstacle);
        }
    }

    public void drawBullets(Graphics2D g, Set<Bullet> bullets) {
        for (Bullet bullet: bullets) {
            int posX = bullet.getPosition().x;
            int posY = bullet.getPosition().y;
            AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
            g.setTransform(t);
            g.setColor(Color.GREEN);
            fillCircle(g, posX, posY, 5);
            g.setColor(Color.BLACK);
            drawCircle(g, posX, posY, 5);
        }
    }

}
