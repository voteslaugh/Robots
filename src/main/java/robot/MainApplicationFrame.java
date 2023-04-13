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

public class MainApplicationFrame extends Frame {

    GameWindow gameWindow;
    LogWindow logWindow;
    MenuBar menuBar;
    ResourceBundle localeBundle;
    Preferences prefs = Preferences.userNodeForPackage(MainApplicationFrame.class);

    public MainApplicationFrame() {
        setContentPane(new JDesktopPane());
        localeBundle = ResourceBundle.getBundle(prefs.get("locale", "en_locale"));
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
                    String name = gameWindow.getName();
                    gameWindow.changeState(prefs.getInt(name + ".x", gameWindow.getX()),
                            prefs.getInt(name + ".y", gameWindow.getY()),
                            prefs.getInt(name + ".width", gameWindow.getWidth()),
                            prefs.getInt(name + ".height", gameWindow.getHeight()),
                            prefs.getBoolean(name + ".icon", gameWindow.isIcon()));
                    name = logWindow.getName();
                    logWindow.changeState(prefs.getInt(name + ".x", logWindow.getX()),
                            prefs.getInt(name + ".y", logWindow.getY()),
                            prefs.getInt(name + ".width", logWindow.getWidth()),
                            prefs.getInt(name + ".height", logWindow.getHeight()),
                            prefs.getBoolean(name + ".icon", logWindow.isIcon()));
                }

                addFrames(gameWindow, logWindow);
                generateMenuBar();
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                pack();

            }
        });
    }

    private void generateMenuBar() {
        menuBar = new MenuBar(localeBundle);
        Menu locale = new Menu(localeBundle.getString("locale"), KeyEvent.VK_C, "Localisation");
        getLocales(locale);
        menuBar.addMenus(locale);
        setJMenuBar(menuBar);
    }

    public void setLocale(String newLocale) {
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
