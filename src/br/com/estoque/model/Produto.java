package br.com.estoque.model;

public class Produto {

    private int id;
    private String nome;
    private String tipo;
    private int quantidade;
    private int estoqueMinimo;

    public Produto() {}

    public Produto(int id, String nome, String tipo, int quantidade, int estoqueMinimo) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.estoqueMinimo = estoqueMinimo;
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

    public int getEstoqueMinimo() { 
        return estoqueMinimo; 
    }
    public void setEstoqueMinimo(int estoqueMinimo) { 
        this.estoqueMinimo = estoqueMinimo; 
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", quantidade=" + quantidade +
                ", estoqueMinimo=" + estoqueMinimo +
                '}';
    }
}