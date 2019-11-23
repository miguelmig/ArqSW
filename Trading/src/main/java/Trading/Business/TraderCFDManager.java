package Trading.Business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TraderCFDManager {
    private Map<Integer, List<CFD>> cfds_by_trader;

    TraderCFDManager() {
        cfds_by_trader = new HashMap<>();
    }

    public void addCFD(CFD cfd) {
        int id_trader = cfd.getTrader().getID();
        List<CFD> cfds = cfds_by_trader.get(id_trader);
        if(cfds == null) {
            List<CFD> new_cfd_list = new ArrayList<>();
            new_cfd_list.add(cfd);
            cfds_by_trader.put(id_trader, new_cfd_list);
            return;
        }

        cfds.add(cfd);
    }

    public List<CFD> getPortfolioTrader(int id_trader) {
        List<CFD> cfds = cfds_by_trader.get(id_trader);
        if(cfds == null)
            return new ArrayList<>();

        return cfds;
    }

    public List<CFD> getHistoricoTrader(int id_trader) {
        return null;
    }
}
