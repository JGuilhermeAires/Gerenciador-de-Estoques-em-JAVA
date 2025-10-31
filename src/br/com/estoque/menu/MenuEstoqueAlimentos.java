package br.com.estoque.menu;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import br.com.estoque.dao.ProdutoAlimentoDAO;
import br.com.estoque.model.ProdutoAlimento;
import br.com.estoque.model.Usuario;
import br.com.estoque.reports.AlimentosVencidosPDF;
import br.com.service.Email;

public class MenuEstoqueAlimentos {
    public static void exibir(Scanner sc, Usuario usuario) {
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
                    System.out.println("Voltando para o menu de escolha de estoque...");
                    MenuEscolherTipoEstoque.exibir(sc, usuario);
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

}
