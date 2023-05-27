package robot.windows.views;

import robot.windows.components.world.Enemy;
import robot.windows.components.world.Player;
import robot.windows.handlers.DrawHandler;
import robot.windows.components.world.Bullet;
import robot.windows.components.world.Character;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.HashSet;
import java.util.Set;

public class GameView extends DrawHandler {

    private Player player;
    private Set<Enemy> enemies;
    private HashSet<Shape> obstacles;
    private Set<Bullet> bullets;
    private int score;

    public void setPaintings(Player player, Set<Enemy> enemies, HashSet<Shape> obstacles, Set<Bullet> bullets, int score) {
        this.player = player;
        this.enemies = enemies;
        this.obstacles = obstacles;
        this.bullets = bullets;
        this.score = score;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform t = new AffineTransform();
        t.translate(player.getPosition().x, player.getPosition().y);
        t.scale(zoomLevel, zoomLevel);
        t.translate(-player.getPosition().x, -player.getPosition().y);
        g2d.setTransform(t);
        drawEnemies(g2d, enemies);
        drawPlayer(g2d, player);
        drawObstacles(g2d, obstacles);
        drawBullets(g2d, bullets);
        g2d.setTransform(new AffineTransform());
        drawScore(g2d, score);
    }

}
