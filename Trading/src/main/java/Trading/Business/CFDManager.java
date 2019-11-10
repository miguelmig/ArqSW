package Trading.Business;

import java.util.*;

public class CFDManager implements Observer {

	Collection<CFD> openCFDs;

	/**
	 * 
	 * @param trader
	 * @param cfd
	 */
	public void fecharCFD(Trader trader, CFD cfd) {
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
	public void update() {

	}
}