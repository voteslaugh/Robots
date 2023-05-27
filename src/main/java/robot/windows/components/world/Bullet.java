package robot.windows.components.world;

import robot.windows.models.GameModel;

import java.awt.*;

public class Bullet {
    private Point position;
    private final double direction;
    private final int hitBoxRadius;

    private final int damage;

    public Bullet(Point start, Point destination, int damage) {
        this.position = start;
        this.direction = GameModel.angleBetweenPoints(start, destination);
        this.hitBoxRadius = 1;
        this.damage = damage;
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

    public int getDamage() {
        return damage;
    }
}
