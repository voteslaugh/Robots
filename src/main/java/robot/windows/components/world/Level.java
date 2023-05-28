package robot.windows.components.world;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Level {

    public Set<Enemy> enemies;
    public Point spawn;
    public HashSet<Shape> obstacles;
    public int minEnemyHP;
    public int maxEnemyHP;
    public int enemyDamage;

    public Level(Set<Enemy> enemies, Point spawn, HashSet<Shape> obstacles, int minEnemyHP, int maxEnemyHP, int enemyDamage) {
        this.enemies = enemies;
        this.spawn = spawn;
        this.obstacles = obstacles;
        this.minEnemyHP = minEnemyHP;
        this.maxEnemyHP = maxEnemyHP;
        this.enemyDamage = enemyDamage;
    }
}
