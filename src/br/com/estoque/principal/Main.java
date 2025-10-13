package br.com.estoque.principal;

import br.com.estoque.dao.ProdutoAlimentoDAO;
import br.com.estoque.dao.ProdutoDAO;
import br.com.estoque.dao.UsuarioDAO;
import br.com.estoque.model.Produto;
import br.com.estoque.model.ProdutoAlimento;
import br.com.estoque.model.Usuario;
import br.com.service.Email;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import br.com.estoque.reports.EstoqueMinimoPDF;
import br.com.estoque.reports.PrimeiroPDF;
import br.com.estoque.reports.AlimentosVencidosPDF;

public class Main {
       public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        boolean executando = true;

        while (executando) {
            System.out.println("\n====== SISTEMA DE ESTOQUE ======");
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
        menuEscolherTipoEstoque(sc, usuarioLogado.get());
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

    private static Optional<Usuario> fazerLogin(Scanner sc, UsuarioDAO dao) {
    System.out.print("Informe seu email: ");
    String email = sc.nextLine();

    System.out.print("Informe sua senha: ");
    String senha = sc.nextLine();

    return dao.findByEmailAndSenha(email, senha);
}

private static void menuEscolherTipoEstoque(Scanner sc, Usuario usuario) {
    boolean escolhendo = true;
    while (escolhendo) {
        System.out.println("\n====== ESCOLHA O TIPO DE ESTOQUE ======");
        System.out.println("1 - Estoque de Alimentos");
        System.out.println("2 - Estoque de Materiais / Objetos");
        System.out.println("3 - Voltar");
        System.out.print("Escolha uma opção: ");

        int escolha = sc.nextInt();
        sc.nextLine();

        switch (escolha) {
            case 1:
            System.out.println("\nVocê está acessando o ESTOQUE DE ALIMENTOS");
            menuEstoqueAlimentos(sc, usuario);
            escolhendo = false;
            break;
            case 2:
                System.out.println("\nVocê está acessando o ESTOQUE DE MATERIAIS / OBJETOS");
                menuPrincipal(sc, usuario, "OBJETOS");
                escolhendo = false;
                break;
            case 3:
                escolhendo = false;
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }
}
private static void menuEstoqueAlimentos(Scanner sc, Usuario usuario) {
    ProdutoAlimentoDAO dao = new ProdutoAlimentoDAO();
    boolean executando = true;

    while (executando) {
        System.out.println("\n====== MENU ESTOQUE DE ALIMENTOS ======");
        System.out.println("1 - Cadastrar alimento");
        System.out.println("2 - Atualizar alimento");
        System.out.println("3 - Deletar alimento");
        System.out.println("4 - Listar todos os alimentos");
        System.out.println("5 - Listar alimento por ID");
        System.out.println("6 - Produtos abaixo do estoque mínimo");
        System.out.println("7 - Alimentos vencidos");
        System.out.println("8 - Alimentos próximos da validade");
         System.out.println("9 - Gerar relatorio de alimentos vencidos");
        System.out.println("10 - Voltar");
        System.out.print("Escolha uma opção: ");

        int opcao = sc.nextInt();
        sc.nextLine();

        switch (opcao) {
            case 1:
                cadastrarProduto(sc, dao);
                break;
            case 2:
                atualizarProduto(sc, dao);
                break;
            case 3:
                deletarProduto(sc, dao);
                break;
            case 4:
                listarProdutos(dao);
                break;
            case 5:
            System.out.print("Informe o ID do produto: ");
            int id = sc.nextInt();
            sc.nextLine();

            ProdutoAlimento p = dao.getProdutoAlimentoById(id);
            if (p != null) {
            System.out.println("\n=== Produto encontrado ===");
            System.out.println(p);
             } else {
            System.out.println("Produto não encontrado.");
            }
            break;
            case 6:
                produtosAbaixoEstoqueMinimo(dao, usuario);
                break;
            case 7:
                produtosVencidos(dao, usuario);
                break;
            case 8:
                System.out.print("Informe o número de dias para alerta de validade próxima: ");
                int diasAlerta = sc.nextInt();
                sc.nextLine();
                produtosProximosValidade(dao, usuario, diasAlerta);
                break;
            case 9:
                AlimentosVencidosPDF();
                break;
            case 10:
                executando = false;
                System.out.println("Saindo do menu de alimentos...");
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }
}

    private static void cadastrarProduto(Scanner sc, ProdutoAlimentoDAO dao) {
        System.out.print("Nome do produto: ");
        String nome = sc.nextLine();

        System.out.print("Categoria: ");
        String categoria = sc.nextLine();

        System.out.print("Data de fabricação (YYYY-MM-DD): ");
        LocalDate fab = LocalDate.parse(sc.nextLine());

        System.out.print("Data de validade (YYYY-MM-DD): ");
        LocalDate val = LocalDate.parse(sc.nextLine());

        System.out.print("Quantidade: ");
        int quantidade = sc.nextInt();
        sc.nextLine();

        System.out.print("Estoque mínimo: ");
        int estoqueMin = sc.nextInt();
        sc.nextLine();

        System.out.print("Preço: ");
        double preco = sc.nextDouble();
        sc.nextLine();

        System.out.print("Fornecedor: ");
        String fornecedor = sc.nextLine();

        ProdutoAlimento produto = new ProdutoAlimento();
        produto.setNome(nome);
        produto.setCategoria(categoria);
        produto.setDataFabricacao(fab);
        produto.setDataValidade(val);
        produto.setQuantidade(quantidade);
        produto.setEstoqueMinimo(estoqueMin);
        produto.setPreco(preco);
        produto.setFornecedor(fornecedor);

        dao.save(produto);
        System.out.println("Produto cadastrado com sucesso!");
    }

    private static void atualizarProduto(Scanner sc, ProdutoAlimentoDAO dao) {
        System.out.print("ID do produto a atualizar: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Novo nome: ");
        String nome = sc.nextLine();

        System.out.print("Nova categoria: ");
        String categoria = sc.nextLine();

        System.out.print("Nova data de fabricação (YYYY-MM-DD): ");
        LocalDate fab = LocalDate.parse(sc.nextLine());

        System.out.print("Nova data de validade (YYYY-MM-DD): ");
        LocalDate val = LocalDate.parse(sc.nextLine());

        System.out.print("Nova quantidade: ");
        int quantidade = sc.nextInt();
        sc.nextLine();

        System.out.print("Novo estoque mínimo: ");
        int estoqueMin = sc.nextInt();
        sc.nextLine();

        System.out.print("Novo preço: ");
        double preco = sc.nextDouble();
        sc.nextLine();

        System.out.print("Novo fornecedor: ");
        String fornecedor = sc.nextLine();

        ProdutoAlimento produto = new ProdutoAlimento();
        produto.setId(id);
        produto.setNome(nome);
        produto.setCategoria(categoria);
        produto.setDataFabricacao(fab);
        produto.setDataValidade(val);
        produto.setQuantidade(quantidade);
        produto.setEstoqueMinimo(estoqueMin);
        produto.setPreco(preco);
        produto.setFornecedor(fornecedor);

        dao.update(produto);
        System.out.println("Produto atualizado com sucesso!");
    }

    private static void deletarProduto(Scanner sc, ProdutoAlimentoDAO dao) {
        System.out.print("ID do produto a deletar: ");
        int id = sc.nextInt();
        sc.nextLine();

        dao.deleteById(id);
        System.out.println("Produto removido com sucesso!");
    }

    private static void listarProdutos(ProdutoAlimentoDAO dao) {
        List<ProdutoAlimento> produtos = dao.getProdutosAlimentos();
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto encontrado.");
        } else {
            System.out.println("\n=== Lista de Produtos ===");
            for (ProdutoAlimento p : produtos) {
                System.out.println(p);
            }
        }
    }

    private static void produtosAbaixoEstoqueMinimo(ProdutoAlimentoDAO dao, Usuario usuario) {
        List<ProdutoAlimento> produtos = dao.getProdutosAbaixoEstoqueMinimo();
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto abaixo do estoque mínimo.");
            return;
        }

        System.out.println("\n=== Produtos abaixo do estoque mínimo ===");
        String mensagem = "Atenção! Produtos abaixo do estoque mínimo:\n\n";
        for (ProdutoAlimento p : produtos) {
            System.out.println(p);
            mensagem += "- " + p.getNome() + " (Qtd: " + p.getQuantidade() + ", Mínimo: " + p.getEstoqueMinimo() + ")\n";
        }

        Email.enviarEmail(usuario.getEmail(), "⚠️ Alerta: Estoque mínimo", mensagem);
        System.out.println("\n📧 E-mail enviado para " + usuario.getEmail());
    }

    private static void produtosVencidos(ProdutoAlimentoDAO dao, Usuario usuario) {
        List<ProdutoAlimento> produtos = dao.getProdutosVencidos();
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto vencido.");
            return;
        }

        System.out.println("\n=== Produtos vencidos ===");
        String mensagem = "Atenção! Produtos vencidos:\n\n";
        for (ProdutoAlimento p : produtos) {
            System.out.println(p);
            mensagem += "- " + p.getNome() + " (Validade: " + p.getDataValidade() + ")\n";
        }

        Email.enviarEmail(usuario.getEmail(), "⚠️ Alerta: Produtos vencidos", mensagem);
        System.out.println("\n📧 E-mail enviado para " + usuario.getEmail());
    }

    private static void produtosProximosValidade(ProdutoAlimentoDAO dao, Usuario usuario, int dias) {
    List<ProdutoAlimento> produtos = dao.getProdutosProximosDaValidade(dias);
    if (produtos.isEmpty()) {
        System.out.println("Nenhum produto próximo da validade.");
        return;
    }

    System.out.println("\n=== Produtos próximos da validade ===");
    String mensagem = "Atenção! Produtos próximos da validade:\n\n";
    for (ProdutoAlimento p : produtos) {
        System.out.println(p);
        mensagem += "- " + p.getNome() + " (Validade: " + p.getDataValidade() + ")\n";
    }

    Email.enviarEmail(usuario.getEmail(), "⚠️ Alerta: Produtos próximos da validade", mensagem);
    System.out.println("\n📧 E-mail enviado para " + usuario.getEmail());
}

private static void AlimentosVencidosPDF(){
    new AlimentosVencidosPDF();
}


    public static void menuPrincipal(Scanner sc, Usuario usuario, String tipoEstoque) {
        ProdutoDAO dao = new ProdutoDAO();

        boolean executando = true;

        while (executando) {
            System.out.println("\n====== MENU ESTOQUE ======");
            System.out.println("1 - Cadastrar produto");
            System.out.println("2 - Atualizar produto");
            System.out.println("3 - Deletar produto");
            System.out.println("4 - Listar todos os produtos");
            System.out.println("5 - Listar apenas um produto");
            System.out.println("6 - Listar todos os produtos abaixo do estoque mínimo");
            System.out.println("7 - Calcular desconto");
            System.out.println("8 - Gerar relatório de proodutos em PDF");
            System.out.println("9 - Gerar relatório de proodutos abaixo do estoque minimo em PDF");
            System.out.println("10 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = sc.nextInt();
            sc.nextLine(); 

            switch (opcao) {
                case 1:
                    cadastrarProduto(sc, dao);
                    break;
                case 2:
                    atualizarProduto(sc, dao);
                    break;
                case 3:
                    deletarProduto(sc, dao);
                    break;
                case 4:
                    listarProdutos(dao);
                    break;
                case 5:
                    produtoById(sc ,dao);
                    break;
                    case 6:
                    ProdutosAbaixoEstoqueMinimo(dao, usuario);
                    break;
                case 7:
                    aplicarDesconto(sc, dao);
                    break;
                     case 8:
                     gerarPdf();
                     break;
                     case 9:
                     EstoqueMinimoPDF();
                     break;
                case 10:
                    executando = false;
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void cadastrarProduto(Scanner sc, ProdutoDAO dao) {
    System.out.print("Nome do produto: ");
    String nome = sc.nextLine();

    System.out.print("Tipo do produto: ");
    String tipo = sc.nextLine();

    System.out.print("Quantidade: ");
    int quantidade = sc.nextInt();
    sc.nextLine();

    System.out.print("Preço: ");
    double preco = sc.nextDouble();
    sc.nextLine();

    System.out.print("Estoque mínimo: ");
    int estoqueMin = sc.nextInt();
    sc.nextLine();

    System.out.print("Corredor que se encontra o produto: ");
    String corredor = sc.nextLine();

    System.out.print("Prateleira que se encontra o produto: ");
    String prateleira = sc.nextLine();

    System.out.print("Posição que se encontra o produto: ");
    String posicao = sc.nextLine();

    Produto produto = new Produto();
    produto.setNome(nome);
    produto.setTipo(tipo);
    produto.setQuantidade(quantidade);
    produto.setPreco(preco);
    produto.setEstoqueMinimo(estoqueMin);
    produto.setCorredor(corredor);
    produto.setPrateleira(prateleira);
    produto.setPosicao(posicao);

    dao.save(produto);
    System.out.println("Produto cadastrado com sucesso!");
}

private static void cadastrarUsuario(Scanner sc, UsuarioDAO dao){
    System.out.print("Informe seu nome:");
    String nome = sc.nextLine();

    System.out.print("Informe seu email:");
    String email = sc.nextLine();

    System.out.print("Informe sua senha:");
    String senha = sc.nextLine();

    Usuario usuario = new Usuario();
    usuario.setNome(nome);
    usuario.setEmail(email);
    usuario.setSenha(senha);

    dao.save(usuario);
    System.out.println("Usuario cadastrado com sucesso!");

   Email.enviarEmail(
    usuario.getEmail(),
    "Bem-vindo ao Sistema de Estoque",
    "Olá " + usuario.getNome() + ",\n\nSeu cadastro no sistema foi realizado com sucesso!\n\nAtenciosamente,\nEquipe do ItemFlow"
);
}

    private static void atualizarProduto(Scanner sc, ProdutoDAO dao) {
    System.out.print("ID do produto a atualizar: ");
    int id = sc.nextInt();
    sc.nextLine();

    System.out.print("Novo nome: ");
    String nome = sc.nextLine();

    System.out.print("Novo tipo: ");
    String tipo = sc.nextLine();

    System.out.print("Nova quantidade: ");
    int quantidade = sc.nextInt();
    sc.nextLine();

    System.out.print("Novo preço: ");
    double preco = sc.nextDouble();
    sc.nextLine();

    System.out.print("Novo estoque mínimo: ");
    int estoqueMin = sc.nextInt();
    sc.nextLine();

    System.out.print("Novo corredor do produto: ");
    String corredor = sc.nextLine();

    System.out.print("Nova prateleira do produto: ");
    String prateleira = sc.nextLine();

    System.out.print("Nova posição do produto: ");
    String posicao = sc.nextLine();

    Produto produto = new Produto();
    produto.setId(id);
    produto.setNome(nome);
    produto.setTipo(tipo);
    produto.setQuantidade(quantidade);
    produto.setPreco(preco);
    produto.setEstoqueMinimo(estoqueMin);
    produto.setCorredor(corredor);
    produto.setPrateleira(prateleira);
    produto.setPosicao(posicao);

    dao.update(produto);
    System.out.println("Produto atualizado com sucesso!");
}
    private static void deletarProduto(Scanner sc, ProdutoDAO dao) {
        System.out.print("ID do produto a deletar: ");
        int id = sc.nextInt();
        sc.nextLine();

        dao.deleteById(id);
        System.out.println("Produto removido com sucesso!");
    }

    private static void listarProdutos(ProdutoDAO dao) {
        List<Produto> produtos = dao.getProdutos();
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto encontrado.");
        } else {
            System.out.println("\n=== Lista de Produtos ===");
            for (Produto p : produtos) {
                System.out.println(
                    "ID: " + p.getId() +
                    " | Nome: " + p.getNome() +
                    " | Tipo: " + p.getTipo() +
                    " | Quantidade: " + p.getQuantidade() +
                    " | Preço: " + p.getPreco() +
                    " | Estoque Mínimo: " + p.getEstoqueMinimo() +
                    " | Corredor: " + p.getCorredor() +
                    " | Prateleira: " + p.getPrateleira() +
                    " | Posição: " + p.getPosicao()
                );
            }
        }
    }
    private static void aplicarDesconto(Scanner sc, ProdutoDAO dao) {
    System.out.print("Digite o ID do produto: ");
    int id = sc.nextInt();

    double preco = dao.getPrecoById(id);

    if (preco == -1) {
        System.out.println("Produto não encontrado!");
        return;
    }

    System.out.print("Percentual de desconto (%): ");
    double desconto = sc.nextDouble();

    double valorFinal = preco - (preco * (desconto / 100.0));
    System.out.printf("Preço original: R$ %.2f%n", preco);
    System.out.printf("Preço com %.2f%% de desconto: R$ %.2f%n", desconto, valorFinal);
}
    private static void ProdutosAbaixoEstoqueMinimo(ProdutoDAO dao, Usuario usuario) {
        List<Produto> produtos = dao.getProdutosAbaixoEstoqueMinimo();
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto abaixo do estoque minimo.");
        } else {
            System.out.println("\n=== Lista de Produtos ===");
            for (Produto p : produtos) {
                System.out.println(
                    "ID: " + p.getId() +
                    " | Nome: " + p.getNome() +
                    " | Tipo: " + p.getTipo() +
                    " | Quantidade: " + p.getQuantidade() +
                    " | Preço: " + p.getPreco() +
                    " | Estoque Mínimo: " + p.getEstoqueMinimo() +
                    " | Corredor: " + p.getCorredor() +
                    " | Prateleira: " + p.getPrateleira() +
                    " | Posição: " + p.getPosicao()
                );
            }
            String mensagem = "Atenção! Existem produtos abaixo do estoque mínimo.\n\n";
        for (Produto p : produtos) {
            mensagem += "- " + p.getNome() + " (Qtd: " + p.getQuantidade() +
                        ", Mínimo: " + p.getEstoqueMinimo() + ")\n";
        }

       Email.enviarEmail(
    usuario.getEmail(),
    "⚠️ Alerta: Produtos abaixo do estoque mínimo",
    mensagem
);

System.out.println("\n📧 E-mail de alerta enviado para " + usuario.getEmail());
        }
    }
    private static void produtoById(Scanner sc, ProdutoDAO dao) {
    System.out.print("Digite o ID do produto: ");
    int id = sc.nextInt();
    sc.nextLine();

    Produto p = dao.getProdutoById(id);
    if (p != null) {
        System.out.println("\n=== Produto encontrado ===");
        System.out.println(
            "ID: " + p.getId() +
            " | Nome: " + p.getNome() +
            " | Tipo: " + p.getTipo() +
            " | Quantidade: " + p.getQuantidade() +
            " | Preço: " + p.getPreco() +
            " | Estoque Mínimo: " + p.getEstoqueMinimo() +
            " | Corredor: " + p.getCorredor() +
            " | Prateleira: " + p.getPrateleira() +
            " | Posição: " + p.getPosicao()
        );
    } else {
        System.out.println("Produto não encontrado.");
    }
}
    private static void gerarPdf() {
    new PrimeiroPDF();
}
    private static void EstoqueMinimoPDF() {
        new EstoqueMinimoPDF();
    }
 
}
