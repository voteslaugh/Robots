package robot.windows;

import robot.windows.log.Logger;
import robot.windows.log.LogVisualizer;
import robot.windows.gui.InternalFrame;

import java.beans.PropertyVetoException;
import java.util.ResourceBundle;


public class LogWindow extends InternalFrame {
    LogVisualizer logVisualizer = new LogVisualizer(Logger.getDefaultLogSource());

    public LogWindow(ResourceBundle bundle) {
        super(bundle.getString("logWindow"), bundle,200, 400, 0, 0, true, true);
        setName("logWindow");
        addPanel(logVisualizer);
        pack();
    }

    public LogWindow(ResourceBundle bundle, int width, int height, int x, int y, boolean icon) {
        super(bundle.getString("logWindow"), bundle, width, height, x, y, true, true);
        addPanel(logVisualizer);
        pack();
        try {
            setIcon(icon);
        } catch (PropertyVetoException e) {
            Logger.error("Couldn't set icon for logWindow");
        }
    }
    @Override
    public void doDefaultCloseAction() {
        logVisualizer.unregister();
        super.doDefaultCloseAction();
    }
}
