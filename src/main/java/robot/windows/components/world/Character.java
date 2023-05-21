package robot.windows.components.world;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Character {
    private Point position;
    private final int hitBoxRadius;
    private final int maxHealthPoints;
    private int healthPoints;
    protected final double velocity;

    public Character(Point position, int hitBoxRadius, int healthPoints) {
        this.position = position;
        this.hitBoxRadius = hitBoxRadius;
        this.healthPoints = healthPoints;
        this.maxHealthPoints = healthPoints;
        this.velocity = computeVelocity(hitBoxRadius);
    }

    public Character(Point position, double velocity, int hitBoxRadius, int healthPoints) {
        this.position = position;
        this.hitBoxRadius = hitBoxRadius;
        this.healthPoints = healthPoints;
        this.maxHealthPoints = healthPoints;
        this.velocity = velocity;
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

    public double getVelocity() {
        return velocity;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public boolean reduceHPAndCheckDeath(int damage) {
        healthPoints -= damage;
        return healthPoints <= 0;
    }

    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }

    private double computeVelocity(int hitBoxRadius) {
        if (hitBoxRadius <= 50)
            return 4;
        else if (hitBoxRadius <= 100)
            return 3;
        else
            return 2;

    }

}