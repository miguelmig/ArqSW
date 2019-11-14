package Trading.Business;

import java.util.*;

public class CFDManager implements Observer {

	Map<String, List<CFD>> openCFDs;
	LiveStock liveStock;
	Creator creator;

	/**
	 * 
	 * @param cfd
	 */
	public void fecharCFD(CFD cfd) {
		// TODO - implement CFDManager.fecharCFD
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param trader
	 * @param ativo
	 * @param quantia
	 * @param tipo
	 * @param sl
	 * @param tp
	 */
	public void abrirCFD(Trader trader, Ativo ativo, float quantia, int tipo, int sl, int tp) {
		// TODO - implement CFDManager.abrirCFD
		throw new UnsupportedOperationException();
	}

    @Override
    public void update(String id_ativo) {
        float current_price = liveStock.getPrecoAtivo(id_ativo);

        for(CFD cfd : openCFDs.get(id_ativo)){
            if(cfd.getTakeProfit() <= current_price || cfd.getStopLoss() >= current_price) fecharCFD(cfd);
        }

    }
}