package windows;

import windows.gui.MenuItem;
import windows.gui.MenuBar;
import windows.gui.Menu;

import java.awt.event.KeyEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;

public class MainApplicationFrame extends windows.gui.Frame
{
    MenuBar menuBar;
    public MainApplicationFrame() {
        setContentPane(new JDesktopPane());
        setJMenuBar(generateMenuBar("en_locale"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }
    
    private MenuBar generateMenuBar(String prop)
    {
        ResourceBundle menuBundle = ResourceBundle.getBundle(prop);
        menuBar = new MenuBar();

        Menu lookAndFeel = new Menu(menuBundle.getString("lookAndFeel"), KeyEvent.VK_V, "Управление режимом отображения приложения");
        MenuItem system = new MenuItem(menuBundle.getString("system"), KeyEvent.VK_S, UIManager.getSystemLookAndFeelClassName(), null);
        MenuItem crossplatform = new MenuItem(menuBundle.getString("crossplatform"), KeyEvent.VK_S, UIManager.getCrossPlatformLookAndFeelClassName(), null);
        lookAndFeel.addMenuItems(system, crossplatform);

        Menu logs = new Menu(menuBundle.getString("logs"), KeyEvent.VK_T, "Тестовые команды");
        MenuItem logMessage = new MenuItem(menuBundle.getString("logMessage"), KeyEvent.VK_S, null, "Новая строка");
        logs.addMenuItems(logMessage);

        Menu locale = new Menu(menuBundle.getString("locale"), KeyEvent.VK_C, "Локализация");
        MenuItem ruLocale = new MenuItem(menuBundle.getString("ruLocale"), KeyEvent.VK_S, null, "Русский язык");
        ruLocale.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                Locale.setDefault(new Locale("ru", "RU", "RUSSIA"));
                menuBar.removeAll();
                generateMenuBar("ru_locale");
                setJMenuBar(menuBar);
                revalidate();
                repaint();
            });
        });
        MenuItem enLocale = new MenuItem(menuBundle.getString("enLocale"), KeyEvent.VK_S, null, "Английский язык");
        enLocale.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                Locale.setDefault(Locale.ENGLISH);
                menuBar.removeAll();
                generateMenuBar("en_locale");
                setJMenuBar(menuBar);
                revalidate();
                repaint();
            });
        });
        locale.addMenuItems(ruLocale, enLocale);

        Menu exit = new Menu(menuBundle.getString("exit"), KeyEvent.VK_X, "Закрытие приложения");
        MenuItem exitItem = new MenuItem(menuBundle.getString("exitItem"), KeyEvent.VK_V, null, "Закрыть приложение");
        exitItem.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(MainApplicationFrame.this, "Вы уверены, что хотите выйти?", "Подтверждение выхода", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                dispose();
            }
        });
        exit.addMenuItems(exitItem);

        menuBar.addMenus(lookAndFeel, logs, locale, exit);
        return menuBar;
    }

}
