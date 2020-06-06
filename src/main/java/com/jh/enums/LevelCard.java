package com.jh.enums;

import java.util.stream.Stream;

public enum LevelCard {
	_2(2), _3(3), _4(4), _5(5), _6(6), _7(7), _8(8), _9(9), _T(10), _J(11), _Q(12), _K(13), _A(14);

	public final int level;

	private LevelCard(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public static Stream<LevelCard> stream() {
		return Stream.of(LevelCard.values());
	}
}
