package org.greenpiggybank.classes;

/**
 * Representa um jogador no sistema de educação financeira gamificada.
 * Armazena informações do usuário, moedas acumuladas e progresso.
 */
public class Player {
    private int id;
    private String username;
    private int coins;
    private int consecutiveDays;

    // Construtores
    public Player() {
    }

    public Player(String username) {
        this.username = username;
        this.coins = 0;
        this.consecutiveDays = 0;
    }

    public Player(int id, String username, int coins, int consecutiveDays) {
        this.id = id;
        this.username = username;
        this.coins = coins;
        this.consecutiveDays = consecutiveDays;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getConsecutiveDays() {
        return consecutiveDays;
    }

    public void setConsecutiveDays(int consecutiveDays) {
        this.consecutiveDays = consecutiveDays;
    }

    // Métodos auxiliares
    public void addCoins(int amount) {
        this.coins += amount;
    }

    public void subtractCoins(int amount) {
        if (this.coins >= amount) {
            this.coins -= amount;
        }
    }

    public void incrementConsecutiveDays() {
        this.consecutiveDays++;
    }

    public void resetConsecutiveDays() {
        this.consecutiveDays = 0;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", coins=" + coins +
                ", consecutiveDays=" + consecutiveDays +
                '}';
    }
}
