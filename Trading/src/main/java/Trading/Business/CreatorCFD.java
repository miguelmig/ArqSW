package Trading.Business;

import Trading.Data.CFDDAO;
import java.util.Date;

public class CreatorCFD {

	public CFD factoryMethod(Trader trader, Ativo ativo, float unidades, String type, boolean aberto, float stop_loss, float take_profit, Date data_fecho) {

		int next_id = new CFDDAO().size() +1 ;

		if(type.equalsIgnoreCase("LONG")){
			float total = unidades * ativo.getPrecoCompra();
			return new Long(next_id , stop_loss, take_profit, unidades, total, aberto, ativo, trader, data_fecho);
		}
		else if (type.equalsIgnoreCase("SHORT")){
			float total = unidades * ativo.getPrecoVenda();
			return new Short(next_id , stop_loss, take_profit, unidades, total, aberto, ativo, trader, data_fecho);
		}
		else return null;
	}
}