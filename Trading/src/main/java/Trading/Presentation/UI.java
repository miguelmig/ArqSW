package Trading.Presentation;

import Trading.Business.*;

public class UI {

	private static Facade facade;

	public static void main(String[] args) {
		facade = new Facade();

		facade.printPrecos();
	}
}