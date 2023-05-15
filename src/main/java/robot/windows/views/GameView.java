package robot.windows.views;

import robot.windows.handlers.DrawHandler;
import robot.windows.components.Bullet;
import robot.windows.components.Character;
import robot.windows.handlers.KeyboardHandler;
import robot.windows.handlers.MouseHandler;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class GameView extends DrawHandler {

    private Character player;
    private Set<Character> enemies;
    private HashSet<Shape> obstacles;
    private Set<Bullet> bullets;
    public final KeyboardHandler keyboard;
    public final MouseHandler mouse;

    public GameView(KeyboardHandler keyboardHandler, MouseHandler mouseHandler) {
        keyboard = keyboardHandler;
        mouse = mouseHandler;
        addKeyListener(keyboard);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }

    public void setPaintings(Character player, Set<Character> enemies, HashSet<Shape> obstacles, Set<Bullet> bullets) {
        this.player = player;
        this.enemies = enemies;
        this.obstacles = obstacles;
        this.bullets = bullets;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(zoomLevel, zoomLevel);
        drawEnemies(g2d, enemies);
        drawPlayer(g2d, player);
        drawObstacles(g2d, obstacles);
        drawBullets(g2d, bullets);
    }

}
