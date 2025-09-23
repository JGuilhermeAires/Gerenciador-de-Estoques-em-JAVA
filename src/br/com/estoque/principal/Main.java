package br.com.estoque.principal;

import br.com.estoque.dao.ProdutoDAO;
import br.com.estoque.model.Produto;

public class Main{
    
    public static void main(String[] args){
        Produto produto = new Produto();
        produto.setNome("Caneta");
        produto.setTipo("Escrita");
        produto.setQuantidade(100);
        produto.setEstoqueMinimo(10);

        ProdutoDAO dao = new ProdutoDAO();
        dao.save(produto);

        System.out.println("Produto inserido com sucesso!");
    }
}
