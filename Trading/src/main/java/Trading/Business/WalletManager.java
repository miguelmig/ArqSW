package Trading.Business;

import Trading.Data.TraderDAO;

public class WalletManager {

	/**
	 *  @param id_trader
	 * @param valor
	 */
	public void adicionarFundos(int id_trader, float valor) {
		TraderDAO traderDAO = new TraderDAO();
		Trader trader = traderDAO.get(id_trader);

		trader.addSaldo(valor);

		traderDAO.put(id_trader, trader);
	}

	/**
	 *  @param id_trader
	 * @param valor
     */
	public void removerFundos(int id_trader, float valor) {
		TraderDAO traderDAO = new TraderDAO();
		Trader trader = traderDAO.get(id_trader);

		trader.subSaldo(valor);

		traderDAO.put(id_trader, trader);
	}

}