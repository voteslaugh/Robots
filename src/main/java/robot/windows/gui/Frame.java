package robot.windows.gui;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class Frame extends LocalizableFrame {

    Preferences preferences;
    LinkedList <InternalFrame> internalFrames;
    protected ResourceBundle localeBundle;
    public Frame(ResourceBundle localeBundle, Preferences preferences) {
        internalFrames = new LinkedList<>();
        this.localeBundle = localeBundle;
        this.preferences = preferences;
        closingListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                int option = JOptionPane.showConfirmDialog(
                        Frame.this,
                        localeBundle.getString("opened.message"),
                        localeBundle.getString("opened.title"),
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (option == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        };
        addWindowListener(closingListener);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                for (InternalFrame internalFrame: internalFrames) {
                    internalFrame.dispose();
                }
                Frame.this.preferences.put("locale", localeBundle.getBaseBundleName());
            }
        });
    }

    public void restoreState() {
        for (InternalFrame internalFrame: internalFrames) {
            String name = internalFrame.getName();
            internalFrame.changeState(preferences.getInt(name + ".x", internalFrame.getX()),
                    preferences.getInt(name + ".y", internalFrame.getY()),
                    preferences.getInt(name + ".width", internalFrame.getWidth()),
                    preferences.getInt(name + ".height", internalFrame.getHeight()),
                    preferences.getBoolean(name + ".icon", internalFrame.isIcon()));
        }
    }

    protected void setLocale(String newLocale) {
        localeBundle = ResourceBundle.getBundle(newLocale);
    }

    public void addFrames(InternalFrame... frames) {
        for (InternalFrame frame: frames) {
            add(frame);
            internalFrames.add(frame);
        }
    }

}
