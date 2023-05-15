package robot.windows.handlers;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter {
    private boolean LMBPressed = false;
    private Point position;

    @Override
    public void mousePressed(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1 -> LMBPressed = true;
        }
        position = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1 -> LMBPressed = false;
        }
        position = e.getPoint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        position = e.getPoint();
    }

    public boolean isLMBPressed() {
        return LMBPressed;
    }

    public Point getPosition() {
        return position;
    }
}
