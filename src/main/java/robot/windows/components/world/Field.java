package robot.windows.components.world;

import robot.windows.handlers.ConfigHandler;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Field {

    ConfigHandler configHandler = new ConfigHandler();
    private Level easy;
    private Level medium;
    private Level hard;
    private final int scoreToMedium;
    private final int scoreToHard;

    public Field() {
        setUpEasy();
        setUpHard();
        setUpMedium();
        scoreToMedium = configHandler.getInt("model", "level.score.medium");
        scoreToHard = configHandler.getInt("model", "level.score.hard");
    }

    private HashSet<Shape> getBorders() {
        HashSet<Shape> obstacles = new HashSet<>();

        obstacles.add(new Rectangle2D.Double(0, 0, 900, 10));
        obstacles.add(new Rectangle2D.Double(0, 0, 10, 900));
        obstacles.add(new Rectangle2D.Double(0, 890, 900, 10));
        obstacles.add(new Rectangle2D.Double(890, 0, 10, 900));

        return obstacles;
    }

    private void setUpEasy() {
        Point spawn = new Point(794, 86);
        Set<Enemy> enemies = ConcurrentHashMap.newKeySet();
        enemies.addAll(List.of(
                new Enemy(new Point(840, 200), 100, 200),
                new Enemy(new Point(840, 600), 30, 60),
                new Enemy(new Point(560, 600), 10, 20)
        ));

        easy = new Level(enemies, spawn, getBorders(), configHandler.getInt("model", "enemy.easy.minHP"), configHandler.getInt("model", "enemy.easy.maxHP"), configHandler.getInt("model", "enemy.easy.damage"));

        easy.obstacles.add(new Rectangle2D.Double(150, 150, 50, 500));
        easy.obstacles.add(new Rectangle2D.Double(150, 150, 500, 50));
        easy.obstacles.add(new Rectangle2D.Double(600, 150, 50, 500));
        easy.obstacles.add(new Rectangle2D.Double(300, 300, 50, 300));
        easy.obstacles.add(new Rectangle2D.Double(450, 300, 50, 300));
        easy.obstacles.add(new Rectangle2D.Double(300, 300, 200, 50));
        easy.obstacles.add(new Rectangle2D.Double(300, 550, 200, 50));


    }

    private void setUpMedium() {
        Point spawn = new Point(700, 700);
        Set<Enemy> enemies = ConcurrentHashMap.newKeySet();
        enemies.addAll(List.of(
                new Enemy(new Point(840, 200), 100, 200),
                new Enemy(new Point(840, 600), 30, 60)
        ));

        medium = new Level(enemies, spawn, getBorders(), configHandler.getInt("model", "enemy.easy.minHP"), configHandler.getInt("model", "enemy.easy.maxHP"), configHandler.getInt("model", "enemy.easy.damage"));

        medium.obstacles.add(new Rectangle2D.Double(600, 150, 50, 500));


    }

    private void setUpHard() {
        Point spawn = new Point(400, 400);
        Set<Enemy> enemies = ConcurrentHashMap.newKeySet();
        enemies.add(
                new Enemy(new Point(200, 300), 50, 100)
        );

        hard = new Level(enemies, spawn, getBorders(), configHandler.getInt("model", "enemy.hard.minHP"), configHandler.getInt("model", "enemy.hard.maxHP"), configHandler.getInt("model", "enemy.hard.damage"));
    }

    public Level getEasy() {
        return easy;
    }

    public Level getMedium() {
        return medium;
    }

    public Level getHard() {
        return hard;
    }

    public int getScoreToMedium() {
        return scoreToMedium;
    }

    public int getScoreToHard() {
        return scoreToHard;
    }
}
