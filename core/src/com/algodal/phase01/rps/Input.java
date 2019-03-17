package com.algodal.phase01.rps;

public interface Input {

	public void up(float x, float y);
	public void down(float x, float y);
	public void drag(float x, float y);
	public void tap(float x, float y, int count);
	public void press(float x, float y);
	public void fling(float vx, float vy);
}
