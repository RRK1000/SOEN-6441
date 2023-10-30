package gamelog;

/**
 * Shows an observable entity
 * @author Yusuke Ishii
 */
public interface Observable {
    void addObserver(Observer p_observer);
    void removeObserver(Observer p_observer);
    void notifyObservers();
}
