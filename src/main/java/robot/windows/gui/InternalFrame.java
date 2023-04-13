package robot.windows.gui;

import robot.MainApplicationFrame;
import robot.windows.log.Logger;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class InternalFrame extends LocalizableInternalFrame {

    public InternalFrame(String title, ResourceBundle bundle, int width, int height, int x, int y, boolean resizable, boolean maximizable) {
        super(title, resizable, true, maximizable, true);
        setSize(width, height);
        setLocation(x, y);
        setMinimumSize(getSize());
        setVisible(true);
        closingListener = getClosingListenerByBundle(bundle);
        addInternalFrameListener(closingListener);
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                Preferences prefs = Preferences.userNodeForPackage(MainApplicationFrame.class);
                String name = e.getInternalFrame().getName();
                prefs.putInt(name + ".x", e.getInternalFrame().getX());
                prefs.putInt(name + ".y", e.getInternalFrame().getY());
                prefs.putInt(name + ".width", e.getInternalFrame().getWidth());
                prefs.putInt(name + ".height", e.getInternalFrame().getHeight());
                prefs.putBoolean(name + ".icon", e.getInternalFrame().isIcon());
                prefs.put("locale", bundle.getBaseBundleName());
            }
        });
    }

    public void addPanel(JPanel logic) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(logic, BorderLayout.CENTER);
        getContentPane().add(panel);
    }

    public void changeState(int x, int y, int width, int height, boolean icon) {
        setBounds(x, y, width, height);
        try {
            setIcon(icon);
        } catch (PropertyVetoException e) {
            Logger.error("Couldn't set icon for InternalFrame");
        }
    }

}
