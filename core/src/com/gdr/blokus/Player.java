package com.gdr.blokus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class Player {
	boolean mHold;
	boolean mOnBoard;
	boolean mOnChess;
	Board mBoard;
	Panel mPanel;
	SpriteBatch mBatch;
	String mChessHold = null;
	OrthographicCamera mCamera;

	InputAdapter mInputHandler;
	
	Player(Board board, Panel panel,OrthographicCamera camera)
	{
		mHold=false;
		mOnBoard =false;
		mBoard = board;
		mPanel = panel;
		mCamera = camera;
		initInputHandler();
	}

	public InputAdapter getInputHandler()
	{
		return mInputHandler;
	}
	
	private boolean checkOnBoard(int x, int y)
	{
		Rectangle recBoard = new Rectangle(Layout.BOARD_LAYOUT.x, Layout.BOARD_LAYOUT.y, 
				mBoard.getWidth(), mBoard.getWidth());
		return recBoard.contains(x, y);
	}
	
	private void holdChess(String type)
	{
		mChessHold = type;
		mHold = true;
	}
	
	private void releaseChess()
	{
		mPanel.getChess(mChessHold).setState(Chess.Status.PANEL);
		mBoard.clearGridsBorder();
		mChessHold = null;
		mHold = false;
	}
	
	private boolean checkOnChess(int x, int y)
	{
		for(String type: GlobalConfig.CHESS_TYPE){
			Chess curChess = mPanel.getChess(type);
			if(curChess.isOnChess(x, y)){
				Gdx.app.error("checkOnChess", "succcess");
				curChess.setState(Chess.Status.HOLD);
				curChess.setHoldAxis(x, y);
				holdChess(type);
				return true;
			}
		}
		Gdx.app.error("checkOnChess", "fail");
		return false;
	}
	
	private void testChess(int x, int y)
	{
		Executor.testBoard(mBoard, mPanel.getChess(mChessHold),x ,y,mPanel.getPlayerID());
	}
	
	private void putChess(int x, int y)
	{
		Executor.putBoard(mBoard, mPanel.getChess(mChessHold),x ,y,mPanel.getPlayerID());
	}
	
	private Vector2 translateAxis(int x,int y)
	{
		Vector3 touchPos = new Vector3(x,y,0);
		mCamera.unproject(touchPos);
		return new Vector2(touchPos.x, touchPos.y);
	}
	
	private void initInputHandler()
	{
		mInputHandler = new InputAdapter(){
			public boolean touchDown (int x, int y, int pointer, int button) {
				Vector2 vectorTouch = translateAxis(x,y);
				int touchX = (int) vectorTouch.x;
				int touchY = (int) vectorTouch.y;

				Gdx.app.error("TouchDown", "onChess");
				checkOnChess(touchX, touchY);
				return true; // return true to indicate the event was handled
			}

			public boolean touchUp (int x, int y, int pointer, int button) {
				Vector2 vectorTouch = translateAxis(x,y);
				int touchX = (int) vectorTouch.x;
				int touchY = (int) vectorTouch.y;

				Gdx.app.error("TouchUp", "releaseChess");
				
				if(mHold){
					if(checkOnBoard(touchX,touchY)){
						putChess(touchX, touchY);
					}else {
						releaseChess();
					}
				}
				return true; // return true to indicate the event was handled
			}
			
			public boolean touchDragged (int x, int y, int pointer) {
				Vector2 vectorTouch = translateAxis(x,y);
				int touchX = (int) vectorTouch.x;
				int touchY = (int) vectorTouch.y;
				
				Gdx.app.error("TouchDragged", "HoldChess");
				if(mHold){
					mPanel.getChess(mChessHold).setHoldAxis(touchX, touchY);
					if(checkOnBoard(touchX,touchY)){
						testChess(touchX,touchY);
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
					if(mHold){
						mPanel.getChess(mChessHold).rotate();
						testChess(touchX,touchY);
					}
				}
			    return true;
			}
		};
	}
	
}
