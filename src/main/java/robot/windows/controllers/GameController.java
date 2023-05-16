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
    private final KeyboardHandler keyboardHandler;
    private final MouseHandler mouseHandler;
    public GameController() {
        this.gameModel = new GameModel();
        keyboardHandler = new KeyboardHandler();
        mouseHandler = new MouseHandler();
        this.gameVisualizer = new GameView();
        gameVisualizer.addKeyListener(keyboardHandler);
        gameVisualizer.addMouseListener(mouseHandler);
        gameVisualizer.addMouseMotionListener(mouseHandler);
        gameVisualizer.setPaintings(gameModel.player, gameModel.enemies, gameModel.obstacles, gameModel.bullets);
        threadPool = Executors.newScheduledThreadPool(4);
        setUpThreads();
        this.playerObservers = new PropertyChangeSupport(this);
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
        if (mouseHandler.isLMBPressed())
            gameModel.shoot(mouseHandler.getPosition());
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

        if (keyboardHandler.isUpPressed())
            posY -= velocity;
        if (keyboardHandler.isDownPressed())
            posY += velocity;
        if (keyboardHandler.isLeftPressed())
            posX -= velocity;
        if (keyboardHandler.isRightPressed())
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