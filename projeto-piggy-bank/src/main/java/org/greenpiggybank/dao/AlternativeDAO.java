package org.greenpiggybank.dao;

import org.greenpiggybank.classes.Alternative;
import org.greenpiggybank.database.ConexaoBanco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para operações CRUD da entidade Alternative.
 */
public class AlternativeDAO {
    private ConexaoBanco conexaoBanco;

    public AlternativeDAO() {
        this.conexaoBanco = new ConexaoBanco();
    }

    /**
     * Insere uma nova alternativa no banco de dados.
     */
    public boolean insert(Alternative alternative) {
        String sql = "INSERT INTO Alternatives (description, correctAnswer, questionId) VALUES (?, ?, ?)";
        
        try (Connection conn = conexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, alternative.getDescription());
            stmt.setBoolean(2, alternative.isCorrectAnswer());
            stmt.setInt(3, alternative.getQuestionId());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    alternative.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir alternativa: " + e.getMessage());
        }
        return false;
    }

    /**
     * Busca uma alternativa pelo ID.
     */
    public Alternative findById(int id) {
        String sql = "SELECT * FROM Alternatives WHERE id = ?";
        Alternative alternative = null;
        
        try (Connection conn = conexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                alternative = new Alternative(
                    rs.getInt("id"),
                    rs.getString("description"),
                    rs.getBoolean("correctAnswer"),
                    rs.getInt("questionId")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar alternativa: " + e.getMessage());
        }
        return alternative;
    }

    /**
     * Lista todas as alternativas de uma pergunta específica.
     */
    public List<Alternative> findByQuestionId(int questionId) {
        String sql = "SELECT * FROM Alternatives WHERE questionId = ? ORDER BY id ASC";
        List<Alternative> alternatives = new ArrayList<>();
        
        try (Connection conn = conexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, questionId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                alternatives.add(new Alternative(
                    rs.getInt("id"),
                    rs.getString("description"),
                    rs.getBoolean("correctAnswer"),
                    rs.getInt("questionId")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar alternativas da pergunta: " + e.getMessage());
        }
        return alternatives;
    }

    /**
     * Lista todas as alternativas.
     */
    public List<Alternative> findAll() {
        String sql = "SELECT * FROM Alternatives ORDER BY questionId, id";
        List<Alternative> alternatives = new ArrayList<>();
        
        try (Connection conn = conexaoBanco.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                alternatives.add(new Alternative(
                    rs.getInt("id"),
                    rs.getString("description"),
                    rs.getBoolean("correctAnswer"),
                    rs.getInt("questionId")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar alternativas: " + e.getMessage());
        }
        return alternatives;
    }

    /**
     * Atualiza uma alternativa existente.
     */
    public boolean update(Alternative alternative) {
        String sql = "UPDATE Alternatives SET description = ?, correctAnswer = ?, questionId = ? WHERE id = ?";
        
        try (Connection conn = conexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, alternative.getDescription());
            stmt.setBoolean(2, alternative.isCorrectAnswer());
            stmt.setInt(3, alternative.getQuestionId());
            stmt.setInt(4, alternative.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar alternativa: " + e.getMessage());
        }
        return false;
    }

    /**
     * Remove uma alternativa do banco de dados.
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM Alternatives WHERE id = ?";
        
        try (Connection conn = conexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar alternativa: " + e.getMessage());
        }
        return false;
    }
}

