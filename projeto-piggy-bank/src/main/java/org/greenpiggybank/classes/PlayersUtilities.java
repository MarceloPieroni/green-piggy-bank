package org.greenpiggybank.classes;

/**
 * Representa o relacionamento muitos-para-muitos entre Players e Utilities.
 * Armazena quais utilidades cada jogador possui.
 */
public class PlayersUtilities {
    private int playerId;
    private int utilityId;

    // Construtores
    public PlayersUtilities() {
    }

    public PlayersUtilities(int playerId, int utilityId) {
        this.playerId = playerId;
        this.utilityId = utilityId;
    }

    // Getters e Setters
    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getUtilityId() {
        return utilityId;
    }

    public void setUtilityId(int utilityId) {
        this.utilityId = utilityId;
    }

    @Override
    public String toString() {
        return "PlayersUtilities{" +
                "playerId=" + playerId +
                ", utilityId=" + utilityId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayersUtilities that = (PlayersUtilities) o;
        return playerId == that.playerId && utilityId == that.utilityId;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(playerId, utilityId);
    }
}

