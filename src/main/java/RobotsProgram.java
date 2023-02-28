import gui.InternalFrame;
import gui.LogWindow;
import gui.MainApplicationFrame;
import log.Logger;
import logic.GameVisualizer;

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

        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        Logger.debug("Протокол работает");

        InternalFrame gameWindow = new InternalFrame("Игровое поле", 400, 400, 400, 400);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new GameVisualizer(), BorderLayout.CENTER);
        gameWindow.getContentPane().add(panel);

      SwingUtilities.invokeLater(() -> {
        MainApplicationFrame frame = new MainApplicationFrame();
        frame.addFrames(gameWindow, logWindow);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
      });
    }}
