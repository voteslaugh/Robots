package robot;

import robot.windows.GameWindow;
import robot.windows.LogWindow;
import robot.windows.gui.Frame;
import robot.windows.gui.MenuItem;
import robot.windows.gui.MenuBar;
import robot.windows.gui.Menu;

import java.awt.event.KeyEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;

public class MainApplicationFrame extends Frame {

    GameWindow gameWindow;
    LogWindow logWindow;
    MenuBar menuBar;
    ResourceBundle bundle;

    public MainApplicationFrame() {
        setContentPane(new JDesktopPane());
        bundle = ResourceBundle.getBundle("en_locale");
        gameWindow = new GameWindow();
        logWindow = new LogWindow();
        addFrames(gameWindow, logWindow);
        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }

    private MenuBar generateMenuBar() {
        menuBar = new MenuBar();

        Menu lookAndFeel = new Menu(bundle.getString("lookAndFeel"), KeyEvent.VK_V, "Управление режимом отображения приложения");
        MenuItem system = new MenuItem(bundle.getString("system"), KeyEvent.VK_S, UIManager.getSystemLookAndFeelClassName(), null);
        MenuItem crossplatform = new MenuItem(bundle.getString("crossplatform"), KeyEvent.VK_S, UIManager.getCrossPlatformLookAndFeelClassName(), null);
        lookAndFeel.addMenuItems(system, crossplatform);

        Menu logs = new Menu(bundle.getString("logs"), KeyEvent.VK_T, "Тестовые команды");
        MenuItem logMessage = new MenuItem(bundle.getString("logMessage"), KeyEvent.VK_S, null, "Новая строка");
        logs.addMenuItems(logMessage);

        Menu locale = new Menu(bundle.getString("locale"), KeyEvent.VK_C, "Локализация");
        MenuItem ruLocale = new MenuItem(bundle.getString("ruLocale"), KeyEvent.VK_S, null, "Русский язык");
        ruLocale.addActionListener(e -> SwingUtilities.invokeLater(() -> {
                Locale.setDefault(new Locale("ru", "RU", "RUSSIA"));
                menuBar.removeAll();
                updateLocale("ru_locale");
                generateMenuBar();
                setJMenuBar(menuBar);
                logWindow.setTitle(bundle.getString("logWindow"));
                gameWindow.setTitle(bundle.getString("gameWindow"));
                revalidate();
                repaint();
            }));
        MenuItem enLocale = new MenuItem(bundle.getString("enLocale"), KeyEvent.VK_S, null, "Английский язык");
        enLocale.addActionListener(e -> SwingUtilities.invokeLater(() -> {
                Locale.setDefault(Locale.ENGLISH);
                menuBar.removeAll();
                updateLocale("en_locale");
                generateMenuBar();
                setJMenuBar(menuBar);
                gameWindow.setTitle(bundle.getString("gameWindow"));
                logWindow.setTitle(bundle.getString("logWindow"));
                revalidate();
                repaint();
            }));
        locale.addMenuItems(ruLocale, enLocale);

        Menu exit = new Menu(bundle.getString("exit"), KeyEvent.VK_X, "Закрытие приложения");
        MenuItem exitItem = new MenuItem(bundle.getString("exitItem"), KeyEvent.VK_V, null, "Закрыть приложение");
        exitItem.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(MainApplicationFrame.this, "Вы уверены, что хотите выйти?", "Подтверждение выхода", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                dispose();
                System.exit(0);
            }
        });
        exit.addMenuItems(exitItem);

        menuBar.addMenus(lookAndFeel, logs, locale, exit);
        return menuBar;
    }

    private void updateLocale(String newLocale) {
        bundle = ResourceBundle.getBundle(newLocale);
    }

}
