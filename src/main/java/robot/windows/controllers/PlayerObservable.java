package robot.windows.controllers;

import java.awt.*;

public interface PlayerObservable {
    void updateLocation(Point point);
    void addPlayerObserver(PlayerObserver observer);
}
