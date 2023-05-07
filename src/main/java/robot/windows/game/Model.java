package robot.windows.game;

import robot.windows.game.world.Character;

import java.awt.*;

public class Model {

    Character player;
    Character enemy;
    final double ENEMY_VELOCITY = 0.05;
    final double PLAYER_VELOCITY = 2;

    public Model() {
        player = new Character(new Point(150, 100), 0);
        enemy = new Character(new Point(300, 300), 0);
    }

    protected void onModelUpdateEvent() {
        moveRobot();
    }

    private synchronized void moveRobot() {
        double angleToTarget = angleBetweenPoints(enemy.getPosition(), player.getPosition());
        double distanceToTarget = enemy.getPosition().distance(player.getPosition());

        double velocityX = ENEMY_VELOCITY * Math.cos(angleToTarget);
        double velocityY = ENEMY_VELOCITY * Math.sin(angleToTarget);

        double signX = Math.signum(velocityX);
        double signY = Math.signum(velocityY);

        double absVelocityX = Math.abs(velocityX);
        double absVelocityY = Math.abs(velocityY);

        double newX = enemy.getPosition().getX() + absVelocityX * distanceToTarget * signX;
        double newY = enemy.getPosition().getY() + absVelocityY * distanceToTarget * signY;

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
