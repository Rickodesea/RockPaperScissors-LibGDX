package com.algodal.phase01.rps;

import static com.algodal.phase01.rps.Constants.defAtlas;
import static com.algodal.phase01.rps.Constants.defSkin;
import static com.algodal.phase01.rps.Constants.skiScene;
import static com.algodal.phase01.rps.Constants.worldHeight;
import static com.algodal.phase01.rps.Constants.worldWidth;

import com.algodal.phase01.rps.dialogs.IDialog;
import com.algodal.phase01.rps.dialogs.QuickSettingDialog;
import com.algodal.phase01.rps.entities.LockBG;
import com.algodal.phase01.rps.entities.LockHand;
import com.algodal.phase01.rps.scenes.Menu;
import com.algodal.phase01.rps.scenes.Play;
import com.algodal.phase01.rps.scenes.SkinSetup;
import com.algodal.phase01.rps.ui.Gallery;
import com.algodal.phase01.rps.utils.BackgroundSkin;
import com.algodal.phase01.rps.utils.HandSkin;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SubGame {

	private final SpriteBatch sb;
	private final Viewport vp;
	private final AssetManager am;
	public final Stage st;
	
	private final Color cc;
	private final Vector2 v;
	
	private Scene scene;
	private final Array<Scene> scenes;
	
	private final ArrayMap<String, AssetDescriptor<?>> adMap;
	private final ArrayMap<String, IDialog> dialogMap;
	
	private final InputMultiplexer im;
	
	public final Data data = new Data();
	
	//Facilitates communication between Entities
	public final LockHand lockHand;
	public final Gallery handGallery;
	public final LockBG lockBG;
	public final Gallery bgGallery;
	
	public SubGame() {
		sb = new SpriteBatch();
		vp = new FitViewport(worldWidth, worldHeight);
		am = new AssetManager();
		st = new Stage(new FitViewport(worldWidth, worldHeight));
		st.getViewport().getCamera().position.setZero();
		st.setDebugAll(true);
		
		cc = new Color(Color.BROWN);
		v = new Vector2();
		
		adMap = new ArrayMap<String, AssetDescriptor<?>>();
		adMap.put(defAtlas, new AssetDescriptor<TextureAtlas>(defAtlas, TextureAtlas.class));
		adMap.put(defSkin, new AssetDescriptor<Skin>(defSkin, Skin.class));
		
		handGallery = new Gallery(data.play.setting.handskins);
		bgGallery = new Gallery(data.play.setting.bgskins);
		lockHand = new LockHand();
		lockBG = new LockBG();
		
		scenes = new Array<Scene>();
		scenes.add(new DefaultScene());
		scenes.add(new SkinSetup());
		scenes.add(new Play());
		scenes.add(new Menu());
		scene = scenes.get(scenes.size-1); //make non-null as soon as possible
		
		dialogMap = new ArrayMap<String, IDialog>();
		dialogMap.put("quicksetting", new QuickSettingDialog());
		
		im = new InputMultiplexer(st, newIP(), newGD());
		Gdx.input.setInputProcessor(im);
		
		//Call LateInitializations
		for(Scene scene : scenes) scene.lateInitialize(this);
		for(Entry<String, IDialog> dialogEntry : dialogMap) dialogEntry.value.getLateInitialization().initialize(this);
		
		//Set scene
		setScene(skiScene);
	}
	
	protected void render() {
		 Gdx.gl.glClearColor( cc.r, cc.g, cc.b, cc.a );
		 Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
		 
		 scene.render(this, 1f/60);
		 
		 am.update();
	}
	
	protected void resize(int width, int height) {
		vp.update(width, height);
		st.getViewport().update(width, height);
	}
	
	protected void destroy() {
		sb.dispose();
		am.dispose();
		st.dispose();
	}
	
	public void applySpriteViewport() {
		vp.apply();
	}
	
	public void applyStageViewport() {
		st.getViewport().apply();
	}
	
	public void begin(ShaderProgram sp, Color c) {
		sb.setShader(sp);
		sb.setColor((c==null)?Color.WHITE:c);
		sb.setProjectionMatrix(vp.getCamera().combined);
		sb.begin();
	}
	
	public void end() {
		sb.end();
		sb.setShader(null);
		sb.setColor(Color.WHITE);
	}
	
	public void draw(TextureRegion tr, Unit o) {
		sb.draw(tr, o.left(), o.bottom(), o.hw(), o.hh(), o.width, o.height, 1.0f, 1.0f, o.angle);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String name) {
		load(name);
		return am.get((AssetDescriptor<T>)adMap.get(name));
	}
	
	public void load(String name) {
		final AssetDescriptor<?> ad = adMap.get(name);
		if(!am.isLoaded(ad)) am.load(ad);
		am.finishLoadingAsset(ad);
	}
	
	public void queue(String name) {
		final AssetDescriptor<?> ad = adMap.get(name);
		if(!am.isLoaded(ad)) am.load(ad);
	}
	
	public boolean done() {
		return am.isFinished();
	}
	
	public boolean done(String...names) {
		for(String name : names) {
			final AssetDescriptor<?> ad = adMap.get(name);
			if(!am.isLoaded(ad)) return false;
		}
		return true;
	}
	
	public void setScene(String name) {
		scene = getScene(name);
		scene.show(this);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Scene> T getScene(String name) {
		for(Scene scene : scenes) if(scene.name().equalsIgnoreCase(name)) return (T) scene;
		Gdx.app.log("get scene", "failed to find scene called " + name);
		return null;
	}
	
	@Deprecated
	@SuppressWarnings("unchecked")
	public <T extends Scene> T  getScene() {
		return (T) scene;
	}
	
	public IDialog getDialog(String name) {
		return dialogMap.get(name);
	}
	
	protected Entity getEntity(String name, Scene scene) {
		for(Entity entity : scene.entities) if(entity.name().equalsIgnoreCase(name)) return entity;
		Gdx.app.log("get entity", "failed to find entity called " + name);
		return new Entity.DefaultEntity();
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Entity> T getEntity(String name, String scene) {
		final Scene s = getScene(scene);
		return (T) getEntity(name, s);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Entity> T getEntity(String name) {
		return (T) getEntity(name, scene);
	}
	
	private InputProcessor newIP() {
		return new InputProcessor() {
			
			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				v.set(screenX, screenY);
				vp.unproject(v);
				scene.up(v.x, v.y);
				return false;
			}
			
			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				v.set(screenX, screenY);
				vp.unproject(v);
				scene.drag(v.x, v.y);
				return false;
			}
			
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				v.set(screenX, screenY);
				vp.unproject(v);
				scene.down(v.x, v.y);
				return false;
			}
			
			@Override
			public boolean scrolled(int amount) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean keyUp(int keycode) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean keyTyped(char character) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean keyDown(int keycode) {
				// TODO Auto-generated method stub
				return false;
			}
		};
	}
	
	private GestureDetector newGD() {
		return new GestureDetector(new GestureListener() {
			
			@Override
			public boolean zoom(float initialDistance, float distance) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean touchDown(float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean tap(float x, float y, int count, int button) {
				v.set(x, y);
				vp.unproject(v);
				scene.tap(v.x, v.y, count);
				return false;
			}
			
			@Override
			public void pinchStop() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean panStop(float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean pan(float x, float y, float deltaX, float deltaY) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean longPress(float x, float y) {
				v.set(x, y);
				vp.unproject(v);
				scene.press(v.x, v.y);
				return false;
			}
			
			@Override
			public boolean fling(float velocityX, float velocityY, int button) {
				v.set(velocityX, velocityY);
				vp.unproject(v);
				scene.fling(v.x, v.y);
				return false;
			}
		});
	}
	
	public static class DefaultScene extends Scene {

		@Override
		public void render(SubGame sg, float delta) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public static class Data {
		
		public final Play play = new Play();
		public final Menu menu = new Menu();
		
		public static class Play {
			public final Player player01 = new Player();
			public final Player player02 = new Player();
			public final Setting setting = new Setting();
			
			public static class Setting {
				public int maxRounds = 3;
				public int completedRounds = 0;
				public HandSkin[] handskins = new HandSkin[] {
						new HandSkin("rock_0", "paper_0", "scissors_0"),
						new HandSkin("rock_1", "paper_1", "scissors_1"),
						new HandSkin("rock_2", "paper_2", "scissors_2"),
						new HandSkin("rock_3", "paper_3", "scissors_3"),
				};
				public BackgroundSkin[] bgskins = new BackgroundSkin[] {
						new BackgroundSkin("bg_0"),
						new BackgroundSkin("bg_1"),
						new BackgroundSkin("bg_2"),
						new BackgroundSkin("bg_3"),
						new BackgroundSkin("bg_4"),
						new BackgroundSkin("bg_5"),
						new BackgroundSkin("bg_6"),
						new BackgroundSkin("bg_7"),
				};
				public int handSkinIndex = 0, bgSkinIndex = 0;
				
				public HandSkin handSkin() {
					return handskins[handSkinIndex];
				}
				
				public BackgroundSkin bgSkin() {
					return bgskins[bgSkinIndex];
				}
			}
			
			public static class Player {
				public int scoreAmount = 0;
			}
		}
		
		public static class Menu {
			public boolean fullVersionPurchased = false;
			public float masterVolume = 1.0f;
			public float soundVolume = 1.0f;
			public float musicVolume = 1.0f;
			public boolean newGame = false;
			public int mode = 0;
		}
	}
}










