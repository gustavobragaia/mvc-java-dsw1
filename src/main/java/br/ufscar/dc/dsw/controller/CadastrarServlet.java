package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.dao.FuncionarioDAO;
import br.ufscar.dc.dsw.model.Funcionario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/cadastrar")
public class CadastrarServlet extends HttpServlet {

    private FuncionarioDAO dao;

    @Override
    public void init(){
        dao = new FuncionarioDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String nome = request.getParameter("nome");
        String cargo = request.getParameter("cargo");
        String[] tecnologias = request.getParameterValues("tecnologia");

        if (tecnologias == null) tecnologias = new String[0];

        Funcionario funcionario = new Funcionario(nome, cargo, tecnologias);
        dao.insert(funcionario);

        response.sendRedirect(request.getContextPath()+"/q4.jsp?sucesso=1");
    }
}
