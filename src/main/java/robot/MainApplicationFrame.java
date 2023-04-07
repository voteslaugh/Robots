package robot;

import robot.windows.GameWindow;
import robot.windows.LogWindow;
import robot.windows.gui.Frame;
import robot.windows.gui.MenuItem;
import robot.windows.gui.MenuBar;
import robot.windows.gui.Menu;

import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.swing.*;

public class MainApplicationFrame extends Frame implements Serializable {

    GameWindow gameWindow;
    LogWindow logWindow;
    MenuBar menuBar;
    static ResourceBundle localeBundle = ResourceBundle.getBundle("en_locale");
    Preferences prefs = Preferences.userNodeForPackage(MainApplicationFrame.class);

    public MainApplicationFrame() {
        setContentPane(new JDesktopPane());
        localeBundle = ResourceBundle.getBundle(prefs.get("locale", localeBundle.getBaseBundleName()));
        gameWindow = new GameWindow(localeBundle);
        logWindow = new LogWindow(localeBundle);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(
                        MainApplicationFrame.this,
                        localeBundle.getString("opened.message"),
                        localeBundle.getString("opened.title"),
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (option == JOptionPane.YES_OPTION) {
                    int x = prefs.getInt("gameWindowX", gameWindow.getX());
                    int y = prefs.getInt("gameWindowY", gameWindow.getY());
                    int width = prefs.getInt("gameWindowWidth", gameWindow.getWidth());
                    int height = prefs.getInt("gameWindowHeight", gameWindow.getHeight());
                    boolean icon = prefs.getBoolean("gameWindowIcon", gameWindow.isIcon());
                    gameWindow = new GameWindow(localeBundle, width, height, x, y, icon);
                    x = prefs.getInt("logWindowX", logWindow.getX());
                    y = prefs.getInt("logWindowY", logWindow.getY());
                    width = prefs.getInt("logWindowWidth", logWindow.getWidth());
                    height = prefs.getInt("logWindowHeight", logWindow.getHeight());
                    icon = prefs.getBoolean("logWindowIcon", logWindow.isIcon());
                    logWindow = new LogWindow(localeBundle, width, height, x, y, icon);
                }

                addFrames(gameWindow, logWindow);
                generateMenuBar();
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                pack();

            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                prefs.putInt("gameWindowX", gameWindow.getX());
                prefs.putInt("gameWindowY", gameWindow.getY());
                prefs.putInt("gameWindowWidth", gameWindow.getWidth());
                prefs.putInt("gameWindowHeight", gameWindow.getHeight());
                prefs.putBoolean("gameWindowIcon", gameWindow.isIcon());
                prefs.putInt("logWindowX", logWindow.getX());
                prefs.putInt("logWindowY", logWindow.getY());
                prefs.putInt("logWindowWidth", logWindow.getWidth());
                prefs.putInt("logWindowHeight", logWindow.getHeight());
                prefs.putBoolean("logWindowIcon", logWindow.isIcon());
                prefs.put("locale", localeBundle.getBaseBundleName());
            }
        });
    }

    private void generateMenuBar() {
        menuBar = new MenuBar();

        Menu lookAndFeel = new Menu(localeBundle.getString("lookAndFeel"), KeyEvent.VK_V, "Managing the application display mode");
        MenuItem system = new MenuItem(localeBundle.getString("system"), KeyEvent.VK_S, UIManager.getSystemLookAndFeelClassName(), null);
        MenuItem crossplatform = new MenuItem(localeBundle.getString("crossplatform"), KeyEvent.VK_S, UIManager.getCrossPlatformLookAndFeelClassName(), null);
        lookAndFeel.addMenuItems(system, crossplatform);

        Menu logs = new Menu(localeBundle.getString("logs"), KeyEvent.VK_T, "Test commands");
        MenuItem logMessage = new MenuItem(localeBundle.getString("logMessage"), KeyEvent.VK_S, null, "New line");
        logs.addMenuItems(logMessage);


        Menu locale = new Menu(localeBundle.getString("locale"), KeyEvent.VK_C, "Localisation");
        getLocales(locale);

        menuBar.addMenus(lookAndFeel, logs, locale);
        setJMenuBar(menuBar);
    }

    public static void setLocale(String newLocale) {
        localeBundle = ResourceBundle.getBundle(newLocale);
    }

    private void getLocales(Menu menu) {
        File directory = new File("src/main/resources");
        String[] files = directory.list((dir, name) -> name.endsWith("locale.properties"));

        assert files != null;
        for (String fileName : files) {
            String localeName = fileName.replace(".properties", "");
            MenuItem locale = new MenuItem(localeBundle.getString(localeName), KeyEvent.VK_S, null, "Changed to %s".formatted(localeName));
            locale.addActionListener(e -> SwingUtilities.invokeLater(() -> {
                setLocale(localeName);
                resetUI();
            }));
            menu.addMenuItems(locale);
        }
    }

    private void resetUI() {
        generateMenuBar();
        gameWindow.setTitle(localeBundle.getString("gameWindow"));
        logWindow.setTitle(localeBundle.getString("logWindow"));
        gameWindow.changeLocale(localeBundle);
        logWindow.changeLocale(localeBundle);
        this.changeLocale(localeBundle);
        revalidate();
        repaint();
    }

}
