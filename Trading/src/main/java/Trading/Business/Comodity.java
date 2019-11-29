package Trading.Business;

import java.text.DecimalFormat;

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
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        return this.id + "   |   Preco compra: " + df.format(this.preco_compra) + "   |   Preco venda: " + df.format(this.preco_venda) + "   |   " + this.nome + "   |   Comodity \n";
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if ( o==null || this.getClass() != o.getClass()) return false;

        Comodity c = (Comodity) o;

        return this.id == c.getID();
    }

    public Comodity(String id, String nome, float preco_compra, float preco_venda){
        this.id = id;
        this.nome = nome;
        this.preco_compra = preco_compra;
        this.preco_venda = preco_venda;
    }
}