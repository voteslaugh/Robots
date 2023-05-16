package robot.windows.controllers;

import robot.windows.handlers.KeyboardHandler;
import robot.windows.handlers.MouseHandler;
import robot.windows.models.GameModel;
import robot.windows.views.GameView;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameController {
    private final GameModel gameModel;
    private final GameView gameVisualizer;
    private final ScheduledExecutorService threadPool;
    private final PropertyChangeSupport playerObservers;
    public GameController() {
        this.gameModel = new GameModel();
        this.gameVisualizer = new GameView(new KeyboardHandler(), new MouseHandler());
        threadPool = Executors.newScheduledThreadPool(4);
        setUpThreads();
        this.playerObservers = new PropertyChangeSupport(this);
        gameVisualizer.setPaintings(gameModel.player, gameModel.enemies, gameModel.obstacles, gameModel.bullets);
    }

    private void setUpThreads() {
        threadPool.scheduleAtFixedRate(gameModel::onModelUpdateEvent, 0, 10, TimeUnit.MILLISECONDS);
        threadPool.scheduleAtFixedRate(this::repaint, 0, 3, TimeUnit.MILLISECONDS);
        threadPool.scheduleAtFixedRate(this::handlePlayerAction, 0, 10, TimeUnit.MILLISECONDS);
        threadPool.scheduleAtFixedRate(this::onSpawnEvent, 0, 3, TimeUnit.SECONDS);
    }


    private void handlePlayerAction() {
        handleMouseAction();
        handleKeyboardAction();
    }

    private void handleMouseAction() {
        if (gameVisualizer.mouse.isLMBPressed())
            gameModel.shoot(gameVisualizer.mouse.getPosition());
    }

    private void handleKeyboardAction() {
        Point oldPosition = gameModel.player.getPosition();
        Point newPosition = getNewPlayerPosition(oldPosition, gameModel.PLAYER_VELOCITY);
        if (!gameModel.isCollisionObstacle(newPosition, gameModel.player.getHitBoxRadius())) {
            gameModel.player.setPosition(newPosition);
        }
    }

    private void onSpawnEvent() {
        gameModel.spawnEnemy();
    }

    public Point getNewPlayerPosition(Point position, double velocity) {
        int posX = position.x;
        int posY = position.y;

        if (gameVisualizer.keyboard.isUpPressed())
            posY -= velocity;
        if (gameVisualizer.keyboard.isDownPressed())
            posY += velocity;
        if (gameVisualizer.keyboard.isLeftPressed())
            posX -= velocity;
        if (gameVisualizer.keyboard.isRightPressed())
            posX += velocity;

        return new Point(posX, posY);
    }

    public void repaint() {
        gameVisualizer.setPaintings(gameModel.player, gameModel.enemies, gameModel.obstacles, gameModel.bullets);
        gameVisualizer.repaint();
    }

    public GameView getVisualizer() {
        return gameVisualizer;
    }

    public GameModel getModel() {
        return gameModel;
    }

    public void addPlayerObserver(PropertyChangeListener observer) {
        playerObservers.addPropertyChangeListener(observer);
    }
}