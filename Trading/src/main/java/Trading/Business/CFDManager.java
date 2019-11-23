package Trading.Business;

import Trading.Data.CFDDAO;
import Trading.Data.DAO;
import Trading.Data.TraderDAO;

import java.util.*;

public class CFDManager implements Observer {

	Map<String, List<CFD>> openCFDs;
	LiveStock liveStock;
	Creator creator;

	/**
	 * 
	 * @param id_cfd
	 */
	public void fecharCFD(int id_cfd) {
		CFDDAO cfdDAO = new CFDDAO();
		CFD cfd = cfdDAO.get(id_cfd);

		Trader t = cfd.getTrader();
		float total = cfd.getTotal();

		System.out.println("*** Antes de vender ***\nSaldo: " + t.getSaldo() + "\nCFD: " + cfd.isAberto());

		t.addSaldo(total);
		cfd.fecha();

		System.out.println("*** Depois de vender ***\nSaldo: " + t.getSaldo() + "\nCFD: " + cfd.isAberto());

		DAO traderDAO = new TraderDAO();
		traderDAO.put(t.getID(), t);

		cfdDAO.put(cfd.getID(), cfd);
	}


	public void abrirCFD(Trader trader, Ativo ativo, float unidades, String tipo, float stop_loss, float take_profit) {
		CFD cfd = this.creator.factoryMethod(trader, ativo, unidades, tipo, stop_loss, take_profit);
		addCFDtoMap(cfd);
		System.out.println(cfd.toString());
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

		CFDDAO cfdDAO = new CFDDAO();
		cfdDAO.put(cfd.getID(), cfd);
	}

	public List<CFD> getPortfolio(String email)
	{
		return this.traderCFDManager.getPortfolio(email);
	}

    public CFDManager(LiveStock ls){
		this.creator = new Creator();
		this.openCFDs = new HashMap<>();
		this.liveStock = ls;
		this.traderCFDManager = new TraderCFDManager();
	}
}