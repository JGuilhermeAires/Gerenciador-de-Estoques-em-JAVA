package br.com.test;

import br.com.estoque.dao.ProdutoDAO;
import br.com.estoque.model.Produto;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProdutoDAOTest {

    private static ProdutoDAO produtoDAO;

    @BeforeAll
    static void setup() {
        produtoDAO = new ProdutoDAO();
    }

    @Test
    @Order(1)
    void deveSalvarProduto() {
        Produto produto = new Produto();
        produto.setNome("Teclado Gamer");
        produto.setTipo("Eletrônico");
        produto.setQuantidade(5);
        produto.setPreco(200.0);
        produto.setEstoqueMinimo(2);
        produto.setCorredor("A");
        produto.setPrateleira("1");
        produto.setPosicao("3");

        assertDoesNotThrow(() -> produtoDAO.save(produto), "Deveria salvar o produto sem lançar exceção");
    }

    @Test
    @Order(2)
    void deveRetornarProdutos() {
        List<Produto> produtos = produtoDAO.getProdutos();
        assertFalse(produtos.isEmpty(), "A lista de produtos não deveria estar vazia");
    }

    @Test
    @Order(3)
    void deveAtualizarProduto() {
        List<Produto> produtos = produtoDAO.getProdutos();
        Produto produto = produtos.get(0);
        produto.setPreco(produto.getPreco() + 50); // Atualiza o preço

        assertDoesNotThrow(() -> produtoDAO.update(produto), "Deveria atualizar o produto sem lançar exceção");

        Produto atualizado = produtoDAO.getProdutoById(produto.getId());
        assertEquals(produto.getPreco(), atualizado.getPreco(), "O preço deveria ser atualizado");
    }

    @Test
    @Order(4)
    void deveRetornarProdutoPorId() {
        List<Produto> produtos = produtoDAO.getProdutos();
        Produto produto = produtos.get(0);

        Produto resultado = produtoDAO.getProdutoById(produto.getId());
        assertNotNull(resultado, "O produto deveria ser encontrado pelo ID");
        assertEquals(produto.getNome(), resultado.getNome());
    }

    @Test
    @Order(5)
    void deveRetornarProdutosAbaixoEstoqueMinimo() {
        List<Produto> produtos = produtoDAO.getProdutosAbaixoEstoqueMinimo();
        assertNotNull(produtos, "A lista de produtos abaixo do estoque mínimo não deveria ser nula");
    }

    @Test
    @Order(6)
    void deveRetornarPrecoPorId() {
        List<Produto> produtos = produtoDAO.getProdutos();
        Produto produto = produtos.get(0);

        double preco = produtoDAO.getPrecoById(produto.getId());
        assertEquals(produto.getPreco(), preco, "O preço retornado deveria ser igual ao do produto");
    }

    @Test
    @Order(7)
    void deveDeletarProdutoPorId() {
        List<Produto> produtos = produtoDAO.getProdutos();
        Produto produto = produtos.get(0);

        assertDoesNotThrow(() -> produtoDAO.deleteById(produto.getId()), "Deveria deletar o produto sem lançar exceção");

        Produto deletado = produtoDAO.getProdutoById(produto.getId());
        assertNull(deletado, "O produto deveria ter sido deletado");
    }
}