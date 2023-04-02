package robot.windows;

import robot.windows.log.Logger;
import robot.windows.log.LogVisualizer;
import robot.windows.gui.InternalFrame;

import java.util.ResourceBundle;


public class LogWindow extends InternalFrame {
    LogVisualizer logVisualizer = new LogVisualizer(Logger.getDefaultLogSource());

    public LogWindow(ResourceBundle bundle) {
        super(bundle.getString("logWindow"), 200, 400, 0, 0, false, false);
        addPanel(logVisualizer);
        pack();
    }

    @Override
    public void doDefaultCloseAction() {
        logVisualizer.unregister();
        super.doDefaultCloseAction();
    }
}
