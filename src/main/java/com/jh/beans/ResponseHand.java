package com.jh.beans;

import java.util.ArrayList;

import lombok.Data;

@Data
public class ResponseHand {
	boolean valid;
	ArrayList<Card> handCombinationOne;
	ArrayList<Card> handCombinationTwo;

	public ResponseHand() {
	}

	public ResponseHand(boolean valid) {
		this.valid = valid;
	}

	public ResponseHand(boolean valid, ArrayList<Card> handCombinationOne) {
		this.valid = valid;
		this.handCombinationOne = handCombinationOne;
	}

	public ResponseHand(boolean valid, ArrayList<Card> handCombinationOne, ArrayList<Card> handCombinationTwo) {
		this.valid = valid;
		this.handCombinationOne = handCombinationOne;
		this.handCombinationTwo = handCombinationTwo;
	}
}
