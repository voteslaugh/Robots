package robot.windows.gui;

import robot.log.Logger;

import javax.swing.*;

public class MenuItem extends JMenuItem {
    public MenuItem(String title, int mnemonic, String className, String debugText) {
        super(title, mnemonic);
        addActionListener((event) -> {
            if (className != null)
                setLookAndFeel(className);
            if (debugText != null)
                Logger.debug(debugText);
            this.invalidate();
        });
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
