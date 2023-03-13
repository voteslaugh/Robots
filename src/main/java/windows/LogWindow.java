package windows;

import log.Logger;
import logic.LogVisualizer;
import windows.gui.InternalFrame;


public class LogWindow extends InternalFrame {
    LogVisualizer logVisualizer = new LogVisualizer(Logger.getDefaultLogSource());

    public LogWindow() {
        super("Протокол работы", 300, 800, 10, 10, false, false);
        addPanel(logVisualizer);
    }

    @Override
    public void doDefaultCloseAction() {
        logVisualizer.getLogSource().unregisterListener(logVisualizer);
        super.doDefaultCloseAction();
    }
}
