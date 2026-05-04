# 📘 Guia passo a passo — Prova DSW1
> Este README é um roteiro prático. Vai te dizer **exatamente o que fazer**, na ordem certa, para cada questão. Use como checklist.

---

## 📋 Antes de começar — Setup inicial

### Passo 1: Estrutura de pastas

Crie um projeto Maven novo no IntelliJ e organize as pastas dentro de `src/main/java`:

```
src/main/java/br/ufscar/dc/dsw/
    ├── controller/      ← onde vão os servlets
    ├── model/           ← onde vão as classes (Pessoa1, Funcionario, Categoria)
    └── dao/             ← onde vai o GenericDAO, FuncionarioDAO, CategoriaDAO

src/main/webapp/
    ├── WEB-INF/
    │   └── web.xml
    ├── q5/              ← JSPs da Q5
    ├── sql/             ← arquivo SQL da Q5
    └── (todos os outros .jsp aqui)
```

### Passo 2: `pom.xml` correto

Dentro de `<dependencies>`, deve ter **três coisas**:

```xml
<dependencies>
    <dependency>
        <groupId>jakarta.servlet</groupId>
        <artifactId>jakarta.servlet-api</artifactId>
        <version>5.0.0</version>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>jakarta.servlet.jsp</groupId>
        <artifactId>jakarta.servlet.jsp-api</artifactId>
        <version>3.0.0</version>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>8.2.0</version>
    </dependency>
</dependencies>
```

> ⚠️ **NÃO** coloque `<dependency>` dentro de `<properties>`. São blocos separados.

### Passo 3: `web.xml` mínimo

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee
         https://jakarta.ee/xml/ns/jakartaee/web-app_5_0.xsd"
         version="5.0">

    <display-name>Prova DSW1</display-name>
</web-app>
```

> 💡 Não precisa de `<servlet>` nem `<servlet-mapping>` — vamos usar **anotações `@WebServlet`** em cima de cada classe.

### Passo 4: Iniciar o MySQL

No terminal:

```bash
brew services start mysql      # iniciar o serviço
mysql -u root -p               # entrar no MySQL
```

Dentro do MySQL:

```sql
CREATE DATABASE aa1db;
USE aa1db;
EXIT;
```

(O `CREATE TABLE` específico vai vir na Q5.)

---

## ✅ Questão 1 — Redirecionamento

### O que criar
- [ ] `controller/Q1Servlet.java`
- [ ] `webapp/ok.jsp`, `yes.jsp`, `no.jsp`
- [ ] Adicionar 3 links no `index.jsp`

### Passo 1: links no `index.jsp`
```jsp
<a href="q1?p=ok">Q1 — p=ok</a>
<a href="q1?p=yes">Q1 — p=yes</a>
<a href="q1?p=no">Q1 — p=no</a>
```

### Passo 2: criar `Q1Servlet.java`
```java
package br.ufscar.dc.dsw.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/q1")
public class Q1Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String p = request.getParameter("p");

        if ("ok".equals(p)) {
            response.sendRedirect(request.getContextPath() + "/ok.jsp");
        } else if ("yes".equals(p)) {
            response.sendRedirect(request.getContextPath() + "/yes.jsp");
        } else {
            // null, vazio, "no" ou inválido → no.jsp
            response.sendRedirect(request.getContextPath() + "/no.jsp");
        }
    }
}
```

### Passo 3: criar os 3 JSPs (ok.jsp, yes.jsp, no.jsp)
Cada um tem título e link de volta. Exemplo do `ok.jsp`:
```jsp
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<html><body>
<h1>Resultado: OK</h1>
<a href="index.jsp">Voltar ao index</a>
</body></html>
```

### ⚠️ Cuidado
- A anotação é `@WebServlet("/q1")` — **com a barra na frente**
- A classe **precisa** ter `extends HttpServlet`
- O enunciado diz "redireciona" → use `sendRedirect`

---

## ✅ Questão 2 — Forward com lista

### O que criar
- [ ] `controller/Q2Servlet.java`
- [ ] `webapp/q2.jsp`
- [ ] Adicionar 2 links no `index.jsp`

### Passo 1: links no `index.jsp`
```jsp
<a href="q2?all=1">Q2 — Lista com 10 itens</a>
<a href="q2?all=2">Q2 — Lista vazia</a>
```

### Passo 2: criar `Q2Servlet.java`
```java
package br.ufscar.dc.dsw.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/q2")
public class Q2Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String all = request.getParameter("all");
        List<String> lista = new ArrayList<>();

        if ("1".equals(all)) {
            for (int i = 1; i <= 10; i++) {
                lista.add("Item " + i);
            }
        }
        // se all=2, lista permanece vazia

        request.setAttribute("lista", lista);
        RequestDispatcher rd = request.getRequestDispatcher("/q2.jsp");
        rd.forward(request, response);
    }
}
```

### Passo 3: criar `q2.jsp`
```jsp
<%@ page contentType="text/html" pageEncoding="UTF-8" import="java.util.List" %>
<html><body>
<%
    List<String> lista = (List<String>) request.getAttribute("lista");

    if (lista == null || lista.isEmpty()) {
%>
        <p>Lista Vazia</p>
        <a href="index.jsp">Voltar ao index</a>
<%
    } else {
%>
        <p>Tamanho: <%= lista.size() %></p>
<%      for (String item : lista) { %>
            <p><%= item %></p>
<%      } %>
        <a href="index.jsp">Voltar ao index</a>
<%
    }
%>
</body></html>
```

### ⚠️ Cuidado
- O enunciado diz **"encaminhar"** → use `forward`, não `sendRedirect`
- `for (int i = 1; i <= 10; i++)` — se errar a condição (ex: `i > 10`) o loop nunca executa
- `"1".equals(all)` é mais seguro que `all.equals("1")` (não estoura se all for null)

---

## ✅ Questão 3 — Sessão HTTP

### O que criar
- [ ] `model/Pessoa1.java`
- [ ] `controller/Q3Servlet.java` (POST)
- [ ] `controller/Q3SessionServlet.java` (GET)
- [ ] `webapp/q3.jsp` (formulário)
- [ ] `webapp/q3sessao.jsp` (exibição)
- [ ] Link no `index.jsp`

### Passo 1: link no `index.jsp`
```jsp
<a href="q3.jsp">Q3 — Formulário</a>
```
> ⚠️ É `q3.jsp` (com `.jsp`), não `q3` — é arquivo, não rota!

### Passo 2: criar `model/Pessoa1.java`
```java
package br.ufscar.dc.dsw.model;

public class Pessoa1 {
    private String nome;
    private int idade;

    public Pessoa1(String nome, int idade) {
        this.nome = nome;       // ⚠️ NÃO ESQUECER ESSAS LINHAS
        this.idade = idade;     // se deixar vazio, vem tudo null!
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }
}
```

### Passo 3: criar `q3.jsp` (formulário)
```jsp
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<html><body>
<h1>Cadastro de Pessoa na Sessão</h1>

<form action="q3criarsessao" method="post">
    Nome: <input type="text" name="nome"><br><br>
    Idade: <input type="text" name="idade"><br><br>
    <button type="submit">Salvar na Sessão</button>
</form>

<br>
<a href="q3mostrarsessao">Ver dados da sessão</a>
<br>
<a href="index.jsp">Voltar ao index</a>
</body></html>
```

### Passo 4: criar `Q3Servlet.java` (recebe POST)
```java
package br.ufscar.dc.dsw.controller;

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        int idade = Integer.parseInt(request.getParameter("idade"));

        Pessoa1 pessoa = new Pessoa1(nome, idade);

        HttpSession session = request.getSession();   // cria/recupera a sessão
        session.setAttribute("pessoa", pessoa);       // guarda na sessão

        response.sendRedirect(request.getContextPath() + "/q3.jsp");
    }
}
```

### Passo 5: criar `Q3SessionServlet.java` (mostra dados via forward)
```java
package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.model.Pessoa1;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@WebServlet("/q3mostrarsessao")
public class Q3SessionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false); // false = não cria nova

        if (session != null) {
            request.setAttribute("sessionId", session.getId());
            request.setAttribute("lastAccess", new Date(session.getLastAccessedTime()));
            request.setAttribute("pessoa", session.getAttribute("pessoa"));
        }

        // ✅ MVC: forward para JSP
        RequestDispatcher rd = request.getRequestDispatcher("/q3sessao.jsp");
        rd.forward(request, response);
    }
}
```

### Passo 6: criar `q3sessao.jsp` (só exibe, sem formulário)
```jsp
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="br.ufscar.dc.dsw.model.Pessoa1" %>
<html><body>
<h1>Dados da Sessão</h1>

<%
    String sessionId = (String) request.getAttribute("sessionId");
    java.util.Date lastAccess = (java.util.Date) request.getAttribute("lastAccess");
    Pessoa1 pessoa = (Pessoa1) request.getAttribute("pessoa");

    if (sessionId == null) {
%>
    <p>Nenhuma sessão ativa.</p>
<%
    } else {
%>
    <p>ID: <%= sessionId %></p>
    <p>Último acesso: <%= lastAccess %></p>
<%
        if (pessoa != null) {
%>
    <p>Nome: <%= pessoa.getNome() %></p>
    <p>Idade: <%= pessoa.getIdade() %></p>
<%
        }
    }
%>

<br>
<a href="q3.jsp">Voltar ao formulário</a>
<br>
<a href="index.jsp">Voltar ao index</a>
</body></html>
```

### ⚠️ Cuidado
- A classe é **`Pessoa1`** (com o "1" no final) conforme o enunciado
- O formulário fica **só no `q3.jsp`** — `q3sessao.jsp` é só exibição
- O Q3Servlet só tem `doPost` (não tem doGet) — é só para receber o formulário
- Não esqueça de preencher o construtor da `Pessoa1`!

---

## ✅ Questão 4 — MVC sem banco de dados

### O que criar
- [ ] `model/Funcionario.java`
- [ ] `dao/FuncionarioDAO.java` (sem SGBD)
- [ ] `controller/CadastrarServlet.java`
- [ ] `webapp/q4.jsp` (já está pronto no enunciado, é só copiar)
- [ ] Link no `index.jsp`

### Passo 1: link no `index.jsp`
```jsp
<a href="q4.jsp">Q4 — Cadastro de Funcionário</a>
```

### Passo 2: criar `model/Funcionario.java`
```java
package br.ufscar.dc.dsw.model;

public class Funcionario {
    private String nome;
    private String cargo;
    private String[] tecnologias;

    public Funcionario(String nome, String cargo, String[] tecnologias) {
        this.nome = nome;              // ⚠️ NÃO DEIXAR VAZIO!
        this.cargo = cargo;
        this.tecnologias = tecnologias;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public String[] getTecnologias() { return tecnologias; }
    public void setTecnologias(String[] tecnologias) { this.tecnologias = tecnologias; }
}
```

### Passo 3: criar `dao/FuncionarioDAO.java`
```java
package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.model.Funcionario;
import java.util.Arrays;

public class FuncionarioDAO {
    public void insert(Funcionario funcionario) {
        // Q4 não pede conexão real — só simula
        System.out.println("=== INSERT ===");
        System.out.println("Nome: " + funcionario.getNome());
        System.out.println("Cargo: " + funcionario.getCargo());
        System.out.println("Tecnologias: " + Arrays.toString(funcionario.getTecnologias()));
    }
}
```

### Passo 4: criar `controller/CadastrarServlet.java`
```java
package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.dao.FuncionarioDAO;
import br.ufscar.dc.dsw.model.Funcionario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cadastrar")
public class CadastrarServlet extends HttpServlet {

    private FuncionarioDAO dao;

    @Override
    public void init() {
        dao = new FuncionarioDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String nome = request.getParameter("nome");
        String cargo = request.getParameter("cargo");
        String[] tecnologias = request.getParameterValues("tecnologia");

        if (tecnologias == null) tecnologias = new String[0];

        Funcionario f = new Funcionario(nome, cargo, tecnologias);
        dao.insert(f);

        response.sendRedirect(request.getContextPath() + "/q4.jsp?sucesso=1");
    }
}
```

### Passo 5: criar `q4.jsp` (cópia exata do enunciado)
```jsp
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<html>
<body>
<h2>Cadastro</h2>
<%
    String sucesso = request.getParameter("sucesso");
    if (sucesso != null) {
%>
<h3>Item cadastrado com sucesso.</h3>
<%
    }
%>
<form action="cadastrar" method="post">
    Nome: <input type="text" name="nome"><br><br>
    Cargo:
    <select name="cargo">
        <option value="programador">Programador</option>
        <option value="testador">Testador</option>
        <option value="engenheiro_software">Engenheiro de Software</option>
        <option value="analista_requisitos">Analista de Requisitos</option>
    </select>
    <br><br>
    <fieldset>
        <legend>Tecnologias de Domínio:</legend>
        <input type="checkbox" name="tecnologia" value="java">Java<br>
        <input type="checkbox" name="tecnologia" value="php">PHP<br>
        <input type="checkbox" name="tecnologia" value="nodejs"> Node.js<br>
        <input type="checkbox" name="tecnologia" value="cpp"> C++<br>
        <input type="checkbox" name="tecnologia" value="dotnet"> Dot.Net
    </fieldset>
    <br><br>
    <button type="submit">Cadastrar</button>
</form>
</body>
</html>
```

### ⚠️ Cuidado
- **Construtor não pode ficar vazio!** Tem que ter `this.nome = nome;` etc.
- Para checkboxes: `getParameterValues("tecnologia")` (com S no final) — retorna array
- Para text/select: `getParameter("nome")` — retorna string
- O servlet chama-se **`CadastrarServlet`** porque a rota é `/cadastrar`

---

## ✅ Questão 5 — CRUD com MySQL/JDBC

### O que criar
- [ ] `model/Categoria.java`
- [ ] `dao/GenericDAO.java`
- [ ] `dao/CategoriaDAO.java`
- [ ] `controller/Q5Servlet.java`
- [ ] `webapp/q5/lista.jsp`
- [ ] `webapp/q5/form.jsp`
- [ ] `webapp/sql/categoria.sql`
- [ ] Link no `index.jsp`
- [ ] Configurar MySQL antes!

### Passo 1: configurar o banco no MySQL

```bash
mysql -u root -p
```

Dentro do MySQL:
```sql
CREATE DATABASE aa1db;
USE aa1db;

CREATE TABLE Categoria (
    id         INT NOT NULL AUTO_INCREMENT,
    nome       VARCHAR(100) NOT NULL UNIQUE,
    prioridade VARCHAR(10)  NOT NULL,
    assuntos   VARCHAR(255),
    PRIMARY KEY (id)
);
```

### Passo 2: criar `webapp/sql/categoria.sql` (mesmo SQL acima)
```sql
CREATE DATABASE IF NOT EXISTS aa1db;
USE aa1db;

CREATE TABLE Categoria (
    id         INT NOT NULL AUTO_INCREMENT,
    nome       VARCHAR(100) NOT NULL UNIQUE,
    prioridade VARCHAR(10)  NOT NULL,
    assuntos   VARCHAR(255),
    PRIMARY KEY (id)
);
```

### Passo 3: criar `model/Categoria.java`
```java
package br.ufscar.dc.dsw.model;

public class Categoria {
    private Long id;
    private String nome;
    private String prioridade;
    private String assuntos;   // armazena como "Clima,Comercio,Governo"

    // construtor para INSERT (banco gera o id)
    public Categoria(String nome, String prioridade, String assuntos) {
        this.nome = nome;
        this.prioridade = prioridade;
        this.assuntos = assuntos;
    }

    // construtor para SELECT/UPDATE (com id)
    public Categoria(Long id, String nome, String prioridade, String assuntos) {
        this.id = id;
        this.nome = nome;
        this.prioridade = prioridade;
        this.assuntos = assuntos;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getPrioridade() { return prioridade; }
    public void setPrioridade(String prioridade) { this.prioridade = prioridade; }
    public String getAssuntos() { return assuntos; }
    public void setAssuntos(String assuntos) { this.assuntos = assuntos; }
}
```

### Passo 4: criar `dao/GenericDAO.java`
```java
package br.ufscar.dc.dsw.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

abstract public class GenericDAO {

    public GenericDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/aa1db";
        return DriverManager.getConnection(url, "root", "root");
    }
}
```

> ⚠️ Ajuste a senha (`"root"`) para a do seu MySQL.

### Passo 5: criar `dao/CategoriaDAO.java` (CRUD completo)
```java
package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.model.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO extends GenericDAO {

    public void insert(Categoria categoria) {
        String sql = "INSERT INTO Categoria (nome, prioridade, assuntos) VALUES (?, ?, ?)";
        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, categoria.getNome());
            statement.setString(2, categoria.getPrioridade());
            statement.setString(3, categoria.getAssuntos());
            statement.executeUpdate();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Categoria> getAll() {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * from Categoria";
        try {
            Connection conn = this.getConnection();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Long id = rs.getLong("id");
                String nome = rs.getString("nome");
                String prioridade = rs.getString("prioridade");
                String assuntos = rs.getString("assuntos");
                lista.add(new Categoria(id, nome, prioridade, assuntos));
            }
            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public Categoria get(Long id) {
        Categoria categoria = null;
        String sql = "SELECT * from Categoria where id = ?";
        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String nome = rs.getString("nome");
                String prioridade = rs.getString("prioridade");
                String assuntos = rs.getString("assuntos");
                categoria = new Categoria(id, nome, prioridade, assuntos);
            }
            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categoria;
    }

    public void update(Categoria categoria) {
        String sql = "UPDATE Categoria SET nome = ?, prioridade = ?, assuntos = ?";
        sql += " WHERE id = ?";
        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, categoria.getNome());
            statement.setString(2, categoria.getPrioridade());
            statement.setString(3, categoria.getAssuntos());
            statement.setLong(4, categoria.getId());
            statement.executeUpdate();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Categoria categoria) {
        String sql = "DELETE FROM Categoria where id = ?";
        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, categoria.getId());
            statement.executeUpdate();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
```

### Passo 6: criar `controller/Q5Servlet.java`
```java
package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.dao.CategoriaDAO;
import br.ufscar.dc.dsw.model.Categoria;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/categoria")
public class Q5Servlet extends HttpServlet {

    private CategoriaDAO dao;

    @Override
    public void init() {
        dao = new CategoriaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");

        if (acao == null) {
            // listar
            List<Categoria> lista = dao.getAll();
            request.setAttribute("lista", lista);
            RequestDispatcher rd = request.getRequestDispatcher("/q5/lista.jsp");
            rd.forward(request, response);

        } else if (acao.equals("novo")) {
            RequestDispatcher rd = request.getRequestDispatcher("/q5/form.jsp");
            rd.forward(request, response);

        } else if (acao.equals("editar")) {
            Long id = Long.parseLong(request.getParameter("id"));
            Categoria c = dao.get(id);
            request.setAttribute("categoria", c);
            RequestDispatcher rd = request.getRequestDispatcher("/q5/form.jsp");
            rd.forward(request, response);

        } else if (acao.equals("excluir")) {
            Long id = Long.parseLong(request.getParameter("id"));
            Categoria c = new Categoria(id, null, null, null);
            dao.delete(c);
            response.sendRedirect(request.getContextPath() + "/categoria");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String acao = request.getParameter("acao");
        String nome = request.getParameter("nome");
        String prioridade = request.getParameter("prioridade");

        String[] assuntosArr = request.getParameterValues("assunto");
        String assuntos = (assuntosArr != null) ? String.join(",", assuntosArr) : "";

        if ("inserir".equals(acao)) {
            Categoria c = new Categoria(nome, prioridade, assuntos);
            dao.insert(c);
        } else if ("atualizar".equals(acao)) {
            Long id = Long.parseLong(request.getParameter("id"));
            Categoria c = new Categoria(id, nome, prioridade, assuntos);
            dao.update(c);
        }

        response.sendRedirect(request.getContextPath() + "/categoria");
    }
}
```

### Passo 7: criar `webapp/q5/lista.jsp`
```jsp
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="br.ufscar.dc.dsw.model.Categoria" %>
<html><body>
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
                <th>ID</th><th>Nome</th><th>Prioridade</th><th>Assuntos</th><th>Ações</th>
            </tr>
<%      for (Categoria c : lista) { %>
            <tr>
                <td><%= c.getId() %></td>
                <td><%= c.getNome() %></td>
                <td><%= c.getPrioridade() %></td>
                <td><%= c.getAssuntos() %></td>
                <td>
                    <a href="categoria?acao=editar&id=<%= c.getId() %>">Editar</a> |
                    <a href="categoria?acao=excluir&id=<%= c.getId() %>"
                       onclick="return confirm('Confirma?')">Excluir</a>
                </td>
            </tr>
<%      } %>
        </table>
<%  } %>

<br>
<a href="index.jsp">Voltar ao index</a>
</body></html>
```

### Passo 8: criar `webapp/q5/form.jsp`
```jsp
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="br.ufscar.dc.dsw.model.Categoria" %>
<html><body>
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
<%  if (isEdicao) { %>
        <input type="hidden" name="id" value="<%= categoria.getId() %>">
<%  } %>

    Nome: <input type="text" name="nome" required
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
</body></html>
```

### Passo 9: link no `index.jsp`
```jsp
<a href="categoria">Q5 — CRUD Categorias</a>
```

### ⚠️ Cuidado
- O MySQL **precisa estar rodando** antes de subir o Tomcat
- O banco `aa1db` e a tabela `Categoria` precisam existir
- A senha do `GenericDAO` precisa bater com a do seu MySQL
- A regra "nome único" é garantida pelo `UNIQUE` no SQL — não precisa validar no Java

---

## 🎯 Tabela de referência rápida — forward vs sendRedirect

| Onde | Tipo | Por quê |
|---|---|---|
| Q1 → ok/yes/no.jsp | `sendRedirect` | Enunciado: "redireciona" |
| Q2 → q2.jsp | **`forward`** | Enunciado: "encaminhar" |
| Q3 POST → q3.jsp | `sendRedirect` | Enunciado: "redireciona" |
| Q3 GET → q3sessao.jsp | **`forward`** | MVC para "mostrar" dados |
| Q4 POST → q4.jsp?sucesso=1 | `sendRedirect` | POST-Redirect-GET |
| Q5 listar/form | **`forward`** | MVC — passa dados para JSP |
| Q5 inserir/atualizar/excluir | `sendRedirect` | Após mutação |

---

## 🐛 Erros comuns e soluções

| Erro | Causa | Solução |
|---|---|---|
| `HTTP 404` | Esqueceu `@WebServlet("/rota")` | Adicionar a anotação |
| `HTTP 404` | Esqueceu `extends HttpServlet` | Adicionar na declaração da classe |
| `HTTP 404` | Link com `q3` em vez de `q3.jsp` | Conferir se é arquivo (`.jsp`) ou rota |
| Nome/Cargo vem `null` | Construtor vazio no model | Adicionar `this.nome = nome;` etc. |
| `method does not override` | Faltou `extends HttpServlet` | Adicionar |
| Checkboxes vêm null | Nenhum marcado | `if (arr == null) arr = new String[0];` |
| Acentos errados no POST | Encoding | `request.setCharacterEncoding("UTF-8");` |
| `Communications link failure` | MySQL não está rodando | `brew services start mysql` |
| `Access denied for user` | Senha errada no GenericDAO | Conferir senha do seu MySQL |
| `Unknown database` | Banco não foi criado | `CREATE DATABASE aa1db;` |

---

## 🚨 Checklist final antes de entregar

### Setup
- [ ] `pom.xml` com 3 dependências (servlet-api, jsp-api, mysql-connector-j)
- [ ] `web.xml` mínimo (sem mappings de servlet)
- [ ] MySQL rodando e banco `aa1db` criado com tabela `Categoria`

### Q1
- [ ] `Q1Servlet` com `@WebServlet("/q1")` e `extends HttpServlet`
- [ ] `ok.jsp`, `yes.jsp`, `no.jsp` com título e link de volta
- [ ] 3 links no `index.jsp` (`q1?p=ok`, `q1?p=yes`, `q1?p=no`)

### Q2
- [ ] `Q2Servlet` com `@WebServlet("/q2")`
- [ ] `q2.jsp` mostra tamanho + itens OU "Lista Vazia"
- [ ] Usa **forward** (não sendRedirect)
- [ ] 2 links no `index.jsp`

### Q3
- [ ] Classe `Pessoa1` (com o "1"!)
- [ ] Construtor da `Pessoa1` preenchido (não vazio)
- [ ] `Q3Servlet` com `@WebServlet("/q3criarsessao")` e `doPost`
- [ ] `Q3SessionServlet` com `@WebServlet("/q3mostrarsessao")` e `doGet`
- [ ] `Q3SessionServlet` faz **forward para q3sessao.jsp** (não usa PrintWriter)
- [ ] Existem `q3.jsp` (formulário) e `q3sessao.jsp` (exibição)
- [ ] Link `q3.jsp` (não `q3`) no index

### Q4
- [ ] Classe `Funcionario` com construtor preenchido
- [ ] `FuncionarioDAO` com método `insert` (sem SGBD)
- [ ] `CadastrarServlet` com `@WebServlet("/cadastrar")`
- [ ] `q4.jsp` cópia exata do enunciado
- [ ] Link no `index.jsp`

### Q5
- [ ] Classe `Categoria` com 2 construtores
- [ ] `GenericDAO` com driver MySQL e URL correta
- [ ] `CategoriaDAO` com `insert`, `getAll`, `get`, `update`, `delete`
- [ ] `Q5Servlet` com `@WebServlet("/categoria")` tratando todas as ações
- [ ] `q5/lista.jsp` e `q5/form.jsp` criados
- [ ] `webapp/sql/categoria.sql` com `CREATE TABLE`
- [ ] CRUD funciona: criar, listar, editar, excluir
- [ ] Link no `index.jsp`

### Final
- [ ] Projeto está compilando sem erros
- [ ] Testou cada questão no navegador
- [ ] Exportou como ZIP do projeto Maven completo

🎉 **Entrega no AVA e avisa o professor para a demo.**