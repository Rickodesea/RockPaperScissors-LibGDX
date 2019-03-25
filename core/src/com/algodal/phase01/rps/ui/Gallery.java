package com.algodal.phase01.rps.ui;

import static com.algodal.phase01.rps.Constants.defAtlas;
import static com.algodal.phase01.rps.Constants.defSkin;
import static com.algodal.phase01.rps.Constants.setFromBottom;
import static com.algodal.phase01.rps.Constants.setFromLeft;
import static com.algodal.phase01.rps.Constants.worldHeight;
import static com.algodal.phase01.rps.Constants.worldWidth;

import com.algodal.phase01.rps.ActorWrap;
import com.algodal.phase01.rps.SubGame;
import com.algodal.phase01.rps.utils.DisplaySkin;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class Gallery extends UI {

	private TextButton left, right;
	private Image image;
	private ActorWrap imageWrap;
	
	private float space = 1;
	private final DisplaySkin[] displaySkins;
	public int skinIndex = 0;
	
	public Gallery(DisplaySkin[] displaySkins) {
		this.displaySkins = displaySkins;
	}
	
	@Override
	public void initialize(final SubGame sg) {
		left  = new TextButton("LEFT ", (Skin) sg.get(defSkin));
		right = new TextButton("RIGHT", (Skin) sg.get(defSkin));
		left.setTransform(true);
		right.setTransform(true);
		
		final TextureAtlas atlas = sg.get(defAtlas);
		final TextureRegion tr = atlas.findRegion(displaySkins[skinIndex].getRegion());
		
		image = new Image(tr);
		
		imageWrap = new ActorWrap(image);
		
		left.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				skinIndex--;
				if(skinIndex < 0) skinIndex = displaySkins.length - 1;
				updateImage(sg);
				sg.playTone();
			}
		});
		
		right.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				skinIndex++;
				if(skinIndex >= displaySkins.length) skinIndex = 0;
				updateImage(sg);
				sg.playTone();
			}
		});
	}
	
	public void updateImage(SubGame sg) {
		final TextureAtlas atlas = sg.get(defAtlas);
		final DisplaySkin skin = displaySkins[skinIndex];
		skin.type = 0;
		final String region = displaySkins[skinIndex].getRegion();
		final TextureRegion tr = atlas.findRegion(region);
		image.setDrawable(new SpriteDrawable(new Sprite(tr)));
	}
	
	@Override
	public void add(Stage stage) {
		super.add(stage);
		stage.addActor(left);
		stage.addActor(right);
		stage.addActor(imageWrap);
	}
	
	@Override
	public void remove() {
		final Stage stage = getStage();
		stage.getActors().removeValue(left, false);
		stage.getActors().removeValue(right, false);
		stage.getActors().removeValue(imageWrap, false);
		super.remove();
	}
	
	@Override
	public void setScale(float s) {
		left.setScale(s);
		right.setScale(s);
		imageWrap.setScale(s);
		super.setScale(s);
	}
	
	private void updatePosition() {
		final float leftx = imageWrap.getX() - left.getWidth() * getScale() - space;
		final float rightx = imageWrap.getX() + imageWrap.getWidth()*getScale() + space;
		left.setX(leftx);
		right.setX(rightx);
	}
	
	public void updateSize() {
		imageWrap.setSize(worldWidth*0.25f/getScale(), worldWidth*0.25f/getScale());
		imageWrap.fitActor();
	}
	
	public void setSpace(float space) {
		this.space = space;
	}
	
	@Override
	public void setPosition(float x, float y) {
		final float px = (x+worldWidth/2)/worldWidth;
		final float py = (y+worldHeight/2)/worldHeight;
		setFromLeft(px, imageWrap);
		updatePosition();
		setFromBottom(py, imageWrap);
		setFromBottom(py, left);
		setFromBottom(py, right);
		super.setPosition(x, y);
	}
	
	public void setUp(SubGame sg, float scale, float space, float x, float y) {
		initialize(sg);
		setScale(scale);
		setSpace(space);
		updateSize();
		setPosition(x, y);
	}
}






