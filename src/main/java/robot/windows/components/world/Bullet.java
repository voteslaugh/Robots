package robot.windows.components.world;

import robot.windows.models.GameModel;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Bullet implements Hitboxable {
    private Point position;
    private final double direction;
    private final int hitBoxRadius;

    private final int damage;
    private final WorldObjectType type;

    public Bullet(Point start, Point destination, int damage) {
        this.position = start;
        this.direction = GameModel.angleBetweenPoints(start, destination);
        this.hitBoxRadius = 5;
        this.damage = damage;
        type = WorldObjectType.BULLET;
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
    public Shape getHitBox() {
        double offset = (double) hitBoxRadius / 2;
        return new Rectangle2D.Double(position.x - offset, position.y - offset, hitBoxRadius, hitBoxRadius);
    }

    public int getDamage() {
        return damage;
    }

    public WorldObjectType getType() {
        return type;
    }
}
