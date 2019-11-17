package Trading.Presentation;

import Trading.Business.*;

public class UI {

	private static Facade facade;

	public static void main(String[] args) {
		facade = new Facade();

		facade.registaTrader("teste", "teste", "teste");
		facade.printPrecos();
		facade.abrirCFD("teste", "APPL", "long", 1, 100, 200);
		facade.fecharCFD();
	}
}