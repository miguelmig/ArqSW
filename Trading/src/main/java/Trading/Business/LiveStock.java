package Trading.Business;

import Trading.Data.AtivoDAO;
import Trading.Data.DAO;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LiveStock implements Subject {

	Map<String, Ativo> ativos;
	List<Observer> observers;
	LiveAPI liveAPI;


	private void initMercado() {
		AtivoDAO ativoDAO = new AtivoDAO();

		List<Ativo> ativos_list = ativoDAO.list();
		System.err.println("A ATUALIZAR ATIVOS");
		for(Ativo a : ativos_list) {
			float[] precos = this.liveAPI.getPrecosAtivo(a.getID());

			a.setPrecoVenda(precos[0]);
			a.setPrecoCompra(precos[1]);

			this.ativos.put(a.getID(), a);
			System.err.print(a.toString());
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


	// FIXME: 19/11/2019 ESTE PREÇO DEVE TER EM CONTA SE O CFD FOI LONG OU SHORT?
	public float getPrecoAtivo(String id_ativo) {
		return this.ativos.get(id_ativo).getPrecoVenda();
	}




	public LiveStock(){
		this.ativos = new HashMap<>();
		this.liveAPI = new AlphaVantageAPI();
		initMercado();
		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		exec.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				initMercado();
			}
		}, 75, 75, TimeUnit.SECONDS);
	}


	public List<Ativo> getAtivos() {
		return new ArrayList<>(this.ativos.values());
	}
}