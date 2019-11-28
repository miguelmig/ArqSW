package Trading.Business;

import Trading.Data.AtivoDAO;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LiveStock implements Subject {

	Map<String, Ativo> ativos;
	List<Observer> observers;
	LiveAPI liveAPI;


	private void updateMercado() {
		AtivoDAO ativoDAO = new AtivoDAO();

		List<Ativo> ativos_list = ativoDAO.list();
		System.err.println("A ATUALIZAR ATIVOS");
		for(Ativo ativo : ativos_list) {
			float[] precos = this.liveAPI.getPrecosAtivo(ativo.getID());
			float preco_venda_temp = ativo.getPrecoVenda();
			float preco_compra_temp = ativo.getPrecoCompra();

			ativo.setPrecoVenda(precos[0]);
			ativo.setPrecoCompra(precos[1]);

			// Notificar apenas quando os preços mudam
			if(preco_compra_temp != precos[1] || preco_venda_temp != precos[0]) notifyObservers(ativo.getID());

			this.ativos.put(ativo.getID(), ativo);
			System.err.print(ativo.toString());
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


	// FIXME: 19/11/2019 ESTE PREÇO DEVE TER EM CONTA SE O CFD FOI LONG OU SHORT?
	public float getPrecoAtivo(String id_ativo) {
		return this.ativos.get(id_ativo).getPrecoCompra();
	}

	public List<Ativo> getAtivos() {
		return new ArrayList<>(this.ativos.values());
	}


	public LiveStock(){
		this.ativos = new HashMap<>();
		this.liveAPI = new AlphaVantageAPI();
		this.observers = new ArrayList<>();
		updateMercado();

		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		exec.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				updateMercado();
			}
		}, 75, 75, TimeUnit.SECONDS);
	}

}