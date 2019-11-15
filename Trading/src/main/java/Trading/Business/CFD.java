package Trading.Business;

public interface CFD {

	Trader getTrader();

	int getID();

	float getStopLoss();

	float getTakeProfit();

	float getUnidades();

	float getTotal();

	Ativo getAtivo();

	/**
	 * 
	 * @param id
	 */
	void setID(int id);

	/**
	 * 
	 * @param stop_loss
	 */
	void setStopLoss(float stop_loss);

	/**
	 * 
	 * @param take_profit
	 */
	void setTakeProfit(float take_profit);

	/**
	 * 
	 * @param unidades
	 */
	void setUnidades(float unidades);

	/**
	 * 
	 * @param ativo
	 */
	void setAtivo(Ativo ativo);

	void calculaTotal();

    void fecha();

	boolean isAberto();

}