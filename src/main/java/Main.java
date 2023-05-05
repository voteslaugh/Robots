import robot.MainApplicationFrame;
import robot.windows.GameWindow;
import robot.windows.LogWindow;

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
        e.printStackTrace();
      }
      Preferences preferences = Preferences.userNodeForPackage(MainApplicationFrame.class);
      ResourceBundle localeBundle = ResourceBundle.getBundle(preferences.get("locale", "en_locale"));

      SwingUtilities.invokeLater(() -> {
        GameWindow gameWindow = new GameWindow(localeBundle);
        LogWindow logWindow = new LogWindow(localeBundle);
        MainApplicationFrame frame = new MainApplicationFrame(localeBundle, preferences, gameWindow, logWindow);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
      });
  }}
