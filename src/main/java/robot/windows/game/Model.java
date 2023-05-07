package robot.windows.game;

import robot.windows.game.world.Character;

import java.awt.*;

public class Model {

    Character player;
    Character enemy;
    final double ENEMY_VELOCITY = 1;
    final double PLAYER_VELOCITY = 1;

    public Model() {
        player = new Character(new Point(150, 100), 0);
        enemy = new Character(new Point(300, 300), 0);
    }

    protected void onModelUpdateEvent() {
        moveRobot();
    }

    private synchronized void moveRobot() {
        double angleToTarget = angleBetweenPoints(enemy.getPosition(), player.getPosition());
        double angle = asNormalizedRadians(angleToTarget);
        enemy.setDirection(angle);

        double newX = enemy.getPosition().getX() + ENEMY_VELOCITY * Math.cos(angle);
        double newY = enemy.getPosition().getY() + ENEMY_VELOCITY * Math.sin(angle);

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
