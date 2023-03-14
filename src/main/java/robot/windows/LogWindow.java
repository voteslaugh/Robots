package robot.windows;

import robot.log.Logger;
import robot.logic.LogVisualizer;
import robot.windows.gui.InternalFrame;


public class LogWindow extends InternalFrame {
    LogVisualizer logVisualizer = new LogVisualizer(Logger.getDefaultLogSource());

    public LogWindow() {
        super("Протокол работы", 200, 400, 0, 0, false, false);
        addPanel(logVisualizer);
        pack();
    }

    @Override
    public void doDefaultCloseAction() {
        logVisualizer.getLogSource().unregisterListener(logVisualizer);
        super.doDefaultCloseAction();
    }
}
