package Trading.Business;

public class Creator {


	public CFD factoryMethod(Trader trader, Ativo ativo, float unidades, String type, float stop_loss, float take_profit) {


		if(type.equalsIgnoreCase("LONG")){
			float total = unidades * ativo.getPrecoCompra();
			return new Long(0 , stop_loss, take_profit, unidades, total, ativo, trader);
		}
		else if (type.equalsIgnoreCase("SHORT")){
			float total = unidades * ativo.getPrecoVenda();
			return new Short(0 , stop_loss, take_profit, unidades, total, ativo, trader);
		}
		else return null;
	}
}