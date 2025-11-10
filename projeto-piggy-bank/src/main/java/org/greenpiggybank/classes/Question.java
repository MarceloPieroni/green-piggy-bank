package org.greenpiggybank.classes;

import java.util.List;

/**
 * Representa uma pergunta dentro de uma fase.
 * Cada pergunta possui um enunciado, descrição, recompensa e múltiplas alternativas.
 */
public class Question {
    private int id;
    private String statement;
    private String description;
    private int reward;
    private int phaseId;
    private List<Alternative> alternatives;

    // Construtores
    public Question() {
    }

    public Question(String statement, String description, int reward, int phaseId) {
        this.statement = statement;
        this.description = description;
        this.reward = reward;
        this.phaseId = phaseId;
    }

    public Question(int id, String statement, String description, int reward, int phaseId) {
        this.id = id;
        this.statement = statement;
        this.description = description;
        this.reward = reward;
        this.phaseId = phaseId;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public int getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(int phaseId) {
        this.phaseId = phaseId;
    }

    public List<Alternative> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(List<Alternative> alternatives) {
        this.alternatives = alternatives;
    }

    /**
     * Verifica se uma alternativa é a resposta correta.
     */
    public boolean isCorrectAnswer(int alternativeId) {
        if (alternatives == null) {
            return false;
        }
        return alternatives.stream()
                .anyMatch(alt -> alt.getId() == alternativeId && alt.isCorrectAnswer());
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", statement='" + statement + '\'' +
                ", description='" + description + '\'' +
                ", reward=" + reward +
                ", phaseId=" + phaseId +
                '}';
    }
}
