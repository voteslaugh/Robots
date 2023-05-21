package robot.windows.components.world;

public class Weapon {
    private final int damage;
    private final double reloadTime;
    private long elapsedTimeAfterShoot;

    public Weapon(int damage, double reloadTime) {
        this.damage = damage;
        this.reloadTime = reloadTime;
        elapsedTimeAfterShoot = System.currentTimeMillis();
    }

    public boolean isReady() {
        long curr = System.currentTimeMillis();
        if (curr - elapsedTimeAfterShoot >= reloadTime * 1000) {
            elapsedTimeAfterShoot = curr;
            return true;
        }
        return false;
    }

    public int getDamage() {
        return damage;
    }
}
