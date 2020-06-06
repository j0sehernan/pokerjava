package com.jh.beans;

import com.jh.enums.LevelCard;
import com.jh.enums.TypeCard;

import lombok.Data;

@Data
public class Card {
	LevelCard levelCard;
	TypeCard type;

	public Card() {
	}

	public Card(LevelCard levelCard, TypeCard type) {
		this.levelCard = levelCard;
		this.type = type;
	}
}
