package robot;

import robot.windows.gui.*;

import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javax.swing.*;

public class MainApplicationFrame extends Frame {

    InternalFrame[] internalFrames;
    MenuBar menuBar;

    public MainApplicationFrame(ResourceBundle localeBundle, Preferences preferences, InternalFrame... internalFrames) {
        super(localeBundle, preferences);
        this.internalFrames = internalFrames;
        setContentPane(new JDesktopPane());
        addFrames(internalFrames);
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
        for (InternalFrame internalFrame: internalFrames) {
            internalFrame.setTitle(localeBundle.getString(internalFrame.getName()));
        }
        this.changeClosingListenerLocale(localeBundle);
        revalidate();
        repaint();
    }

}
