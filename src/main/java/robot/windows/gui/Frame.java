package robot.windows.gui;

import javax.swing.*;

public class Frame extends JFrame {

    public void addFrames(InternalFrame... frames) {
        for (InternalFrame frame: frames)
            add(frame);
    }
}
