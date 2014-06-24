package com.gdr.blokus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class blokus extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Board board;
	OrthographicCamera camera;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		board = new Board();
		board.initial(batch);
	}
	
	public void testBoard()
	{
		board.setColor(2, 5, 5);
		board.setColor(1, 2, 3);
		board.setGridBorder(false, 2, 3);
	}
	
	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		board.draw();
		batch.end();
	}
	
}
