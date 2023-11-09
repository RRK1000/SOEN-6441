package gamelog;

/**
 * Shows an observer that can be updated by an observable
 *
 * @author Yusuke Ishii
 */
public interface Observer {
    /**
     * Updates observers
     *
     * @param p_observable observable object
     * @param p_arg        Argument object
     */
    void update(Observable p_observable, Object p_arg);
}