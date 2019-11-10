package Trading.Business;

public class Ativo {

	private String id;
	private float preco_venda;
	private float preco_compra;

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public float getPrecoVenda() {
		return preco_venda;
	}

	public void setPrecoVenda(float preco_venda) {
		this.preco_venda = preco_venda;
	}

	public float getPrecoCompra() {
		return preco_compra;
	}

	public void setPrecoCompra(float preco_compra) {
		this.preco_compra = preco_compra;
	}

	public Ativo(){
		this.id = "";
		this.preco_compra = -1;
		this.preco_venda = 1;
	}

	public Ativo(String id, float preco_compra, float preco_venda) {
		this.id = id;
		this.preco_venda = preco_venda;
		this.preco_compra = preco_compra;
	}

	@Override
	public String toString() {
		return "ID: " + id + "\nVenda: " + preco_venda + "€\nCompra: " + preco_compra + "€\n";
	}
}