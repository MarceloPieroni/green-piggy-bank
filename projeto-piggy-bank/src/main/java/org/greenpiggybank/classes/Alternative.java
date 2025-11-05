package org.greenpiggybank.classes;

/**
 * Representa uma alternativa de resposta para uma pergunta.
 * Cada alternativa pode ser marcada como correta ou incorreta.
 */
public class Alternative {
    private int id;
    private String description;
    private boolean correctAnswer;
    private int questionId;

    // Construtores
    public Alternative() {
    }

    public Alternative(String description, boolean correctAnswer, int questionId) {
        this.description = description;
        this.correctAnswer = correctAnswer;
        this.questionId = questionId;
    }

    public Alternative(int id, String description, boolean correctAnswer, int questionId) {
        this.id = id;
        this.description = description;
        this.correctAnswer = correctAnswer;
        this.questionId = questionId;
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

    public boolean isCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Override
    public String toString() {
        return "Alternative{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", correctAnswer=" + correctAnswer +
                ", questionId=" + questionId +
                '}';
    }
}
