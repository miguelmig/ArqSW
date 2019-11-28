package Trading.Business;

import Trading.Data.DAO;
import Trading.Data.TraderDAO;

public class TraderManager {

	public int registarTrader(String email, String password, String data_nasc) {
		TraderDAO traderDAO = new TraderDAO();
		int next_id = traderDAO.size() + 1 ;
		Trader t = new Trader(next_id, email, password, data_nasc, 1000);

		// FIXME: 10/11/2019 ver se já existe
		if(traderDAO.getByEmail(email) == null){
			traderDAO.put(next_id, t);
			return 1;
		}
		else return 0;
	}

	/**
	 *
	 * @param email
	 * @param password
	 */
	public int login(String email, String password) {
		TraderDAO traderDAO = new TraderDAO();

		Trader t = traderDAO.getByEmail(email);

		if(t.getPassword().equals(password)) return t.getID();
		else return -1;
	}

}