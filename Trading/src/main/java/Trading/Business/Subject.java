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
	 * @param ativo
	 */
	void notifyObservers(Ativo ativo);


}