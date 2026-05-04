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
        List<Categoria> listaCategorias = new ArrayList<>();
        String sql = "SELECT * from Categoria";
        try {
            Connection conn = this.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String nome = resultSet.getString("nome");
                String prioridade = resultSet.getString("prioridade");
                String assuntos = resultSet.getString("assuntos");

                Categoria categoria = new Categoria(id, nome, prioridade, assuntos);
                listaCategorias.add(categoria);
            }
            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaCategorias;
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

    public Categoria get(Long id) {
        Categoria categoria = null;

        String sql = "SELECT * from Categoria where id = ?";
        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String nome = resultSet.getString("nome");
                String prioridade = resultSet.getString("prioridade");
                String assuntos = resultSet.getString("assuntos");

                categoria = new Categoria(id, nome, prioridade, assuntos);
            }
            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categoria;
    }
}