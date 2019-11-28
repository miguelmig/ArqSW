package Trading.Business;

public interface Observer {

	/**
	 * 
	 * @param ativo
	 */
	void update(Ativo ativo);

}