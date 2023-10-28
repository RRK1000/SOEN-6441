package gamelog;

/**
 * Shows an observer that can be updated by an observable
 * @author Yusuke Ishii
 */
public interface Observer {
    void update(Observable p_observable, Object p_arg);
}