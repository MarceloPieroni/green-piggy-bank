package org.greenpiggybank.dao;

import org.greenpiggybank.classes.Question;
import org.greenpiggybank.database.ConexaoBanco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para operações CRUD da entidade Question.
 */
public class QuestionDAO {
    private ConexaoBanco conexaoBanco;

    public QuestionDAO() {
        this.conexaoBanco = new ConexaoBanco();
    }

    /**
     * Insere uma nova pergunta no banco de dados.
     */
    public boolean insert(Question question) {
        String sql = "INSERT INTO Questions (statement, description, reward, phaseId) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = conexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, question.getStatement());
            stmt.setString(2, question.getDescription());
            stmt.setInt(3, question.getReward());
            stmt.setInt(4, question.getPhaseId());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    question.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir pergunta: " + e.getMessage());
        }
        return false;
    }

    /**
     * Busca uma pergunta pelo ID.
     */
    public Question findById(int id) {
        String sql = "SELECT * FROM Questions WHERE id = ?";
        Question question = null;
        
        try (Connection conn = conexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                question = new Question(
                    rs.getInt("id"),
                    rs.getString("statement"),
                    rs.getString("description"),
                    rs.getInt("reward"),
                    rs.getInt("phaseId")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pergunta: " + e.getMessage());
        }
        return question;
    }

    /**
     * Lista todas as perguntas de uma fase específica.
     */
    public List<Question> findByPhaseId(int phaseId) {
        String sql = "SELECT * FROM Questions WHERE phaseId = ? ORDER BY id ASC";
        List<Question> questions = new ArrayList<>();
        
        try (Connection conn = conexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, phaseId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                questions.add(new Question(
                    rs.getInt("id"),
                    rs.getString("statement"),
                    rs.getString("description"),
                    rs.getInt("reward"),
                    rs.getInt("phaseId")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar perguntas da fase: " + e.getMessage());
        }
        return questions;
    }

    /**
     * Lista todas as perguntas.
     */
    public List<Question> findAll() {
        String sql = "SELECT * FROM Questions ORDER BY phaseId, id";
        List<Question> questions = new ArrayList<>();
        
        try (Connection conn = conexaoBanco.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                questions.add(new Question(
                    rs.getInt("id"),
                    rs.getString("statement"),
                    rs.getString("description"),
                    rs.getInt("reward"),
                    rs.getInt("phaseId")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar perguntas: " + e.getMessage());
        }
        return questions;
    }

    /**
     * Atualiza uma pergunta existente.
     */
    public boolean update(Question question) {
        String sql = "UPDATE Questions SET statement = ?, description = ?, reward = ?, phaseId = ? WHERE id = ?";
        
        try (Connection conn = conexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, question.getStatement());
            stmt.setString(2, question.getDescription());
            stmt.setInt(3, question.getReward());
            stmt.setInt(4, question.getPhaseId());
            stmt.setInt(5, question.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar pergunta: " + e.getMessage());
        }
        return false;
    }

    /**
     * Remove uma pergunta do banco de dados.
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM Questions WHERE id = ?";
        
        try (Connection conn = conexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar pergunta: " + e.getMessage());
        }
        return false;
    }
}

