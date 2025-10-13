package br.com.estoque.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.estoque.factory.ConnectionFactory;
import br.com.estoque.model.ProdutoAlimento;

public class ProdutoAlimentoDAO {

    public void save(ProdutoAlimento produtoAlimento) {
        String sql = """
            INSERT INTO produtos_alimentos
            (nome, categoria, data_fabricacao, data_validade, quantidade, estoque_minimo, preco, fornecedor)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = ConnectionFactory.createConnectionToSQLServer();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, produtoAlimento.getNome());
            pstm.setString(2, produtoAlimento.getCategoria());
            pstm.setDate(3, java.sql.Date.valueOf(produtoAlimento.getDataFabricacao()));
            pstm.setDate(4, java.sql.Date.valueOf(produtoAlimento.getDataValidade()));
            pstm.setInt(5, produtoAlimento.getQuantidade());
            pstm.setInt(6, produtoAlimento.getEstoqueMinimo());
            pstm.setDouble(7, produtoAlimento.getPreco());
            pstm.setString(8, produtoAlimento.getFornecedor());

            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ProdutoAlimento> getProdutosAlimentos() {
        String sql = "SELECT * FROM produtos_alimentos";
        List<ProdutoAlimento> produtosAlimentos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.createConnectionToSQLServer();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rset = pstm.executeQuery()) {

            while (rset.next()) {
                ProdutoAlimento produtoAlimento = new ProdutoAlimento();
                produtoAlimento.setId(rset.getInt("id"));
                produtoAlimento.setNome(rset.getString("nome"));
                produtoAlimento.setCategoria(rset.getString("categoria"));
                produtoAlimento.setDataFabricacao(rset.getDate("data_fabricacao").toLocalDate());
                produtoAlimento.setDataValidade(rset.getDate("data_validade").toLocalDate());
                produtoAlimento.setQuantidade(rset.getInt("quantidade"));
                produtoAlimento.setEstoqueMinimo(rset.getInt("estoque_minimo"));
                produtoAlimento.setPreco(rset.getDouble("preco"));
                produtoAlimento.setFornecedor(rset.getString("fornecedor"));

                produtosAlimentos.add(produtoAlimento);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtosAlimentos;
    }

    public void update(ProdutoAlimento produtoAlimento) {
        String sql = """
            UPDATE produtos_alimentos
               SET nome = ?, categoria = ?, data_fabricacao = ?, data_validade = ?,
                   quantidade = ?, estoque_minimo = ?, preco = ?, fornecedor = ?
             WHERE id = ?
        """;

        try (Connection conn = ConnectionFactory.createConnectionToSQLServer();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, produtoAlimento.getNome());
            pstm.setString(2, produtoAlimento.getCategoria());
            pstm.setDate(3, java.sql.Date.valueOf(produtoAlimento.getDataFabricacao()));
            pstm.setDate(4, java.sql.Date.valueOf(produtoAlimento.getDataValidade()));
            pstm.setInt(5, produtoAlimento.getQuantidade());
             pstm.setInt(6, produtoAlimento.getEstoqueMinimo());
            pstm.setDouble(7, produtoAlimento.getPreco());
            pstm.setString(8, produtoAlimento.getFornecedor());
            pstm.setInt(9, produtoAlimento.getId());
            

            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     public void deleteById(int id) {
        String sql = "DELETE FROM produtos_alimentos WHERE id = ?";

        try (Connection conn = ConnectionFactory.createConnectionToSQLServer();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, id);
            pstm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ProdutoAlimento> getProdutosVencidos() {
    String sql = "SELECT * FROM produtos_alimentos WHERE data_validade <= ?";
    List<ProdutoAlimento> produtosVencidos = new ArrayList<>();

    try (Connection conn = ConnectionFactory.createConnectionToSQLServer();
         PreparedStatement pstm = conn.prepareStatement(sql)) {

       
        pstm.setDate(1, java.sql.Date.valueOf(LocalDate.now()));

        try (ResultSet rset = pstm.executeQuery()) {
            while (rset.next()) {
                ProdutoAlimento produto = new ProdutoAlimento();
                produto.setId(rset.getInt("id"));
                produto.setNome(rset.getString("nome"));
                produto.setCategoria(rset.getString("categoria"));
                produto.setDataFabricacao(rset.getDate("data_fabricacao").toLocalDate());
                produto.setDataValidade(rset.getDate("data_validade").toLocalDate());
                produto.setQuantidade(rset.getInt("quantidade"));
                produto.setEstoqueMinimo(rset.getInt("estoque_minimo"));
                produto.setPreco(rset.getDouble("preco"));
                produto.setFornecedor(rset.getString("fornecedor"));

                produtosVencidos.add(produto);
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return produtosVencidos;
}

public List<ProdutoAlimento> getProdutosProximosDaValidade(int diasAlerta) {
    String sql = "SELECT * FROM produtos_alimentos WHERE data_validade > ? AND data_validade <= ?";
    List<ProdutoAlimento> produtosProximos = new ArrayList<>();

    LocalDate hoje = LocalDate.now();
    LocalDate limite = hoje.plusDays(diasAlerta);
    try (Connection conn = ConnectionFactory.createConnectionToSQLServer();
         PreparedStatement pstm = conn.prepareStatement(sql)) {

        pstm.setDate(1, java.sql.Date.valueOf(hoje));
        pstm.setDate(2, java.sql.Date.valueOf(limite));

        try (ResultSet rset = pstm.executeQuery()) {
            while (rset.next()) {
                ProdutoAlimento produto = new ProdutoAlimento();
                produto.setId(rset.getInt("id"));
                produto.setNome(rset.getString("nome"));
                produto.setCategoria(rset.getString("categoria"));
                produto.setDataFabricacao(rset.getDate("data_fabricacao").toLocalDate());
                produto.setDataValidade(rset.getDate("data_validade").toLocalDate());
                produto.setQuantidade(rset.getInt("quantidade"));
                produto.setEstoqueMinimo(rset.getInt("estoque_minimo"));
                produto.setPreco(rset.getDouble("preco"));
                produto.setFornecedor(rset.getString("fornecedor"));

                produtosProximos.add(produto);
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return produtosProximos;
}

public List<ProdutoAlimento> getProdutosAbaixoEstoqueMinimo() {
    String sql = "SELECT * FROM produtos_alimentos WHERE quantidade < estoque_minimo";
    List<ProdutoAlimento> produtos = new ArrayList<>();

    try (Connection conn = ConnectionFactory.createConnectionToSQLServer();
         PreparedStatement pstm = conn.prepareStatement(sql);
         ResultSet rset = pstm.executeQuery()) {

        while (rset.next()) {
            ProdutoAlimento produto = new ProdutoAlimento();
            produto.setId(rset.getInt("id"));
            produto.setNome(rset.getString("nome"));
            produto.setCategoria(rset.getString("categoria"));
            produto.setDataFabricacao(rset.getDate("data_fabricacao").toLocalDate());
            produto.setDataValidade(rset.getDate("data_validade").toLocalDate());
            produto.setQuantidade(rset.getInt("quantidade"));
            produto.setEstoqueMinimo(rset.getInt("estoque_minimo"));
            produto.setPreco(rset.getDouble("preco"));
            produto.setFornecedor(rset.getString("fornecedor"));

            produtos.add(produto);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return produtos;
}
public ProdutoAlimento getProdutoAlimentoById(int id) {
    String sql = "SELECT * FROM produtos_alimentos WHERE id = ?";
    ProdutoAlimento produto = null;

    try (Connection conn = ConnectionFactory.createConnectionToSQLServer();
         PreparedStatement pstm = conn.prepareStatement(sql)) {

        pstm.setInt(1, id);

        try (ResultSet rset = pstm.executeQuery()) {
            if (rset.next()) {
                produto = new ProdutoAlimento();
                produto.setId(rset.getInt("id"));
                produto.setNome(rset.getString("nome"));
                produto.setCategoria(rset.getString("categoria"));
                produto.setDataFabricacao(rset.getDate("data_fabricacao").toLocalDate());
                produto.setDataValidade(rset.getDate("data_validade").toLocalDate());
                produto.setQuantidade(rset.getInt("quantidade"));
                produto.setEstoqueMinimo(rset.getInt("estoque_minimo"));
                produto.setPreco(rset.getDouble("preco"));
                produto.setFornecedor(rset.getString("fornecedor"));
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return produto;
}
}