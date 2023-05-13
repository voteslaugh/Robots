package robot.windows;

import robot.windows.components.gui.InternalFrame;
import robot.windows.controllers.LocationController;

import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class PlayerLocationWindow extends InternalFrame {
    public LocationController locationController;
    public PlayerLocationWindow(ResourceBundle locale, Preferences preferences) {
        super(locale.getString("playerLocationWindow"), locale, preferences, 200, 100, 1700, 20, true, true);
        setName("playerLocationWindow");
        locationController = new LocationController();
        add(locationController.locationView);
        pack();
    }
}
