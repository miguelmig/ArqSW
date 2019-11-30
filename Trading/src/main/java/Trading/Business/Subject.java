package Trading.Business;

import java.util.*;

public interface Subject {

	void addObserver(Observer observer);

	void removeObserver(Observer observer);

	void notifyObservers(Ativo ativo);


}