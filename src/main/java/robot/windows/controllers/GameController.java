package robot.windows.controllers;

import robot.windows.models.GameModel;
import robot.windows.views.GameView;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameController implements PlayerObserved{
    private final GameModel gameModel;
    private final GameView gameVisualizer;
    public LinkedList<PlayerObserver> observers;

    public GameController() {
        this.gameModel = new GameModel();
        this.gameVisualizer = new GameView();
        this.observers = new LinkedList<>();
        gameVisualizer.setPaintings(gameModel.player, gameModel.enemies, gameModel.obstacles, gameModel.bullets);
        setUpThreads();
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                gameModel.shoot(e.getPoint());
            }
        };
        gameVisualizer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameModel.shoot(e.getPoint());
            }

            @Override
            public void mousePressed(MouseEvent e) {
                gameVisualizer.addMouseMotionListener(mouseAdapter);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                gameVisualizer.removeMouseMotionListener(mouseAdapter);
            }
        });
    }

    private void setUpThreads() {
        ScheduledExecutorService updateScheduler = Executors.newScheduledThreadPool(1);
        updateScheduler.scheduleAtFixedRate(gameModel::onModelUpdateEvent, 0, 10, TimeUnit.MILLISECONDS);
        ScheduledExecutorService drawScheduler = Executors.newScheduledThreadPool(1);
        drawScheduler.scheduleAtFixedRate(this::repaint, 0, 3, TimeUnit.MILLISECONDS);
        ScheduledExecutorService actionScheduler = Executors.newScheduledThreadPool(1);
        actionScheduler.scheduleAtFixedRate(this::handlePlayerAction, 0, 10, TimeUnit.MILLISECONDS);
    }

    private void handlePlayerAction() {
        Point playerPosition = gameModel.player.getPosition();
        int posX = playerPosition.x;
        int posY = playerPosition.y;

        if (gameVisualizer.keyboard.isUpPressed()) {
            posY -= gameModel.PLAYER_VELOCITY;
        }
        if (gameVisualizer.keyboard.isDownPressed()) {
            posY += gameModel.PLAYER_VELOCITY;
        }
        if (gameVisualizer.keyboard.isLeftPressed()) {
            posX -= gameModel.PLAYER_VELOCITY;
        }
        if (gameVisualizer.keyboard.isRightPressed()) {
            posX += gameModel.PLAYER_VELOCITY;
        }
        if (!gameModel.isCollisionObstacle(new Point(posX, posY))) {
            Point newPos = new Point(posX, posY);
            gameModel.player.setPosition(newPos);
            updateLocation(newPos);
        }
    }

    public void repaint() {
        gameVisualizer.setPaintings(gameModel.player, gameModel.enemies, gameModel.obstacles, gameModel.bullets);
        gameVisualizer.repaint();
    }

    public GameView getVisualizer() {
        return gameVisualizer;
    }

    @Override
    public void updateLocation(Point location) {
        for (PlayerObserver playerObserver: observers)
            playerObserver.updateLocation(location);
    }

    @Override
    public void addObserver(PlayerObserver observer) {
        observers.add(observer);
    }
}
