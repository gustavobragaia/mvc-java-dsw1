<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="br.ufscar.dc.dsw.model.Pessoa1" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Q3 — Dados da Sessão</title>
</head>
<body>
<h1>Questão 3 — Dados da Sessão HTTP</h1>

<%
    String sessionId = (String) request.getAttribute("sessionId");
    java.util.Date lastAccess = (java.util.Date) request.getAttribute("lastAccess");
    Pessoa1 pessoa = (Pessoa1) request.getAttribute("pessoa");

    if (sessionId == null) {
%>
        <p><strong>Nenhuma sessão ativa.</strong></p>
<%
    } else {
%>
        <p><strong>ID da sessão:</strong> <%= sessionId %></p>
        <p><strong>Último acesso:</strong> <%= lastAccess %></p>

        <h2>Pessoa armazenada na sessão:</h2>
<%
        if (pessoa != null) {
%>
            <p><strong>Nome:</strong> <%= pessoa.getNome() %></p>
            <p><strong>Idade:</strong> <%= pessoa.getIdade() %></p>
<%
        } else {
%>
            <p><em>Nenhuma pessoa armazenada na sessão.</em></p>
<%
        }
    }
%>

<br>
<a href="q3.jsp">Voltar ao formulário</a>
<br>
<a href="index.jsp">Voltar ao index</a>
</body>
</html>
