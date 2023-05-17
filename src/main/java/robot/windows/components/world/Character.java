package robot.windows.components.world;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Character {
    private Point position;
    private volatile double direction;
    private final int hitBoxRadius;
    public int healthPoints;

    public Character(Point position, double direction, int hitBoxRadius, int healthPoints) {
        this.position = position;
        this.direction = direction;
        this.hitBoxRadius = hitBoxRadius;
        this.healthPoints = healthPoints;
    }

    public Shape getHitBox() {
        double offset = (double) hitBoxRadius / 2;
        return new Rectangle2D.Double(position.x - offset, position.y - offset, hitBoxRadius, hitBoxRadius);
    }

    public Integer getHitBoxRadius() {
        return hitBoxRadius;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public double getDirection() {
        return direction;
    }
}
