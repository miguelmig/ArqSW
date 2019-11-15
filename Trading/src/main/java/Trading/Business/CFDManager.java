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
		CFD cfd = this.openCFDs.get("teste").get(0);
		Trader t = cfd.getTrader();
		float total = cfd.getTotal();

		System.out.println("*** Antes de vender ***\nSaldo: " + t.getSaldo() + "\nCFD: " + cfd.isAberto());

		t.addSaldo(total);
		cfd.setAberto(false);

		System.out.println("*** Depois de vender ***\nSaldo: " + t.getSaldo() + "\nCFD: " + cfd.isAberto());


		/*
		DAO traderDAO = new TraderDAO();
		traderDAO.put(t.getID(), t);

		DAO cfdDAO = new CFDDAO();
		cfdDAO.put(cfd.getID(), cfd);*/
	}

	/**
	 * 
	 * @param trader
	 * @param ativo
	 * @param quantia
	 * @param tipo
	 * @param sl
	 * @param tp
	 */
	public void abrirCFD(Trader trader, Ativo ativo, float quantia, int tipo, int sl, int tp) {
		CFD cfd = this.creator.factoryMethod("Long", trader);
		this.openCFDs.put("teste", new ArrayList<CFD>(Arrays.asList(cfd)));
		System.out.println(cfd.toString());
	}

    @Override
    public void update(String id_ativo) {
        float current_price = liveStock.getPrecoAtivo(id_ativo);

        for(CFD cfd : openCFDs.get(id_ativo)){
            if(cfd.getTakeProfit() <= current_price || cfd.getStopLoss() >= current_price) fecharCFD(cfd.getID());
        }

    }

    public CFDManager(){
		this.creator = new Creator();
		this.openCFDs = new HashMap<>();
		this.liveStock = new LiveStock();
	}
}