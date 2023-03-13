package windows;

import windows.gui.MenuItem;

import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class MainApplicationFrame extends windows.gui.Frame
{
    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);

        setContentPane(new JDesktopPane());
        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }
    
    private windows.gui.MenuBar generateMenuBar()
    {
        windows.gui.MenuBar menuBar = new windows.gui.MenuBar();

        windows.gui.Menu lookAndFeel = new windows.gui.Menu("Режим отображения", KeyEvent.VK_V, "Управление режимом отображения приложения");
        windows.gui.MenuItem systemLookAndFeel = new windows.gui.MenuItem("Системная схема", KeyEvent.VK_S, UIManager.getSystemLookAndFeelClassName(), null);
        windows.gui.MenuItem crossplatformLookAndFeel = new windows.gui.MenuItem("Универсальная схема", KeyEvent.VK_S, UIManager.getCrossPlatformLookAndFeelClassName(), null);
        lookAndFeel.addMenuItems(systemLookAndFeel, crossplatformLookAndFeel);

        windows.gui.Menu test = new windows.gui.Menu("Тесты", KeyEvent.VK_T, "Тестовые команды");
        windows.gui.MenuItem addLogMessageItem = new MenuItem("Сообщение в лог", KeyEvent.VK_S, null, "Новая строка");
        test.addMenuItems(addLogMessageItem);

        menuBar.addMenus(lookAndFeel, test);
        return menuBar;
    }

}
