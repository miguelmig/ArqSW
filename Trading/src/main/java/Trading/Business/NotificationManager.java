package Trading.Business;

import java.util.ArrayList;
import java.util.List;

public class NotificationManager implements Observer
{
    private List<Ativo> watchlist = new ArrayList<>();
    private Facade facade;
    private LiveStock liveStock;

    /*
     * Minima variação necessária para ser emitida uma notificação.
     */
    private static final float NOTIFICATION_THRESHOLD = 0.05f;
    public NotificationManager(Facade facade, LiveStock l)
    {
        this.facade = facade;
        l.addObserver(this);
        liveStock = l;
    }

    public void setWatchList(List<String> watch)
    {
        watchlist = new ArrayList<>();
        if(watch == null)
            return;

        for(String id_ativo : watch)
        {
            Ativo ativo = this.liveStock.getAtivo(id_ativo);
            if(ativo != null)
                watchlist.add(ativo);
        }
        System.out.println("Watching : " + watchlist.get(0).toString());
        update(watchlist.get(0));
    }
    /**
     *
     * @param ativo
     */
    public void update(Ativo ativo)
    {

        for(int i = 0; i < watchlist.size(); ++i)
        {
            Ativo a = watchlist.get(i);
            if(!a.getID().equals(ativo.getID()) )
                continue;

            float difference = a.getPrecoCompra() - ativo.getPrecoCompra();
            float percentage = 1;
            if(ativo.getPrecoCompra() != 0)
            {
                percentage = difference / a.getPrecoCompra();
            }

            if(Math.abs(percentage) > NOTIFICATION_THRESHOLD)
            {
                // Notify!
                facade.notifyAtivoChange(a, percentage);
            }

            watchlist.set(i, ativo);
            return;
        }
    }

}
