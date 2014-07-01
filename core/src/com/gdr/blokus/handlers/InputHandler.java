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
		int touchX = (int) vectorTouch.x;
		int touchY = (int) vectorTouch.y;

		Gdx.app.error("TouchDown", String.format("onChess:(%d,%d)", touchX,touchY));
		mPlayer.checkOnChess(touchX, touchY);
		return true; // return true to indicate the event was handled
	}

	public boolean touchUp (int x, int y, int pointer, int button) {
		Vector2 vectorTouch = translateAxis(x,y);
		int touchX = (int) vectorTouch.x;
		int touchY = (int) vectorTouch.y;

		Gdx.app.error("TouchUp", "releaseChess");
		
		if(mPlayer.isHold()){
			if(mPlayer.checkOnBoard(touchX,touchY)){
				mPlayer.putChess(touchX, touchY);
			}else {
				mPlayer.getHoldChess().setStatus(Chess.Status.PANEL);
			}
			mPlayer.releaseChess();
		}
		return true; // return true to indicate the event was handled
	}
	
	public boolean touchDragged (int x, int y, int pointer) {
		Vector2 vectorTouch = translateAxis(x,y);
		int touchX = (int) vectorTouch.x;
		int touchY = (int) vectorTouch.y;
		
		Gdx.app.error("TouchDragged", "HoldChess");
		if(mPlayer.isHold()){
			mPlayer.getHoldChess().setHoldAxis(touchX, touchY);
			if(mPlayer.checkOnBoard(touchX,touchY)){
				mPlayer.testChess(touchX,touchY);
			}
		}
		return true;
	}
	
	public boolean keyDown (int keycode) {
		Gdx.app.error("KeyDown", "Event");
		if(keycode == Keys.DOWN){
			int firstX = Gdx.input.getX();
			int firstY = Gdx.input.getY();
			Vector2 vectorTouch = translateAxis(firstX,firstY);
			int touchX = (int) vectorTouch.x;
			int touchY = (int) vectorTouch.y;
			
			Gdx.app.error("KeyDown", "Down");
			if(mPlayer.isHold()){
				mPlayer.getHoldChess().rotate();
				mPlayer.testChess(touchX,touchY);
			}
		}
	    return true;
	}
}
