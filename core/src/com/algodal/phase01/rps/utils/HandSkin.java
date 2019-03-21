package com.algodal.phase01.rps.utils;

public class HandSkin extends DisplaySkin {

	public final String rock, paper, scissors;

	public HandSkin(String rock, String paper, String scissors) {
		this.rock = rock;
		this.paper = paper;
		this.scissors = scissors;
	}
	
	@Override
	public String getRegion() {
		switch(type) {
		case 1: return paper;
		case 2: return scissors;
		default: return rock;
		}
	}
	
}
