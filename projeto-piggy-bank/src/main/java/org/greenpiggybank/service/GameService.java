package org.greenpiggybank.service;

import org.greenpiggybank.classes.*;
import org.greenpiggybank.dao.*;

import java.util.List;

/**
 * Serviço que contém a lógica de negócio do jogo de educação financeira.
 * Coordena as operações entre jogadores, fases, perguntas e recompensas.
 */
public class GameService {
    private PlayerDAO playerDAO;
    private PhaseDAO phaseDAO;
    private QuestionDAO questionDAO;
    private AlternativeDAO alternativeDAO;
    private UnityDAO unityDAO;
    private PlayersUtilitiesDAO playersUtilitiesDAO;

    public GameService() {
        this.playerDAO = new PlayerDAO();
        this.phaseDAO = new PhaseDAO();
        this.questionDAO = new QuestionDAO();
        this.alternativeDAO = new AlternativeDAO();
        this.unityDAO = new UnityDAO();
        this.playersUtilitiesDAO = new PlayersUtilitiesDAO();
    }

    /**
     * Registra um novo jogador no sistema.
     */
    public Player registerPlayer(String username) {
        // Verifica se o username já existe
        if (playerDAO.findByUsername(username) != null) {
            throw new IllegalArgumentException("Username já existe!");
        }
        
        Player player = new Player(username);
        if (playerDAO.insert(player)) {
            return player;
        }
        throw new RuntimeException("Erro ao registrar jogador");
    }

    /**
     * Faz login de um jogador pelo username.
     */
    public Player loginPlayer(String username) {
        Player player = playerDAO.findByUsername(username);
        if (player == null) {
            throw new IllegalArgumentException("Jogador não encontrado!");
        }
        return player;
    }

    /**
     * Busca todas as perguntas de uma fase específica com suas alternativas.
     */
    public List<Question> getQuestionsByPhase(int phaseId) {
        List<Question> questions = questionDAO.findByPhaseId(phaseId);
        
        // Carrega as alternativas para cada pergunta
        for (Question question : questions) {
            List<Alternative> alternatives = alternativeDAO.findByQuestionId(question.getId());
            question.setAlternatives(alternatives);
        }
        
        return questions;
    }

    /**
     * Valida a resposta de um jogador e concede recompensa se correta.
     */
    public boolean answerQuestion(int playerId, int questionId, int alternativeId) {
        Question question = questionDAO.findById(questionId);
        if (question == null) {
            throw new IllegalArgumentException("Pergunta não encontrada!");
        }

        // Verifica se a resposta está correta
        boolean isCorrect = question.isCorrectAnswer(alternativeId);
        
        if (isCorrect) {
            Player player = playerDAO.findById(playerId);
            if (player != null) {
                // Adiciona a recompensa
                player.addCoins(question.getReward());
                playerDAO.update(player);
            }
        }
        
        return isCorrect;
    }

    /**
     * Concede a recompensa de conclusão de fase ao jogador.
     */
    public boolean completePhase(int playerId, int phaseId) {
        Phase phase = phaseDAO.findById(phaseId);
        if (phase == null) {
            throw new IllegalArgumentException("Fase não encontrada!");
        }

        Player player = playerDAO.findById(playerId);
        if (player == null) {
            throw new IllegalArgumentException("Jogador não encontrado!");
        }

        // Adiciona a recompensa de conclusão
        player.addCoins(phase.getConclusionReward());
        return playerDAO.update(player);
    }

    /**
     * Compra uma utilidade para o jogador.
     */
    public boolean buyUtility(int playerId, int utilityId) {
        Player player = playerDAO.findById(playerId);
        Unity utility = unityDAO.findById(utilityId);

        if (player == null) {
            throw new IllegalArgumentException("Jogador não encontrado!");
        }
        if (utility == null) {
            throw new IllegalArgumentException("Utilidade não encontrada!");
        }

        // Verifica se o jogador já possui a utilidade
        if (playersUtilitiesDAO.exists(playerId, utilityId)) {
            throw new IllegalArgumentException("Jogador já possui esta utilidade!");
        }

        // Verifica se o jogador tem moedas suficientes
        if (!utility.canAfford(player.getCoins())) {
            throw new IllegalArgumentException("Moedas insuficientes!");
        }

        // Deduz o preço e adiciona a utilidade
        player.subtractCoins(utility.getPrice());
        playerDAO.update(player);
        
        PlayersUtilities playersUtilities = new PlayersUtilities(playerId, utilityId);
        return playersUtilitiesDAO.insert(playersUtilities);
    }

    /**
     * Lista todas as utilidades disponíveis para compra.
     */
    public List<Unity> getAvailableUtilities() {
        return unityDAO.findAll();
    }

    /**
     * Lista todas as utilidades que um jogador possui.
     */
    public List<Unity> getPlayerUtilities(int playerId) {
        List<Integer> utilityIds = playersUtilitiesDAO.findUtilitiesByPlayerId(playerId);
        List<Unity> utilities = new java.util.ArrayList<>();
        
        for (Integer id : utilityIds) {
            Unity utility = unityDAO.findById(id);
            if (utility != null) {
                utilities.add(utility);
            }
        }
        
        return utilities;
    }

    /**
     * Incrementa os dias consecutivos de um jogador.
     */
    public void incrementConsecutiveDays(int playerId) {
        Player player = playerDAO.findById(playerId);
        if (player != null) {
            player.incrementConsecutiveDays();
            playerDAO.update(player);
        }
    }

    /**
     * Reseta os dias consecutivos de um jogador.
     */
    public void resetConsecutiveDays(int playerId) {
        Player player = playerDAO.findById(playerId);
        if (player != null) {
            player.resetConsecutiveDays();
            playerDAO.update(player);
        }
    }

    /**
     * Retorna o ranking de jogadores ordenado por moedas.
     */
    public List<Player> getLeaderboard() {
        return playerDAO.findAll();
    }
}

