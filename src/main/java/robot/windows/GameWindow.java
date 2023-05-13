package robot.windows;

import robot.windows.components.Character;
import robot.windows.controllers.GameController;
import robot.windows.components.gui.InternalFrame;

import java.awt.*;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class GameWindow extends InternalFrame {
    public GameController gameController;
    public GameWindow(ResourceBundle locale, Preferences preferences) {
        super(locale.getString("gameWindow"), locale, preferences, 900, 900, 500, 20, true, true);
        setName("gameWindow");
        setPreferredSize(new Dimension(900, 900));
        gameController = new GameController();
        addPanel(gameController.getVisualizer());
        pack();
    }

    public Point getPlayerPosition() {
        return gameController.getModel().player.getPosition();
    }
}