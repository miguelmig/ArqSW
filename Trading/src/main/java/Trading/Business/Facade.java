package Trading.Business;

import Trading.Data.TraderDAO;
import Trading.Presentation.UI;

import java.util.List;

public class Facade {

	TraderManager traderManager;
	WalletManager walletManager;
	LiveStock liveStock;
	CFDManager CFDManager;
	UI ui;
	NotificationManager notificationManager;

    public void printPrecos(){
        for(Ativo a : liveStock.ativos.values()) System.out.println(a.toString());
    }

    /********************** Live Stock ***********************/
    public List<Ativo> getMercado() {

        return this.liveStock.getAtivos();
    }


    /********************** CFD MANAGER **********************/

    public int abrirCFD(int id_trader, String id_ativo, String tipo, float unidades, float stop_loss, float take_profit){

        return this.CFDManager.abrirCFD(id_trader, id_ativo, unidades, tipo, stop_loss, take_profit);
    }

    public void fecharCFD(int id_cfd){
        this.CFDManager.fecharCFD(id_cfd);
    }

    public List<CFD> getHistoricoTrader(int id_trader) {
        return this.CFDManager.getHistoricoTrader(id_trader);
    }

    public List<CFD> getPortfolioTrader(int id_trader) {
        return this.CFDManager.getPortfolioTrader(id_trader);
    }


    /********************** USER MANAGER **********************/

    public int registaTrader(String email, String password, String data_nasc) {
        return this.traderManager.registarTrader(email, password, data_nasc);
    }

    public int login(String email, String password) {
        int id_trader = this.traderManager.login(email, password);
        if(id_trader != -1)
            this.notificationManager.setWatchList(new TraderDAO().getAtivoWatchList(id_trader));
        return id_trader;
    }




    /********************** WALLET MANAGER **********************/

    public void levantaFundos(int id_trader, float valor){
        this.walletManager.removerFundos(id_trader, valor);
    }

    public void adicionaFundos(int id_trader, float valor){
        this.walletManager.adicionarFundos(id_trader, valor);
    }

    public float getSaldoTrader(int id_trader) {
        return this.walletManager.getSaldoTrader(id_trader);
    }

    /********************** NOTIFICATION MANAGER ***************/
    public void notifyAtivoChange(Ativo a, float percentage) { this.ui.notifyAtivoChange(a, percentage); }

    public Facade(UI ui){
        this.liveStock = new LiveStock();
        this.CFDManager = new CFDManager(this.liveStock);
        this.traderManager = new TraderManager();
        this.walletManager = new WalletManager();
        this.ui = ui;
        this.notificationManager = new NotificationManager(this, this.liveStock);
    }



}