package com.algodal.phase01.rps.ui;

import com.algodal.phase01.rps.SubGame;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class UI {

	/** Only non-null after add(Stage) called.  Don't try to use directly. 
	 *  Null again after remove() is called.**/
	@SuppressWarnings("unused")
	private Stage stage;
	
	public void add(Stage stage) {
		this.stage = stage;
		//TODO stage.addActor();
	}
	
	public void remove() {
		//TODO stage.getActors().removeValue()
		stage = null;
	}
	
	public void initialize(SubGame sg) {}
	
	private float x, y, s;
	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	public void setScale(float s) {
		this.s = s;
	}
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public float getScale() {
		return s;
	}
	public Stage getStage() {
		return stage;
	}

	
}
