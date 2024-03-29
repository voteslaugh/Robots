package robot.windows.log;

import javax.swing.*;
import java.awt.*;

public class LogVisualizer extends JPanel implements LogChangeListener {
    private LogWindowSource logSource;
    private TextArea logContent;

    public LogVisualizer(LogWindowSource logSource) {
        this.logSource = logSource;
        this.logSource.registerListener(this);
        logContent = new TextArea();
        logContent.setPreferredSize(new Dimension(200, 400));
        updateLogContent();
        add(logContent);
        Logger.debug("The protocol is working");
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

    public LogWindowSource getLogSource() {
        return logSource;
    }
    @Override
    public void onLogChanged() {
        EventQueue.invokeLater(this::updateLogContent);
    }

    public void unregister(){
        this.getLogSource().unregisterListener(this);
    }
}
