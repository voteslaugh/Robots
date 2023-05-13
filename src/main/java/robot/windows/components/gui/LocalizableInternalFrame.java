package robot.windows.components.gui;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.util.ResourceBundle;

public abstract class LocalizableInternalFrame extends JInternalFrame {

    protected InternalFrameAdapter closingListener;

    public LocalizableInternalFrame(String title, boolean resizable, boolean b, boolean maximizable, boolean b1) {
        super(title, resizable, b, maximizable, b1);
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
                        LocalizableInternalFrame.this,
                        bundle.getString("closing.message"),
                        bundle.getString("closing.title"),
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (option == JOptionPane.YES_OPTION)
                    dispose();
            }
        };
    }

    public void changeClosingListenerLocale(ResourceBundle bundle) {
        replaceClosingListener(getClosingListenerByBundle(bundle));
    }

}
