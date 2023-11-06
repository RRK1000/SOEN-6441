package gamelog;

import java.util.ArrayList;
import java.util.List;

/**
 * Shows a buffer for log entries that notifies observers when updated
 * @author Yusuke Ishii
 */
public class LogEntryBuffer implements Observable {
    private List<Observer> d_observers;
    private String d_actionInfo;

    /**
     * Default constructor for LogEntryBuffer.
     */
    public LogEntryBuffer() {
        d_observers = new ArrayList<>();
    }

    @Override
    public void addObserver(Observer p_observer) {
        d_observers.add(p_observer);
    }

    @Override
    public void removeObserver(Observer p_observer) {
        d_observers.remove(p_observer);
    }

    @Override
    public synchronized void notifyObservers() {
        for (Observer l_observer : d_observers) {
            l_observer.update(this, null);
        }
    }

    /**
     * Sets the action info and notifies observers.
     *
     * @param p_info The action info to set.
     */
    public synchronized void setActionInfo(String p_info) {
        this.d_actionInfo = p_info;
        notifyObservers();
    }

    /**
     * Retrieves the action info.
     *
     * @return The action info.
     */
    public String getActionInfo() {
        return d_actionInfo;
    }
}