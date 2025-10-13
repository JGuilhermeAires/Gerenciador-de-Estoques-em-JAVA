package br.com.estoque.reports;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import org.openpdf.text.Document;
import org.openpdf.text.DocumentException;
import org.openpdf.text.Paragraph;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfWriter;

import br.com.estoque.model.ProdutoAlimento;
import br.com.estoque.dao.ProdutoAlimentoDAO;

public class AlimentosVencidosPDF {
    public AlimentosVencidosPDF(){
        Document documentPDF = new Document();

        try {
            PdfWriter.getInstance(documentPDF, new FileOutputStream("RelatorioAlimentosVencidos.pdf"));
            documentPDF.open();

            documentPDF.add(new Paragraph("Relatório de Alimentos Vencidos\n\n"));

            PdfPTable tabela = new PdfPTable(9);
            tabela.addCell("ID");
            tabela.addCell("Nome");
            tabela.addCell("Categoria");
            tabela.addCell("Data Fabricação");
            tabela.addCell("Data Validade");
            tabela.addCell("Quantidade");
            tabela.addCell("Estoque Minimo");
            tabela.addCell("Preço");
            tabela.addCell("Fornecedor");

            ProdutoAlimentoDAO dao = new ProdutoAlimentoDAO();
            List<ProdutoAlimento> produtosAlimentos = dao.getProdutosVencidos();

        for (ProdutoAlimento p : produtosAlimentos) {
    tabela.addCell(String.valueOf(p.getId()));
    tabela.addCell(p.getNome());
    tabela.addCell(p.getCategoria());
    tabela.addCell(String.valueOf(p.getQuantidade()));
    tabela.addCell(String.valueOf(p.getEstoqueMinimo()));
    tabela.addCell(String.format("%.2f", p.getPreco()));
    tabela.addCell(String.valueOf(p.getDataFabricacao()));
    tabela.addCell(String.valueOf(p.getDataValidade()));
    tabela.addCell(p.getFornecedor());
}

            documentPDF.add(tabela);

            System.out.println("PDF gerado com sucesso: RelatorioAlimentosVencidos.pdf");

        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            documentPDF.close();
        }
    }
}
