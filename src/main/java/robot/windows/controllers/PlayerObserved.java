package robot.windows.controllers;

import robot.windows.controllers.PlayerObserver;

import java.awt.*;

public interface PlayerObserved {
    void updateLocation(Point point);
    void addObserver(PlayerObserver observer);
}
