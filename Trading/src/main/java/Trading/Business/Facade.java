package Trading.Business;

import Trading.Data.TraderDAO;

import java.util.List;

public class Facade {

	UserManager userManager;
	WalletManager walletManager;
	LiveStock liveStock;
	CFDManager CFDManager;

    public void printPrecos(){
        for(Ativo a : liveStock.ativos.values()) System.out.println(a.toString());
    }

    /********************** Live Stock **********************/
    public String getMercado() {
        StringBuilder sb = new StringBuilder();
        List<Ativo> ativos = this.liveStock.getAtivos();
        for(Ativo a : ativos)
            sb.append(a.toString());

        return sb.toString();
    }


    /********************** CFD MANAGER **********************/

    public void abrirCFD(int id_trader, String id_ativo, String tipo, float unidades, float stop_loss, float take_profit){
        TraderDAO traderDAO = new TraderDAO();

        Trader trader = traderDAO.get(id_trader);

        System.out.println(this.liveStock.ativos.size());

        Ativo ativo = liveStock.ativos.get(id_ativo);

        this.CFDManager.abrirCFD(trader, ativo, unidades, tipo, stop_loss, take_profit);
    }

    public void fecharCFD(int id_cfd){
        this.CFDManager.fecharCFD(id_cfd);
    }



    /********************** USER MANAGER **********************/

    public void registaTrader(String email, String password, String data_nasc) {
        this.userManager.registarTrader(email, password, data_nasc);
    }

    public int login(String email, String password) {
        return this.userManager.login(email, password);
    }




    /********************** WALLET MANAGER **********************/

    public void levantaFundos(int id_trader, float valor){
        this.walletManager.removerFundos(id_trader, valor);
    }

    public void adicionaFundos(int id_trader, float valor){
        this.walletManager.adicionarFundos(id_trader, valor);
    }




    public Facade(){
        this.liveStock = new LiveStock();
        this.CFDManager = new CFDManager(this.liveStock);
        this.userManager = new UserManager();
        this.walletManager = new WalletManager();
    }


}