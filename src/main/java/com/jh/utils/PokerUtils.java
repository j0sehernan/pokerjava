package com.jh.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

import com.jh.beans.Card;
import com.jh.beans.PokerGame;
import com.jh.beans.ResponseHand;
import com.jh.enums.LevelCard;
import com.jh.enums.TypeCard;

public class PokerUtils {
	private static final String _INTRO = "\n";
	private static final String _ONE_SPACE = " ";
	private static final String _EMPTY = "";
	private static final String _SUB = "_";
	private static final String _PLAYER_1 = "PLAYER 1";
	private static final String _PLAYER_2 = "PLAYER 2";
	private static final String _NEITHER = "NEITHER";

	public static boolean isRoyalFlush(ArrayList<Card> playerHand) {
		boolean response = false;
		int countHits = 0;
		TypeCard type = playerHand.get(0).getType();
		LevelCard[] arrValues = { LevelCard._A, LevelCard._K, LevelCard._Q, LevelCard._J, LevelCard._T };

		for (LevelCard value : arrValues) {
			Card currentCard = findValueInHand(playerHand, value);

			if (currentCard != null && currentCard.getType() == type)
				countHits++;
			else
				return response;
		}

		if (countHits == 5)
			response = true;

		return response;
	}

	public static boolean isStraightFlush(ArrayList<Card> playerHand) {
		boolean response = false;
		AtomicInteger countHits = new AtomicInteger(0);
		TypeCard type = playerHand.get(0).getType();
		LevelCard maxValue = maxValueInHand(playerHand).getLevelCard();

		if (maxValue.level - 4 < 0)
			return response;

		LevelCard.stream().filter(levelCard -> levelCard.getLevel() <= maxValue.level && levelCard.getLevel() >= maxValue.level - 4).forEach(levelCard -> {
			Card currentCard = findValueInHand(playerHand, levelCard);

			if (currentCard != null && currentCard.getType() == type)
				countHits.incrementAndGet();
		});

		if (countHits.get() == 5)
			response = true;

		return response;
	}

	public static ResponseHand isFourOfAKind(ArrayList<Card> playerHand) {
		ResponseHand response = new ResponseHand(false);
		ArrayList<Card> responseHand = findSameValuesInAHand(playerHand, 4);

		if (responseHand.size() == 4) {
			response.setValid(true);
			response.setHandCombinationOne(responseHand);
		}

		return response;
	}

	public static ResponseHand isFullHouse(ArrayList<Card> playerHand) {
		ResponseHand response = new ResponseHand(false);
		ArrayList<Card> responseHandCombinationOne = findSameValuesInAHand(playerHand, 3);
		ArrayList<Card> responseHandCombinationTwo = findSameValuesInAHand(playerHand, responseHandCombinationOne, 2);

		if (responseHandCombinationOne.size() == 3 && responseHandCombinationTwo.size() == 2) {
			response.setValid(true);
			response.setHandCombinationOne(responseHandCombinationOne);
			response.setHandCombinationTwo(responseHandCombinationTwo);
		}

		return response;
	}

	public static boolean isFlush(ArrayList<Card> playerHand) {
		boolean response = false;
		int countHits = 0;
		TypeCard type = playerHand.get(0).getType();

		for (Card card : playerHand) {
			if (card.getType() == type) {
				countHits++;
			} else {
				return response;
			}
		}

		if (countHits == 5)
			response = true;

		return response;
	}

	public static boolean isStraight(ArrayList<Card> playerHand) {
		boolean response = false;
		AtomicInteger countHits = new AtomicInteger(0);
		LevelCard maxValue = maxValueInHand(playerHand).getLevelCard();

		if (maxValue.level - 4 < 0)
			return response;

		LevelCard.stream().filter(levelCard -> levelCard.getLevel() <= maxValue.level && levelCard.getLevel() >= maxValue.level - 4).forEach(levelCard -> {
			Card currentCard = findValueInHand(playerHand, levelCard);

			if (currentCard != null)
				countHits.incrementAndGet();
		});

		if (countHits.get() == 5)
			response = true;

		return response;
	}

	public static ResponseHand isThreeOfAKind(ArrayList<Card> playerHand) {
		ResponseHand response = new ResponseHand(false);
		ArrayList<Card> responseHand = findSameValuesInAHand(playerHand, 3);

		if (responseHand.size() == 3) {
			response.setValid(true);
			response.setHandCombinationOne(responseHand);
		}

		return response;
	}

	public static ResponseHand isTwoPairs(ArrayList<Card> playerHand) {
		ResponseHand response = new ResponseHand(false);
		ArrayList<Card> responseHandCombinationOne = findSameValuesInAHand(playerHand, 2);
		ArrayList<Card> responseHandCombinationTwo = findSameValuesInAHand(playerHand, responseHandCombinationOne, 2);

		if (responseHandCombinationOne.size() == 2 && responseHandCombinationTwo.size() == 2) {
			response.setValid(true);
			response.setHandCombinationOne(responseHandCombinationOne);
			response.setHandCombinationTwo(responseHandCombinationTwo);
		}

		return response;
	}

	public static ResponseHand isOnePair(ArrayList<Card> playerHand) {
		ResponseHand response = new ResponseHand(false);
		ArrayList<Card> responseHand = findSameValuesInAHand(playerHand, 2);

		if (responseHand.size() == 2) {
			response.setValid(true);
			response.setHandCombinationOne(responseHand);
		}

		return response;
	}

	public static ArrayList<Card> findSameValuesInAHand(ArrayList<Card> playerHand, ArrayList<Card> exeptPlayerHand, int expectedHits) {
		ArrayList<Card> playerHandTest = new ArrayList<Card>();

		for (Card card : playerHand) {
			playerHandTest.add(card);
		}

		AtomicInteger countHits = new AtomicInteger(0);
		ArrayList<Card> responseHand = new ArrayList<Card>();

		for (Card card : exeptPlayerHand) {
			playerHandTest.remove(card);
		}

		for (Card card : playerHandTest) {
			TypeCard.stream().forEach(typeCard -> {
				Card testCard = new Card(card.getLevelCard(), typeCard);
				Card currentCard = findCardInHand(playerHandTest, testCard);

				if (currentCard != null) {
					countHits.incrementAndGet();
					responseHand.add(currentCard);
				}
			});

			if (countHits.get() == expectedHits) {
				break;
			} else {
				countHits.set(0);
				responseHand.clear();
			}
		}

		return responseHand;
	}

	public static ArrayList<Card> findSameValuesInAHand(ArrayList<Card> playerHand, int expectedHits) {
		AtomicInteger countHits = new AtomicInteger(0);
		ArrayList<Card> responseHand = new ArrayList<Card>();

		for (Card card : playerHand) {
			TypeCard.stream().forEach(typeCard -> {
				Card testCard = new Card(card.getLevelCard(), typeCard);
				Card currentCard = findCardInHand(playerHand, testCard);

				if (currentCard != null) {
					countHits.incrementAndGet();
					responseHand.add(currentCard);
				}
			});

			if (countHits.get() == expectedHits) {
				break;
			} else {
				countHits.set(0);
				responseHand.clear();
			}
		}

		return responseHand;
	}

	public static ArrayList<PokerGame> formatPokerGame(String content) {
		ArrayList<PokerGame> arrPokerGames = new ArrayList<>();
		String[] arrPokerGamesString = content.split(_INTRO);

		for (String pokerGameTemp : arrPokerGamesString) {

			String[] arrCardsString = pokerGameTemp.split(_ONE_SPACE);
			// Fill First and Second Hand
			ArrayList<Card> player1Hand = new ArrayList<>();
			ArrayList<Card> player2Hand = new ArrayList<>();

			for (int i = 0; i < arrCardsString.length; i++) {
				String[] arrValue = arrCardsString[i].split(_EMPTY);
				Card card = new Card();
				card.setLevelCard(LevelCard.valueOf(_SUB + arrValue[0]));
				card.setType(TypeCard.valueOf(_SUB + arrValue[1]));
				if (i < 5) {
					player1Hand.add(card);
				} else {
					player2Hand.add(card);
				}
			}
			// Sort Hands
			player1Hand.sort(Comparator.comparing(Card::getLevelCard));
			player2Hand.sort(Comparator.comparing(Card::getLevelCard));

			arrPokerGames.add(new PokerGame(player1Hand, player2Hand));
		}
		return arrPokerGames;
	}

	public static Card findValueInHand(ArrayList<Card> playerHand, LevelCard levelCard) {
		return playerHand.stream().filter(card -> card.getLevelCard().equals(levelCard)).findAny().orElse(null);
	}

	public static Card findCardInHand(ArrayList<Card> playerHand, Card card) {
		return playerHand.stream().filter(item -> item.equals(card)).findAny().orElse(null);
	}

	public static Card maxValueInHand(ArrayList<Card> playerHand) {
		return playerHand.stream().max(Comparator.comparing(Card::getLevelCard)).get();
	}

	public static ArrayList<Card> removeValueInHand(ArrayList<Card> playerHand, LevelCard levelCard) {
		TypeCard.stream().forEach(item -> {
			Card card = new Card(levelCard, item);
			playerHand.remove(card);
		});

		return playerHand;
	}

	public static String compareHighest(ArrayList<Card> player1Hand, ArrayList<Card> player2Hand) {
		if (player1Hand.size() == 0 || player2Hand.size() == 0)
			return _NEITHER;
		else {
			Card maxLevelPlayer1Hand = maxValueInHand(player1Hand);
			Card maxLevelPlayer2Hand = maxValueInHand(player2Hand);

			if (maxLevelPlayer1Hand.getLevelCard().getLevel() > maxLevelPlayer2Hand.getLevelCard().getLevel())
				return _PLAYER_1;
			else if (maxLevelPlayer1Hand.getLevelCard().getLevel() < maxLevelPlayer2Hand.getLevelCard().getLevel())
				return _PLAYER_2;
			else {
				// DELETE REPEATED
				player1Hand = removeValueInHand(player1Hand, maxLevelPlayer1Hand.getLevelCard());
				player2Hand = removeValueInHand(player2Hand, maxLevelPlayer2Hand.getLevelCard());
				return compareHighest(player1Hand, player2Hand);
			}
		}
	}
}
