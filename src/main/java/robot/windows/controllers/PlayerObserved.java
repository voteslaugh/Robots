package robot.windows.controllers;

import java.awt.*;

public interface PlayerObserved {
    void updateLocation(Point point);
    void addPlayerObserver(PlayerObserver observer);
}
