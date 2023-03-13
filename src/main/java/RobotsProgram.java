import windows.GameWindow;
import windows.LogWindow;
import windows.MainApplicationFrame;

import java.awt.*;

import javax.swing.*;

public class RobotsProgram
{
    public static void main(String[] args) {
      try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception e) {
        e.printStackTrace();
      }
      LogWindow logWindow = new LogWindow();
      GameWindow gameWindow = new GameWindow();


      SwingUtilities.invokeLater(() -> {
        MainApplicationFrame frame = new MainApplicationFrame();
        frame.addFrames(gameWindow, logWindow);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
      });
  }}

