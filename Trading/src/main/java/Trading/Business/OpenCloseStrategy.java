package Trading.Business;

public interface OpenCloseStrategy {

	int abrirCFD(CFD cfd);

	void fecharCFD(CFD cfd, Ativo ativo);

}