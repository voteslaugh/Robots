package robot.windows.components.world;

import robot.windows.handlers.ConfigHandler;
import robot.windows.handlers.RandomHandler;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Drop implements Hitboxable{
    private final Point position;
    private final int hitBoxRadius = ConfigHandler.getInt("model", "drop.hitBoxRadius");
    WorldObjectType type;

    public Drop(Point position) {
        this.position = position;
        computeType();

    }

    @Override
    public Shape getHitBox() {
        double offset = (double) hitBoxRadius / 2;
        return new Rectangle2D.Double(position.x - offset, position.y - offset, hitBoxRadius, hitBoxRadius);
    }

    @Override
    public int getHitBoxRadius() {
        return hitBoxRadius;
    }

    private void computeType() {
        switch (RandomHandler.getRandomInRange(0, 4)) {
            case 0 -> type = WorldObjectType.HEAL;
            case 1 -> type = WorldObjectType.SLOWDOWN;
            case 2 -> type = WorldObjectType.DAMAGE_INCREASE;
            case 3 -> type = WorldObjectType.SPEED_BOOST;
        }
    }

    public Point getPosition() {
        return position;
    }

    public WorldObjectType getType() {
        return type;
    }
}
