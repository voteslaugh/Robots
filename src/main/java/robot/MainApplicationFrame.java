package robot;

import robot.windows.GameWindow;
import robot.windows.LogWindow;
import robot.windows.gui.Frame;
import robot.windows.gui.MenuItem;
import robot.windows.gui.MenuBar;
import robot.windows.gui.Menu;
import robot.windows.state.SerializedHandler;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ResourceBundle;

import javax.swing.*;

public class MainApplicationFrame extends Frame {

    GameWindow gameWindow;
    LogWindow logWindow;
    MenuBar menuBar;
    ResourceBundle bundle;
    SerializedHandler serializedHandler;

    public MainApplicationFrame() {
        setContentPane(new JDesktopPane());
        bundle = ResourceBundle.getBundle("en_locale");
        gameWindow = new GameWindow(bundle);
        logWindow = new LogWindow(bundle);
        addFrames(gameWindow, logWindow);
        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }

    private MenuBar generateMenuBar() {
        menuBar = new MenuBar();

        Menu lookAndFeel = new Menu(bundle.getString("lookAndFeel"), KeyEvent.VK_V, "Managing the application display mode");
        MenuItem system = new MenuItem(bundle.getString("system"), KeyEvent.VK_S, UIManager.getSystemLookAndFeelClassName(), null);
        MenuItem crossplatform = new MenuItem(bundle.getString("crossplatform"), KeyEvent.VK_S, UIManager.getCrossPlatformLookAndFeelClassName(), null);
        lookAndFeel.addMenuItems(system, crossplatform);

        Menu logs = new Menu(bundle.getString("logs"), KeyEvent.VK_T, "Test commands");
        MenuItem logMessage = new MenuItem(bundle.getString("logMessage"), KeyEvent.VK_S, null, "New line");
        logs.addMenuItems(logMessage);


        Menu locale = new Menu(bundle.getString("locale"), KeyEvent.VK_C, "Localisation");
        getLocales(locale);

        menuBar.addMenus(lookAndFeel, logs, locale);
        return menuBar;
    }

    private void updateLocale(String newLocale) {
        bundle = ResourceBundle.getBundle(newLocale);
    }

    private void getLocales(Menu menu) {
        File directory = new File("src/main/resources");
        String[] files = directory.list((dir, name) -> name.endsWith(".properties"));

        assert files != null;
        for (String fileName : files) {
            String localeName = fileName.replace(".properties", "");
            MenuItem locale = new MenuItem(bundle.getString(localeName), KeyEvent.VK_S, null, "Changed to %s".formatted(localeName));
            locale.addActionListener(e -> SwingUtilities.invokeLater(() -> {
                menuBar.removeAll();
                updateLocale(localeName);
                resetUI();
            }));
            menu.addMenuItems(locale);
        }
    }

    private void resetUI() {
        generateMenuBar();
        setJMenuBar(menuBar);
        gameWindow.setTitle(bundle.getString("gameWindow"));
        logWindow.setTitle(bundle.getString("logWindow"));
        gameWindow.changeLocale(bundle);
        logWindow.changeLocale(bundle);
        this.changeLocale(bundle);
        revalidate();
        repaint();
    }

}
