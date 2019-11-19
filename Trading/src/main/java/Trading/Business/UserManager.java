package Trading.Business;

import Trading.Data.DAO;
import Trading.Data.TraderDAO;

public class UserManager {

	public void registarTrader(String email, String password, String data_nasc) {
		DAO traderDAO = new TraderDAO();
		int next_id = traderDAO.size() + 1 ;
		Trader t = new Trader(next_id, email, password, data_nasc, 1000);
		// FIXME: 10/11/2019 ver se já existe
		traderDAO.put(next_id, t);
	}

	/**
	 *
	 * @param email
	 * @param password
	 */
	public int login(String email, String password) {
		TraderDAO traderDAO = new TraderDAO();

		Trader t = (Trader) traderDAO.getByEmail(email);

		if(t.getPassword().equals(password)) return t.getID();
		else return -1;
	}

}