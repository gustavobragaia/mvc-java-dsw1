<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="br.ufscar.dc.dsw.model.Categoria" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Q5 - Categorias</title></head>
<body>
<h1>Lista de Categorias</h1>

<a href="categoria?acao=novo">Nova Categoria</a>
<br><br>

<%
    List<Categoria> lista = (List<Categoria>) request.getAttribute("lista");

    if (lista == null || lista.isEmpty()) {
%>
        <p>Nenhuma categoria cadastrada.</p>
<%
    } else {
%>
        <table border="1" cellpadding="6">
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Prioridade</th>
                <th>Assuntos</th>
                <th>Ações</th>
            </tr>
<%
            for (Categoria c : lista) {
%>
                <tr>
                    <td><%= c.getId() %></td>
                    <td><%= c.getNome() %></td>
                    <td><%= c.getPrioridade() %></td>
                    <td><%= c.getAssuntos() %></td>
                    <td>
                        <a href="categoria?acao=editar&id=<%= c.getId() %>">Editar</a>
                        |
                        <a href="categoria?acao=excluir&id=<%= c.getId() %>"
                           onclick="return confirm('Confirma exclusão?')">Excluir</a>
                    </td>
                </tr>
<%
            }
%>
        </table>
<%
    }
%>

<br>
<a href="index.jsp">Voltar ao index</a>
</body>
</html>