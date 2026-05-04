<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<html>
<body>
<h2>Cadastro de Categoria</h2>

<%
 String sucesso = request.getParameter("sucesso");
 if (sucesso != null) {
%>
<h3>Categoria cadastrada com sucesso.</h3>
<%
 }
%>

<form action="cadastrarCategoria" method="post">

 Nome: <input type="text" name="nome" required><br><br>

 Prioridade:
 <select name="prioridade" required>
     <option value="">Selecione</option>
     <option value="alta">Alta</option>
     <option value="media">Média</option>
     <option value="baixa">Baixa</option>
 </select>
 <br><br>

 <fieldset>
     <legend>Assuntos:</legend>

     <input type="checkbox" name="assuntos" value="clima"> Clima<br>
     <input type="checkbox" name="assuntos" value="comercio"> Comércio<br>
     <input type="checkbox" name="assuntos" value="industria"> Indústria<br>
     <input type="checkbox" name="assuntos" value="governo"> Governo<br>

 </fieldset>

 <br><br>
 <button type="submit">Cadastrar</button>

</form>
</body>
</html>