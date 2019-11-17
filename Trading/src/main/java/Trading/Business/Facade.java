package Trading.Business;

import Trading.Data.TraderDAO;

public class Facade {

	UserManager userManager;
	WalletManager walletManager;
	LiveStock liveStock;
	CFDManager CFDManager;

    public void printPrecos(){
        for(Ativo a : liveStock.ativos.values()) System.out.println(a.toString());
    }

    public void abrirCFD(String email, String id_ativo, String tipo, float unidades, float stop_loss, float take_profit){
        TraderDAO traderDAO = new TraderDAO();

        Trader trader = traderDAO.get(email);
        Ativo ativo = liveStock.ativos.get(id_ativo);

        this.CFDManager.abrirCFD(trader, ativo, unidades, tipo, stop_loss, take_profit);
    }

    public void fecharCFD(){
        this.CFDManager.fecharCFD(0);
    }


    public void registaTrader(String email, String password, String data_nasc) {
        this.userManager.registarTrader(email, password, data_nasc);
    }

    public Facade(){
        this.liveStock = new LiveStock();
        this.CFDManager = new CFDManager(this.liveStock);
        this.userManager = new UserManager();
    }
}