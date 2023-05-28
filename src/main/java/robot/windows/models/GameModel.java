package robot.windows.models;

import robot.windows.components.world.*;
import robot.windows.handlers.ConfigHandler;
import robot.windows.handlers.RandomHandler;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GameModel {
    public Field field;

    public Player player;
    public Set<Enemy> enemies;
    public HashSet<Shape> obstacles;
    public Set<Drop> drops;
    public Set<Bullet> bullets;
    public final PropertyChangeSupport modelObservers;
    public double BULLET_VELOCITY;
    private int enemyDamage;
    private Point spawnPosition;
    private final Point startPosition = new Point(390, 254);
    public int score;
    public Effect effect;
    Timer spawner = new Timer();
    TimerTask spawnerTask;


    public GameModel() {
        modelObservers = new PropertyChangeSupport(this);
        field = new Field();
        bullets = ConcurrentHashMap.newKeySet();
        enemies = ConcurrentHashMap.newKeySet();
        drops = ConcurrentHashMap.newKeySet();
        obstacles = new HashSet<>();
        BULLET_VELOCITY = ConfigHandler.getInt("model", "bullet.velocity");
        player = new Player(startPosition, ConfigHandler.getInt("model", "player.velocity"), 20,
                ConfigHandler.getInt("model", "player.HP"),
                new Weapon(10, 0.1), new Weapon(500, 0.8));
        effect = new Effect();
        setUpLevel(field.getEasy());
        score = 0;
    }

    private void setUpLevel(Level level) {
        spawnPosition = level.spawn;
        enemies = level.enemies;
        obstacles = level.obstacles;
        enemyDamage = level.enemyDamage;
        setUpSpawn(level.minEnemyHP, level.maxEnemyHP);
    }

    public void setUpSpawn(int minHP, int maxHP) {
        if (spawnerTask != null) {
            spawnerTask.cancel();
        }
        spawnerTask = new TimerTask() {
            @Override
            public void run() {
                int random = RandomHandler.getRandomInRange(minHP, maxHP);
                spawnEnemy(random, random * 2);
            }
        };
        spawner.schedule(spawnerTask, 0, 3000);
    }

    public static double angleBetweenPoints(Point p1, Point p2) {
        return Math.atan2(p2.y - p1.y, p2.x - p1.x);
    }

    public void onModelUpdateEvent() {
        moveBullets();
        moveEnemiesToPlayer();
        handleReachDrop();
        effect.handleEffectStatus();
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
        Iterator<Enemy> enemyIterator = enemies.iterator();
        LinkedList<Integer> oldDistances = getDistancesToEnemies();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();
            if(enemy.reduceHPAndCheckDeath(getDamageToEnemy(enemy.getHitBox()))) {
                handleEnemyDeath(enemy);
                enemyIterator.remove();
                if (score >= field.getScoreToHard())
                    setUpLevel(field.getHard());
                else if(score >= field.getScoreToMedium())
                    setUpLevel(field.getMedium());
            }
            handlePlayerReached(player, enemy);
        }
        modelObservers.firePropertyChange("enemyDistance", oldDistances, getDistancesToEnemies());
    }

    private void handleEnemyDeath(Enemy enemy) {
        if (RandomHandler.getRandomInRange(0, 2) == 1)
            drops.add(new Drop(enemy.getPosition()));
        score += 1;
    }

    private void handleReachDrop() {
        Rectangle2D playerHitBox = player.getHitBox();
        for (Drop drop: drops) {
            if (drop.getHitBox().intersects(playerHitBox)) {
                switch (drop.getType()) {
                    case HEAL -> player.setHealthPoints(Math.min(player.getHealthPoints() + ConfigHandler.getInt("model", "drop.heal"), player.getMaxHealthPoints()));
                    default -> effect.startEffect(drop.getType());

                }
                drops.remove(drop);
            }
        }
    }

    public int getDamageToEnemy(Shape hitBox) {
        int damage = 0;
        for (Bullet bullet : bullets)
            if (isIntersects(getHitBoxShape(bullet.getPosition(), bullet.getHitBoxRadius()), hitBox)) {
                damage += bullet.getDamage() * effect.curDamageMultiply;
                bullets.remove(bullet);
            }
        return damage;
    }

    private void handlePlayerReached(Player player, Enemy enemy) {
        if (isIntersects(enemy.getHitBox(), player.getHitBox())) {
            if (player.reduceHPAndCheckDeath(enemyDamage)) {
                player.setHealthPoints(player.getMaxHealthPoints());
                player.setPosition(startPosition);
                setUpLevel(field.getEasy());
                score = 0;
            }
        }
        else
            moveEnemy(enemy, player.getPosition());
    }

    public void spawnEnemy(int hitBoxRadius, int healthPoints) {
        enemies.add(new Enemy(spawnPosition,hitBoxRadius, healthPoints));
    }

    private synchronized void moveEnemy(Enemy enemy, Point destination) {
        Point oldPosition = enemy.getPosition();
        double direction = angleBetweenPoints(oldPosition, destination);
        double velocity = enemy.getVelocity() / effect.curEnemySlowdown;
        Point newPos = getNewEnemyPosition(oldPosition, direction, velocity, enemy.getHitBoxRadius());
        enemy.setPosition(newPos);
    }

    public static boolean isCollinear(Point p1, Point p2, Point p3) {
        int area = (p1.x * (p2.y - p3.y) + p2.x * (p3.y - p1.y) + p3.x * (p1.y - p2.y));
        return area == 0;
    }

    private Point getNewEnemyPosition(Point oldPosition, double direction, double velocity, int hitBoxRadius) {
        Point newPosition = moveByDirection(oldPosition, direction, velocity);

        if (isCollisionObstacle(newPosition, hitBoxRadius)) {
            if (isCollinear(newPosition, oldPosition, player.getPosition()))
                return oldPosition;
            double angleStep = Math.PI / 8;
            double maxAngle = Math.PI / 2;
            for (double angle = angleStep; angle <= maxAngle; angle += angleStep) {
                Point candidatePoint = moveByDirection(oldPosition, direction + angle, velocity);

                if (!isCollisionObstacle(candidatePoint, hitBoxRadius)) {
                    return candidatePoint;
                }
                candidatePoint = moveByDirection(oldPosition, direction - angle, velocity);

                if (!isCollisionObstacle(candidatePoint, hitBoxRadius)) {
                    return candidatePoint;
                }
            }
        }
        else
            return newPosition;
        return oldPosition;
    }

    public void shoot(Point destination) {
        bullets.add(new Bullet(player.getPosition(), destination, player.getWeapon().getDamage()));
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
        for (Enemy enemy : enemies) {
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
