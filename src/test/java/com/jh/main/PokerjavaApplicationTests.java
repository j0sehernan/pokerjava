package com.jh.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import com.jh.beans.Card;
import com.jh.beans.ResponseHand;
import com.jh.config.Config;
import com.jh.enums.LevelCard;
import com.jh.enums.TypeCard;
import com.jh.utils.PokerUtils;

@SpringBootTest
@EnableConfigurationProperties(Config.class)
class PokerjavaApplicationTests {

	@Autowired
	Config config;

	@Test
	void applicationRunner() {
		assertEquals("pokerdata.txt", config.getPokerFileNameData());
	}

	@Test
	void isRoyalFlushTest() {
		ArrayList<Card> playerHandTest = new ArrayList<Card>();
		playerHandTest.add(new Card(LevelCard._A, TypeCard._D));
		playerHandTest.add(new Card(LevelCard._K, TypeCard._D));
		playerHandTest.add(new Card(LevelCard._Q, TypeCard._D));
		playerHandTest.add(new Card(LevelCard._J, TypeCard._D));
		playerHandTest.add(new Card(LevelCard._T, TypeCard._D));
		boolean response = PokerUtils.isRoyalFlush(playerHandTest);
		assertEquals(true, response);

		ArrayList<Card> playerHandNOK = new ArrayList<Card>();
		playerHandNOK.add(new Card(LevelCard._A, TypeCard._C));
		playerHandNOK.add(new Card(LevelCard._K, TypeCard._D));
		playerHandNOK.add(new Card(LevelCard._Q, TypeCard._D));
		playerHandNOK.add(new Card(LevelCard._J, TypeCard._S));
		playerHandNOK.add(new Card(LevelCard._T, TypeCard._D));
		boolean responseNOK = PokerUtils.isRoyalFlush(playerHandNOK);
		assertNotEquals(true, responseNOK);
	}

	@Test
	void isStraightFlushTest() {
		ArrayList<Card> playerHandTest = new ArrayList<Card>();
		playerHandTest.add(new Card(LevelCard._8, TypeCard._D));
		playerHandTest.add(new Card(LevelCard._7, TypeCard._D));
		playerHandTest.add(new Card(LevelCard._6, TypeCard._D));
		playerHandTest.add(new Card(LevelCard._5, TypeCard._D));
		playerHandTest.add(new Card(LevelCard._4, TypeCard._D));

		boolean response = PokerUtils.isStraightFlush(playerHandTest);
		assertEquals(true, response);

		ArrayList<Card> playerHandNOK = new ArrayList<Card>();
		playerHandNOK.add(new Card(LevelCard._8, TypeCard._D));
		playerHandNOK.add(new Card(LevelCard._7, TypeCard._D));
		playerHandNOK.add(new Card(LevelCard._6, TypeCard._D));
		playerHandNOK.add(new Card(LevelCard._5, TypeCard._D));
		playerHandNOK.add(new Card(LevelCard._2, TypeCard._D));
		boolean responseNOK = PokerUtils.isStraightFlush(playerHandNOK);
		assertNotEquals(true, responseNOK);
	}

	@Test
	void isFourOfAKindTest() {
		ArrayList<Card> playerHandTest = new ArrayList<Card>();
		playerHandTest.add(new Card(LevelCard._2, TypeCard._H));
		playerHandTest.add(new Card(LevelCard._2, TypeCard._C));
		playerHandTest.add(new Card(LevelCard._6, TypeCard._D));
		playerHandTest.add(new Card(LevelCard._2, TypeCard._D));
		playerHandTest.add(new Card(LevelCard._2, TypeCard._S));

		ResponseHand response = PokerUtils.isFourOfAKind(playerHandTest);
		assertEquals(true, response.isValid());

		// THIS PART DEPENDS OF TYPECARD ENUM SORT, OTHERWAYS FAIL
		ArrayList<Card> playerHandOKHand = new ArrayList<Card>();
		playerHandOKHand.add(new Card(LevelCard._2, TypeCard._S));
		playerHandOKHand.add(new Card(LevelCard._2, TypeCard._H));
		playerHandOKHand.add(new Card(LevelCard._2, TypeCard._D));
		playerHandOKHand.add(new Card(LevelCard._2, TypeCard._C));
		assertEquals(playerHandOKHand, response.getHandCombinationOne());

		ArrayList<Card> playerHandNOK = new ArrayList<Card>();
		playerHandNOK.add(new Card(LevelCard._2, TypeCard._H));
		playerHandNOK.add(new Card(LevelCard._2, TypeCard._C));
		playerHandNOK.add(new Card(LevelCard._2, TypeCard._D));
		playerHandNOK.add(new Card(LevelCard._3, TypeCard._S));
		playerHandNOK.add(new Card(LevelCard._3, TypeCard._D));
		ResponseHand responseNOK = PokerUtils.isFourOfAKind(playerHandNOK);
		assertNotEquals(true, responseNOK.isValid());
	}

	@Test
	void isFullHouseTest() {
		ArrayList<Card> playerHandTest = new ArrayList<Card>();
		playerHandTest.add(new Card(LevelCard._2, TypeCard._S));
		playerHandTest.add(new Card(LevelCard._2, TypeCard._H));
		playerHandTest.add(new Card(LevelCard._2, TypeCard._D));
		playerHandTest.add(new Card(LevelCard._3, TypeCard._S));
		playerHandTest.add(new Card(LevelCard._3, TypeCard._C));

		ResponseHand response = PokerUtils.isFullHouse(playerHandTest);
		assertEquals(true, response.isValid());

		// THIS PART DEPENDS OF TYPECARD ENUM SORT, OTHERWAYS FAIL
		ArrayList<Card> playerHandOKHand1 = new ArrayList<Card>();
		playerHandOKHand1.add(new Card(LevelCard._2, TypeCard._S));
		playerHandOKHand1.add(new Card(LevelCard._2, TypeCard._H));
		playerHandOKHand1.add(new Card(LevelCard._2, TypeCard._D));
		assertEquals(playerHandOKHand1, response.getHandCombinationOne());

		// THIS PART DEPENDS OF TYPECARD ENUM SORT, OTHERWAYS FAIL
		ArrayList<Card> playerHandOKHand2 = new ArrayList<Card>();
		playerHandOKHand2.add(new Card(LevelCard._3, TypeCard._S));
		playerHandOKHand2.add(new Card(LevelCard._3, TypeCard._C));
		assertEquals(playerHandOKHand2, response.getHandCombinationTwo());

		ArrayList<Card> playerHandTestNOK = new ArrayList<Card>();
		playerHandTestNOK.add(new Card(LevelCard._2, TypeCard._H));
		playerHandTestNOK.add(new Card(LevelCard._2, TypeCard._C));
		playerHandTestNOK.add(new Card(LevelCard._2, TypeCard._D));
		playerHandTestNOK.add(new Card(LevelCard._3, TypeCard._S));
		playerHandTestNOK.add(new Card(LevelCard._4, TypeCard._D));
		ResponseHand responseNOK = PokerUtils.isFullHouse(playerHandTestNOK);
		assertNotEquals(true, responseNOK.isValid());
	}

	@Test
	void isFlushTest() {
		ArrayList<Card> playerHandTest = new ArrayList<Card>();
		playerHandTest.add(new Card(LevelCard._K, TypeCard._D));
		playerHandTest.add(new Card(LevelCard._T, TypeCard._D));
		playerHandTest.add(new Card(LevelCard._2, TypeCard._D));
		playerHandTest.add(new Card(LevelCard._5, TypeCard._D));
		playerHandTest.add(new Card(LevelCard._6, TypeCard._D));
		boolean response = PokerUtils.isFlush(playerHandTest);
		assertEquals(true, response);

		ArrayList<Card> playerHandNOK = new ArrayList<Card>();
		playerHandNOK.add(new Card(LevelCard._2, TypeCard._D));
		playerHandNOK.add(new Card(LevelCard._5, TypeCard._D));
		playerHandNOK.add(new Card(LevelCard._6, TypeCard._S));
		playerHandNOK.add(new Card(LevelCard._7, TypeCard._D));
		playerHandNOK.add(new Card(LevelCard._T, TypeCard._D));
		boolean responseNOK = PokerUtils.isRoyalFlush(playerHandNOK);
		assertNotEquals(true, responseNOK);
	}

	@Test
	void isStraightTest() {
		ArrayList<Card> playerHandTest = new ArrayList<Card>();
		playerHandTest.add(new Card(LevelCard._7, TypeCard._C));
		playerHandTest.add(new Card(LevelCard._8, TypeCard._H));
		playerHandTest.add(new Card(LevelCard._9, TypeCard._D));
		playerHandTest.add(new Card(LevelCard._T, TypeCard._H));
		playerHandTest.add(new Card(LevelCard._J, TypeCard._S));

		boolean response = PokerUtils.isStraight(playerHandTest);
		assertEquals(true, response);

		ArrayList<Card> playerHandNOK = new ArrayList<Card>();
		playerHandNOK.add(new Card(LevelCard._7, TypeCard._C));
		playerHandNOK.add(new Card(LevelCard._8, TypeCard._H));
		playerHandNOK.add(new Card(LevelCard._9, TypeCard._D));
		playerHandNOK.add(new Card(LevelCard._T, TypeCard._H));
		playerHandNOK.add(new Card(LevelCard._Q, TypeCard._S));
		boolean responseNOK = PokerUtils.isStraight(playerHandNOK);
		assertNotEquals(true, responseNOK);
	}

	@Test
	void isThreeOfAKindTest() {
		ArrayList<Card> playerHandTest = new ArrayList<Card>();
		playerHandTest.add(new Card(LevelCard._Q, TypeCard._H));
		playerHandTest.add(new Card(LevelCard._Q, TypeCard._C));
		playerHandTest.add(new Card(LevelCard._Q, TypeCard._D));
		playerHandTest.add(new Card(LevelCard._5, TypeCard._S));
		playerHandTest.add(new Card(LevelCard._A, TypeCard._D));

		ResponseHand response = PokerUtils.isThreeOfAKind(playerHandTest);
		assertEquals(true, response.isValid());

		// THIS PART DEPENDS OF TYPECARD ENUM SORT, OTHERWAYS FAIL
		ArrayList<Card> playerHandOKHand = new ArrayList<Card>();
		playerHandOKHand.add(new Card(LevelCard._Q, TypeCard._H));
		playerHandOKHand.add(new Card(LevelCard._Q, TypeCard._D));
		playerHandOKHand.add(new Card(LevelCard._Q, TypeCard._C));
		assertEquals(playerHandOKHand, response.getHandCombinationOne());

		ArrayList<Card> playerHandNOK = new ArrayList<Card>();
		playerHandNOK.add(new Card(LevelCard._Q, TypeCard._H));
		playerHandNOK.add(new Card(LevelCard._2, TypeCard._C));
		playerHandNOK.add(new Card(LevelCard._4, TypeCard._D));
		playerHandNOK.add(new Card(LevelCard._3, TypeCard._S));
		playerHandNOK.add(new Card(LevelCard._3, TypeCard._D));
		ResponseHand responseNOK = PokerUtils.isThreeOfAKind(playerHandNOK);
		assertNotEquals(true, responseNOK.isValid());
	}

	@Test
	void isTwoPairsTest() {
		ArrayList<Card> playerHandTest = new ArrayList<Card>();
		playerHandTest.add(new Card(LevelCard._3, TypeCard._H));
		playerHandTest.add(new Card(LevelCard._3, TypeCard._D));
		playerHandTest.add(new Card(LevelCard._6, TypeCard._C));
		playerHandTest.add(new Card(LevelCard._6, TypeCard._H));
		playerHandTest.add(new Card(LevelCard._K, TypeCard._S));

		ResponseHand response = PokerUtils.isTwoPairs(playerHandTest);
		assertEquals(true, response.isValid());

		// THIS PART DEPENDS OF TYPECARD ENUM SORT, OTHERWAYS FAIL
		ArrayList<Card> playerHandOKHand1 = new ArrayList<Card>();
		playerHandOKHand1.add(new Card(LevelCard._3, TypeCard._H));
		playerHandOKHand1.add(new Card(LevelCard._3, TypeCard._D));
		assertEquals(playerHandOKHand1, response.getHandCombinationOne());

		// THIS PART DEPENDS OF TYPECARD ENUM SORT, OTHERWAYS FAIL
		ArrayList<Card> playerHandOKHand2 = new ArrayList<Card>();
		playerHandOKHand2.add(new Card(LevelCard._6, TypeCard._H));
		playerHandOKHand2.add(new Card(LevelCard._6, TypeCard._C));
		assertEquals(playerHandOKHand2, response.getHandCombinationTwo());

		ArrayList<Card> playerHandTestNOK = new ArrayList<Card>();
		playerHandTestNOK.add(new Card(LevelCard._3, TypeCard._H));
		playerHandTestNOK.add(new Card(LevelCard._A, TypeCard._C));
		playerHandTestNOK.add(new Card(LevelCard._6, TypeCard._D));
		playerHandTestNOK.add(new Card(LevelCard._6, TypeCard._S));
		playerHandTestNOK.add(new Card(LevelCard._4, TypeCard._D));
		ResponseHand responseNOK = PokerUtils.isTwoPairs(playerHandTestNOK);
		assertNotEquals(true, responseNOK.isValid());
	}

	@Test
	void isOnePairTest() {
		ArrayList<Card> playerHandTest = new ArrayList<Card>();
		playerHandTest.add(new Card(LevelCard._5, TypeCard._H));
		playerHandTest.add(new Card(LevelCard._5, TypeCard._S));
		playerHandTest.add(new Card(LevelCard._2, TypeCard._C));
		playerHandTest.add(new Card(LevelCard._J, TypeCard._C));
		playerHandTest.add(new Card(LevelCard._A, TypeCard._D));

		ResponseHand response = PokerUtils.isOnePair(playerHandTest);
		assertEquals(true, response.isValid());

		// THIS PART DEPENDS OF TYPECARD ENUM SORT, OTHERWAYS FAIL
		ArrayList<Card> playerHandOKHand = new ArrayList<Card>();
		playerHandOKHand.add(new Card(LevelCard._5, TypeCard._S));
		playerHandOKHand.add(new Card(LevelCard._5, TypeCard._H));
		assertEquals(playerHandOKHand, response.getHandCombinationOne());

		ArrayList<Card> playerHandNOK = new ArrayList<Card>();
		playerHandNOK.add(new Card(LevelCard._Q, TypeCard._H));
		playerHandNOK.add(new Card(LevelCard._2, TypeCard._C));
		playerHandNOK.add(new Card(LevelCard._4, TypeCard._D));
		playerHandNOK.add(new Card(LevelCard._6, TypeCard._S));
		playerHandNOK.add(new Card(LevelCard._3, TypeCard._D));
		ResponseHand responseNOK = PokerUtils.isOnePair(playerHandNOK);
		assertNotEquals(true, responseNOK.isValid());
	}

	@Test
	void findSameValuesInAHandTest2() {
		ArrayList<Card> playerHandTest = new ArrayList<Card>();
		playerHandTest.add(new Card(LevelCard._2, TypeCard._S));
		playerHandTest.add(new Card(LevelCard._2, TypeCard._H));
		playerHandTest.add(new Card(LevelCard._2, TypeCard._D));
		playerHandTest.add(new Card(LevelCard._3, TypeCard._C));
		playerHandTest.add(new Card(LevelCard._3, TypeCard._H));

		ArrayList<Card> playerHandExcept = new ArrayList<Card>();
		playerHandExcept.add(new Card(LevelCard._2, TypeCard._S));
		playerHandExcept.add(new Card(LevelCard._2, TypeCard._H));
		playerHandExcept.add(new Card(LevelCard._2, TypeCard._D));

		// THIS PART DEPENDS OF TYPECARD ENUM SORT, OTHERWAYS FAIL
		ArrayList<Card> playerHandExpected = new ArrayList<Card>();
		playerHandExpected.add(new Card(LevelCard._3, TypeCard._H));
		playerHandExpected.add(new Card(LevelCard._3, TypeCard._C));

		ArrayList<Card> response = PokerUtils.findSameValuesInAHand(playerHandTest, playerHandExcept, 2);
		assertEquals(playerHandExpected, response);
	}

	@Test
	void findSameValuesInAHandTest() {
		ArrayList<Card> playerHandOK = new ArrayList<Card>();
		playerHandOK.add(new Card(LevelCard._2, TypeCard._S));
		playerHandOK.add(new Card(LevelCard._2, TypeCard._H));
		playerHandOK.add(new Card(LevelCard._2, TypeCard._D));
		playerHandOK.add(new Card(LevelCard._2, TypeCard._C));
		playerHandOK.add(new Card(LevelCard._T, TypeCard._H));

		ArrayList<Card> playerHandExpected = new ArrayList<Card>();
		playerHandExpected.add(new Card(LevelCard._2, TypeCard._S));
		playerHandExpected.add(new Card(LevelCard._2, TypeCard._H));
		playerHandExpected.add(new Card(LevelCard._2, TypeCard._D));
		playerHandExpected.add(new Card(LevelCard._2, TypeCard._C));

		ArrayList<Card> response = PokerUtils.findSameValuesInAHand(playerHandOK, 4);
		assertEquals(playerHandExpected, response);
	}

	@Test
	void findValueInHandTest() {
		ArrayList<Card> playerHandOK = new ArrayList<Card>();
		playerHandOK.add(new Card(LevelCard._2, TypeCard._D));
		playerHandOK.add(new Card(LevelCard._5, TypeCard._D));
		playerHandOK.add(new Card(LevelCard._J, TypeCard._D));
		playerHandOK.add(new Card(LevelCard._3, TypeCard._D));
		playerHandOK.add(new Card(LevelCard._7, TypeCard._D));

		LevelCard levelCard = LevelCard._J;
		Card card = new Card(levelCard, TypeCard._D);
		Card response = PokerUtils.findValueInHand(playerHandOK, levelCard);
		assertEquals(card, response);
	}

	@Test
	void findCardInHandTest() {
		ArrayList<Card> playerHandOK = new ArrayList<Card>();
		playerHandOK.add(new Card(LevelCard._2, TypeCard._D));
		playerHandOK.add(new Card(LevelCard._5, TypeCard._D));
		playerHandOK.add(new Card(LevelCard._J, TypeCard._H));
		playerHandOK.add(new Card(LevelCard._3, TypeCard._D));
		playerHandOK.add(new Card(LevelCard._7, TypeCard._D));

		Card card = new Card(LevelCard._J, TypeCard._H);
		Card response = PokerUtils.findCardInHand(playerHandOK, card);
		assertEquals(card, response);
	}

	@Test
	void maxValueInHandTest() {
		ArrayList<Card> playerHandTest = new ArrayList<Card>();
		playerHandTest.add(new Card(LevelCard._2, TypeCard._D));
		playerHandTest.add(new Card(LevelCard._5, TypeCard._D));
		playerHandTest.add(new Card(LevelCard._8, TypeCard._D));
		playerHandTest.add(new Card(LevelCard._3, TypeCard._D));
		playerHandTest.add(new Card(LevelCard._7, TypeCard._D));

		Card card = new Card(LevelCard._8, TypeCard._D);
		Card response = PokerUtils.maxValueInHand(playerHandTest);
		assertEquals(card, response);
	}

	@Test
	void compareHighestTest() {
		ArrayList<Card> player1HandTest1 = new ArrayList<Card>();
		player1HandTest1.add(new Card(LevelCard._4, TypeCard._D));
		player1HandTest1.add(new Card(LevelCard._5, TypeCard._S));
		player1HandTest1.add(new Card(LevelCard._6, TypeCard._H));
		player1HandTest1.add(new Card(LevelCard._7, TypeCard._D));
		player1HandTest1.add(new Card(LevelCard._8, TypeCard._C));

		ArrayList<Card> player2HandTest1 = new ArrayList<Card>();
		player2HandTest1.add(new Card(LevelCard._4, TypeCard._D));
		player2HandTest1.add(new Card(LevelCard._5, TypeCard._S));
		player2HandTest1.add(new Card(LevelCard._6, TypeCard._H));
		player2HandTest1.add(new Card(LevelCard._7, TypeCard._D));
		player2HandTest1.add(new Card(LevelCard._8, TypeCard._C));

		String responseTest1 = PokerUtils.compareHighest(player1HandTest1, player2HandTest1);
		assertEquals("NEITHER", responseTest1);

		ArrayList<Card> player1HandTest2 = new ArrayList<Card>();
		player1HandTest2.add(new Card(LevelCard._4, TypeCard._D));
		player1HandTest2.add(new Card(LevelCard._5, TypeCard._S));
		player1HandTest2.add(new Card(LevelCard._6, TypeCard._H));
		player1HandTest2.add(new Card(LevelCard._A, TypeCard._D));
		player1HandTest2.add(new Card(LevelCard._2, TypeCard._C));

		ArrayList<Card> player2HandTest2 = new ArrayList<Card>();
		player2HandTest2.add(new Card(LevelCard._4, TypeCard._D));
		player2HandTest2.add(new Card(LevelCard._5, TypeCard._S));
		player2HandTest2.add(new Card(LevelCard._K, TypeCard._H));
		player2HandTest2.add(new Card(LevelCard._7, TypeCard._D));
		player2HandTest2.add(new Card(LevelCard._8, TypeCard._C));

		String responseTest2 = PokerUtils.compareHighest(player1HandTest2, player2HandTest2);
		assertEquals("PLAYER 1", responseTest2);

		ArrayList<Card> player1HandTest3 = new ArrayList<Card>();
		player1HandTest3.add(new Card(LevelCard._A, TypeCard._D));
		player1HandTest3.add(new Card(LevelCard._A, TypeCard._S));
		player1HandTest3.add(new Card(LevelCard._6, TypeCard._H));
		player1HandTest3.add(new Card(LevelCard._A, TypeCard._C));
		player1HandTest3.add(new Card(LevelCard._A, TypeCard._H));

		ArrayList<Card> player2HandTest3 = new ArrayList<Card>();
		player2HandTest3.add(new Card(LevelCard._A, TypeCard._D));
		player2HandTest3.add(new Card(LevelCard._A, TypeCard._S));
		player2HandTest3.add(new Card(LevelCard._J, TypeCard._H));
		player2HandTest3.add(new Card(LevelCard._7, TypeCard._D));
		player2HandTest3.add(new Card(LevelCard._8, TypeCard._C));

		String responseTest3 = PokerUtils.compareHighest(player1HandTest3, player2HandTest3);
		assertEquals("PLAYER 2", responseTest3);
	}
}
