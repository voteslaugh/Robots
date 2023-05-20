package robot.windows.controllers;

import robot.windows.views.LocationView;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LocationController implements PropertyChangeListener {
    public LocationView locationView;

    public LocationController() {
        this.locationView = new LocationView();
    }

    public void setText(String text) {
        locationView.location.setText(text);
    }

    public String convertToString(Point p) {
        return String.format("X: %d, Y: %d", p.x, p.y);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("playerPosition")) {
            Point newLocation = (Point) evt.getNewValue();
            setText(convertToString(newLocation));
        }
    }
}
