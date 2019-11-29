package Trading.Business;

import java.util.*;

public class Trader {

	List<CFD> currentCFDs;
	private int id;
	private String email;
	private String password;
	private String data_nasc;
	private float saldo;
	private List<Ativo> watchlist;


	public List<CFD> getCurrentCFDs() {
		return currentCFDs;
	}

	public void setCurrentCFDs(List<CFD> currentCFDs) {
		this.currentCFDs = currentCFDs;
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDataNasc() {
		return data_nasc;
	}

	public void setDataNasc(String data_nasc) {
		this.data_nasc = data_nasc;
	}

	public float getSaldo() {
		return saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	public Trader() {
		this.id = -1;
		this.email = "";
		this.password = "";
		this.data_nasc = "";
		this.saldo = 0;
		this.currentCFDs = new ArrayList<>();
	}

	public void setWatchlist(List<Ativo> watchlist) {
		this.watchlist = watchlist;
	}

	public List<Ativo> getWatchlist() {
		return watchlist;
	}

	public Trader(int id, String email, String password, String data_nasc, float saldo) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.data_nasc = data_nasc;
		this.saldo = saldo;
		this.currentCFDs = new ArrayList<>();
		this.watchlist = new ArrayList<>();
	}



	/**
	 *
	 * @param valor
	 */
    public void subSaldo(float valor) {
		this.saldo -= valor;

	}

    public void addSaldo(float valor) {
		this.saldo += valor;
    }


}