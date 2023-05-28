package robot.windows.components.world;

import java.awt.*;

public class Enemy extends Character{
    public Enemy(Point position, int hitBoxRadius, int healthPoints) {
        super(position, hitBoxRadius, healthPoints);
        setUpParams();
    }

    private void setUpParams() {
        if (hitBoxRadius <= 50) {
            velocity = 4;
            type = WorldObjectType.SMALL_ENEMY;
        }
        else if (hitBoxRadius <= 100) {
            velocity = 3;
            type = WorldObjectType.MEDIUM_ENEMY;
        }
        else {
            velocity = 2;
            type = WorldObjectType.BIG_ENEMY;
        }
    }
}
