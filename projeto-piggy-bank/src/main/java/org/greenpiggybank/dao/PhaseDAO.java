package org.greenpiggybank.dao;

import org.greenpiggybank.classes.Phase;
import org.greenpiggybank.database.ConexaoBanco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para operações CRUD da entidade Phase.
 */
public class PhaseDAO {
    private ConexaoBanco conexaoBanco;

    public PhaseDAO() {
        this.conexaoBanco = new ConexaoBanco();
    }

    /**
     * Insere uma nova fase no banco de dados.
     */
    public boolean insert(Phase phase) {
        String sql = "INSERT INTO Phases (number, description, theme, conclusionReward) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = conexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, phase.getNumber());
            stmt.setString(2, phase.getDescription());
            stmt.setString(3, phase.getTheme());
            stmt.setInt(4, phase.getConclusionReward());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    phase.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir fase: " + e.getMessage());
        }
        return false;
    }

    /**
     * Busca uma fase pelo ID.
     */
    public Phase findById(int id) {
        String sql = "SELECT * FROM Phases WHERE id = ?";
        Phase phase = null;
        
        try (Connection conn = conexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                phase = new Phase(
                    rs.getInt("id"),
                    rs.getInt("number"),
                    rs.getString("description"),
                    rs.getString("theme"),
                    rs.getInt("conclusionReward")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar fase: " + e.getMessage());
        }
        return phase;
    }

    /**
     * Lista todas as fases ordenadas por número.
     */
    public List<Phase> findAll() {
        String sql = "SELECT * FROM Phases ORDER BY number ASC";
        List<Phase> phases = new ArrayList<>();
        
        try (Connection conn = conexaoBanco.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                phases.add(new Phase(
                    rs.getInt("id"),
                    rs.getInt("number"),
                    rs.getString("description"),
                    rs.getString("theme"),
                    rs.getInt("conclusionReward")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar fases: " + e.getMessage());
        }
        return phases;
    }

    /**
     * Atualiza uma fase existente.
     */
    public boolean update(Phase phase) {
        String sql = "UPDATE Phases SET number = ?, description = ?, theme = ?, conclusionReward = ? WHERE id = ?";
        
        try (Connection conn = conexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, phase.getNumber());
            stmt.setString(2, phase.getDescription());
            stmt.setString(3, phase.getTheme());
            stmt.setInt(4, phase.getConclusionReward());
            stmt.setInt(5, phase.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar fase: " + e.getMessage());
        }
        return false;
    }

    /**
     * Remove uma fase do banco de dados.
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM Phases WHERE id = ?";
        
        try (Connection conn = conexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar fase: " + e.getMessage());
        }
        return false;
    }
}

