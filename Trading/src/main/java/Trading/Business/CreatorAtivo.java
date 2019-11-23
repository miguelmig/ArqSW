package Trading.Business;

public class CreatorAtivo {

	/**
	 *  @param id_ativo
	 * @param nome
	 * @param preco_compra
	 * @param preco_venda
	 * @param tipo
	 */
	public Ativo factoryMethod(String id_ativo, String nome, float preco_compra, float preco_venda, String tipo) {
		if(tipo.equalsIgnoreCase("COMODITY")){
			return new Comodity(id_ativo, nome, preco_compra, preco_venda);
		}
		else if (tipo.equalsIgnoreCase("ACAO")) {
			return new Acao(id_ativo, nome, preco_compra, preco_venda);
		}
		else return null;
	}

}