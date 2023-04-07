package robot.windows.gui;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.util.ResourceBundle;

public class InternalFrame extends JInternalFrame {

    private InternalFrameAdapter closingListener;
    public InternalFrame(String title, int width, int height, int x, int y, boolean resizable, boolean maximizable) {
        super(title, resizable, true, maximizable, true);
        setSize(width, height);
        setLocation(x, y);
        setMinimumSize(getSize());
        setVisible(true);
        closingListener = new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                int option = JOptionPane.showInternalConfirmDialog(
                        InternalFrame.this,
                        "Do you really want to close this window?",
                        "Confirm closing",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (option == JOptionPane.YES_OPTION)
                    dispose();
            }
        };
        addInternalFrameListener(closingListener);
    }

    public void addPanel(JPanel logic) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(logic, BorderLayout.CENTER);
        getContentPane().add(panel);
    }

    public void replaceClosingListener(InternalFrameAdapter newClosingListener) {
        this.removeInternalFrameListener(closingListener);
        closingListener = newClosingListener;
        this.addInternalFrameListener(newClosingListener);
    }

    public InternalFrameAdapter getClosingListenerByBundle(ResourceBundle bundle) {
        return new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                int option = JOptionPane.showInternalConfirmDialog(
                        InternalFrame.this,
                        bundle.getString("dialog.message"),
                        bundle.getString("dialog.title"),
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (option == JOptionPane.YES_OPTION)
                    dispose();
            }
        };
    }

    public void changeLocale(ResourceBundle bundle) {
        replaceClosingListener(getClosingListenerByBundle(bundle));
    }

}
