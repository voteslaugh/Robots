package robot.windows.components.gui;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

public class MenuBar extends JMenuBar {

    public MenuBar(ResourceBundle localeBundle) {
        Menu lookAndFeel = new Menu(localeBundle.getString("lookAndFeel"), KeyEvent.VK_V, "Managing the application display mode");
        MenuItem system = new MenuItem(localeBundle.getString("system"), KeyEvent.VK_S, UIManager.getSystemLookAndFeelClassName(), null);
        MenuItem crossplatform = new MenuItem(localeBundle.getString("crossplatform"), KeyEvent.VK_S, UIManager.getCrossPlatformLookAndFeelClassName(), null);
        lookAndFeel.addMenuItems(system, crossplatform);

        Menu logs = new Menu(localeBundle.getString("logs"), KeyEvent.VK_T, "Test commands");
        MenuItem logMessage = new MenuItem(localeBundle.getString("logMessage"), KeyEvent.VK_S, null, "New line");
        logs.addMenuItems(logMessage);

        addMenus(lookAndFeel, logs);
    }

    public void addMenus(Menu... menus) {
        for(Menu menu: menus)
            add(menu);
    }
}
