package de.runje.tenthousand.observer;

/**
 * Interface for Observer as part of an observer pattern.
 * @author jmayer
 *
 */
public interface IObserver {
    /**
     * Called from Observable when new data is available.
     */
    void update();
}
