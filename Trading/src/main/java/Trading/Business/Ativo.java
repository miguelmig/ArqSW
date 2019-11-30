package Trading.Business;

public interface Ativo {

	String getID();

	float getPrecoVenda();

	float getPrecoCompra();

	String getNome();

	void setID(String id);

	void setPrecoVenda(float preco_venda);

	void setPrecoCompra(float preco_compra);

	void setNome(String nome);

	String toString();

	boolean equals(Object o);

	Ativo clone();

}