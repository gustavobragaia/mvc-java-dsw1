package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.dao.CategoriaDAO;
import br.ufscar.dc.dsw.model.Categoria;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/categoria")
public class Q5Servlet extends HttpServlet {

    private CategoriaDAO dao;

    @Override
    public void init() {
        dao = new CategoriaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");

        if (acao == null) {
            // listar
            List<Categoria> lista = dao.getAll();
            request.setAttribute("lista", lista);
            RequestDispatcher rd = request.getRequestDispatcher("/q5/lista.jsp");
            rd.forward(request, response);

        } else if (acao.equals("novo")) {
            // form vazio
            RequestDispatcher rd = request.getRequestDispatcher("/q5/form.jsp");
            rd.forward(request, response);

        } else if (acao.equals("editar")) {
            // form preenchido
            Long id = Long.parseLong(request.getParameter("id"));
            Categoria c = dao.get(id);
            request.setAttribute("categoria", c);
            RequestDispatcher rd = request.getRequestDispatcher("/q5/form.jsp");
            rd.forward(request, response);

        } else if (acao.equals("excluir")) {
            Long id = Long.parseLong(request.getParameter("id"));
            Categoria c = new Categoria(id, null, null, null);
            dao.delete(c);
            response.sendRedirect(request.getContextPath() + "/categoria");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String acao = request.getParameter("acao");
        String nome = request.getParameter("nome");
        String prioridade = request.getParameter("prioridade");

        String[] assuntosArr = request.getParameterValues("assunto");
        String assuntos = (assuntosArr != null) ? String.join(",", assuntosArr) : "";

        if ("inserir".equals(acao)) {
            Categoria c = new Categoria(nome, prioridade, assuntos);
            dao.insert(c);

        } else if ("atualizar".equals(acao)) {
            Long id = Long.parseLong(request.getParameter("id"));
            Categoria c = new Categoria(id, nome, prioridade, assuntos);
            dao.update(c);
        }

        response.sendRedirect(request.getContextPath() + "/categoria");
    }
}