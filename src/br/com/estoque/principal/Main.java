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
            System.out.println("5 - Sair");
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

        System.out.print("Preço: ");
        double preco = sc.nextDouble();

        System.out.print("Estoque mínimo: ");
        int estoqueMin = sc.nextInt();
        sc.nextLine(); 

        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setTipo(tipo);
        produto.setQuantidade(quantidade);
        produto.setPreco(preco);
        produto.setEstoqueMinimo(estoqueMin);

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

        System.out.print("Novo preço: ");
        int preco = sc.nextInt();

        System.out.print("Novo estoque mínimo: ");
        int estoqueMin = sc.nextInt();
        sc.nextLine();

        Produto produto = new Produto();
        produto.setId(id);
        produto.setNome(nome);
        produto.setTipo(tipo);
        produto.setQuantidade(quantidade);
        produto.setPreco(preco);
        produto.setEstoqueMinimo(estoqueMin);

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
                    " | Estoque Mínimo: " + p.getEstoqueMinimo()
                );
            }
        }
    }
}