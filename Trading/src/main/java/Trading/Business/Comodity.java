package Trading.Business;

public class Comodity implements Ativo {

    private String id;
    private float preco_compra;
    private float preco_venda;
    private String nome;

    @Override
    public String getID() {
        return id;
    }

    @Override
    public void setID(String id) {
        this.id = id;
    }

    @Override
    public float getPrecoCompra() {
        return preco_compra;
    }

    @Override
    public void setPrecoCompra(float preco_compra) {
        this.preco_compra = preco_compra;
    }

    @Override
    public float getPrecoVenda() {
        return preco_venda;
    }

    @Override
    public void setPrecoVenda(float preco_venda) {
        this.preco_venda = preco_venda;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return this.id + "   |   Preco compra: " + this.preco_compra + "   |   Preco venda: " + this.preco_venda + "   |   " + this.nome + "\n";
    }

    public Comodity(String id, String nome, float preco_compra, float preco_venda){
        this.id = id;
        this.nome = nome;
        this.preco_compra = preco_compra;
        this.preco_venda = preco_venda;
    }
}