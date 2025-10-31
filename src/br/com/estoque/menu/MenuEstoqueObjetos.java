package br.com.estoque.menu;

import java.util.List;
import java.util.Scanner;

import br.com.estoque.dao.ProdutoDAO;
import br.com.estoque.model.Produto;
import br.com.estoque.model.Usuario;
import br.com.estoque.reports.EstoqueMinimoPDF;
import br.com.estoque.reports.PrimeiroPDF;
import br.com.service.Email;

public class MenuEstoqueObjetos {
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
            System.out.println("7 - Gerar relatório de produtos em PDF");
            System.out.println("8 - Gerar relatório de produtos abaixo do estoque minimo em PDF");
            System.out.println("9 - Voltar");
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
                     gerarPdf();
                     break;
                     case 8:
                     EstoqueMinimoPDF();
                     break;
                case 9:
                      executando = false;
                    System.out.println("Voltando para o menu de escolha de estoque...");
                    MenuEscolherTipoEstoque.exibir(sc, usuario);
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

