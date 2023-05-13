package robot.windows.components;

import robot.windows.models.GameModel;

import java.awt.*;

public class Bullet {
    private Point position;
    private final double direction;

    public Bullet(Point start, Point destination) {
        this.position = start;
        this.direction = GameModel.angleBetweenPoints(start, destination);
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
}
