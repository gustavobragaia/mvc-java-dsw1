<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="br.ufscar.dc.dsw.model.Categoria" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Q5 - Formulário</title></head>
<body>
<%
    Categoria categoria = (Categoria) request.getAttribute("categoria");
    boolean isEdicao = (categoria != null);
    String acao = isEdicao ? "atualizar" : "inserir";
    String titulo = isEdicao ? "Editar Categoria" : "Nova Categoria";

    String assuntosAtuais = "";
    if (isEdicao && categoria.getAssuntos() != null) {
        assuntosAtuais = categoria.getAssuntos();
    }
%>

<h1><%= titulo %></h1>

<form action="categoria" method="post">
    <input type="hidden" name="acao" value="<%= acao %>">
<%
    if (isEdicao) {
%>
        <input type="hidden" name="id" value="<%= categoria.getId() %>">
<%
    }
%>

    Nome:
    <input type="text" name="nome" required
           value="<%= isEdicao ? categoria.getNome() : "" %>">
    <br><br>

    Prioridade:
    <select name="prioridade" required>
        <option value="Alta"  <%= isEdicao && "Alta".equals(categoria.getPrioridade())  ? "selected" : "" %>>Alta</option>
        <option value="Media" <%= isEdicao && "Media".equals(categoria.getPrioridade()) ? "selected" : "" %>>Média</option>
        <option value="Baixa" <%= isEdicao && "Baixa".equals(categoria.getPrioridade()) ? "selected" : "" %>>Baixa</option>
    </select>
    <br><br>

    <fieldset>
        <legend>Assuntos:</legend>
        <input type="checkbox" name="assunto" value="Clima"
               <%= assuntosAtuais.contains("Clima") ? "checked" : "" %>> Clima<br>
        <input type="checkbox" name="assunto" value="Comercio"
               <%= assuntosAtuais.contains("Comercio") ? "checked" : "" %>> Comércio<br>
        <input type="checkbox" name="assunto" value="Industria"
               <%= assuntosAtuais.contains("Industria") ? "checked" : "" %>> Indústria<br>
        <input type="checkbox" name="assunto" value="Governo"
               <%= assuntosAtuais.contains("Governo") ? "checked" : "" %>> Governo<br>
    </fieldset>
    <br>

    <button type="submit">Salvar</button>
</form>

<br>
<a href="categoria">Voltar à lista</a>
</body>
</html>