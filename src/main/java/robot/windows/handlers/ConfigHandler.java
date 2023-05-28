package robot.windows.handlers;

import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;

public class ConfigHandler {
    File config = new File("config.ini");

    public String getString(String section, String key) {
        try {
            return new Ini(config).get(section).get(key, String.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer getInt(String section, String key) {
        try {
            return new Ini(config).get(section).get(key, Integer.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}