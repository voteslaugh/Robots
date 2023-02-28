package gui;

import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class MainApplicationFrame extends Frame
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
    
    private MenuBar generateMenuBar()
    {
        MenuBar menuBar = new MenuBar();

        Menu lookAndFeel = new Menu("Режим отображения", KeyEvent.VK_V, "Управление режимом отображения приложения");
        MenuItem systemLookAndFeel = new MenuItem("Системная схема", KeyEvent.VK_S, UIManager.getSystemLookAndFeelClassName(), null);
        MenuItem crossplatformLookAndFeel = new MenuItem("Универсальная схема", KeyEvent.VK_S, UIManager.getCrossPlatformLookAndFeelClassName(), null);
        lookAndFeel.addMenuItems(systemLookAndFeel, crossplatformLookAndFeel);

        Menu test = new Menu("Тесты", KeyEvent.VK_T, "Тестовые команды");
        MenuItem addLogMessageItem = new MenuItem("Сообщение в лог", KeyEvent.VK_S, null, "Новая строка");
        test.addMenuItems(addLogMessageItem);

        menuBar.addMenus(lookAndFeel, test);
        return menuBar;
    }

}
