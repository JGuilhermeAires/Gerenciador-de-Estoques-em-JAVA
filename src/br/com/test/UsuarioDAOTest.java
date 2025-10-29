package br.com.test;

import br.com.estoque.dao.UsuarioDAO;
import br.com.estoque.model.Usuario;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioDAOTest {

    private static UsuarioDAO usuarioDAO;

    @BeforeAll
    static void setup() {
        usuarioDAO = new UsuarioDAO();
    }

    @Test
    @Order(1)
    void deveSalvarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNome("João Teste");
        usuario.setEmail("joao@teste.com");
        usuario.setSenha("123456");

        assertDoesNotThrow(() -> usuarioDAO.save(usuario), "Deveria salvar o usuário sem lançar exceção");
    }

    @Test
    @Order(2)
    void deveEncontrarUsuarioPorEmailESenha() {
        String email = "joao@teste.com";
        String senha = "123456";

        Optional<Usuario> resultado = usuarioDAO.findByEmailAndSenha(email, senha);

        assertTrue(resultado.isPresent(), "O usuário deveria ser encontrado");
        assertEquals(email, resultado.get().getEmail());
        assertEquals(senha, resultado.get().getSenha());
    }

    @Test
    @Order(3)
    void naoDeveEncontrarUsuarioInexistente() {
        Optional<Usuario> resultado = usuarioDAO.findByEmailAndSenha("inexistente@teste.com", "senha");
        assertTrue(resultado.isEmpty(), "Não deveria encontrar usuário inexistente");
    }
}