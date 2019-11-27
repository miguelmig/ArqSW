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


	public int abrirCFD(int id_trader, String id_ativo, float unidades, String tipo, float stop_loss, float take_profit) {
		int id = new CFDDAO().size() + 1;
		DAO<Integer, Trader> traderDAO = new TraderDAO();

		Trader trader = traderDAO.get(id_trader);

		Ativo ativo = liveStock.ativos.get(id_ativo);

		if(checkSaldo(ativo.getPrecoCompra()*unidades, trader)){
			CFD cfd = this.creatorCFD.factoryMethod(id, trader, ativo, unidades, tipo, true, stop_loss, take_profit, null);
			addCFDtoMap(cfd);

			WalletManager wm = new WalletManager();
			wm.removerFundos(id_trader, cfd.getTotal());

			return 1;
		}
		else return 0;
	}

	private boolean checkSaldo(float valor_total, Trader trader){
		if(valor_total > trader.getSaldo()) return false;
		else return true;
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
		this.openCFDs = new HashMap<>();
		this.liveStock = ls;
		this.traderCFDManager = new TraderCFDManager();

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