package robot.windows.controllers;

import robot.windows.views.DistanceView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;

public class DistanceController implements PropertyChangeListener {
    public DistanceView distanceView;

    public DistanceController() {
        this.distanceView = new DistanceView();
    }

    public void setText(String text) {
        distanceView.distances.setText(text);
    }

    public String convertToString(LinkedList<Integer> distances) {
        StringBuilder distancesBuilder = new StringBuilder();
        distancesBuilder.append("<html>");
        for (Integer distance : distances) {
            distancesBuilder.append(distance).append("<br>");
        }
        distancesBuilder.append("</html>");
        return distancesBuilder.toString();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LinkedList<Integer> distances = (LinkedList<Integer>) evt.getNewValue();
        setText(convertToString(distances));
    }
}
