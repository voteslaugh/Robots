package logic;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

public class GameVisualizer extends JPanel
{
    private volatile Point robotPosition = new Point(100, 100);
    private volatile double robotDirection = 0;

    private volatile Point targetPosition = new Point(150, 100);

    private static final double MAX_VELOCITY = 0.1;
    private static final double MAX_ANGULAR_VELOCITY = 0.001;

    private double distanceToTarget;
    private double angleToTarget;

    public GameVisualizer() 
    {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        scheduler.scheduleAtFixedRate(this::onRedrawEvent, 0, 50, TimeUnit.MILLISECONDS);
        scheduler.scheduleAtFixedRate(this::onModelUpdateEvent, 0, 10, TimeUnit.MILLISECONDS);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setTargetPosition(e.getPoint());
                repaint();
            }
        });
        setDoubleBuffered(true);
    }

    protected void setTargetPosition(Point point) {
        targetPosition = point;
    }

    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }


    private static double angleBetweenPoints(Point p1, Point p2) {
        double angle = Math.atan2(p2.y - p1.y, p2.x - p1.x);
        return asNormalizedRadians(angle);
    }

    protected void onModelUpdateEvent()
    {
        distanceToTarget = targetPosition.distance(robotPosition);
        if (distanceToTarget >= 0.5) {
            double velocity = MAX_VELOCITY;
            double angularVelocity;
            angleToTarget = angleBetweenPoints(targetPosition, robotPosition);
            double angle = asNormalizedRadians(angleToTarget - robotDirection);
            if (angle > Math.PI)
                angularVelocity = MAX_ANGULAR_VELOCITY;
            else
                angularVelocity = -MAX_ANGULAR_VELOCITY;

            if (Math.abs(angle) >= 0.1)
                velocity = distanceToTarget * Math.abs(angularVelocity) / 2;


            moveRobot(velocity, angularVelocity, 10);
        }
    }
    
    private static double applyLimits(double value, double min, double max)
    {
        if (value < min)
            return min;
        else if (value > max)
            return max;
        return value;
    }
    
    private void moveRobot(double velocity, double angularVelocity, double duration)
    {
        velocity = applyLimits(velocity, 0, MAX_VELOCITY);
        angularVelocity = applyLimits(angularVelocity, -MAX_ANGULAR_VELOCITY, MAX_ANGULAR_VELOCITY);
        double newX = robotPosition.getX() + velocity / angularVelocity *
            (Math.sin(robotDirection + angularVelocity * duration) -
                Math.sin(robotDirection));
        if (!Double.isFinite(newX))
        {
            newX = robotPosition.getX() + velocity * duration * Math.cos(robotDirection);
        }
        double newY = robotPosition.getY() - velocity / angularVelocity *
            (Math.cos(robotDirection + angularVelocity * duration) -
                Math.cos(robotDirection));
        if (!Double.isFinite(newY))
        {
            newY = robotPosition.getY() + velocity * duration * Math.sin(robotDirection);
        }
        robotPosition.setLocation(newX, newY);
        robotDirection = asNormalizedRadians(robotDirection + angularVelocity * duration);
    }

    private static double asNormalizedRadians(double angle)
    {
        while (angle < 0)
        {
            angle += 2*Math.PI;
        }
        while (angle >= 2*Math.PI)
        {
            angle -= 2*Math.PI;
        }
        return angle;
    }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g; 
        drawRobot(g2d, (int)robotPosition.getX(), (int)robotPosition.getY(), robotDirection);
        drawTarget(g2d, (int)targetPosition.getX(), (int)targetPosition.getY());
    }
    
    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2)
    {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }
    
    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2)
    {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }
    
    private void drawRobot(Graphics2D g, int x, int y, double direction)
    {
        AffineTransform t = AffineTransform.getRotateInstance(direction, x, y);
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        fillOval(g, x, y, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, x  + 10, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x  + 10, y, 5, 5);
    }
    
    private void drawTarget(Graphics2D g, int x, int y)
    {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0); 
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }
}
