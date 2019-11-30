package Trading.Business;

import java.util.Date;

public interface CFD {

	Trader getTrader();

	int getID();

	float getStopLoss();

	float getTakeProfit();

	float getUnidades();

	float getTotal();

	Ativo getAtivo();

	void setID(int id);

	void setStopLoss(float stop_loss);

	void setTakeProfit(float take_profit);

	void setUnidades(float unidades);

	void setAtivo(Ativo ativo);

	void calculaTotal();

    void fecha();

	boolean isAberto();

	Date getDataFecho();

	void setDataFecho(Date data);

	boolean equals(Object o);
}