package br.com.estoque.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import br.com.estoque.model.Produto;        
import br.com.estoque.factory.ConnectionFactory;

public class ProdutoDAO {

    public void save(Produto produto) {
        String sql = "INSERT INTO produtos (nome, tipo, quantidade, estoqueMinimo) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.createConnectionToSQLServer();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, produto.getNome());
            pstm.setString(2, produto.getTipo());
            pstm.setInt(3, produto.getQuantidade());
            pstm.setInt(4, produto.getEstoqueMinimo());

            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}