package robot.windows.controllers;

import robot.windows.handlers.BackgroundMusicHandler;
import robot.windows.handlers.ConfigHandler;
import robot.windows.handlers.KeyboardHandler;
import robot.windows.handlers.MouseHandler;
import robot.windows.models.GameModel;
import robot.windows.views.GameView;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameController {
    private final BackgroundMusicHandler musicHandler = new BackgroundMusicHandler(ConfigHandler.getString("controller", "path.music"));
    private final GameModel gameModel;
    private final GameView gameVisualizer;
    private final ScheduledExecutorService threadPool;
    private final KeyboardHandler keyboardHandler;
    private final MouseHandler mouseHandler;
    public GameController() {
        musicHandler.play();
        this.gameModel = new GameModel();
        keyboardHandler = new KeyboardHandler();
        mouseHandler = new MouseHandler();
        this.gameVisualizer = new GameView();
        gameVisualizer.addKeyListener(keyboardHandler);
        gameVisualizer.addMouseListener(mouseHandler);
        gameVisualizer.addMouseMotionListener(mouseHandler);
        gameVisualizer.setPaintings(gameModel.player, gameModel.enemies, gameModel.obstacles, gameModel.bullets, gameModel.drops, gameModel.score);
        threadPool = Executors.newScheduledThreadPool(3);
        setUpThreads();
    }

    private void setUpThreads() {
        threadPool.scheduleAtFixedRate(gameModel::onModelUpdateEvent, 0, 10, TimeUnit.MILLISECONDS);
        threadPool.scheduleAtFixedRate(this::repaint, 0, 3, TimeUnit.MILLISECONDS);
        threadPool.scheduleAtFixedRate(this::handlePlayerAction, 0, 10, TimeUnit.MILLISECONDS);
    }

    private void handlePlayerAction() {
        handleMouseAction();
        handleKeyboardAction();
    }

    private void handleMouseAction() {
        if (mouseHandler.isLMBPressed() && gameModel.player.getWeapon().isReady())
            gameModel.shoot(mouseHandler.getPosition());
    }

    private void handleKeyboardAction() {
        Point oldPosition = gameModel.player.getPosition();
        Point newPosition = getNewPlayerPosition(oldPosition, gameModel.player.getVelocity());
        if (!gameModel.isCollisionObstacle(newPosition, gameModel.player.getHitBoxRadius())) {
            gameModel.setPlayerPosition(newPosition);
        }
        gameModel.player.setWeapon(keyboardHandler.getWeaponIndex());
    }

    public Point getNewPlayerPosition(Point position, double velocity) {
        int posX = position.x;
        int posY = position.y;
        double effectedVelocity = velocity * gameModel.effect.curSpeedBoost;
        if (keyboardHandler.isUpPressed())
            posY -= effectedVelocity;
        if (keyboardHandler.isDownPressed())
            posY += effectedVelocity;
        if (keyboardHandler.isLeftPressed())
            posX -= effectedVelocity;
        if (keyboardHandler.isRightPressed())
            posX += effectedVelocity;

        return new Point(posX, posY);
    }

    public void repaint() {
        gameVisualizer.setPaintings(gameModel.player, gameModel.enemies, gameModel.obstacles, gameModel.bullets, gameModel.drops, gameModel.score);
        gameVisualizer.repaint();
    }

    public GameView getVisualizer() {
        return gameVisualizer;
    }

    public GameModel getModel() {
        return gameModel;
    }

}