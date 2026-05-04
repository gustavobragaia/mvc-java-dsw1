package br.ufscar.dc.dsw.servlet;

import br.ufscar.dc.dsw.model.Pessoa1;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/q3criarsessao")
public class Q3Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //receive that params
        String nome = request.getParameter("nome");
        int idade = Integer.parseInt(request.getParameter("idade"));

        Pessoa1 pessoa = new Pessoa1(nome, idade);

        //make a sessionStorage in server, not in browser
        HttpSession session = request.getSession();

        //save object into session
        session.setAttribute("pessoa", pessoa);

        response.sendRedirect(request.getContextPath() + "/q3.jsp");
    }
}
