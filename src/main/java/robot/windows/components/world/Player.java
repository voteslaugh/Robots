package robot.windows.components.world;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Player extends Character{
    private final List<Weapon> weapons;
    private Weapon currentWeapon;

    public Player(Point position, double velocity, int hitBoxRadius, int healthPoints, Weapon... weapons) {
        super(position, velocity, hitBoxRadius, healthPoints);
        this.weapons = Arrays.stream(weapons).toList();
        type = WorldObjectType.PLAYER;
    }

    public Weapon getWeapon() {
        return currentWeapon;
    }

    public void setWeapon(int index) {
        currentWeapon = weapons.get(index);
    }
}
