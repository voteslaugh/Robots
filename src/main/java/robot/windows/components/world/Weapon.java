package robot.windows.components.world;

public class Weapon {
    private boolean isReady;

    public Weapon() {
        this.isReady = true;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }
}
