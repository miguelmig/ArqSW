package Trading.Business;

import java.util.*;

public class LiveStock implements Subject {

	Collection<Ativo> ativos;

	public List<Ativo> getMercado() {
		// TODO - implement LiveStock.getMercado
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<Observer> getObservers() {
		return null;
	}

	@Override
	public void addObserver(Observer observer) {

	}

	@Override
	public void removeObserver(Observer observer) {

	}

	@Override
	public void notifyObservers() {

	}
}