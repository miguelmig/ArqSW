package Trading.Business;


public class Long implements CFD {

    private int id;
    private float stop_loss;
    private float take_profit;
    private float unidades;
    private float total;
    private Ativo ativo;


    @Override
    public Trader getTrader() {
        return null;
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

}