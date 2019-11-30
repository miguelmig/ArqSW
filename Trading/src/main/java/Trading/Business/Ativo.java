package Trading.Business;

public interface Ativo {

	String getID();

	float getPrecoVenda();

	float getPrecoCompra();

	String getNome();

	/**
	 * 
	 * @param id
	 */
	void setID(String id);

	/**
	 * 
	 * @param preco_venda
	 */
	void setPrecoVenda(float preco_venda);

	/**
	 * 
	 * @param preco_compra
	 */
	void setPrecoCompra(float preco_compra);

	/**
	 * 
	 * @param nome
	 */
	void setNome(String nome);

	String toString();

	boolean equals(Object o);

	Ativo clone();

}