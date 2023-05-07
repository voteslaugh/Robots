package robot.windows.game.world;

import java.awt.*;

public class Character {
    private Point position;
    private volatile double direction;

    public Character(Point position, double direction) {
        this.position = position;
        this.direction = direction;
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
