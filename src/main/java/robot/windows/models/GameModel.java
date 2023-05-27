package robot.windows.models;

import org.ini4j.Ini;
import org.ini4j.Profile;
import robot.windows.components.world.Bullet;
import robot.windows.components.world.Character;
import robot.windows.components.world.Player;
import robot.windows.components.world.Weapon;
import robot.windows.handlers.RandomHandler;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class GameModel {

    public Player player;
    public Set<Character> enemies;
    public HashSet<Shape> obstacles;
    public Set<Bullet> bullets;
    public final PropertyChangeSupport modelObservers;
    public double BULLET_VELOCITY = 6;
    private int ENEMY_DAMAGE;
    private final Point spawnPosition = new Point(794, 86);
    public int score;

    public GameModel() {
        modelObservers = new PropertyChangeSupport(this);
        bullets = ConcurrentHashMap.newKeySet();
        enemies = ConcurrentHashMap.newKeySet();
        obstacles = new HashSet<>();
        setUpEnemies();
        setUpObstacles();
        try {
            Profile.Section modelSection = new Ini(new File("config.ini")).get("model");
            setUpSpawn(modelSection.get("enemy.minHP", Integer.class), modelSection.get("enemy.maxHP", Integer.class));
            BULLET_VELOCITY = modelSection.get("bullet.velocity", Integer.class);
            ENEMY_DAMAGE = modelSection.get("enemy.damage", Integer.class);
            player = new Player(new Point(390, 254), modelSection.get("player.velocity", Integer.class), 20,
                    modelSection.get("player.HP", Integer.class),
                    new Weapon(10, 0.1), new Weapon(500, 0.8));
            score = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                new Character(new Point(840, 200), 100, 200),
                new Character(new Point(840, 600), 30, 60),
                new Character(new Point(560, 600), 10, 20)
        ));
    }

    public void setUpSpawn(int minHP, int maxHP) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                int random = RandomHandler.getRandomInRange(minHP, maxHP);
                spawnEnemy(random, random * 2);
            }
        }, 0, 3000);
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
        Iterator<Character> enemyIterator = enemies.iterator();
        LinkedList<Integer> oldDistances = getDistancesToEnemies();
        while (enemyIterator.hasNext()) {
            Character enemy = enemyIterator.next();
            if(isEnemyDied(enemy)) {
                enemyIterator.remove();
                score += 1;
            }
            handlePlayerReached(player, enemy);
        }
        modelObservers.firePropertyChange("enemyDistance", oldDistances, getDistancesToEnemies());
    }

    private boolean isEnemyDied(Character enemy) {
        if (isEnemyHit(enemy.getHitBox())) {
            return enemy.reduceHPAndCheckDeath(player.getWeapon().getDamage());
        }
        return false;
    }

    private void handlePlayerReached(Player player, Character enemy) {
        if (isIntersects(enemy.getHitBox(), player.getHitBox())) {
            if (player.reduceHPAndCheckDeath(ENEMY_DAMAGE)) {
                player.setHealthPoints(player.getMaxHealthPoints());
                score = 0;
            }
        }
        else
            moveEnemy(enemy, player.getPosition());
    }

    public void spawnEnemy(int hitBoxRadius, int healthPoints) {
        enemies.add(new Character(spawnPosition,hitBoxRadius, healthPoints));
    }

    private synchronized void moveEnemy(Character enemy, Point destination) {
        Point oldPosition = enemy.getPosition();
        double direction = angleBetweenPoints(oldPosition, destination);
        Point newPosition = moveByDirection(oldPosition, direction, enemy.getVelocity());
        if (!isCollisionObstacle(newPosition, enemy.getHitBoxRadius()))
            enemy.setPosition(newPosition);
    }

    public void shoot(Point destination) {
        bullets.add(new Bullet(player.getPosition(), destination));
    }

    public boolean isEnemyHit(Shape hitBox) {
        for (Bullet bullet : bullets)
            if (isIntersects(getHitBoxShape(bullet.getPosition(), bullet.getHitBoxRadius()), hitBox)) {
                bullets.remove(bullet);
                return true;
            }
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

    public synchronized LinkedList<Integer> getDistancesToEnemies() {
        LinkedList<Integer> positions = new LinkedList<>();
        for (Character enemy : enemies) {
            positions.add((int) player.getPosition().distance(enemy.getPosition()));
        }
        return positions;
    }

    public void addModelObserver(PropertyChangeListener observer) {
        modelObservers.addPropertyChangeListener(observer);
    }

    public void setPlayerPosition(Point newPosition) {
        modelObservers.firePropertyChange("playerPosition", player.getPosition(), newPosition);
        player.setPosition(newPosition);
    }
}
