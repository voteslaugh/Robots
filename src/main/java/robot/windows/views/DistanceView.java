package robot.windows.views;

import javax.swing.*;

public class DistanceView extends JPanel {
    public JLabel distances;

    public DistanceView() {
        distances = new JLabel();
        distances.setFont(UIManager.getFont("Label.font").deriveFont(24f));
        add(distances);
    }
}
