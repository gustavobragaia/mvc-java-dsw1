package br.ufscar.dc.dsw.model;

public class Categoria {

    private Long id;
    private String nome;
    private String prioridade;
    private String assuntos;

    //to create, without id
    public Categoria(String nome, String prioridade, String assuntos) {
        this.nome = nome;
        this.prioridade = prioridade;
        this.assuntos = assuntos;
    }

    //to edit, with id
    public Categoria(Long id, String nome, String prioridade, String assuntos) {
        this.id = id;
        this.nome = nome;
        this.prioridade = prioridade;
        this.assuntos = assuntos;
    }

    public String getAssuntos() {
        return assuntos;
    }

    public void setAssuntos(String assuntos) {
        this.assuntos = assuntos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }
}
