package gui;

import log.Logger;

import javax.swing.*;

public class MenuItem extends JMenuItem {
    public MenuItem(String text, int mnemonic, String className, String debugText) {
        super(text, mnemonic);
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
