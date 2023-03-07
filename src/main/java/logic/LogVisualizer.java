package logic;

import log.LogChangeListener;
import log.LogEntry;
import log.LogWindowSource;
import log.Logger;

import javax.swing.*;
import java.awt.*;

public class LogVisualizer extends JPanel implements LogChangeListener {
    private LogWindowSource logSource;
    private TextArea logContent;

    public LogVisualizer(LogWindowSource logSource) {
        this.logSource = logSource;
        this.logSource.registerListener(this);
        logContent = new TextArea();
        logContent.setPreferredSize(new Dimension(250, 900));
        logContent.setMaximumSize(new Dimension(250, 900));
        updateLogContent();
        add(logContent);
        Logger.debug("Протокол работает");
    }
    private void updateLogContent()
    {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : logSource.all())
        {
            content.append(entry.getMessage()).append("\n");
        }
        logContent.setText(content.toString());
        logContent.invalidate();
    }
    @Override
    public void onLogChanged() {
        EventQueue.invokeLater(this::updateLogContent);
    }
}
