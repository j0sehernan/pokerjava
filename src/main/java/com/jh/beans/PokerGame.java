package com.jh.beans;

import java.util.ArrayList;

import lombok.Data;

@Data
public class PokerGame {
	ArrayList<Card> player1Hand;
	ArrayList<Card> player2Hand;

	public PokerGame(ArrayList<Card> player1Hand, ArrayList<Card> player2Hand) {
		this.player1Hand = player1Hand;
		this.player2Hand = player2Hand;
	}
}
