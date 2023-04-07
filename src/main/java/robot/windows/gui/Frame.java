package robot.windows.gui;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

public class Frame extends JFrame {

    WindowAdapter closingListener;

    public Frame() {

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
                if (option == JOptionPane.YES_OPTION)
                    dispose();
            }
        };
        addWindowListener(closingListener);
    }

    public void addFrames(InternalFrame... frames) {
        for (InternalFrame frame: frames)
            add(frame);
    }

    public WindowAdapter getClosingListenerByBundle(ResourceBundle bundle) {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                int option = JOptionPane.showConfirmDialog(
                        Frame.this,
                        bundle.getString("dialog.message"),
                        bundle.getString("dialog.title"),
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (option == JOptionPane.YES_OPTION)
                    dispose();
            }
        };
    }

    public void replaceClosingListener(WindowAdapter newClosingListener) {
        removeWindowListener(closingListener);
        closingListener = newClosingListener;
        addWindowListener(newClosingListener);
    }

    public void changeLocale(ResourceBundle bundle) {
        replaceClosingListener(getClosingListenerByBundle(bundle));
    }

}
