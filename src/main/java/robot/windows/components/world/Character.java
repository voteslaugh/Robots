package robot.windows.components.world;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Character {
    protected Point position;
    protected final int hitBoxRadius;
    protected final int maxHealthPoints;
    protected int healthPoints;
    protected double velocity;

    public Character(Point position, int hitBoxRadius, int healthPoints) {
        this.position = position;
        this.hitBoxRadius = hitBoxRadius;
        this.healthPoints = healthPoints;
        this.maxHealthPoints = healthPoints;
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

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
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

}