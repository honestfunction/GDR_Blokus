package com.gdr.blokus;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Chess extends UIBox {
	String mID;
	public static enum Status {PANEL, HOLD, BOARD};
	Status mStatus = Status.PANEL;	// 0: on panel 1: hold 2: on board
	int mDirection=0;
	Array<Vector2> mDefaultGrids;
	Array<Vector2> mRotationGrids;
	Texture mChessUnit;
	int mHoldX;
	int mHoldY;
	
	Chess(UIBox parent, float x, float y, String id, Array<Vector2> DefaultGrids, Texture unit)
	{
		super(parent, x, y);
		mID = id;
		mChessUnit = unit;
		mDefaultGrids = DefaultGrids;
		mRotationGrids = mDefaultGrids;
	}
	
	private Vector2 getRealVectors(Vector2 grid)
	{
		Vector2 vTarget = new Vector2();
		vTarget.x = (grid.x * GlobalConfig.GRID_WIDTH_DEFAULT) + getAbsolutePos().x;
		vTarget.y = (grid.y * GlobalConfig.GRID_WIDTH_DEFAULT) + getAbsolutePos().y;
		return vTarget;
	}
	
	private Vector2 getHoldVectors(Vector2 grid)
	{
		Vector2 vTarget = new Vector2();
		vTarget.x = (grid.x * GlobalConfig.GRID_WIDTH_DEFAULT) + mHoldX;
		vTarget.y = (grid.y * GlobalConfig.GRID_WIDTH_DEFAULT) + mHoldY;
		
		//shift for drawing on Center
		vTarget.x -= GlobalConfig.GRID_WIDTH_DEFAULT/2;
		vTarget.y -= GlobalConfig.GRID_WIDTH_DEFAULT/2;
		
		return vTarget;
	}
	
	public void setState(Status val)
	{
		mStatus = val;
	}
	
	public void setHoldAxis(int x, int y)
	{
		mHoldX = x;
		mHoldY = y;
	}
	
	public boolean isOnChess(int x, int y)
	{
		Vector2 vectorKey = new Vector2(x,y);
		Rectangle curRect = new Rectangle();
		
		for(Vector2 grid: mDefaultGrids){
			Vector2 realVector= getRealVectors(grid);
			curRect.set(realVector.x, realVector.y,
					Utils.getChessUnitWidth(), Utils.getChessUnitWidth());
			if(curRect.contains(vectorKey))
				return true;
		}
		return false;
	}
		
	private Vector2 rotateAxis(Vector2 vector, int times)
	{
		float posX = vector.x;
		float posY = vector.y;
		float temp;
		for(int i=0; i<times; i++ ) {
			temp = posX;
			posX = posY;
			posY = -temp;
		}
		return new Vector2(posX, posY);
	}
	
	public void rotate()
	{
		mDirection= (mDirection+1) % 4;
		if (mDirection==0){
			mRotationGrids = mDefaultGrids;
		}
		
		mRotationGrids = new Array<Vector2>();		
		
		for(Vector2 grid: mDefaultGrids){
			mRotationGrids.add(rotateAxis(grid, mDirection));
		}
	}
	
	public void draw(SpriteBatch batch)
	{
		if(mStatus==Status.BOARD)
			return;
		
		if (mStatus==Status.HOLD){
			for(Vector2 grid: mRotationGrids) {
				Vector2 holdVector = getHoldVectors(grid);
				batch.draw(mChessUnit, holdVector.x,holdVector.y);
			}
			return;
		}
		
		for(Vector2 grid: mDefaultGrids) {
			Vector2 realVector= getRealVectors(grid);
			batch.draw(mChessUnit, realVector.x,realVector.y);
		}
	}
	
	public Array<Vector2> getCurGrids()
	{
		return mRotationGrids;
	}
	
	public void dispose()
	{
		mChessUnit.dispose();
	}
	
}
