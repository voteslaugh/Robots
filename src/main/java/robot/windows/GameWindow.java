package robot.windows;

import robot.logic.GameVisualizer;
import robot.windows.gui.InternalFrame;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.util.ResourceBundle;

public class GameWindow extends InternalFrame {
    public GameWindow(ResourceBundle bundle) {
        super(bundle.getString("gameWindow"), 960, 540, 400, 200, true, true);
        setPreferredSize(new Dimension(960, 540));
        addPanel(new GameVisualizer());
        pack();
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                int option = JOptionPane.showInternalConfirmDialog(
                        GameWindow.this,
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
}
