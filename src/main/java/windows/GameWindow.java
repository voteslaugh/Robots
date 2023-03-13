package windows;

import logic.GameVisualizer;
import windows.gui.InternalFrame;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class GameWindow extends InternalFrame {
    public GameWindow() {
        super("Игровое поле", 400, 400, 400, 400, true, true);
        addPanel(new GameVisualizer());
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                int option = JOptionPane.showInternalConfirmDialog(
                        GameWindow.this,
                        "Вы действительно хотите закрыть это окно?",
                        "Подтвердить закрытие",
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
