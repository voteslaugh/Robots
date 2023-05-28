package robot.windows.handlers;

import robot.windows.components.world.WorldObjectType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class ImageHandler {

    ConcurrentHashMap<WorldObjectType, BufferedImage> typeToImage;
    ConfigHandler configHandler = new ConfigHandler();

    public ImageHandler() {
        typeToImage = new ConcurrentHashMap<>();
        try {
            loadImage(WorldObjectType.SMALL, "path.enemy.small");
            loadImage(WorldObjectType.MEDIUM, "path.enemy.medium");
            loadImage(WorldObjectType.BIG, "path.enemy.big");
            loadImage(WorldObjectType.PLAYER, "path.player");
            loadImage(WorldObjectType.BULLET, "path.bullet");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadImage(WorldObjectType type, String configKey) throws IOException {
        typeToImage.put(type, ImageIO.read(new File(configHandler.getString("view", configKey))));
    }

    public BufferedImage getImage(WorldObjectType type) {
        return typeToImage.get(type);
    }
}
