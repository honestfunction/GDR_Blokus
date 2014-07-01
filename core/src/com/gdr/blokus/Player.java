package com.gdr.blokus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

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
	
	Player(Board board, Panel panel, OrthographicCamera camera)
	{
		mHold=false;
		mOnBoard =false;
		mBoard = board;
		mPanel = panel;
		mCamera = camera;
	}

	public InputAdapter getInputHandler()
	{
		return mInputHandler;
	}
	
	public boolean checkOnBoard(int x, int y)
	{
		Rectangle recBoard = new Rectangle(Layout.BOARD_LAYOUT.x, Layout.BOARD_LAYOUT.y, 
				mBoard.getWidth(), mBoard.getWidth());
		return recBoard.contains(x, y);
	}
	
	public void holdChess(String type)
	{
		mChessHold = type;
		mHold = true;
	}
	
	public void releaseChess()
	{
		
		mBoard.clearGridsBorder();
		mChessHold = null;
		mHold = false;
	}
	
	public boolean checkOnChess(int x, int y)
	{
		for(String type: GlobalConfig.CHESS_TYPE){
			Chess curChess = mPanel.getChess(type);
			if(curChess.isOnChess(x, y) && curChess.getStatus()== Chess.Status.PANEL){
				Gdx.app.error("checkOnChess", "succcess");
				curChess.setStatus(Chess.Status.HOLD);
				curChess.setHoldAxis(x, y);
				holdChess(type);
				return true;
			}
		}
		Gdx.app.error("checkOnChess", "fail");
		return false;
	}
	
	public OrthographicCamera getCamera()
	{
		return mCamera;
	}
	
	public boolean isHold()
	{
		return mHold;
	}
	
	public Chess getHoldChess()
	{
		return mPanel.getChess(mChessHold);
	}
	
	public void testChess(int x, int y)
	{
		Executor.testBoard(mBoard, mPanel.getChess(mChessHold),x ,y,mPanel.getPlayerID());
	}
	
	public void putChess(int x, int y)
	{
		Executor.putBoard(mBoard, mPanel.getChess(mChessHold),x ,y,mPanel.getPlayerID());
	}
}
