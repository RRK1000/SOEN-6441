package gamelog;

/**
 * Shows an observable entity
 * @author Yusuke Ishii
 */
public interface Observable {
    /**
     * adds an observer
     * @param p_observer Observer object
     */
    void addObserver(Observer p_observer);

    /**
     * removes an observer
     * @param p_observer observer object
     */
    void removeObserver(Observer p_observer);

    /**
     * Notifies observer
     */
    void notifyObservers();
}
