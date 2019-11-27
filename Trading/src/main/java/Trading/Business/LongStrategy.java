package Trading.Business;

public class LongStrategy implements OpenCloseStrategy {
    @Override
    public int abrirCFD(CFD cfd) {
        Trader trader = cfd.getTrader();

        if(checkSaldo(trader, cfd.getTotal())){
            removeFundos(trader, cfd.getTotal());
            return 1;
        }
        else return 0;

    }

    private boolean checkSaldo(Trader trader, float total) {
        return trader.getSaldo() > total;
    }

    private void removeFundos(Trader trader, float total) {
        WalletManager wm = new WalletManager();
        wm.removerFundos(trader.getID(), total);
    }



    @Override
    public void fecharCFD(CFD cfd, Ativo ativo) {
        float unidades = cfd.getUnidades();

        float total = ativo.getPrecoCompra() * unidades;

        adicionaFundos(cfd.getTrader(), total);

        cfd.fecha();
    }

    private void adicionaFundos(Trader trader, float total) {
        WalletManager wm = new WalletManager();
        wm.adicionarFundos(trader.getID(), total);
    }
}