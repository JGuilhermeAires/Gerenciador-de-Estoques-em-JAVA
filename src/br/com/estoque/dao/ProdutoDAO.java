package br.com.estoque.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.estoque.model.Produto;
import br.com.estoque.factory.ConnectionFactory;

public class ProdutoDAO {

    public void save(Produto produto) {
        String sql = "INSERT INTO produtos (nome, tipo, quantidade, preco, estoqueMinimo) VALUES (?, ?, ?, ? ,?)";

        try (Connection conn = ConnectionFactory.createConnectionToSQLServer();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, produto.getNome());
            pstm.setString(2, produto.getTipo());
            pstm.setInt(3, produto.getQuantidade());
            pstm.setDouble(4, produto.getPreco());
            pstm.setInt(5, produto.getEstoqueMinimo());

            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Produto> getProdutos() {
        String sql = "SELECT * FROM produtos";
        List<Produto> produtos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.createConnectionToSQLServer();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rset = pstm.executeQuery()) {

            while (rset.next()) {
                Produto produto = new Produto();
                produto.setId(rset.getInt("id"));
                produto.setNome(rset.getString("nome"));
                produto.setTipo(rset.getString("tipo"));
                produto.setQuantidade(rset.getInt("quantidade"));
                produto.setPreco(rset.getDouble("preco"));
                produto.setEstoqueMinimo(rset.getInt("estoqueMinimo"));

                produtos.add(produto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtos;
    }

    public void update(Produto produto) {
        String sql = "UPDATE produtos SET nome = ?, tipo = ?, quantidade = ?, preco = ?, estoqueMinimo = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.createConnectionToSQLServer();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, produto.getNome());
            pstm.setString(2, produto.getTipo());
            pstm.setInt(3, produto.getQuantidade());
            pstm.setDouble(4, produto.getPreco());
            pstm.setInt(5, produto.getEstoqueMinimo());
            pstm.setInt(6, produto.getId());

            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM produtos WHERE id = ?";

        try (Connection conn = ConnectionFactory.createConnectionToSQLServer();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, id);
            pstm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public double getPrecoById(int id) {
    String sql = "SELECT preco FROM produtos WHERE id = ?";
    double preco = -1;

    try (Connection conn = ConnectionFactory.createConnectionToSQLServer();
         PreparedStatement pstm = conn.prepareStatement(sql)) {

        pstm.setInt(1, id);
        try (ResultSet rset = pstm.executeQuery()) {
            if (rset.next()) {
                preco = rset.getDouble("preco");
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return preco;
}
}