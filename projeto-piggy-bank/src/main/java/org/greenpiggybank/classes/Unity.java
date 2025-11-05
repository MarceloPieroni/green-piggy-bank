package org.greenpiggybank.classes;

/**
 * Representa uma utilidade/conquista que pode ser adquirida pelos jogadores.
 * As utilidades podem ser compradas com moedas acumuladas.
 */
public class Unity {
    private int id;
    private String description;
    private int price;

    // Construtores
    public Unity() {
    }

    public Unity(String description, int price) {
        this.description = description;
        this.price = price;
    }

    public Unity(int id, String description, int price) {
        this.id = id;
        this.description = description;
        this.price = price;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Verifica se um jogador tem moedas suficientes para comprar esta utilidade.
     */
    public boolean canAfford(int playerCoins) {
        return playerCoins >= this.price;
    }

    @Override
    public String toString() {
        return "Unity{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
