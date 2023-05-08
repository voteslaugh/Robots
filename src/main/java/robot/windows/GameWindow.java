package robot.windows;

import robot.windows.game.GameController;
import robot.windows.gui.InternalFrame;

import java.awt.*;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class GameWindow extends InternalFrame {
    public GameWindow(ResourceBundle locale, Preferences preferences) {
        super(locale.getString("gameWindow"), locale, preferences, 900, 900, 500, 20, true, true);
        setName("gameWindow");
        setPreferredSize(new Dimension(900, 900));
        addPanel(new GameController());
        pack();
    }

}