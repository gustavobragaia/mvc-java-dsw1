package br.ufscar.dc.dsw.model;

public class Pessoa1 {
    private String nome;
    private int idade;

    public Pessoa1(String nome, int idade){
        this.idade = idade;
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
