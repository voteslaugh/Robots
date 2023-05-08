package robot.windows.game;

import robot.windows.game.world.Character;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Model {

    Character player;
    Character enemy;
    ArrayList<Shape> obstacles;
    final double ENEMY_VELOCITY = 3;
    final double PLAYER_VELOCITY = 4;

    public Model() {
        player = new Character(new Point(70, 150), 0);
        enemy = new Character(new Point(880, 150), 0);
        obstacles = new ArrayList<>();
        setUpObstacles();
    }

    public void setUpObstacles() {
        obstacles.add(new Rectangle2D.Double(0, 0, 900, 10));
        obstacles.add(new Rectangle2D.Double(0, 0, 10, 900));
        obstacles.add(new Rectangle2D.Double(0, 890, 900, 10));
        obstacles.add(new Rectangle2D.Double(890, 0, 10, 900));

        obstacles.add(new Rectangle2D.Double(150, 150, 50, 500));
        obstacles.add(new Rectangle2D.Double(150, 150, 500, 50));
        obstacles.add(new Rectangle2D.Double(600, 150, 50, 500));
        obstacles.add(new Rectangle2D.Double(300, 300, 50, 300));
        obstacles.add(new Rectangle2D.Double(450, 300, 50, 300));
        obstacles.add(new Rectangle2D.Double(300, 300, 200, 50));
        obstacles.add(new Rectangle2D.Double(300, 550, 200, 50));
    }

    protected void onModelUpdateEvent() {
        moveEnemy(enemy);
    }

    public boolean isCollision(double x, double y) {
        for (Shape obstacle : obstacles) {
            if (obstacle.contains(x, y)) {
                return true;
            }
        }
        return false;
    }

    public boolean isCollision(int x, int y) {
        return isCollision((double) x, (double) y);
    }

    private synchronized void moveEnemy(Character enemy) {
        if (enemy.getPosition().distance(player.getPosition()) < 2)
            return;
        double angleToTarget = angleBetweenPoints(enemy.getPosition(), player.getPosition());
        double velocityX = ENEMY_VELOCITY * Math.cos(angleToTarget);
        double velocityY = ENEMY_VELOCITY * Math.sin(angleToTarget);


        double newX = enemy.getPosition().getX() + velocityX;
        double newY = enemy.getPosition().getY() + velocityY;
        if (!isCollision(newX, newY))
            enemy.setPosition(new Point((int) newX, (int) newY));
    }


    private static double angleBetweenPoints(Point p1, Point p2) {
        double angle = Math.atan2(p2.y - p1.y, p2.x - p1.x);
        return asNormalizedRadians(angle);
    }

    private static double asNormalizedRadians(double angle) {
        angle %= 2 * Math.PI;
        if (angle < 0) {
            angle += 2 * Math.PI;
        }
        return angle;
    }
}
