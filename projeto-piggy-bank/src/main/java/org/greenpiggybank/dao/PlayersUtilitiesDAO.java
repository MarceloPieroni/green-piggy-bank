package org.greenpiggybank.dao;

import org.greenpiggybank.classes.PlayersUtilities;
import org.greenpiggybank.database.ConexaoBanco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para operações da tabela de relacionamento PlayersUtilities.
 */
public class PlayersUtilitiesDAO {
    private ConexaoBanco conexaoBanco;

    public PlayersUtilitiesDAO() {
        this.conexaoBanco = new ConexaoBanco();
    }

    /**
     * Adiciona uma utilidade a um jogador.
     */
    public boolean insert(PlayersUtilities playersUtilities) {
        String sql = "INSERT INTO PlayersUtilities (playerId, utilityId) VALUES (?, ?)";
        
        try (Connection conn = conexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, playersUtilities.getPlayerId());
            stmt.setInt(2, playersUtilities.getUtilityId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar utilidade ao jogador: " + e.getMessage());
        }
        return false;
    }

    /**
     * Verifica se um jogador possui uma utilidade específica.
     */
    public boolean exists(int playerId, int utilityId) {
        String sql = "SELECT COUNT(*) FROM PlayersUtilities WHERE playerId = ? AND utilityId = ?";
        
        try (Connection conn = conexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, playerId);
            stmt.setInt(2, utilityId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar utilidade do jogador: " + e.getMessage());
        }
        return false;
    }

    /**
     * Lista todas as utilidades de um jogador.
     */
    public List<Integer> findUtilitiesByPlayerId(int playerId) {
        String sql = "SELECT utilityId FROM PlayersUtilities WHERE playerId = ?";
        List<Integer> utilityIds = new ArrayList<>();
        
        try (Connection conn = conexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, playerId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                utilityIds.add(rs.getInt("utilityId"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar utilidades do jogador: " + e.getMessage());
        }
        return utilityIds;
    }

    /**
     * Lista todos os jogadores que possuem uma utilidade específica.
     */
    public List<Integer> findPlayersByUtilityId(int utilityId) {
        String sql = "SELECT playerId FROM PlayersUtilities WHERE utilityId = ?";
        List<Integer> playerIds = new ArrayList<>();
        
        try (Connection conn = conexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, utilityId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                playerIds.add(rs.getInt("playerId"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar jogadores com a utilidade: " + e.getMessage());
        }
        return playerIds;
    }

    /**
     * Remove uma utilidade de um jogador.
     */
    public boolean delete(int playerId, int utilityId) {
        String sql = "DELETE FROM PlayersUtilities WHERE playerId = ? AND utilityId = ?";
        
        try (Connection conn = conexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, playerId);
            stmt.setInt(2, utilityId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao remover utilidade do jogador: " + e.getMessage());
        }
        return false;
    }

    /**
     * Remove todas as utilidades de um jogador.
     */
    public boolean deleteAllByPlayerId(int playerId) {
        String sql = "DELETE FROM PlayersUtilities WHERE playerId = ?";
        
        try (Connection conn = conexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, playerId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao remover utilidades do jogador: " + e.getMessage());
        }
        return false;
    }
}

