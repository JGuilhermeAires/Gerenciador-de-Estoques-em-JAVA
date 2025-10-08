package br.com.estoque.reports;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import org.openpdf.text.Document;
import org.openpdf.text.DocumentException;
import org.openpdf.text.Paragraph;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfWriter;

import br.com.estoque.dao.ProdutoDAO;
import br.com.estoque.model.Produto;

public class EstoqueMinimoPDF {
    public EstoqueMinimoPDF(){
        Document documentPDF = new Document();

        try {
            PdfWriter.getInstance(documentPDF, new FileOutputStream("RelatorioProdutosAbaixoEstoqueMinimo.pdf"));
            documentPDF.open();

            documentPDF.add(new Paragraph("Relatório de Produtos Cadastrados Abaixo do Estoque Minimo\n\n"));

            PdfPTable tabela = new PdfPTable(9);
            tabela.addCell("ID");
            tabela.addCell("Nome");
            tabela.addCell("Tipo");
            tabela.addCell("Qtd");
            tabela.addCell("Preço");
            tabela.addCell("Estoque Mínimo");
            tabela.addCell("Corredor");
            tabela.addCell("Prateleira");
            tabela.addCell("Posição");

            ProdutoDAO dao = new ProdutoDAO();
            List<Produto> produtos = dao.getProdutosAbaixoEstoqueMinimo();

            for (Produto p : produtos) {
                tabela.addCell(String.valueOf(p.getId()));
                tabela.addCell(p.getNome());
                tabela.addCell(p.getTipo());
                tabela.addCell(String.valueOf(p.getQuantidade()));
                tabela.addCell(String.format("%.2f", p.getPreco()));
                tabela.addCell(String.valueOf(p.getEstoqueMinimo()));
                tabela.addCell(p.getCorredor());
                tabela.addCell(p.getPrateleira());
                tabela.addCell(p.getPosicao());
            }

            documentPDF.add(tabela);

            System.out.println("PDF gerado com sucesso: RelatorioProdutos.pdf");

        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            documentPDF.close();
        }
    }
}
