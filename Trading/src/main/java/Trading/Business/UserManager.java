package Trading.Business;

import Trading.Data.DAO;
import Trading.Data.TraderDAO;

import java.sql.*;

public class UserManager {

	/**
	 * 
	 * @param email
	 * @param password
	 * @param data_nasc
	 */
	public void registarTrader(String email, String password, String data_nasc) {
		DAO traderDAO = new TraderDAO();
		Trader t = new Trader(-1, email, password, data_nasc, 1000);
		// FIXME: 10/11/2019 ver se já existe e por o nextID
		traderDAO.put(email, t);
	}

	/**
	 * 
	 * @param email
	 * @param password
	 */
	public int login(String email, String password) {
		DAO traderDAO = new TraderDAO();

		Trader t = (Trader) traderDAO.get(email);

		if(t.getPassword().equals(password)) return 1;
		else return 0;
	}

}