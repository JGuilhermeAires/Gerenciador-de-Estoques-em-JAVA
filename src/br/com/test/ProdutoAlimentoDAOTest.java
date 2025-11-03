package br.com.test;

import br.com.estoque.dao.ProdutoAlimentoDAO;
import br.com.estoque.model.ProdutoAlimento;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProdutoAlimentoDAOTest {

    private static ProdutoAlimentoDAO produtoAlimentoDAO;

    @BeforeAll
    static void setup() {
        produtoAlimentoDAO = new ProdutoAlimentoDAO();
    }

    @Test
    @Order(1)
    void deveSalvarProdutoAlimento() {
        ProdutoAlimento produto = new ProdutoAlimento();
        produto.setNome("Arroz Integral");
        produto.setCategoria("Grãos");
        produto.setDataFabricacao(LocalDate.now().minusDays(15));
        produto.setDataValidade(LocalDate.now().plusDays(60));
        produto.setQuantidade(20);
        produto.setEstoqueMinimo(5);
        produto.setPreco(12.50);
        produto.setFornecedor("Fornecedor Teste");

        assertDoesNotThrow(() -> produtoAlimentoDAO.save(produto),
                "Deveria salvar o produto de alimento sem lançar exceção");
    }

    @Test
    @Order(2)
    void deveRetornarProdutosAlimentos() {
        List<ProdutoAlimento> produtos = produtoAlimentoDAO.getProdutosAlimentos();
        assertFalse(produtos.isEmpty(), "A lista de produtos de alimentos não deveria estar vazia");
    }

    @Test
    @Order(3)
    void deveAtualizarProdutoAlimento() {
        List<ProdutoAlimento> produtos = produtoAlimentoDAO.getProdutosAlimentos();
        ProdutoAlimento produto = produtos.get(0);
        produto.setPreco(produto.getPreco() + 2.0);

        assertDoesNotThrow(() -> produtoAlimentoDAO.update(produto),
                "Deveria atualizar o produto de alimento sem lançar exceção");

        ProdutoAlimento atualizado = produtoAlimentoDAO.getProdutoAlimentoById(produto.getId());
        assertEquals(produto.getPreco(), atualizado.getPreco(),
                "O preço do produto de alimento deveria ser atualizado");
    }

    @Test
    @Order(4)
    void deveRetornarProdutoAlimentoPorId() {
        List<ProdutoAlimento> produtos = produtoAlimentoDAO.getProdutosAlimentos();
        ProdutoAlimento produto = produtos.get(0);

        ProdutoAlimento resultado = produtoAlimentoDAO.getProdutoAlimentoById(produto.getId());
        assertNotNull(resultado, "O produto de alimento deveria ser encontrado pelo ID");
        assertEquals(produto.getNome(), resultado.getNome());
    }

    @Test
    @Order(5)
    void deveRetornarProdutosAbaixoEstoqueMinimo() {
        List<ProdutoAlimento> produtos = produtoAlimentoDAO.getProdutosAbaixoEstoqueMinimo();
        assertNotNull(produtos, "A lista de produtos de alimentos abaixo do estoque mínimo não deveria ser nula");
    }

    @Test
    @Order(6)
    void deveRetornarProdutosVencidosOuProximos() {
        List<ProdutoAlimento> vencidos = produtoAlimentoDAO.getProdutosVencidos();
        List<ProdutoAlimento> proximos = produtoAlimentoDAO.getProdutosProximosDaValidade(7);

        assertNotNull(vencidos, "A lista de produtos vencidos não deveria ser nula");
        assertNotNull(proximos, "A lista de produtos próximos da validade não deveria ser nula");
    }

    @Test
    @Order(7)
    void deveDeletarProdutoAlimentoPorId() {
        List<ProdutoAlimento> produtos = produtoAlimentoDAO.getProdutosAlimentos();
        ProdutoAlimento produto = produtos.get(0);

        assertDoesNotThrow(() -> produtoAlimentoDAO.deleteById(produto.getId()),
                "Deveria deletar o produto de alimento sem lançar exceção");

        ProdutoAlimento deletado = produtoAlimentoDAO.getProdutoAlimentoById(produto.getId());
        assertNull(deletado, "O produto de alimento deveria ter sido deletado");
    }
}