package br.com.estoque.principal;

import java.util.Scanner;

import br.com.estoque.model.Usuario;

public class MenuEscolherTipoEstoque {

    public static void exibir(Scanner sc, Usuario usuario) {
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
                    MenuEstoqueAlimentos.exibir(sc, usuario);
                    escolhendo = false;
                    break;
                case 2:
                    System.out.println("\nVocê está acessando o ESTOQUE DE MATERIAIS / OBJETOS");
                    MenuEstoqueObjetos.menuPrincipal(sc, usuario, "OBJETOS");
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
}
