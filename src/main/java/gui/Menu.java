package gui;

import javax.swing.*;

public class Menu extends JMenu {

    public Menu(String title, int mnemonic, String accessibleDescription) {
        super(title);
        setMnemonic(mnemonic);
        getAccessibleContext().setAccessibleDescription(accessibleDescription);
    }

    public void addMenuItems(MenuItem... menuItems) {
        for(MenuItem menuItem: menuItems)
            add(menuItem);
    }
}
