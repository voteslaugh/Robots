package robot.windows;

import robot.windows.log.Logger;
import robot.windows.log.LogVisualizer;
import robot.windows.gui.InternalFrame;

import java.util.ResourceBundle;
import java.util.prefs.Preferences;


public class LogWindow extends InternalFrame {
    LogVisualizer logVisualizer = new LogVisualizer(Logger.getDefaultLogSource());

    public LogWindow(ResourceBundle locale, Preferences preferences) {
        super(locale.getString("logWindow"), locale,preferences, 200, 400, 0, 0, true, true);
        setName("logWindow");
        addPanel(logVisualizer);
        pack();
    }

    @Override
    public void doDefaultCloseAction() {
        logVisualizer.unregister();
        super.doDefaultCloseAction();
    }
}
