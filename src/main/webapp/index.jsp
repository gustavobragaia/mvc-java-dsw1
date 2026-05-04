<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Prova DSW1</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        h1 { color: #333; }
        h2 { color: #555; margin-top: 30px; }
        ul { line-height: 2; }
        a { color: #0066cc; }
    </style>
</head>
<body>
    <h1>Prova DSW1 — Menu Principal</h1>

    <h2>Questão 1 — Redirect por parâmetro</h2>
    <ul>
        <li><a href="q1?p=ok">Q1 — p=ok</a></li>
        <li><a href="q1?p=yes">Q1 — p=yes</a></li>
        <li><a href="q1?p=no">Q1 — p=no</a></li>
        <li><a href="q1?p=invalido">Q1 — p=invalido (deve ir para no.jsp)</a></li>
        <li><a href="q1">Q1 — sem parâmetro (deve ir para no.jsp)</a></li>
    </ul>

    <h2>Questão 2 — Mostrar lista por parâmetro</h2>
    <ul>
        <li><a href="q2?all=1">Q2 All 1</a></li>
        <li><a href="q2?all=2">Q2 All 2</a></li>
    </ul>

    <h2>Questão 3 — Redirect por parâmetro</h2>
    <ul>
        <li><a href="q3.jsp">Q3 — Formulário</a></li>
    </ul>

    <h2>Questão 4 — Implementar na arquitetura MVC</h2>
    <ul>
        <li><a href="q4.jsp">Q4— Ir para Q4</a></li>
    </ul>

    <h2>Questão 5 — Implementar na arquitetura MVC</h2>
    <ul>
        <li><a href="q5.jsp">Q5— Ir para Q5</a></li>
        <li><a href="categoria">Q5— Ir para CRUD</a></li>
    </ul>

</body>
</html>
