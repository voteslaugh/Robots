package robot.log;

public enum LogLevel
{
    Trace(0),
    Debug(1),
    Info(2),
    Warning(3),
    Error(4),
    Fatal(5);

    private int level;

    LogLevel(int level)
    {
        this.level = level;
    }

    public int getLevel()
    {
        return level;
    }
}

