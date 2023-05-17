package robot.windows.handlers;

import java.util.Random;

public class RandomHandler {

    static Random random = new Random();

    public static synchronized int getRandomWithStep(int from, int to, int step) {
        return random.nextInt(to / step - from / step + 1) * step;
    }

}
