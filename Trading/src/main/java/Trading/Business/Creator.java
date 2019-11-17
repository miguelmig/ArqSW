package Trading.Business;

import Trading.Data.CFDDAO;

public class Creator {


	public CFD factoryMethod(Trader trader, Ativo ativo, float unidades, String type, float stop_loss, float take_profit) {

		int next_id = new CFDDAO().size() +1 ;

		if(type.equalsIgnoreCase("LONG")){
			float total = unidades * ativo.getPrecoCompra();
			return new Long(next_id , stop_loss, take_profit, unidades, total, ativo, trader);
		}
		else if (type.equalsIgnoreCase("SHORT")){
			float total = unidades * ativo.getPrecoVenda();
			return new Short(next_id , stop_loss, take_profit, unidades, total, ativo, trader);
		}
		else return null;
	}
}