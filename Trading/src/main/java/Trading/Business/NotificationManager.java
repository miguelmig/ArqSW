package Trading.Business;

import Trading.Data.DAO;
import Trading.Data.TraderDAO;

import java.util.ArrayList;
import java.util.List;

public class NotificationManager implements Observer
{
    private List<Ativo> watchlist = new ArrayList<>();
    private Facade facade;
    private LiveStock liveStock;

    private static final float NOTIFICATION_THRESHOLD = 0.05f; // Minima variação necessária para ser emitida uma notificação.

    public NotificationManager(Facade facade, LiveStock l)
    {
        this.facade = facade;
        l.addObserver(this);
        liveStock = l;
    }

    public void setWatchList(List<Ativo> watch)
    {
        watchlist = new ArrayList<>();
        if(watch == null)
            return;

        for(Ativo a : watch) {
            Ativo ativo = this.liveStock.getAtivo(a.getID());
            if(ativo != null)
                watchlist.add(ativo.clone());
        }
    }


    public void update(Ativo ativo) {

        for(int i = 0; i < watchlist.size(); i++) {
            Ativo a = watchlist.get(i);
            if(!a.getID().equals(ativo.getID()))
                continue;
            float difference = ativo.getPrecoCompra() - a.getPrecoCompra();
            float percentage = 1;
            if(ativo.getPrecoCompra() != 0) {
                percentage = difference / a.getPrecoCompra();
            }

            if(Math.abs(percentage) > NOTIFICATION_THRESHOLD) {
                // Notify!
                facade.notifyAtivoChange(a, percentage);
            }

            watchlist.set(i, ativo.clone());
            return;
        }
    }

    public List<Ativo> getWatchlist() {
        return this.watchlist;
    }

    public void removeWatchlist(int id_trader, Ativo ativo) {
        this.watchlist.remove(ativo);
        saveWatchList(id_trader);
    }

    public void adicionaWatchlist(int id_trader, Ativo ativo) {
        this.watchlist.add(ativo.clone());
        saveWatchList(id_trader);
    }

    private void saveWatchList(int id_trader) {
        DAO<Integer, Trader> traderDAO = new TraderDAO();
        Trader trader = traderDAO.get(id_trader);
        trader.setWatchlist(this.watchlist);

        traderDAO.put(id_trader, trader);
    }

    public void reset(int id_trader) {
        DAO<Integer, Trader> traderDAO = new TraderDAO();
        Trader trader = traderDAO.get(id_trader);

        trader.setWatchlist(watchlist);

        traderDAO.put(trader.getID(), trader);

        this.watchlist = new ArrayList<>();
    }
}
