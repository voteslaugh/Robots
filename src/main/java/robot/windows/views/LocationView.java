package robot.windows.views;

import javax.swing.*;
import java.awt.*;

public class LocationView extends JPanel {
    public JLabel location;

    public LocationView() {
        location = new JLabel();
        location.setFont(UIManager.getFont("Label.font").deriveFont(24f));
        add(location);
    }

}
