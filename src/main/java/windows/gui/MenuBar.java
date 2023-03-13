package windows.gui;

import javax.swing.*;

public class MenuBar extends JMenuBar {

    public void addMenus(Menu... menus) {
        for(Menu menu: menus)
            add(menu);
    }
}
