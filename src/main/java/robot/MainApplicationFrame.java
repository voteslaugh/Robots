package robot;

import robot.windows.gui.*;
import robot.windows.gui.Frame;
import robot.windows.gui.Menu;
import robot.windows.gui.MenuBar;
import robot.windows.gui.MenuItem;

import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javax.swing.*;

public class MainApplicationFrame extends Frame {

    MenuBar menuBar;

    public MainApplicationFrame(ResourceBundle localeBundle, Preferences preferences, InternalFrame... internalFrames) {
        super(localeBundle, preferences);
        setContentPane(new JDesktopPane());
        addFrames(internalFrames);
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
        for (InternalFrame internalFrame: internalFrames) {
            internalFrame.setTitle(localeBundle.getString(internalFrame.getName()));
            internalFrame.changeClosingListenerLocale(localeBundle);
        }
        this.changeClosingListenerLocale(localeBundle);
        revalidate();
        repaint();
    }

}
