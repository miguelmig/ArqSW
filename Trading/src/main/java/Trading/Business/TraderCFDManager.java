package Trading.Business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TraderCFDManager {
    private Map<String, List<CFD>> cfds_by_email;
    private Map<String, List<CFD>> closed_cfd_history;

    TraderCFDManager() {
        cfds_by_email = new HashMap<>();
    }

    public void addCFD(CFD cfd) {
        String email = cfd.getTrader().getEmail();
        List<CFD> cfds = cfds_by_email.get(email);
        if(cfds == null)
        {
            List<CFD> new_cfd_list = new ArrayList<>();
            new_cfd_list.add(cfd);
            cfds_by_email.put(email, new_cfd_list);
            return;
        }

        cfds.add(cfd);
    }

    public List<CFD> getTraderPortfolio(String email)
    {
        List<CFD> cfds = cfds_by_email.get(email);
        if(cfds == null)
            return new ArrayList<>();

        return cfds;
    }

    public List<CFD> getTraderHistory(String email)
    {
        List<CFD> cfds = closed_cfd_history.get(email);
        if(cfds == null) {

            return new ArrayList<>();
        }

        return cfds;
    }
}
