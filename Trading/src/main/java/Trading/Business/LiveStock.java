package Trading.Business;

import Trading.Data.AtivoDAO;
import Trading.Data.DAO;

import java.util.*;

public class LiveStock implements Subject {

	Map<String, Ativo> ativos;
	List<Observer> observers;
	LiveAPI liveAPI;


	private void initMercado() {
		AtivoDAO ativoDAO = new AtivoDAO();

		List<Ativo> ativos_list = ativoDAO.list();

		for(Ativo a : ativos_list) {
			float[] precos = this.liveAPI.getPrecosAtivo(a.getID());

			a.setPrecoVenda(precos[0]);
			a.setPrecoCompra(precos[1]);

			this.ativos.put(a.getID(), a);
			System.out.println(a.toString());
		}
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
		this.ativos = new HashMap<>();
		this.liveAPI = new AlphaVantageAPI();

		initMercado();
	}


}