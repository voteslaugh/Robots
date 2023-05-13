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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Point newLocation = (Point) evt.getNewValue();
        locationView.location.setText(String.format("X: %d, Y: %d", newLocation.x, newLocation.y));
    }
}
