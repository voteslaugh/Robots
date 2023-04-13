package robot.windows.gui;

import robot.MainApplicationFrame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class Frame extends LocalizableFrame {

    Preferences prefs;
    LinkedList <InternalFrame> internalFrames;
    protected ResourceBundle localeBundle;
    public Frame() {
        prefs = Preferences.userNodeForPackage(MainApplicationFrame.class);
        internalFrames = new LinkedList<>();
        localeBundle = ResourceBundle.getBundle(prefs.get("locale", "en_locale"));
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
                prefs.put("locale", localeBundle.getBaseBundleName());
            }
        });
    }

    public void restoreState() {
        for (InternalFrame internalFrame: internalFrames) {
            String name = internalFrame.getName();
            internalFrame.changeState(prefs.getInt(name + ".x", internalFrame.getX()),
                    prefs.getInt(name + ".y", internalFrame.getY()),
                    prefs.getInt(name + ".width", internalFrame.getWidth()),
                    prefs.getInt(name + ".height", internalFrame.getHeight()),
                    prefs.getBoolean(name + ".icon", internalFrame.isIcon()));
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
