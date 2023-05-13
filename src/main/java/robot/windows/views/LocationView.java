package robot.windows.views;

import javax.swing.*;

public class LocationView extends JPanel {
    public JLabel location;

    public LocationView() {
        location = new JLabel("X: ?, Y: ?");
        add(location);
    }

}
