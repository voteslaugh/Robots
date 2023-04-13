package robot.windows.gui;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

public abstract class LocalizableFrame extends JFrame {

    protected WindowAdapter closingListener;

    public WindowAdapter getClosingListenerByBundle(ResourceBundle bundle) {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                int option = JOptionPane.showConfirmDialog(
                        LocalizableFrame.this,
                        bundle.getString("closing.message"),
                        bundle.getString("closing.title"),
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

    public void changeClosingListenerLocale(ResourceBundle bundle) {
        replaceClosingListener(getClosingListenerByBundle(bundle));
    }

}
