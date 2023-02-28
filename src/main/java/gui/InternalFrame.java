package gui;

import javax.swing.*;
import java.awt.*;

public class InternalFrame extends JInternalFrame {

    public InternalFrame(String title, int width, int height, int x, int y) {
        super(title, true, true, true, true);
        setSize(width, height);
        setLocation(x, y);
        setMinimumSize(getSize());
        setVisible(true);
    }
}
