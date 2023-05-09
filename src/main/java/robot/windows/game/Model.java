package robot.windows.game;

import robot.windows.game.world.Bullet;
import robot.windows.game.world.Character;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Model {

    Character player;
    Set<Character> enemies;
    HashSet<Shape> obstacles;
    Set<Bullet> bullets;
    final double ENEMY_VELOCITY = 3;
    final double PLAYER_VELOCITY = 4;
    final double BULLET_VELOCITY = 4;

    public Model() {
        player = new Character(new Point(70, 150), 0, null);
        bullets = ConcurrentHashMap.newKeySet();
        enemies = ConcurrentHashMap.newKeySet();
        setUpEnemies();
        obstacles = new HashSet<>();
        setUpObstacles();
    }

    public void setUpObstacles() {
        obstacles.add(new Rectangle2D.Double(0, 0, 900, 10));
        obstacles.add(new Rectangle2D.Double(0, 0, 10, 900));
        obstacles.add(new Rectangle2D.Double(0, 890, 900, 10));
        obstacles.add(new Rectangle2D.Double(890, 0, 10, 900));

        obstacles.add(new Rectangle2D.Double(150, 150, 50, 500));
        obstacles.add(new Rectangle2D.Double(150, 150, 500, 50));
        obstacles.add(new Rectangle2D.Double(600, 150, 50, 500));
        obstacles.add(new Rectangle2D.Double(300, 300, 50, 300));
        obstacles.add(new Rectangle2D.Double(450, 300, 50, 300));
        obstacles.add(new Rectangle2D.Double(300, 300, 200, 50));
        obstacles.add(new Rectangle2D.Double(300, 550, 200, 50));
    }

    public void setUpEnemies() {
        enemies.addAll(List.of(new Character(new Point(880, 150), 0, 20), new Character(new Point(880, 600), 0, 20)));
    }

    public static double angleBetweenPoints(Point p1, Point p2) {
        return Math.atan2(p2.y - p1.y, p2.x - p1.x);
    }

    protected void onModelUpdateEvent() {
        for (Character enemy: enemies) {
            Point destination = player.getPosition();
            if (!(enemy.getPosition().distance(destination) < 2))
                moveEnemy(enemy, destination);
        }
        moveBullets();
    }

    private synchronized Point moveByDirection(Point start, double direction, double velocity) {
        double velocityX = velocity * Math.cos(direction);
        double velocityY = velocity * Math.sin(direction);
        return new Point((int) (start.getX() + velocityX), (int) ((start.getY() + velocityY)));
    }

    public boolean isContains(Point position, Shape shape) {
        return shape.contains(position);
    }

    public boolean isCollisionObstacle(Point position) {
        for (Shape obstacle: obstacles) {
            if (isContains(position, obstacle))
                return true;
        }
        return false;
    }

    private synchronized void moveEnemy(Character enemy, Point destination) {
        double direction = angleBetweenPoints(enemy.getPosition(), destination);
        Point newPosition = moveByDirection(enemy.getPosition(), direction, ENEMY_VELOCITY);
        if (isEnemyHit(enemy.getHitBox()))
            enemies.remove(enemy);
        if (!isCollisionObstacle(new Point(newPosition.x, newPosition.y)))
            enemy.setPosition(newPosition);
    }

    public void shoot(Point destination) {
        bullets.add(new Bullet(player.getPosition(), destination));
    }

    public boolean isEnemyHit(Shape hitBox) {
        for (Bullet bullet: bullets)
            if (isContains(bullet.getPosition(), hitBox))
                return true;
        return false;
    }

    private synchronized void moveBullets() {
            Iterator<Bullet> iterator = bullets.iterator();
            while (iterator.hasNext()) {
                Bullet bullet = iterator.next();
                Point position = bullet.getPosition();
                bullet.setPosition(moveByDirection(position, bullet.getDirection(), BULLET_VELOCITY));
                if (isCollisionObstacle(new Point(position.x, position.y))) {
                    iterator.remove();
                }
        }
    }
}
