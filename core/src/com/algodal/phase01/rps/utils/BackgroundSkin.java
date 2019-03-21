package com.algodal.phase01.rps.utils;

public class BackgroundSkin extends DisplaySkin {

	public final String bg;

	public BackgroundSkin(String bg) {
		this.bg = bg;
	}
	
	@Override
	public String getRegion() {
		return bg;
	}
}
