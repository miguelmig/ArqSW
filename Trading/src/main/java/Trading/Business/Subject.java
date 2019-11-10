package Trading.Business;

import java.util.*;

public interface Subject {

	Collection<Observer> getObservers();

	/**
	 * 
	 * @param observer
	 */
	void addObserver(Observer observer);

	/**
	 * 
	 * @param observer
	 */
	void removeObserver(Observer observer);

	void notifyObservers();

}