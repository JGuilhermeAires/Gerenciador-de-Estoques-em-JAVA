package br.com.estoque.principal;

import br.com.estoque.dao.ProdutoDAO;
import br.com.estoque.model.Produto;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ProdutoDAO dao = new ProdutoDAO();
        Scanner sc = new Scanner(System.in);

        boolean executando = true;

        while (executando) {
            System.out.println("\n====== MENU ESTOQUE ======");
            System.out.println("1 - Cadastrar produto");
            System.out.println("2 - Atualizar produto");
            System.out.println("3 - Deletar produto");
            System.out.println("4 - Listar todos os produtos");
            System.out.println("5 - Listar todos os produtos abaixo do estoque mínimo");
            System.out.println("6 - Calcular desconto");
            System.out.println("7 - Sair");
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
                    ProdutosAbaixoEstoqueMinimo(dao);
                    break;
                case 6:
                    aplicarDesconto(sc, dao);
                    break;
                case 7:
                    executando = false;
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
        sc.close();
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
    private static void ProdutosAbaixoEstoqueMinimo(ProdutoDAO dao) {
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
        }
    }
}