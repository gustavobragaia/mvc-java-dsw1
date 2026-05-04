package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.model.Funcionario;

import java.util.Arrays;

public class FuncionarioDAO {
    public void insert(Funcionario funcionario){
        System.out.println("=== INSERT ===");
        System.out.println("Nome: " + funcionario.getNome());
        System.out.println("Cargo: " + funcionario.getCargo());
        System.out.println("Tecnologias: " + Arrays.toString(funcionario.getTecnologias()));
    }
}
