package Trading.Business;

import Trading.Data.CFDDAO;
import java.util.Date;

public class CreatorCFD {

	public CFD factoryMethod(int id, Trader trader, Ativo ativo, float unidades, String type, boolean aberto, float stop_loss, float take_profit, Date data_fecho) {

		if(type.equalsIgnoreCase("LONG")){
			float total = unidades * ativo.getPrecoCompra();
			return new Long(id , stop_loss, take_profit, unidades, total, aberto, ativo, trader, data_fecho);
		}
		else if (type.equalsIgnoreCase("SHORT")){
			float total = unidades * ativo.getPrecoVenda();
			return new Short(id , stop_loss, take_profit, unidades, total, aberto, ativo, trader, data_fecho);
		}
		else return null;
	}


	public CFD loadMethod(int id, Trader trader, Ativo ativo, float unidades, float total, String type, boolean aberto, float stop_loss, float take_profit, Date data_fecho) {

		if(type.equalsIgnoreCase("LONG")){
			return new Long(id , stop_loss, take_profit, unidades, total, aberto, ativo, trader, data_fecho);
		}
		else if (type.equalsIgnoreCase("SHORT")){
			return new Short(id , stop_loss, take_profit, unidades, total, aberto, ativo, trader, data_fecho);
		}
		else return null;
	}
}