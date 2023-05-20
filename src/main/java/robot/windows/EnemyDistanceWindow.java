package robot.windows;

import robot.windows.components.gui.InternalFrame;

import java.awt.*;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class EnemyDistanceWindow extends InternalFrame {
    public EnemyDistanceWindow(ResourceBundle locale, Preferences preferences) {
        super(locale.getString("enemiesDistanceWindow"), locale, preferences, 220, 100, 1700, 120, true, true);
        setPreferredSize(new Dimension(220, 150));
        setName("enemiesDistanceWindow");
        pack();
    }
}
