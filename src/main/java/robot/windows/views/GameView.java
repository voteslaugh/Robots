package robot.windows.views;

import robot.windows.handlers.DrawHandler;
import robot.windows.components.Bullet;
import robot.windows.components.Character;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class GameView extends DrawHandler {

    private Character player;
    private Set<Character> enemies;
    private HashSet<Shape> obstacles;
    private Set<Bullet> bullets;

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
        drawEnemies(g2d, enemies);
        drawPlayer(g2d, player);
        drawObstacles(g2d, obstacles);
        drawBullets(g2d, bullets);
    }

}
