package br.ufscar.dc.dsw.model;

public class Funcionario {
    private String nome;
    private String cargo;
    private String[] tecnologias;

    public Funcionario(String nome, String cargo, String[] tecnologias) {
        this.nome = nome;
        this.cargo = cargo;
        this.tecnologias = tecnologias;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String[] getTecnologias() {
        return tecnologias;
    }

    public void setTecnologias(String[] tecnologias) {
        this.tecnologias = tecnologias;
    }
}
