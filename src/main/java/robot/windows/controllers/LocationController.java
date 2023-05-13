package robot.windows.controllers;

import robot.windows.views.LocationView;

import java.awt.*;

public class LocationController implements PlayerObserver{
    public LocationView locationView;

    public LocationController() {
        this.locationView = new LocationView();
    }

    @Override
    public void updateLocation(Point location) {
        String positionText = String.format("X: %d, Y: %d", location.x, location.y);
        locationView.location.setText(positionText);
    }
}
