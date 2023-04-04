package robot.windows.gui;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ResourceBundle;

public class Frame extends JFrame {

    public void addFrames(InternalFrame... frames) {
        for (InternalFrame frame: frames)
            add(frame);

        addListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(
                        Frame.this,
                        "Do you really want to close this window?",
                        "Confirm closing",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (option == JOptionPane.YES_OPTION)
                    dispose();
                else
                    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            }
        });
    }

    private void addListener(WindowAdapter adapter) {
        addWindowListener(adapter);
    }

    public void removeListeners() {
        WindowListener[] listeners = getWindowListeners();

        for (WindowListener listener : listeners) {
            removeWindowListener(listener);
        }
    }

    public void changeLocale(ResourceBundle bundle) {
        removeListeners();
        addListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(
                        Frame.this,
                        bundle.getString("dialog.message"),
                        bundle.getString("dialog.title"),
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (option == JOptionPane.YES_OPTION)
                    dispose();
                else
                    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            }
        });
    }
}
