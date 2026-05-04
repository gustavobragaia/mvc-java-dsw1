<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<html>
<head><title>Q2 - Lista</title></head>
<body>
<h1>Questão 2</h1>

<%
    List<String> lista = (List<String>) request.getAttribute("lista");

    if (lista != null && !lista.isEmpty()) {
%>
        <p>Tamanho: <%= lista.size() %></p>
        <% for (String item : lista) { %>
            <p><%= item %></p>
        <% } %>
<% } else { %>
        <p>Lista Vazia</p>
<% } %>

<a href="index.jsp">Voltar</a>
</body>
</html>