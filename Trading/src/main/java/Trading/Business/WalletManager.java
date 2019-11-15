package Trading.Business;

import Trading.Data.TraderDAO;

public class WalletManager {

	/**
	 * 
	 * @param trader
	 * @param valor
	 */
	public void adicionarFundos(Trader trader, float valor) {
		trader.addSaldo(valor);

		TraderDAO traderDAO = new TraderDAO();
		traderDAO.put(trader.getEmail(), trader);
	}

	/**
	 * 
	 * @param trader
	 * @param valor
	 */
	public void removerFundos(Trader trader, float valor) {
		trader.subSaldo(valor);

		TraderDAO traderDAO = new TraderDAO();
		traderDAO.put(trader.getEmail(), trader);
	}

}