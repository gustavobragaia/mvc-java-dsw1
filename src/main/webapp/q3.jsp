<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Q3 — Formulário</title>
</head>
<body>
<h1>Questão 3 — Cadastro de Pessoa na Sessão</h1>

<form action="q3criarsessao" method="post">
    Nome: <input type="text" name="nome"><br><br>
    Idade: <input type="text" name="idade"><br><br>
    <button type="submit">Salvar na Sessão</button>
</form>

<br>
<a href="q3mostrarsessao">Ver dados da sessão</a>
<br><br>
<a href="index.jsp">Voltar ao index</a>
</body>
</html>
