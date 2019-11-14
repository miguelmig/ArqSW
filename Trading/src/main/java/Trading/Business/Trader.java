package Trading.Business;

import java.util.*;

public class Trader {

	List<CFD> currentCFDs;
	private int id;
	private String email;
	private String password;
	private String data_nasc;
	private float saldo;

	public List<CFD> getCurrentCFDs() {
		return currentCFDs;
	}

	public void setCurrentCFDs(List<CFD> currentCFDs) {
		this.currentCFDs = currentCFDs;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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


}