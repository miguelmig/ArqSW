package Trading.Business;

public class ShortStrategy implements OpenCloseStrategy {

    @Override
    public int abrirCFD(CFD cfd) {
        Trader trader = cfd.getTrader();

        if(trader.getSaldo() > 0) return 1;
        else return 0;
    }

    @Override
    public void fecharCFD(CFD cfd, Ativo ativo) {
        float current_price = ativo.getPrecoVenda() * cfd.getUnidades();
        float valor_pago = cfd.getTotal();

        gerirFundos(cfd.getTrader(), current_price - valor_pago);

        cfd.fecha();
    }


    private void gerirFundos(Trader trader, float total) {
        WalletManager wm = new WalletManager();
        if (total > 0) wm.adicionarFundos(trader.getID(), total); // Para ser possível ver o método no stacktrace
        else wm.removerFundos(trader.getID(), -total);
    }
}