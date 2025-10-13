package br.com.estoque.model;

import java.time.LocalDate;

public class ProdutoAlimento {
    private int id;
    private String nome;
    private String categoria;
    private LocalDate dataFabricacao;
    private LocalDate dataValidade;
    private int quantidade;
    private int estoqueMinimo;
    private double preco;
    private String fornecedor;
    
    public ProdutoAlimento(){}
    
    public ProdutoAlimento(int id, String nome, String categoria, LocalDate dataFabricacao, LocalDate dataValidade,
            int quantidade, int estoqueMinimo, double preco, String fornecedor) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.dataFabricacao = dataFabricacao;
        this.dataValidade = dataValidade;
        this.quantidade = quantidade;
        this.estoqueMinimo = estoqueMinimo;
        this.preco = preco;
        this.fornecedor = fornecedor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public LocalDate getDataFabricacao() {
        return dataFabricacao;
    }

    public void setDataFabricacao(LocalDate dataFabricacao) {
        this.dataFabricacao = dataFabricacao;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(int estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public String toString() {
        return "ProdutoAlimento{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", dataFabricacao='" + dataFabricacao + '\'' +
                ", dataValidade=" + dataValidade +
                ", quantidade=" + quantidade +
                ", estoqueMinimo=" + estoqueMinimo +
                ", preco=" + preco +
                ", fornecedor=" + fornecedor +
                '}';
    }
}

