package Trading.Business;

import java.util.*;

public class Trader {

	Collection<CFD> currentCFDs;
	private int id;
	private String email;
	private String password;
	private String data_nasc;
	private float saldo;


	public Collection<CFD> getCurrentCFDs() {
		return currentCFDs;
	}

	public void setCurrentCFDs(Collection<CFD> currentCFDs) {
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


	public Trader(String email, String password, String data_nasc, float saldo) {
		this.id = -1;
		this.email = email;
		this.password = password;
		this.data_nasc = data_nasc;
		this.saldo = saldo;
		this.currentCFDs = new ArrayList<>();
	}
}