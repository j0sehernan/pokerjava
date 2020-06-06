package com.jh.enums;

import java.util.stream.Stream;

public enum TypeCard {
	_S("SPADES"), _H("HEARTS"), _D("DIAMONDS"), _C("CLUBS");

	public final String label;

	private TypeCard(String label) {
		this.label = label;
	}

	public static Stream<TypeCard> stream() {
		return Stream.of(TypeCard.values());
	}
}
