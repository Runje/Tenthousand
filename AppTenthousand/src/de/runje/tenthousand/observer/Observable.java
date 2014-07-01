package de.runje.tenthousand.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of Observable as List<IObserver>.
 * @author jmayer
 *
 */
public class Observable implements IObservable {

    private List<IObserver> subscribers = new ArrayList<IObserver>(2);

    @Override
    public void addObserver(IObserver s) {
        subscribers.add(s);
    }

    @Override
    public void removeObserver(IObserver s) {
        subscribers.remove(s);
    }

    @Override
    public void removeAllObservers() {
        subscribers.clear();
    }

    @Override
    public void notifyObservers() {
        for (IObserver iObserver : subscribers) {
            iObserver.update();
        }
    }
}
