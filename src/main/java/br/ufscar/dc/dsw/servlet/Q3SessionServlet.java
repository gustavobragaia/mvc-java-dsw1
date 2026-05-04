package br.ufscar.dc.dsw.servlet;

import br.ufscar.dc.dsw.model.Pessoa1;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/q3mostrarsessao")
public class Q3SessionServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //get current session
        HttpSession session = request.getSession(false);

        if(session != null){
            request.setAttribute("sessionId", session.getId());
            request.setAttribute("lastAccess", new java.util.Date(session.getLastAccessedTime()));
            request.setAttribute("pessoa", (Pessoa1) session.getAttribute("pessoa"));
        }

        RequestDispatcher rd = request.getRequestDispatcher("/q3sessao.jsp");
        rd.forward(request, response);

    }
}