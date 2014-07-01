package com.gdr.blokus.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gdr.blokus.Chess;
import com.gdr.blokus.Player;

public class InputHandler extends InputAdapter{
	Player mPlayer;
	
	public InputHandler(Player player)
	{
		super();
		mPlayer = player;
	}
	
	private Vector2 translateAxis(int x,int y)
	{
		Vector3 touchPos = new Vector3(x,y,0);
		mPlayer.getCamera().unproject(touchPos);
		return new Vector2(touchPos.x, touchPos.y);
	}
	
	public boolean touchDown (int x, int y, int pointer, int button) {
		Vector2 vectorTouch = translateAxis(x,y);
		mPlayer.mouseDown((int) vectorTouch.x, (int) vectorTouch.y);
		
		return true; // return true to indicate the event was handled
	}

	public boolean touchUp (int x, int y, int pointer, int button) {
		Vector2 vectorTouch = translateAxis(x,y);

		Gdx.app.error("TouchUp", "releaseChess");
		mPlayer.mouseUp((int) vectorTouch.x, (int) vectorTouch.y);
		return true; // return true to indicate the event was handled
	}
	
	public boolean touchDragged (int x, int y, int pointer) {
		Vector2 vectorTouch = translateAxis(x,y);
		mPlayer.mouseDragged((int) vectorTouch.x, (int) vectorTouch.y);
		return true;
	}
	
	public boolean keyDown (int keycode) {
		Gdx.app.error("KeyDown", "Event");
		if(keycode == Keys.DOWN){
			int curX = Gdx.input.getX();
			int curY = Gdx.input.getY();
			Vector2 vectorTouch = translateAxis(curX,curY);
			mPlayer.rotatePressed((int) vectorTouch.x, (int) vectorTouch.y);
		}
	    return true;
	}
}
