package robot.windows.handlers;

import robot.windows.components.world.WorldObjectType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class ImageHandler {

    ConcurrentHashMap<WorldObjectType, BufferedImage> typeToImage;

    public ImageHandler() {
        typeToImage = new ConcurrentHashMap<>();
        try {
            loadImage(WorldObjectType.SMALL_ENEMY, "path.enemy.small");
            loadImage(WorldObjectType.MEDIUM_ENEMY, "path.enemy.medium");
            loadImage(WorldObjectType.BIG_ENEMY, "path.enemy.big");
            loadImage(WorldObjectType.PLAYER, "path.player");
            loadImage(WorldObjectType.BULLET, "path.bullet");
            loadImage(WorldObjectType.HEAL, "path.heal");
            loadImage(WorldObjectType.DAMAGE_INCREASE, "path.damage.increase");
            loadImage(WorldObjectType.SPEED_BOOST, "path.speed.boost");
            loadImage(WorldObjectType.SLOWDOWN, "path.slowdown");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadImage(WorldObjectType type, String configKey) throws IOException {
        typeToImage.put(type, ImageIO.read(new File(ConfigHandler.getString("view", configKey))));
    }

    public BufferedImage getImage(WorldObjectType type) {
        return typeToImage.get(type);
    }
}
