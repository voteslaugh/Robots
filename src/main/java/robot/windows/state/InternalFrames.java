package robot.windows.state;

import robot.windows.gui.InternalFrame;

import java.io.*;
import java.util.LinkedList;

public class InternalFrames implements Serializable {
    private LinkedList<InternalFrame> internalFrames;

    public InternalFrames() {
        this.internalFrames = new LinkedList<>();
    }

    public void addInternalFrame(InternalFrame frame) {
        internalFrames.add(frame);
    }

    public LinkedList<InternalFrame> getInternalFrames() {
        return internalFrames;
    }

}