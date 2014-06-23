package com.gdr.blokus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class blokus extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Board mBoard;
	Sprite mSprite;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		mBoard = new Board();
		mBoard.initial();
		mBoard.setColor(2, 5, 5);
		mBoard.setColor(1, 2, 3);
		mBoard.setGridBorder(false, 10, 10);
	}
	
	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		mBoard.draw(batch);
		batch.end();
	}
}
