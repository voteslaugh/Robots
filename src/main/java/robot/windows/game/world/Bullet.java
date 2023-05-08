package robot.windows.game.world;

import robot.windows.game.Model;

import java.awt.*;

public class Bullet {
    private Point position;
    private final Point start;
    private final double direction;

    public Bullet(Point start, Point destination) {
        this.position = start;
        this.start = start;
        this.direction = Model.angleBetweenPoints(start, destination);
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public Point getStart() {
        return start;
    }

    public double getDirection() {
        return direction;
    }
}
