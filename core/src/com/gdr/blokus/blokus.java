package com.gdr.blokus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class blokus extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Board board;
	OrthographicCamera camera;
	Player player;
	Panel panel;
	BitmapFont font;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
	    camera = new OrthographicCamera();
	    camera.setToOrtho(false, 800, 480);

		board = new Board();
		board.initial(batch);
		panel = new Panel(null, Layout.PANEL_LAYOUT.x, Layout.PANEL_LAYOUT.y, board, 1);
		player = new Player(board, panel,camera);
		Gdx.input.setInputProcessor(player.getInputHandler());
		font = new BitmapFont(Gdx.files.internal("font/version.fnt"));
		font.setColor(Color.BLACK);
	}
		
	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		board.draw();
		panel.drawBox(batch);
		font.draw(batch, String.format("����: %s", GlobalConfig.VERSION), 0, 20);
		batch.end();
	}
	
}
