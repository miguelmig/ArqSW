package Trading.Business;

import java.util.*;

public interface Subject {



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

	/**
	 * 
	 * @param id_ativo
	 */
	void notifyObservers(String id_ativo);

	Collection<Observer> getObservers();


}