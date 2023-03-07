import gui.InternalFrame;
import gui.MainApplicationFrame;
import log.Logger;
import logic.GameVisualizer;
import logic.LogVisualizer;

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
        InternalFrame logWindow = new InternalFrame("Протокол работы", 300, 800, 10, 10, false, false);
        logWindow.addPanel(new LogVisualizer(Logger.getDefaultLogSource()));

        InternalFrame gameWindow = new InternalFrame("Игровое поле", 400, 400, 400, 400, true, true);
        gameWindow.addPanel(new GameVisualizer());


      SwingUtilities.invokeLater(() -> {
        MainApplicationFrame frame = new MainApplicationFrame();
        frame.addFrames(gameWindow, logWindow);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
      });
  }}

