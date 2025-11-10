package org.greenpiggybank.dao;

import org.greenpiggybank.classes.Player;
import org.greenpiggybank.database.ConexaoBanco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para operações CRUD da entidade Player.
 */
public class PlayerDAO {
    private ConexaoBanco conexaoBanco;

    public PlayerDAO() {
        this.conexaoBanco = new ConexaoBanco();
    }

    /**
     * Insere um novo jogador no banco de dados.
     */
    public boolean insert(Player player) {
        String sql = "INSERT INTO Players (username, coins, consecutiveDays) VALUES (?, ?, ?)";
        
        try (Connection conn = conexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, player.getUsername());
            stmt.setInt(2, player.getCoins());
            stmt.setInt(3, player.getConsecutiveDays());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    player.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir jogador: " + e.getMessage());
        }
        return false;
    }

    /**
     * Busca um jogador pelo ID.
     */
    public Player findById(int id) {
        String sql = "SELECT * FROM Players WHERE id = ?";
        Player player = null;
        
        try (Connection conn = conexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                player = new Player(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getInt("coins"),
                    rs.getInt("consecutiveDays")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar jogador: " + e.getMessage());
        }
        return player;
    }

    /**
     * Busca um jogador pelo username.
     */
    public Player findByUsername(String username) {
        String sql = "SELECT * FROM Players WHERE username = ?";
        Player player = null;
        
        try (Connection conn = conexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                player = new Player(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getInt("coins"),
                    rs.getInt("consecutiveDays")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar jogador por username: " + e.getMessage());
        }
        return player;
    }

    /**
     * Lista todos os jogadores.
     */
    public List<Player> findAll() {
        String sql = "SELECT * FROM Players ORDER BY coins DESC";
        List<Player> players = new ArrayList<>();
        
        try (Connection conn = conexaoBanco.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                players.add(new Player(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getInt("coins"),
                    rs.getInt("consecutiveDays")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar jogadores: " + e.getMessage());
        }
        return players;
    }

    /**
     * Atualiza um jogador existente.
     */
    public boolean update(Player player) {
        String sql = "UPDATE Players SET username = ?, coins = ?, consecutiveDays = ? WHERE id = ?";
        
        try (Connection conn = conexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, player.getUsername());
            stmt.setInt(2, player.getCoins());
            stmt.setInt(3, player.getConsecutiveDays());
            stmt.setInt(4, player.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar jogador: " + e.getMessage());
        }
        return false;
    }

    /**
     * Remove um jogador do banco de dados.
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM Players WHERE id = ?";
        
        try (Connection conn = conexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar jogador: " + e.getMessage());
        }
        return false;
    }
}

