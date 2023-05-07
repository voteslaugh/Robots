package robot.windows.game;

import robot.windows.game.components.KeyboardHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameController extends JPanel implements ActionListener {
    Model model;
    Visualizer visualizer;
    KeyboardHandler keyboardHandler;

    public GameController() {
        super(true);
        keyboardHandler = new KeyboardHandler();
        addKeyListener(keyboardHandler);
        setFocusable(true);
        this.model = new Model();
        this.visualizer = new Visualizer();
        Timer timer = new Timer(3, this);
        timer.start();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        scheduler.scheduleAtFixedRate(() -> model.onModelUpdateEvent(), 0, 10, TimeUnit.MILLISECONDS);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Point playerPosition = model.player.getPosition();

        if (keyboardHandler.isUpPressed()) {
            playerPosition.y -= model.PLAYER_VELOCITY;
        }
        if (keyboardHandler.isDownPressed()) {
            playerPosition.y += model.PLAYER_VELOCITY;
        }
        if (keyboardHandler.isLeftPressed()) {
            playerPosition.x -= model.PLAYER_VELOCITY;
        }
        if (keyboardHandler.isRightPressed()) {
            playerPosition.x += model.PLAYER_VELOCITY;
        }
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        visualizer.drawEnemy(g2d, model.enemy.getPosition(), model.enemy.getDirection());
        visualizer.drawPlayer(g2d, model.player.getPosition());
    }

}
