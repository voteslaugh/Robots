import robot.MainApplicationFrame;
import robot.windows.EnemiesDistanceWindow;
import robot.windows.GameWindow;
import robot.windows.LogWindow;
import robot.windows.PlayerLocationWindow;
import robot.windows.controllers.DistanceController;
import robot.windows.controllers.LocationController;

import java.awt.*;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.swing.*;

public class Main
{
    public static void main(String[] args) {
      try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      Preferences preferences = Preferences.userNodeForPackage(MainApplicationFrame.class);
      ResourceBundle localeBundle = ResourceBundle.getBundle(preferences.get("locale", "en_locale"));

      GameWindow gameWindow = new GameWindow(localeBundle, preferences);
      LogWindow logWindow = new LogWindow(localeBundle, preferences);
      PlayerLocationWindow locationWindow = new PlayerLocationWindow(localeBundle, preferences);
      EnemiesDistanceWindow distanceWindow = new EnemiesDistanceWindow(localeBundle, preferences);

      LocationController locationController = locationWindow.locationController;
      locationController.setText(locationController.convertToString(gameWindow.getPlayerPosition()));
      gameWindow.gameController.addPlayerObserver(locationWindow.locationController);

      DistanceController distanceController = new DistanceController();
      distanceController.setText(distanceController.convertToString(gameWindow.gameController.getModel().getDistancesToEnemies()));
      gameWindow.gameController.getModel().addEnemiesObserver(distanceController);
      distanceWindow.add(distanceController.distanceView);

      SwingUtilities.invokeLater(() -> {
        MainApplicationFrame frame = new MainApplicationFrame(localeBundle, preferences, gameWindow, logWindow, locationWindow, distanceWindow);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
      });
  }}

