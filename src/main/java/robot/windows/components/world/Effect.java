package robot.windows.components.world;

public class Effect {

    public int damageMultiply;
    public int speedBoost;
    public int enemySlowdown;
    public long elapsedTimeEffect;

    public Effect() {
        reset();
    }

    private void reset() {
        damageMultiply = 1;
        speedBoost = 1;
        enemySlowdown = 1;
    }

    public void handleEffectStatus() {
        long cur = System.currentTimeMillis();
        if (cur - elapsedTimeEffect >= 3000) {
            reset();
            elapsedTimeEffect = cur;
        }
    }

    public void startEffect(WorldObjectType type) {
        elapsedTimeEffect = System.currentTimeMillis();
        reset();
        switch (type) {
            case DAMAGE_INCREASE -> damageMultiply = 2;
            case SLOWDOWN -> enemySlowdown = 2;
            case SPEED_BOOST -> speedBoost = 2;
        }
    }
}
