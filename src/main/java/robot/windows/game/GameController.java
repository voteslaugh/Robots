package robot.windows.game;

import robot.windows.game.components.KeyboardHandler;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameController extends JPanel {
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
        ScheduledExecutorService updateScheduler = Executors.newScheduledThreadPool(1);
        updateScheduler.scheduleAtFixedRate(() -> model.onModelUpdateEvent(), 0, 10, TimeUnit.MILLISECONDS);
        ScheduledExecutorService drawScheduler = Executors.newScheduledThreadPool(1);
        drawScheduler.scheduleAtFixedRate(this::repaint, 0, 3, TimeUnit.MILLISECONDS);
        ScheduledExecutorService actionScheduler = Executors.newScheduledThreadPool(1);
        actionScheduler.scheduleAtFixedRate(this::handlePlayerAction, 0, 10, TimeUnit.MILLISECONDS);
    }

    private void handlePlayerAction() {
        Point playerPosition = model.player.getPosition();
        int posX = playerPosition.x;
        int posY = playerPosition.y;

        if (keyboardHandler.isUpPressed()) {
            posY -= model.PLAYER_VELOCITY;
        }
        if (keyboardHandler.isDownPressed()) {
            posY += model.PLAYER_VELOCITY;
        }
        if (keyboardHandler.isLeftPressed()) {
            posX -= model.PLAYER_VELOCITY;
        }
        if (keyboardHandler.isRightPressed()) {
            posX += model.PLAYER_VELOCITY;
        }
        if (!model.isCollision(posX, posY))
            model.player.setPosition(new Point(posX, posY));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        visualizer.drawEnemy(g2d, model.enemy.getPosition(), model.enemy.getDirection());
        visualizer.drawPlayer(g2d, model.player.getPosition());
        visualizer.drawObstacles(g2d, model.obstacles);
    }

}
