package Trading.Business;


import java.util.Date;

public class Long implements CFD {

    private int id;
    private float stop_loss;
    private float take_profit;
    private float unidades;
    private float total;
    private Ativo ativo;
    private boolean aberto;
    private Date data_fecho;
    private Trader trader;


    @Override
    public Trader getTrader() {
        return trader;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void setID(int id) {
        this.id = id;
    }

    @Override
    public float getStopLoss() {
        return stop_loss;
    }

    @Override
    public void setStopLoss(float stop_loss) {
        this.stop_loss = stop_loss;
    }

    @Override
    public float getTakeProfit() {
        return take_profit;
    }

    @Override
    public void setTakeProfit(float take_profit) {
        this.take_profit = take_profit;
    }

    @Override
    public float getUnidades() {
        return unidades;
    }

    @Override
    public void setUnidades(float unidades) {
        this.unidades = unidades;
    }

    @Override
    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    @Override
    public Ativo getAtivo() {
        return ativo;
    }

    @Override
    public void setAtivo(Ativo ativo) {
        this.ativo = ativo;
    }

    @Override
    public void calculaTotal() {

    }

    @Override
    public void fecha() {
        this.aberto = false;
        setDataFecho(new Date());
    }

    @Override
    public boolean isAberto() {
        return this.aberto;
    }


    @Override
    public Date getDataFecho() { return this.data_fecho; }

    @Override
    public void setDataFecho(Date data) { this.data_fecho = data; }


    public Long(int id, float stop_loss, float take_profit, float unidades, float total, boolean aberto, Ativo ativo, Trader trader, Date data_fecho) {
        this.id = id;
        this.stop_loss = stop_loss;
        this.take_profit = take_profit;
        this.unidades = unidades;
        this.total = total;
        this.ativo = ativo;
        this.aberto = aberto;
        this.trader = trader;
        setDataFecho(data_fecho);
    }

    @Override
    public String toString() {
        return "CFD (" + this.getClass().toString() + ")";
    }
}