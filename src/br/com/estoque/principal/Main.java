package br.com.estoque.principal;

import br.com.estoque.dao.ProdutoDAO;
import br.com.estoque.model.Produto;

public class Main {

    public static void main(String[] args) {

        ProdutoDAO dao = new ProdutoDAO();

        Produto produto = new Produto();
        produto.setNome("Cadeira");
        produto.setTipo("Objeto");
        produto.setQuantidade(30);
        produto.setEstoqueMinimo(12);
        dao.save(produto);
        System.out.println("Produto inserido com sucesso!\n");

        Produto p1 = new Produto();
        p1.setId(1);
        p1.setNome("Cadeira Gamer");
        p1.setTipo("Mobiliário");
        p1.setQuantidade(40);
        p1.setEstoqueMinimo(10);
        dao.update(p1);
        System.out.println("Produto atualizado com sucesso!\n");

        dao.deleteById(1);
        System.out.println("Produto removido com sucesso!\n");

        for (Produto p : dao.getProdutos()) {
            System.out.println(
                "ID: " + p.getId() +
                " | Nome: " + p.getNome() +
                " | Tipo: " + p.getTipo() +
                " | Quantidade: " + p.getQuantidade() +
                " | Estoque Mínimo: " + p.getEstoqueMinimo()
            );
        }
    }
}