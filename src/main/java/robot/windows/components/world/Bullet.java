package robot.windows.components.world;

import robot.windows.models.GameModel;

import java.awt.*;

public class Bullet {
    private Point position;
    private final double direction;
    private final int hitBoxRadius;

    public Bullet(Point start, Point destination) {
        this.position = start;
        this.direction = GameModel.angleBetweenPoints(start, destination);
        this.hitBoxRadius = 1;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public double getDirection() {
        return direction;
    }

    public int getHitBoxRadius() {
        return hitBoxRadius;
    }
}
