package org.greenpiggybank.classes;

/**
 * Representa uma unidade monetária no sistema.
 * Pode ser usado para cálculos financeiros e representação de valores.
 */
public class Money {
    private int id;
    private double amount;
    private String currency;

    // Construtores
    public Money() {
        this.currency = "BRL"; // Real Brasileiro por padrão
    }

    public Money(double amount) {
        this.amount = amount;
        this.currency = "BRL";
    }

    public Money(double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Money(int id, double amount, String currency) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    // Métodos auxiliares
    public void add(Money other) {
        if (this.currency.equals(other.currency)) {
            this.amount += other.amount;
        }
    }

    public void subtract(Money other) {
        if (this.currency.equals(other.currency) && this.amount >= other.amount) {
            this.amount -= other.amount;
        }
    }

    public Money multiply(double factor) {
        return new Money(this.amount * factor, this.currency);
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", amount, currency);
    }
}
