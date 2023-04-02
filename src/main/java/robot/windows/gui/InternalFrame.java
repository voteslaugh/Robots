package robot.windows.gui;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import java.awt.*;
import java.util.ResourceBundle;

public class InternalFrame extends JInternalFrame {

    public InternalFrame(String title, int width, int height, int x, int y, boolean resizable, boolean maximizable) {
        super(title, resizable, true, maximizable, true);
        setSize(width, height);
        setLocation(x, y);
        setMinimumSize(getSize());
        setVisible(true);
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                int option = JOptionPane.showInternalConfirmDialog(
                        InternalFrame.this,
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

    private void addListener(ResourceBundle bundle) {
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                int option = JOptionPane.showInternalConfirmDialog(
                        InternalFrame.this,
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

    private void removeListeners() {
        InternalFrameListener[] listeners = getInternalFrameListeners();

        for (InternalFrameListener ignored : listeners) {
            removeInternalFrameListener(ignored);
        }
    }

    public void changeLocale(ResourceBundle bundle) {
        removeListeners();
        addListener(bundle);
    }

    public void addPanel(JPanel logic) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(logic, BorderLayout.CENTER);
        getContentPane().add(panel);
    }
}
