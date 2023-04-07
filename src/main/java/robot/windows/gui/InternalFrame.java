package robot.windows.gui;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ResourceBundle;

public class InternalFrame extends LocalizableInternalFrame implements Serializable {

    public InternalFrame(String title, ResourceBundle bundle, int width, int height, int x, int y, boolean resizable, boolean maximizable) {
        super(title, resizable, true, maximizable, true);
        setSize(width, height);
        setLocation(x, y);
        setMinimumSize(getSize());
        setVisible(true);
        closingListener = getClosingListenerByBundle(bundle);
        addInternalFrameListener(closingListener);
    }

    public void addPanel(JPanel logic) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(logic, BorderLayout.CENTER);
        getContentPane().add(panel);
    }

}
