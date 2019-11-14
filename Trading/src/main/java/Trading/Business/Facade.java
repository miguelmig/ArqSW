package Trading.Business;

public class Facade {

    private LiveStock livestock;
	UserManager userManager;
	WalletManager walletManager;
	LiveStock liveStock;
	CFDManager CFDManager;

    public void printPrecos(){
        for(Ativo a : livestock.ativos.values()) System.out.println(a.toString());
    }

    public Facade(){
        this.livestock = new LiveStock();
    }
}