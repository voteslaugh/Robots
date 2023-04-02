package robot.windows;

import robot.windows.game.GameVisualizer;
import robot.windows.gui.InternalFrame;

import java.awt.*;
import java.util.ResourceBundle;

public class GameWindow extends InternalFrame {
    public GameWindow(ResourceBundle bundle) {
        super(bundle.getString("gameWindow"), 960, 540, 400, 200, false, false);
        setPreferredSize(new Dimension(960, 540));
        addPanel(new GameVisualizer());
        pack();

    }
}
