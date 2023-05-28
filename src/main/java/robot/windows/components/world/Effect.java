package robot.windows.components.world;

import robot.windows.handlers.ConfigHandler;

public class Effect {

    public int curDamageMultiply;
    public int damageMultiply;
    public int curSpeedBoost;
    public int speedBoost;
    public int curEnemySlowdown;
    public int enemySlowdown;
    public long curElapsedTimeEffect;
    public int cooldown;

    public Effect() {
        cooldown = ConfigHandler.getInt("model", "drop.cooldown");
        damageMultiply = ConfigHandler.getInt("model", "drop.damage.multiply");
        speedBoost = ConfigHandler.getInt("model", "drop.speed.boost");
        enemySlowdown = ConfigHandler.getInt("model", "drop.enemy.slowdown");
        reset();
    }

    private void reset() {
        curDamageMultiply = 1;
        curSpeedBoost = 1;
        curEnemySlowdown = 1;
    }

    public void handleEffectStatus() {
        long cur = System.currentTimeMillis();
        if (cur - curElapsedTimeEffect >= cooldown * 1000L) {
            reset();
            curElapsedTimeEffect = cur;
        }
    }

    public void startEffect(WorldObjectType type) {
        curElapsedTimeEffect = System.currentTimeMillis();
        reset();
        switch (type) {
            case DAMAGE_INCREASE -> curDamageMultiply = damageMultiply;
            case SLOWDOWN -> curEnemySlowdown = enemySlowdown;
            case SPEED_BOOST -> curSpeedBoost = speedBoost;
        }
    }
}
