package com.jh.enums;

public enum LevelHand {
	_HIGH_CARD(1, 50.1177), _ONE_PAIR(2, 42.2569), _TWO_PAIRS(3, 4.7539), _THREE_OF_A_KIND(4, 2.1128), _STRAIGHT(5, 0.3925), _FLUSH(6, 0.1965), _FULL_HOUSE(7, 0.1441), _FOUR_OF_A_KIND(8, 0.0240),
	_STRAIGHT_FLUSH(9, 0.00139), _ROYAL_FLUSH(10, 0.000154);

	public final int level;
	public final double probability;

	private LevelHand(int level, double probability) {
		this.level = level;
		this.probability = 100 - probability;
	}

}
