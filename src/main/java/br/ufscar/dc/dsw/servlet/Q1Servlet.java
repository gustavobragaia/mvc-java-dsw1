package br.ufscar.dc.dsw.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/q1")
public class Q1Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        //get the ?p param
        String p = request.getParameter("p");

        if( p == null || p.isEmpty()){
            response.sendRedirect(request.getContextPath() + "/no.jsp");
            return;
        }

        switch (p) {
            case "ok":
                response.sendRedirect(request.getContextPath() + "/ok.jsp");
                break;

            case "yes":
                response.sendRedirect(request.getContextPath() + "/yes.jsp");
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/no.jsp");
                break;
        }
    }
}
