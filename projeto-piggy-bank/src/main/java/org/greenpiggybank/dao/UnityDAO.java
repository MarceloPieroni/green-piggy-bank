package org.greenpiggybank.dao;

import org.greenpiggybank.classes.Unity;
import org.greenpiggybank.database.ConexaoBanco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para operações CRUD da entidade Unity (Utilities).
 */
public class UnityDAO {
    private ConexaoBanco conexaoBanco;

    public UnityDAO() {
        this.conexaoBanco = new ConexaoBanco();
    }

    /**
     * Insere uma nova utilidade no banco de dados.
     */
    public boolean insert(Unity unity) {
        String sql = "INSERT INTO Utilities (description, price) VALUES (?, ?)";
        
        try (Connection conn = conexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, unity.getDescription());
            stmt.setInt(2, unity.getPrice());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    unity.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir utilidade: " + e.getMessage());
        }
        return false;
    }

    /**
     * Busca uma utilidade pelo ID.
     */
    public Unity findById(int id) {
        String sql = "SELECT * FROM Utilities WHERE id = ?";
        Unity unity = null;
        
        try (Connection conn = conexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                unity = new Unity(
                    rs.getInt("id"),
                    rs.getString("description"),
                    rs.getInt("price")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar utilidade: " + e.getMessage());
        }
        return unity;
    }

    /**
     * Lista todas as utilidades.
     */
    public List<Unity> findAll() {
        String sql = "SELECT * FROM Utilities ORDER BY price ASC";
        List<Unity> utilities = new ArrayList<>();
        
        try (Connection conn = conexaoBanco.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                utilities.add(new Unity(
                    rs.getInt("id"),
                    rs.getString("description"),
                    rs.getInt("price")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar utilidades: " + e.getMessage());
        }
        return utilities;
    }

    /**
     * Atualiza uma utilidade existente.
     */
    public boolean update(Unity unity) {
        String sql = "UPDATE Utilities SET description = ?, price = ? WHERE id = ?";
        
        try (Connection conn = conexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, unity.getDescription());
            stmt.setInt(2, unity.getPrice());
            stmt.setInt(3, unity.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar utilidade: " + e.getMessage());
        }
        return false;
    }

    /**
     * Remove uma utilidade do banco de dados.
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM Utilities WHERE id = ?";
        
        try (Connection conn = conexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar utilidade: " + e.getMessage());
        }
        return false;
    }
}

