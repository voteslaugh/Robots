package robot.log;

public class LogEntry
{
    private LogLevel logLevel;
    private String message;
    
    public LogEntry(LogLevel logLevel, String strMessage)
    {
        message = strMessage;
        this.logLevel = logLevel;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public LogLevel getLevel()
    {
        return logLevel;
    }
}

