package robot.windows.handlers;

import java.util.Random;

public class RandomHandler {

    static Random random = new Random();

    public static synchronized int getRandomInRange(int from, int to) {
        return random.nextInt(to - from) + from;
    }

}
