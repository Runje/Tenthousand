package de.Runje.tenthousand.observer;

/**
 * Interface for Observable as part of an observer pattern.
 * @author jmayer
 *
 */
public interface IObservable {

    /**
     * method to add a observer.
     * @param s
     */
    void addObserver(IObserver s);

    /**
     * method to remove one specified observer.
     * @param s
     */
    void removeObserver(IObserver s);

    /**
     * method to remove all observer.
     */
    void removeAllObservers();

    /**
     * send a notify.
     */
    void notifyObservers();
}
