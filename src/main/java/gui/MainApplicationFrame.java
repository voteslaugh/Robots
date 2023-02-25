package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;

import log.Logger;

/**
 * Что требуется сделать:
 * 1. Метод создания меню перегружен функционалом и трудно читается. 
 * Следует разделить его на серию более простых методов (или вообще выделить отдельный класс).
 *
 */
public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    
    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 100;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);

        setContentPane(desktopPane);
        
        
        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(400,  400);
        addWindow(gameWindow);

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    protected LogWindow createLogWindow()
    {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10,10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }
    
    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
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
        menuBar.add(test);
        return menuBar;
    }
    
    private void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (ClassNotFoundException | InstantiationException
            | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            // just ignore
        }
    }
}
