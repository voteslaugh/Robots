package windows;

import logic.GameVisualizer;
import windows.gui.InternalFrame;

public class GameWindow extends InternalFrame {
    public GameWindow() {
        super("Игровое поле", 400, 400, 400, 400, true, true);
        addPanel(new GameVisualizer());
    }
}
