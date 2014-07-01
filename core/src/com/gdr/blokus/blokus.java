package com.gdr.blokus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdr.blokus.handlers.InputHandler;

public class blokus extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Board board;
	OrthographicCamera camera;
	Player player;
	Panel panel;
	BitmapFont font;
	Viewport viewport;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
	    camera = new OrthographicCamera();
	    camera.setToOrtho(false, GlobalConfig.VISUAL_WIDTH, GlobalConfig.VISUAL_HEIGHT);
	    camera.update();
	    viewport = new FitViewport(GlobalConfig.VISUAL_WIDTH, GlobalConfig.VISUAL_HEIGHT,camera);
	    
		board = new Board(null, Layout.BOARD_LAYOUT.x, Layout.BOARD_LAYOUT.y, GlobalConfig.DEFAULT_PLAYERS);
		board.initial();
		panel = new Panel(null, Layout.PANEL_LAYOUT.x, Layout.PANEL_LAYOUT.y, board, 1);
		player = new Player(board, panel,camera);
		Gdx.input.setInputProcessor(new InputHandler(player));
		font = new BitmapFont(Gdx.files.internal("font/version.fnt"));
		font.setColor(Color.BLACK);
	}
		
	@Override
	public void render () {
		batch.setProjectionMatrix(camera.projection);
		batch.setTransformMatrix(camera.view);

		Gdx.gl.glClearColor(1, 1, 1, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		board.draw(batch);
		panel.drawBox(batch);
		font.draw(batch, String.format("ª©¥»: %s", GlobalConfig.VERSION), 0, 20);
		batch.end();
	}
	

	public void resize(int width, int height) {
	    viewport.update(width, height);
	}

}
