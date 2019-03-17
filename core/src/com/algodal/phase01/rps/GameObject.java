package com.algodal.phase01.rps;

public class GameObject {

	public float x, y, width, height, angle;
	
	public float hw() { return width / 2; }
	public float hh() { return height / 2; }
	
	public float left() { return x - hw(); }
	public float right() { return x + hw(); }
	public float bottom() { return y - hh(); }
	public float top() { return y + hh(); }
	
	public void left(float left) { x = left + hw(); }
	public void right(float right) { x = right - hw(); }
	public void bottom(float bottom) { y = bottom + hh(); }
	public void top(float top) { y = top - hh(); }
	
	public void copy(GameObject o) {
		x = o.x;
		y = o.y;
		width = o.width;
		height = o.height;
		angle = o.angle;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		final GameObject o = new GameObject();
		o.copy(this);
		return o;
	}
	
	@Override
	public String toString() {
		return
				" GameObject" +
				" x: " + x +
				" y: " + y +
				" width: " + width +
				" height: " + height +
				" angle: " + angle;
	}
	
	public boolean contains(float x, float y) {
		return
				x >= left() && x <= right() &&
				y >= bottom() && y <= top();
	}
}




















