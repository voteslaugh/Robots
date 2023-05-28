package robot.windows.handlers;

import robot.windows.components.world.*;
import robot.windows.components.world.Character;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class DrawHandler extends JPanel {

    protected double zoomLevel;
    private final ImageHandler imageHandler;

    public DrawHandler() {
        super(true);
        imageHandler = new ImageHandler();
        setFocusable(true);
        zoomLevel = 1;
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_EQUALS -> setZoomLevel(getZoomLevel() + 0.1);
                    case KeyEvent.VK_MINUS -> setZoomLevel(getZoomLevel() - 0.1);
                }
            }
        });
    }

    public void setZoomLevel(double zoomLevel) {
        this.zoomLevel = zoomLevel;
    }

    public double getZoomLevel() {
        return zoomLevel;
    }

    private static void fillCircle(Graphics g, int centerX, int centerY, int diameter) {
        g.fillArc(centerX - diameter / 2, centerY - diameter / 2, diameter, diameter, 0, 360);
    }

    private static void drawCircle(Graphics g, int centerX, int centerY, int diameter) {
        g.drawArc(centerX - diameter / 2, centerY - diameter / 2, diameter, diameter, 0, 360);
    }

    public void drawEnemies(Graphics2D g, Set<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            drawCharacter(g, enemy);
        }
    }

    public void drawPlayer(Graphics2D g, Player player) {
        drawCharacter(g, player);
    }

    private void drawHitBoxImage(Graphics2D g, int posX, int posY, int hitBoxRadius, BufferedImage bufferedImage) {
        g.drawImage(bufferedImage, posX - hitBoxRadius / 2, posY - hitBoxRadius / 2, hitBoxRadius, hitBoxRadius, null);
    }

    private void drawCharacter(Graphics2D g, Character character) {
        int posX = character.getPosition().x;
        int posY = character.getPosition().y;
        int hitBoxRadius = character.getHitBoxRadius();
        drawHitBoxImage(g, posX, posY, hitBoxRadius, imageHandler.getImage(character.getType()));
        drawHPBar(g, character, posX - hitBoxRadius / 2, posY - hitBoxRadius / 2 - 10);
    }

    public void drawObstacles(Graphics2D g, HashSet<Shape> obstacles) {
        for (Shape obstacle : obstacles) {
            g.draw((obstacle));
            g.setColor(Color.GRAY);
            g.fill(obstacle);
        }
    }

    public void drawBullets(Graphics2D g, Set<Bullet> bullets) {
        for (Bullet bullet : bullets) {
            int posX = bullet.getPosition().x;
            int posY = bullet.getPosition().y;
            int hitBoxRadius = bullet.getHitBoxRadius();
            drawHitBoxImage(g, posX, posY, hitBoxRadius, imageHandler.getImage(bullet.getType()));
        }
    }

    public void drawHPBar(Graphics g, Character character, int posX, int posY) {
        int maxHP = character.getMaxHealthPoints();
        int currentHP = character.getHealthPoints();
        int barWidth = character.getHitBoxRadius();
        int greenWidth = (int) ((double) currentHP / maxHP * barWidth);
        int redWidth = barWidth - greenWidth;
        g.setColor(Color.GREEN);
        g.fillRect(posX, posY, greenWidth, 5);
        g.setColor(Color.RED);
        g.fillRect(posX + greenWidth, posY, redWidth, 5);
    }

    public void drawScore(Graphics g, int score) {
        g.setColor(Color.darkGray);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        String text = "Убито: " + score;
        int textWidth = g.getFontMetrics().stringWidth(text);
        int x = textWidth + 10;
        int y = getHeight() - 20;
        g.drawString(text, x, y);
    }

}
