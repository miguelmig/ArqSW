package Trading.Business;

import Trading.Data.CFDDAO;
import Trading.Data.DAO;
import Trading.Data.TraderDAO;

import java.util.*;

public class CFDManager implements Observer {

	Map<String, List<CFD>> openCFDs;
	LiveStock liveStock;
	CreatorCFD creatorCFD;
	TraderCFDManager traderCFDManager;

	/**
	 * 
	 * @param id_cfd
	 */
	public void fecharCFD(int id_cfd) {
		CFDDAO cfdDAO = new CFDDAO();
		CFD cfd = cfdDAO.get(id_cfd);
		cfd.setDataFecho(new Date());

		removeCFDFromMap(cfd);

		Trader t = cfd.getTrader();
		float total = cfd.getTotal();

		t.addSaldo(total);
		cfd.fecha();

		DAO traderDAO = new TraderDAO();
		traderDAO.put(t.getID(), t);

		cfdDAO.put(cfd.getID(), cfd);
	}

	private void removeCFDFromMap(CFD cfd) {
		List<CFD> open_by_type = this.openCFDs.get(cfd.getAtivo().getID());
		open_by_type.remove(cfd);
		this.openCFDs.put(cfd.getAtivo().getID(), open_by_type);

		this.traderCFDManager.removeCFD(cfd);

	}


	public void abrirCFD(Trader trader, Ativo ativo, float unidades, String tipo, float stop_loss, float take_profit) {
		int id = new CFDDAO().size() + 1;
		CFD cfd = this.creatorCFD.factoryMethod(id, trader, ativo, unidades, tipo, true, stop_loss, take_profit, null);
		addCFDtoMap(cfd);
	}

    @Override
    public void update(String id_ativo) {
        float current_price = liveStock.getPrecoAtivo(id_ativo);

        for(CFD cfd : openCFDs.get(id_ativo)){
            if(cfd.getTakeProfit() <= current_price || cfd.getStopLoss() >= current_price) fecharCFD(cfd.getID());
        }

    }

    private void addCFDtoMap(CFD cfd){
		List<CFD> l = this.openCFDs.getOrDefault(cfd.getAtivo().getID(), new ArrayList<>());
		l.add(cfd);
		this.openCFDs.put(cfd.getAtivo().getID(), l);

		this.traderCFDManager.addCFD(cfd);

		CFDDAO cfdDAO = new CFDDAO();
		cfdDAO.put(cfd.getID(), cfd);
	}


	public List<CFD> getPortfolioTrader(int id_trader) {
		return this.traderCFDManager.getPortfolioTrader(id_trader);
	}

	public List<CFD> getHistoricoTrader(int id_trader){
		return this.traderCFDManager.getHistoricoTrader(id_trader);
	}

    public CFDManager(LiveStock ls){
		this.creatorCFD = new CreatorCFD();
		this.openCFDs = new HashMap<>(); // FIXME: 23/11/2019 tem que ir buscar os CFDs abertos À DB!
		this.liveStock = ls;
		this.traderCFDManager = new TraderCFDManager();

		// FIXME: 23/11/2019 FAZER INITCFDMANAGER porque o traderCFDManager também precisa de ser carregado
		initCFDManager();
	}

	private void initCFDManager() {
		DAO<Integer, CFD> cfdDAO = new CFDDAO();

		List<CFD> cfds = cfdDAO.list();

		for(CFD cfd : cfds){
			if(cfd.isAberto()) addCFDtoMap(cfd);
		}
	}
}