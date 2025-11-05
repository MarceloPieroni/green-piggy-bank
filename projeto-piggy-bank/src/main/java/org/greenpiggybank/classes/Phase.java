package org.greenpiggybank.classes;

/**
 * Representa uma fase do jogo de educação financeira.
 * Cada fase contém múltiplas questões e um tema específico.
 */
public class Phase {
    private int id;
    private int number;
    private String description;
    private String theme;
    private int conclusionReward;

    // Construtores
    public Phase() {
    }

    public Phase(int number, String description, String theme, int conclusionReward) {
        this.number = number;
        this.description = description;
        this.theme = theme;
        this.conclusionReward = conclusionReward;
    }

    public Phase(int id, int number, String description, String theme, int conclusionReward) {
        this.id = id;
        this.number = number;
        this.description = description;
        this.theme = theme;
        this.conclusionReward = conclusionReward;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public int getConclusionReward() {
        return conclusionReward;
    }

    public void setConclusionReward(int conclusionReward) {
        this.conclusionReward = conclusionReward;
    }

    @Override
    public String toString() {
        return "Phase{" +
                "id=" + id +
                ", number=" + number +
                ", description='" + description + '\'' +
                ", theme='" + theme + '\'' +
                ", conclusionReward=" + conclusionReward +
                '}';
    }
}
