package robot.windows.components.world;

import java.awt.*;

public class Enemy extends Character{
    public Enemy(Point position, int hitBoxRadius, int healthPoints) {
        super(position, hitBoxRadius, healthPoints);
        setUpVelocity();
    }

    private void setUpVelocity() {
        if (hitBoxRadius <= 50)
            velocity = 4;
        else if (hitBoxRadius <= 100)
            velocity = 3;
        else
            velocity = 2;

    }
}
