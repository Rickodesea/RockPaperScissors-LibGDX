package com.algodal.phase01.rps.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Shaders {

	public final String vertPassthrough, vertShock, vertNeon;
	public final String fragPassthrough, fragShock, fragNeon;
	
	public final ShaderProgram shaderPassthrough, shaderShock, shaderNeon;
	
	public Shaders() {
		vertPassthrough = Gdx.files.internal("shader/passthrough.vert.glsl").readString();
		vertShock = Gdx.files.internal("shader/shock.vert.glsl").readString();
		vertNeon = Gdx.files.internal("shader/neon.vert.glsl").readString();
		
		fragPassthrough = Gdx.files.internal("shader/passthrough.frag.glsl").readString();
		fragShock = Gdx.files.internal("shader/shock.frag.glsl").readString();
		fragNeon = Gdx.files.internal("shader/neon.frag.glsl").readString();
		
		shaderPassthrough = new ShaderProgram(vertPassthrough, fragPassthrough);
		shaderNeon = new ShaderProgram(vertPassthrough, fragNeon);
		shaderShock = new ShaderProgram(vertShock, fragShock);
		
		Gdx.app.log("passthrough compiled", Boolean.toString(shaderPassthrough.isCompiled()));
		Gdx.app.log("shock compiled", Boolean.toString(shaderShock.isCompiled()));
		Gdx.app.log("neon compiled", Boolean.toString(shaderNeon.isCompiled()));
		
		if(!shaderPassthrough.isCompiled()) Gdx.app.log("invert error", shaderPassthrough.getLog());
		if(!shaderShock.isCompiled()) Gdx.app.log("shock error", shaderShock.getLog());
		if(!shaderNeon.isCompiled()) Gdx.app.log("neon error", shaderNeon.getLog());
	}
}







