package br.com.estoque.model;

public class Produto {

    private int id;
    private String nome;
    private String tipo;
    private int quantidade;
    private double preco;
    private int estoqueMinimo;
    private String corredor;
    private String prateleira;
    private String posicao;

    public Produto() {}

    public Produto(int id, String nome, String tipo, int quantidade, double preco , int estoqueMinimo, String corredor, String prateleira, String posicao) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.preco = preco;
        this.estoqueMinimo = estoqueMinimo;
        this.corredor = corredor;
        this.prateleira = prateleira;
        this.posicao = posicao;
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

    public String getTipo() { 
        return tipo; 
    }
    public void setTipo(String tipo) { 
        this.tipo = tipo; 
    }

    public int getQuantidade() { 
        return quantidade; 
    }
    public void setQuantidade(int quantidade) { 
        this.quantidade = quantidade; 
    }
    public double getPreco() {
        return preco;
    }
    public void setPreco(double preco){
        this.preco = preco;
    }

    public int getEstoqueMinimo() { 
        return estoqueMinimo; 
    }
    public void setEstoqueMinimo(int estoqueMinimo) { 
        this.estoqueMinimo = estoqueMinimo; 
    }
    public String getCorredor(){
        return corredor;
    }
    public void setCorredor(String corredor){
        this.corredor = corredor;
    }
    public String getPrateleira(){
        return prateleira;
    }
    public void setPrateleira(String prateleira){
        this.prateleira = prateleira;
    }
    public String getPosicao(){
        return posicao;
    }
    public void setPosicao(String posicao){
        this.posicao = posicao;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", quantidade=" + quantidade +
                ", preco=" + preco +
                ", estoqueMinimo=" + estoqueMinimo +
                ", corredor=" + corredor +
                ", prateleira=" + prateleira +
                ", posicao=" + posicao +
                '}';
    }
}