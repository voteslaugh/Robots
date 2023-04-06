package robot.windows.state;

import robot.windows.gui.InternalFrame;
import robot.windows.log.Logger;

import java.io.*;
import java.util.LinkedList;

public class SerializedHandler {
    String filePath;
    InternalFrames internalFrames;

    public SerializedHandler() {
        filePath = "src/main/serialized/internal_frames.ser";
        internalFrames = new InternalFrames();
    }

    public void saveSerialized() {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(internalFrames);
        } catch (IOException e) {
            Logger.error("Couldn't save serialized to ser file");
        }
    }

    private void loadSerialized() {
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            internalFrames = (InternalFrames) objectInputStream.readObject();
        } catch (IOException e) {
            Logger.error("Couldn't save serialized to ser file");
        } catch (ClassNotFoundException e) {
            Logger.error("Couldn't find serialized class when load");
        }
    }

    public void add(InternalFrame internalFrame) {
        internalFrames.addInternalFrame(internalFrame);
    }

    public LinkedList<InternalFrame> getSerializedInternalFrames() {
        loadSerialized();
        return internalFrames.getInternalFrames();
    }
}