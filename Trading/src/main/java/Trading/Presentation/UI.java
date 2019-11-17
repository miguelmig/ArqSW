package Trading.Presentation;

import Trading.Business.*;

public class UI {


	public static void main(String[] args) {
		Facade facade = new Facade();

		//facade.registaTrader("teste", "teste", "teste");
		facade.printPrecos();
		//facade.abrirCFD(1, "AAPL", "long", 1, 100, 200);
		//facade.fecharCFD(1);
		facade.adicionaFundos(1, 100);
		facade.levantaFundos(1, 200);
	}
}