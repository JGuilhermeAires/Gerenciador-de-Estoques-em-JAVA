package br.com.estoque.dao;

import br.com.estoque.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import br.com.estoque.factory.ConnectionFactory;

public class UsuarioDAO {
    
    public void save(Usuario usuario){
        String sql = "INSERT INTO usuarios (nome, email, senha) VALUES (?,?,?)";
         try (Connection conn = ConnectionFactory.createConnectionToSQLServer();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, usuario.getNome());
            pstm.setString(2, usuario.getEmail());
            pstm.setString(3, usuario.getSenha());

            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Optional<Usuario> findByEmailAndSenha(String email, String senha) {
        String sql = """
            SELECT * FROM usuarios WHERE email = ? AND senha = ?
        """;

        try (Connection conn = ConnectionFactory.createConnectionToSQLServer();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, email);
            pstm.setString(2, senha);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setSenha(rs.getString("senha"));

                    return Optional.of(usuario);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}


