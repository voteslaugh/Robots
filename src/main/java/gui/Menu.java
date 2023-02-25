package gui;

import javax.swing.*;

public class Menu extends JMenu {

    public Menu(String text, int mnemonic, String accessibleDescription) {
        super(text);
        setMnemonic(mnemonic);
        getAccessibleContext().setAccessibleDescription(accessibleDescription);
    }

    public void addMenuItems(MenuItem... menuItems) {
        for(MenuItem menuItem: menuItems)
            add(menuItem);
    }
}
