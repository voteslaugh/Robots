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
import javax.swing.*;

public class MainApplicationFrame extends Frame {

    GameWindow gameWindow;
    LogWindow logWindow;
    MenuBar menuBar;

    public MainApplicationFrame() {
        setContentPane(new JDesktopPane());
        gameWindow = new GameWindow(localeBundle);
        logWindow = new LogWindow(localeBundle);
        addFrames(gameWindow, logWindow);
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
                    restoreState();
                }
            }
        });
        generateMenuBar();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }

    private void generateMenuBar() {
        menuBar = new MenuBar(localeBundle);
        Menu localeMenu = new Menu(localeBundle.getString("locale"), KeyEvent.VK_C, "Localisation");
        getLocales(localeMenu);
        menuBar.addMenus(localeMenu);
        setJMenuBar(menuBar);
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
        gameWindow.changeClosingListenerLocale(localeBundle);
        logWindow.changeClosingListenerLocale(localeBundle);
        this.changeClosingListenerLocale(localeBundle);
        revalidate();
        repaint();
    }

}
