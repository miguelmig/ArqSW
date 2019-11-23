package Trading.Business;

import Trading.Data.CFDDAO;

public class CreatorCFD {


	public CFD factoryMethod(Trader trader, Ativo ativo, float unidades, String type, boolean aberto, float stop_loss, float take_profit) {

		int next_id = new CFDDAO().size() +1 ;

		if(type.equalsIgnoreCase("LONG")){
			float total = unidades * ativo.getPrecoCompra();
			return new Long(next_id , stop_loss, take_profit, unidades, total, aberto, ativo, trader);
		}
		else if (type.equalsIgnoreCase("SHORT")){
			float total = unidades * ativo.getPrecoVenda();
			return new Short(next_id , stop_loss, take_profit, unidades, total, aberto, ativo, trader);
		}
		else return null;
	}
}