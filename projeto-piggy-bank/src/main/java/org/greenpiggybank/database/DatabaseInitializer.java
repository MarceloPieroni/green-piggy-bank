package org.greenpiggybank.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe responsável por inicializar o banco de dados e criar as tabelas necessárias.
 */
public class DatabaseInitializer {
    private ConexaoBanco conexaoBanco;

    public DatabaseInitializer() {
        this.conexaoBanco = new ConexaoBanco();
    }

    /**
     * Cria todas as tabelas do banco de dados.
     */
    public void initializeDatabase() {
        try (Connection conn = conexaoBanco.getConexao();
             Statement stmt = conn.createStatement()) {

            // Tabela Players
            String createPlayers = """
                CREATE TABLE IF NOT EXISTS Players (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    username VARCHAR(100) UNIQUE NOT NULL,
                    coins INT DEFAULT 0,
                    consecutiveDays INT DEFAULT 0
                )
                """;

            // Tabela Phases
            String createPhases = """
                CREATE TABLE IF NOT EXISTS Phases (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    number INT NOT NULL,
                    description TEXT,
                    theme VARCHAR(200),
                    conclusionReward INT DEFAULT 0
                )
                """;

            // Tabela Questions
            String createQuestions = """
                CREATE TABLE IF NOT EXISTS Questions (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    statement TEXT NOT NULL,
                    description TEXT,
                    reward INT DEFAULT 0,
                    phaseId INT,
                    FOREIGN KEY (phaseId) REFERENCES Phases(id) ON DELETE CASCADE
                )
                """;

            // Tabela Alternatives
            String createAlternatives = """
                CREATE TABLE IF NOT EXISTS Alternatives (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    description TEXT NOT NULL,
                    correctAnswer BOOLEAN DEFAULT FALSE,
                    questionId INT,
                    FOREIGN KEY (questionId) REFERENCES Questions(id) ON DELETE CASCADE
                )
                """;

            // Tabela Utilities
            String createUtilities = """
                CREATE TABLE IF NOT EXISTS Utilities (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    description TEXT NOT NULL,
                    price INT NOT NULL
                )
                """;

            // Tabela PlayersUtilities (relacionamento N:N)
            String createPlayersUtilities = """
                CREATE TABLE IF NOT EXISTS PlayersUtilities (
                    playerId INT,
                    utilityId INT,
                    PRIMARY KEY (playerId, utilityId),
                    FOREIGN KEY (playerId) REFERENCES Players(id) ON DELETE CASCADE,
                    FOREIGN KEY (utilityId) REFERENCES Utilities(id) ON DELETE CASCADE
                )
                """;

            // Executa as criações
            stmt.execute(createPlayers);
            stmt.execute(createPhases);
            stmt.execute(createQuestions);
            stmt.execute(createAlternatives);
            stmt.execute(createUtilities);
            stmt.execute(createPlayersUtilities);

            System.out.println("Banco de dados inicializado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao inicializar banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Remove todas as tabelas do banco de dados (útil para testes).
     */
    public void dropAllTables() {
        try (Connection conn = conexaoBanco.getConexao();
             Statement stmt = conn.createStatement()) {

            stmt.execute("DROP TABLE IF EXISTS PlayersUtilities");
            stmt.execute("DROP TABLE IF EXISTS Alternatives");
            stmt.execute("DROP TABLE IF EXISTS Questions");
            stmt.execute("DROP TABLE IF EXISTS Utilities");
            stmt.execute("DROP TABLE IF EXISTS Phases");
            stmt.execute("DROP TABLE IF EXISTS Players");

            System.out.println("Todas as tabelas foram removidas!");

        } catch (SQLException e) {
            System.err.println("Erro ao remover tabelas: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

