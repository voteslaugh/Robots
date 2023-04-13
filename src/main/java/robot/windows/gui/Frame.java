package robot.windows.gui;

import robot.MainApplicationFrame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.prefs.Preferences;

public class Frame extends LocalizableFrame {

    Preferences prefs;
    LinkedList <InternalFrame> internalFrames;
    public Frame() {
        prefs = Preferences.userNodeForPackage(MainApplicationFrame.class);
        internalFrames = new LinkedList<>();
        closingListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                int option = JOptionPane.showConfirmDialog(
                        Frame.this,
                        "Do you really want to close this window?",
                        "Confirm closing",
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
            }
        });
    }

    public void addFrames(InternalFrame... frames) {
        for (InternalFrame frame: frames) {
            add(frame);
            internalFrames.add(frame);
        }
    }

}
