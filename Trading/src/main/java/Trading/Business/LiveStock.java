package Trading.Business;

import java.util.*;

public class LiveStock implements Subject {

	Map<String, Ativo> ativos;
	List<Observer> observers;
	LiveAPI liveAPI;


	public List<Ativo> getMercado() {
		// TODO - implement LiveStock.getMercado
		throw new UnsupportedOperationException();
	}



	@Override
	public void addObserver(Observer observer) {
		this.observers.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {
		this.observers.remove(observer);
	}

	@Override
	public void notifyObservers(String id_ativo) {
		this.observers.forEach(o -> o.update(id_ativo));

	}

	@Override
	public Collection<Observer> getObservers() {
		return null;
	}


	public float getPrecoAtivo(String id_ativo) {
		return this.ativos.get(id_ativo).getPrecoVenda();
	}

	public LiveStock(){
		/*
		DAO ativoDAO = new AtivoDAO();
		this.ativos = new HashMap<>();

		List<Ativo> ativos_list = ativoDAO.list();
		for(Ativo a : ativos_list) this.ativos.put(a.getID(), a); */

		this.ativos = new HashMap<>();
		this.liveAPI = new AlphaVantageAPI();

		Ativo a = new Acao("FB");

		float precos[] = liveAPI.getPrecosAtivo(a.getID());
		a.setPrecoVenda(precos[0]);
		a.setPrecoCompra(precos[1]);

		this.ativos.put(a.getID(), a);
	}


}