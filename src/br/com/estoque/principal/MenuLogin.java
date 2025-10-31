package br.com.estoque.principal;

import java.util.Optional;
import java.util.Scanner;

import br.com.estoque.dao.UsuarioDAO;
import br.com.estoque.model.Usuario;
import br.com.service.Email;

public class MenuLogin {

    public static void exibir() {
        Scanner sc = new Scanner(System.in);
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        boolean executando = true;

        while (executando) {
            System.out.println("\n====== ItemFlow ======");
            System.out.println("1 - Fazer cadastro");
            System.out.println("2 - Fazer login");
            System.out.println("3 - Sair");
            System.out.print("Escolha uma opção: ");

            int opc = sc.nextInt();
            sc.nextLine();

            switch (opc) {
                case 1:
                    cadastrarUsuario(sc, usuarioDAO);
                    break;
                case 2:
                    Optional<Usuario> usuarioLogado = fazerLogin(sc, usuarioDAO);
                    if (usuarioLogado.isPresent()) {
                        System.out.println("\nLogin realizado com sucesso!");
                        MenuEscolherTipoEstoque.exibir(sc, usuarioLogado.get());
                    } else {
                        System.out.println("\nEmail ou senha incorretos!");
                    }
                    break;
                case 3:
                    executando = false;
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }

        sc.close();
    }
    private static void cadastrarUsuario(Scanner sc, UsuarioDAO dao) {
        System.out.print("Informe seu nome: ");
        String nome = sc.nextLine();

        System.out.print("Informe seu email: ");
        String email = sc.nextLine();

        System.out.print("Informe sua senha: ");
        String senha = sc.nextLine();

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);

        dao.save(usuario);
        System.out.println("Usuário cadastrado com sucesso!");

        Email.enviarEmail(
                usuario.getEmail(),
                "Bem-vindo ao ItemFlow",
                "Olá " + usuario.getNome() + ",\n\nSeu cadastro no sistema foi realizado com sucesso!\n\nAtenciosamente,\nEquipe ItemFlow"
        );
    }
    private static Optional<Usuario> fazerLogin(Scanner sc, UsuarioDAO dao) {
        System.out.print("Informe seu email: ");
        String email = sc.nextLine();

        System.out.print("Informe sua senha: ");
        String senha = sc.nextLine();

        return dao.findByEmailAndSenha(email, senha);
    }
}