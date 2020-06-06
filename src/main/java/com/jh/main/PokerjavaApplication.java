package com.jh.main;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.jh.beans.Card;
import com.jh.beans.PlayResponse;
import com.jh.beans.PokerGame;
import com.jh.config.Config;
import com.jh.enums.LevelHand;
import com.jh.utils.PokerUtils;
import com.jh.utils.Util;

@SpringBootApplication
@EnableConfigurationProperties(Config.class)
public class PokerjavaApplication {
	private static final Logger log = LoggerFactory.getLogger(PokerjavaApplication.class);
	private static final String USER_DIR = "user.dir";

	private static final String _INTRO = "\n";
	private static final String _PLAYER_1 = "PLAYER 1";
	private static final String _PLAYER_2 = "PLAYER 2";
	private static final String _NEITHER = "NEITHER";

	public static void main(String[] args) {
		SpringApplication.run(PokerjavaApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(Config config) {
		return args -> {
			String dirPathData = System.getProperty(USER_DIR) + config.getPokerPathData();
			String filePathData = dirPathData + config.getPokerFileNameData();
			String filePathResult = dirPathData + config.getPokerFileNameResult();
			String encoding = config.getEncoding();

			log.info("FILE PATH DATA: {}, FILE PATH RESULT: {}", filePathData, filePathResult);

			String content = Util.getContentFile(filePathData, encoding);

			ArrayList<PokerGame> arrPokerGames = PokerUtils.formatPokerGame(content);

			StringBuilder result = new StringBuilder();
			StringBuilder probabilities = new StringBuilder();
			int countPlayer1Wins = 0;
			int countPlayer2Wins = 0;
			int countPlayerNeitherWins = 0;

			// Validate the Player Win
			for (PokerGame pokerGame : arrPokerGames) {
				PlayResponse response = play(pokerGame);
				String winner = response.getWinner();

				probabilities.append(response.getProbability()).append(_INTRO);

				switch (winner) {
				case _PLAYER_1:
					countPlayer1Wins++;
					break;
				case _PLAYER_2:
					countPlayer2Wins++;
					break;
				case _NEITHER:
					countPlayerNeitherWins++;
					break;
				}
			}
			// Build the Result
			result.append("------------------------- ANSWERS -------------------------").append(_INTRO);
			result.append("1: ").append(countPlayer1Wins).append(_INTRO);
			result.append("2: ").append(countPlayer2Wins).append(_INTRO);
			result.append("3: ").append(countPlayerNeitherWins).append(_INTRO);
			result.append("4: ").append(_INTRO);
			result.append("--------- ").append(_PLAYER_1).append(" --------- | --------- ").append(_PLAYER_2).append(" ---------").append(_INTRO);
			result.append(probabilities);

			log.info("FINAL RESULT:" + result);
			Util.generateFile(result.toString(), filePathResult, encoding);
		};
	}

	private PlayResponse play(PokerGame pokerGame) {
		PlayResponse response = new PlayResponse();

		ArrayList<Card> player1Hand = pokerGame.getPlayer1Hand();
		ArrayList<Card> player2Hand = pokerGame.getPlayer2Hand();

		LevelHand player1HandLevel = getLevelHand(player1Hand);
		LevelHand player2HandLevel = getLevelHand(player2Hand);

		response.setProbability("       " + Util.formatProbability(player1HandLevel.probability) + "%           |        " + Util.formatProbability(player2HandLevel.probability) + "%          ");

		if (player1HandLevel.level > player2HandLevel.level) {
			response.setWinner(_PLAYER_1);
		} else if (player1HandLevel.level < player2HandLevel.level) {
			response.setWinner(_PLAYER_2);
		} else if (player1HandLevel.level == player2HandLevel.level) {
			if (player1HandLevel == LevelHand._ROYAL_FLUSH) {
				response.setWinner(_NEITHER);
			} else if (player1HandLevel == LevelHand._STRAIGHT_FLUSH) {
				response.setWinner(_NEITHER);
			} else if (player1HandLevel == LevelHand._FOUR_OF_A_KIND) {
				ArrayList<Card> player1HandCombination = PokerUtils.isFourOfAKind(player1Hand).getHandCombinationOne();
				ArrayList<Card> player2HandCombination = PokerUtils.isFourOfAKind(player2Hand).getHandCombinationOne();

				int levelPlayer1Hand = PokerUtils.maxValueInHand(player1HandCombination).getLevelCard().getLevel();
				int levelPlayer2Hand = PokerUtils.maxValueInHand(player2HandCombination).getLevelCard().getLevel();

				if (levelPlayer1Hand > levelPlayer2Hand)
					response.setWinner(_PLAYER_1);
				else if (levelPlayer1Hand < levelPlayer2Hand)
					response.setWinner(_PLAYER_2);
				else
					response.setWinner(PokerUtils.compareHighest(player1Hand, player2Hand));
			} else if (player1HandLevel == LevelHand._FULL_HOUSE) {
				ArrayList<Card> player1HandCombination1 = PokerUtils.isFullHouse(player1Hand).getHandCombinationOne();
				ArrayList<Card> player1HandCombination2 = PokerUtils.isFullHouse(player1Hand).getHandCombinationTwo();
				ArrayList<Card> player2HandCombination1 = PokerUtils.isFullHouse(player2Hand).getHandCombinationOne();
				ArrayList<Card> player2HandCombination2 = PokerUtils.isFullHouse(player2Hand).getHandCombinationTwo();

				int levelPlayer1HandCombination1 = PokerUtils.maxValueInHand(player1HandCombination1).getLevelCard().getLevel();
				int levelPlayer1HandCombination2 = PokerUtils.maxValueInHand(player1HandCombination2).getLevelCard().getLevel();
				int levelPlayer2HandCombination1 = PokerUtils.maxValueInHand(player2HandCombination1).getLevelCard().getLevel();
				int levelPlayer2HandCombination2 = PokerUtils.maxValueInHand(player2HandCombination2).getLevelCard().getLevel();

				if (levelPlayer1HandCombination1 > levelPlayer2HandCombination1)
					response.setWinner(_PLAYER_1);
				else if (levelPlayer1HandCombination1 < levelPlayer2HandCombination1)
					response.setWinner(_PLAYER_2);
				else if (levelPlayer1HandCombination2 > levelPlayer2HandCombination2)
					response.setWinner(_PLAYER_1);
				else if (levelPlayer1HandCombination2 < levelPlayer2HandCombination2)
					response.setWinner(_PLAYER_2);
				else
					response.setWinner(PokerUtils.compareHighest(player1Hand, player2Hand));
			} else if (player1HandLevel == LevelHand._FLUSH) {
				response.setWinner(PokerUtils.compareHighest(player1Hand, player2Hand));
			} else if (player1HandLevel == LevelHand._STRAIGHT) {
				response.setWinner(PokerUtils.compareHighest(player1Hand, player2Hand));
			} else if (player1HandLevel == LevelHand._THREE_OF_A_KIND) {
				ArrayList<Card> player1HandCombination = PokerUtils.isThreeOfAKind(player1Hand).getHandCombinationOne();
				ArrayList<Card> player2HandCombination = PokerUtils.isThreeOfAKind(player2Hand).getHandCombinationOne();

				int levelPlayer1Hand = PokerUtils.maxValueInHand(player1HandCombination).getLevelCard().getLevel();
				int levelPlayer2Hand = PokerUtils.maxValueInHand(player2HandCombination).getLevelCard().getLevel();

				if (levelPlayer1Hand > levelPlayer2Hand)
					response.setWinner(_PLAYER_1);
				else if (levelPlayer1Hand < levelPlayer2Hand)
					response.setWinner(_PLAYER_2);
				else
					response.setWinner(PokerUtils.compareHighest(player1Hand, player2Hand));
			} else if (player1HandLevel == LevelHand._TWO_PAIRS) {
				ArrayList<Card> player1HandCombination1 = PokerUtils.isTwoPairs(player1Hand).getHandCombinationOne();
				ArrayList<Card> player1HandCombination2 = PokerUtils.isTwoPairs(player1Hand).getHandCombinationTwo();
				ArrayList<Card> player2HandCombination1 = PokerUtils.isTwoPairs(player2Hand).getHandCombinationOne();
				ArrayList<Card> player2HandCombination2 = PokerUtils.isTwoPairs(player2Hand).getHandCombinationTwo();

				int levelPlayer1HandCombination1 = PokerUtils.maxValueInHand(player1HandCombination1).getLevelCard().getLevel();
				int levelPlayer1HandCombination2 = PokerUtils.maxValueInHand(player1HandCombination2).getLevelCard().getLevel();
				int levelPlayer2HandCombination1 = PokerUtils.maxValueInHand(player2HandCombination1).getLevelCard().getLevel();
				int levelPlayer2HandCombination2 = PokerUtils.maxValueInHand(player2HandCombination2).getLevelCard().getLevel();

				if (levelPlayer1HandCombination1 > levelPlayer2HandCombination1)
					response.setWinner(_PLAYER_1);
				else if (levelPlayer1HandCombination1 < levelPlayer2HandCombination1)
					response.setWinner(_PLAYER_2);
				else if (levelPlayer1HandCombination2 > levelPlayer2HandCombination2)
					response.setWinner(_PLAYER_1);
				else if (levelPlayer1HandCombination2 < levelPlayer2HandCombination2)
					response.setWinner(_PLAYER_2);
				else
					response.setWinner(PokerUtils.compareHighest(player1Hand, player2Hand));
			} else if (player1HandLevel == LevelHand._ONE_PAIR) {
				ArrayList<Card> player1HandCombination = PokerUtils.isOnePair(player1Hand).getHandCombinationOne();
				ArrayList<Card> player2HandCombination = PokerUtils.isOnePair(player2Hand).getHandCombinationOne();

				int levelPlayer1Hand = PokerUtils.maxValueInHand(player1HandCombination).getLevelCard().getLevel();
				int levelPlayer2Hand = PokerUtils.maxValueInHand(player2HandCombination).getLevelCard().getLevel();

				if (levelPlayer1Hand > levelPlayer2Hand)
					response.setWinner(_PLAYER_1);
				else if (levelPlayer1Hand < levelPlayer2Hand)
					response.setWinner(_PLAYER_2);
				else
					response.setWinner(PokerUtils.compareHighest(player1Hand, player2Hand));
			} else {
				response.setWinner(PokerUtils.compareHighest(player1Hand, player2Hand));
			}
		}
		return response;
	}

	private LevelHand getLevelHand(ArrayList<Card> playerHand) {
		if (PokerUtils.isRoyalFlush(playerHand)) {
			return LevelHand._ROYAL_FLUSH;
		} else if (PokerUtils.isStraightFlush(playerHand)) {
			return LevelHand._STRAIGHT_FLUSH;
		} else if (PokerUtils.isFourOfAKind(playerHand).isValid()) {
			return LevelHand._FOUR_OF_A_KIND;
		} else if (PokerUtils.isFullHouse(playerHand).isValid()) {
			return LevelHand._FULL_HOUSE;
		} else if (PokerUtils.isFlush(playerHand)) {
			return LevelHand._FLUSH;
		} else if (PokerUtils.isStraight(playerHand)) {
			return LevelHand._STRAIGHT;
		} else if (PokerUtils.isThreeOfAKind(playerHand).isValid()) {
			return LevelHand._THREE_OF_A_KIND;
		} else if (PokerUtils.isTwoPairs(playerHand).isValid()) {
			return LevelHand._TWO_PAIRS;
		} else if (PokerUtils.isOnePair(playerHand).isValid()) {
			return LevelHand._ONE_PAIR;
		} else {
			return LevelHand._HIGH_CARD;
		}
	}

}
