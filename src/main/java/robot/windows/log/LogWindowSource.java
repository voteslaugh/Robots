package robot.windows.log;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Что починить:
 * 1. Этот класс порождает утечку ресурсов (связанные слушатели оказываются
 * удерживаемыми в памяти)
 * 2. Этот класс хранит активные сообщения лога, но в такой реализации он 
 * их лишь накапливает. Надо же, чтобы количество сообщений в логе было ограничено 
 * величиной queueLength (т.е. реально нужна очередь сообщений
 * ограниченного размера) 
 */
public class LogWindowSource
{
    private int queueLength;
    
    private LinkedBlockingQueue<LogEntry> messages;
    private final ArrayList<LogChangeListener> listeners;
    private volatile LogChangeListener[] activeListeners;
    
    public LogWindowSource(int iQueueLength) 
    {
        queueLength = iQueueLength;
        messages = new LinkedBlockingQueue<>(iQueueLength);
        listeners = new ArrayList<>();
    }
    
    public void registerListener(LogChangeListener listener)
    {
        synchronized(listeners)
        {
            listeners.add(listener);
            activeListeners = null;
        }
    }
    
    public void unregisterListener(LogChangeListener listener)
    {
        synchronized(listeners)
        {
            listeners.remove(listener);
            activeListeners = listeners.toArray(new LogChangeListener[0]);
        }
    }
    
    public void append(LogLevel logLevel, String strMessage)
    {
        LogEntry entry = new LogEntry(logLevel, strMessage);
        messages.add(entry);
        LogChangeListener [] activeListeners = this.activeListeners;
        if (activeListeners == null)
        {
            synchronized (listeners)
            {
                if (this.activeListeners == null)
                {
                    activeListeners = listeners.toArray(new LogChangeListener [0]);
                    this.activeListeners = activeListeners;
                }
            }
        }
        for (LogChangeListener listener : activeListeners)
        {
            listener.onLogChanged();
        }
    }
    
    public int size()
    {
        return messages.size();
    }

    public Iterable<LogEntry> getMessages(int count)
    {
        ArrayList<LogEntry>elements = new ArrayList<>();
        messages.drainTo(elements, count);
        return elements;
    }

    public Iterable<LogEntry> all()
    {
        return messages;
    }
}
