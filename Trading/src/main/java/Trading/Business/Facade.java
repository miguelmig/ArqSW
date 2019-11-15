package Trading.Business;

public class Facade {

	UserManager userManager;
	WalletManager walletManager;
	LiveStock liveStock;
	CFDManager CFDManager;

    public void printPrecos(){
        for(Ativo a : liveStock.ativos.values()) System.out.println(a.toString());
    }

    public void abrirCFD(){
        Trader trader = new Trader("teste", "asd", "asd", 0);
        this.CFDManager.abrirCFD(trader, null, 0, 0, 0, 0);
    }

    public void fecharCFD(){
        this.CFDManager.fecharCFD(0);
    }


    public Facade(){
        this.liveStock = new LiveStock();
        this.CFDManager = new CFDManager();
    }
}