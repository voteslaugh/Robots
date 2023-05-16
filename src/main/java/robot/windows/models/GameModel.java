package robot.windows.models;

import robot.windows.components.Bullet;
import robot.windows.components.Character;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class GameModel {

    public Character player;
    public Set<Character> enemies;
    public HashSet<Shape> obstacles;
    public Set<Bullet> bullets;
    public final PropertyChangeSupport enemiesObservers;
    public final double ENEMY_VELOCITY = 3;
    public final double PLAYER_VELOCITY = 4;
    public final double BULLET_VELOCITY = 6;
    private final Point spawnPosition = new Point(794, 86);

    public GameModel() {
        player = new Character(new Point(390, 254), 0, 20);
        enemiesObservers = new PropertyChangeSupport(this);
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
        enemies.addAll(List.of(
                new Character(new Point(840, 200), 0, 60),
                new Character(new Point(840, 600), 0, 30),
                new Character(new Point(560, 600), 0, 10)
        ));
    }

    public static double angleBetweenPoints(Point p1, Point p2) {
        return Math.atan2(p2.y - p1.y, p2.x - p1.x);
    }

    public void onModelUpdateEvent() {
        moveBullets();
        moveEnemiesToPlayer();
    }

    private synchronized Point moveByDirection(Point start, double direction, double velocity) {
        double velocityX = velocity * Math.cos(direction);
        double velocityY = velocity * Math.sin(direction);
        return new Point((int) (start.getX() + velocityX), (int) ((start.getY() + velocityY)));
    }

    public boolean isIntersects(Shape shape1, Shape shape2) {
        return shape1.intersects((Rectangle2D) shape2);
    }

    public boolean isCollisionObstacle(Point position, int hitBoxRadius) {
        for (Shape obstacle : obstacles) {
            if (isIntersects(getHitBoxShape(position, hitBoxRadius), obstacle))
                return true;
        }
        return false;
    }

    public Shape getHitBoxShape(Point position, int hitBoxRadius) {
        Point drawPosition = new Point(position.x - hitBoxRadius / 2, position.y - hitBoxRadius / 2);
        return new Rectangle2D.Double(drawPosition.x, drawPosition.y, hitBoxRadius, hitBoxRadius);
    }

    private synchronized void moveEnemiesToPlayer() {
        Point destination = player.getPosition();
        Iterator<Character> iterator = enemies.iterator();
        LinkedList<Integer> oldDistances = getDistancesToEnemies();
        while (iterator.hasNext()) {
            Character enemy = iterator.next();
            if (isEnemyHit(enemy.getHitBox())) {
                iterator.remove();
            }
            if (!(enemy.getPosition().distance(destination) < 2)) {
                moveEnemy(enemy, destination);
            }
        }
        enemiesObservers.firePropertyChange("enemiesDistance", oldDistances, getDistancesToEnemies());
    }

    public void spawnEnemy() {
        enemies.add(new Character(spawnPosition, 0, 40));
    }

    private synchronized void moveEnemy(Character enemy, Point destination) {
        Point oldPosition = enemy.getPosition();
        double direction = angleBetweenPoints(oldPosition, destination);
        Point newPosition = moveByDirection(oldPosition, direction, ENEMY_VELOCITY);
        if (!isCollisionObstacle(newPosition, enemy.getHitBoxRadius()))
            enemy.setPosition(newPosition);
    }

    public void shoot(Point destination) {
        bullets.add(new Bullet(player.getPosition(), destination));
    }

    public boolean isEnemyHit(Shape hitBox) {
        for (Bullet bullet : bullets)
            if (isIntersects(getHitBoxShape(bullet.getPosition(), bullet.getHitBoxRadius()), hitBox))
                return true;
        return false;
    }

    private synchronized void moveBullets() {
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            Point position = bullet.getPosition();
            bullet.setPosition(moveByDirection(position, bullet.getDirection(), BULLET_VELOCITY));
            if (isCollisionObstacle(position, bullet.getHitBoxRadius())) {
                iterator.remove();
            }
        }
    }

    public LinkedList<Integer> getDistancesToEnemies() {
        LinkedList<Integer> positions = new LinkedList<>();
        for (Character enemy : enemies) {
            positions.add((int) player.getPosition().distance(enemy.getPosition()));
        }
        return positions;
    }

    public void addEnemiesObserver(PropertyChangeListener observer) {
        enemiesObservers.addPropertyChangeListener(observer);
    }
}