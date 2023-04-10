package robot.windows;

import robot.windows.game.GameVisualizer;
import robot.windows.gui.InternalFrame;
import robot.windows.log.Logger;

import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.Serializable;
import java.util.ResourceBundle;

public class GameWindow extends InternalFrame {
    public GameWindow(ResourceBundle bundle) {
        super(bundle.getString("gameWindow"), bundle, 960, 540, 400, 200, true, true);
        setPreferredSize(new Dimension(960, 540));
        addPanel(new GameVisualizer());
        pack();
    }

    public GameWindow(ResourceBundle bundle, int width, int height, int x, int y, boolean icon) {
        super(bundle.getString("gameWindow"), bundle, width, height, x, y, true, true);
        setPreferredSize(new Dimension(width, height));
        addPanel(new GameVisualizer());
        pack();
        try {
            setIcon(icon);
        } catch (PropertyVetoException e) {
            Logger.error("Couldn't set icon for gameWindow");
        }
    }
}
