package robot.windows.gui.logic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class WindowProperties {
    private Properties properties;

    public WindowProperties() {
        properties = new Properties();
    }

    public void load(String filename) throws IOException {
        try (FileInputStream fis = new FileInputStream(filename)) {
            properties.load(fis);
        }
    }

    public void save(String filename) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            properties.store(fos, "Window Properties");
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }
}
