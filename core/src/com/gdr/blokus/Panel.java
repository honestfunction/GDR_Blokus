package com.gdr.blokus;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Panel extends UIBox{
	int mPlayerID;
	SpriteBatch mBatch;
	Texture mTexturePanel;
	Board mBoard;
	Texture mTextureChessUnit;
	Map<String, Array<Vector2>> mChessGridDict;
	Map<String, Vector2> mChessLayoutDict;
	Map<String, Chess> mChessDictionary;

	
	Panel(UIBox parent, float x, float y, Board board, int playerID)
	{
		super(parent, x, y);
		mBoard = board;
		mPlayerID = playerID;
		initPanel();
		initChess();
	}
	
	private void initChess()
	{
		initChessDictionary();
		createChessUnitImage();
		createAllChess();
	}
	
	private void initChessDictionary()
	{		
		mChessGridDict = new HashMap<String, Array<Vector2>>();
		mChessLayoutDict = new HashMap<String, Vector2>();
		for(String type: GlobalConfig.CHESS_TYPE) {
			mChessGridDict.put(type, getChessVectors(type));
			mChessLayoutDict.put(type, getChessLayout(type));
		}
	}
		
	private void createChessUnitImage()
	{
		int chessUnitWidth =Utils.getChessUnitWidth();
		Pixmap pixmapChessUnit = new Pixmap(chessUnitWidth, chessUnitWidth, 
				Pixmap.Format.RGBA8888);
		pixmapChessUnit.setColor(Utils.getPlayerColor(mPlayerID));
		pixmapChessUnit.fill();
		pixmapChessUnit.setColor(GlobalConfig.EDGE_COLOR);
		pixmapChessUnit.drawRectangle(0, 0, chessUnitWidth, chessUnitWidth);
		mTextureChessUnit= new Texture(pixmapChessUnit);
		pixmapChessUnit.dispose();
	}
	
	private void createAllChess()
	{
		mChessDictionary = new HashMap<String, Chess>();
		for(String type: GlobalConfig.CHESS_TYPE) {
			appendChess(type);
		}
	}
	
	private void appendChess(String type)
	{
		Vector2 layout=mChessLayoutDict.get(type);
		mChessDictionary.put(type, new Chess(this, 
				layout.x, layout.y,  type, mChessGridDict.get(type) , mTextureChessUnit));
	}
	
	private void initPanel()
	{
		mTexturePanel = new Texture(Gdx.files.internal(Assest.CHESS_PANEL));
	}

	public Chess getChess(String type)
	{
		return mChessDictionary.get(type);
	}
	
	public int getPlayerID()
	{
		return mPlayerID;
	}
	
	public void draw(SpriteBatch batch)
	{
		batch.draw(mTexturePanel, getAbsolutePos().x, getAbsolutePos().y);
	}
	
	public void dispose()
	{
		mTexturePanel.dispose();
		mTextureChessUnit.dispose();
	}
	
	private Array<Vector2> getChessVectors(String type)
	{
		Array<Vector2> target= new Array<Vector2>();
		Vector2 gPoint=new Vector2(0,0);
		target.add(gPoint);
		
		if(type.equals("1")){
			/*
			 *  O
			 */
		}
		else if(type.equals("2")){
			/*
			 *  OX
			 */
			target.add(new Vector2(1,0));
		}
		else if(type.equals("3")){
			/*
			 *  XOX
			 */
			target.add(new Vector2(1,0));
			target.add(new Vector2(-1,0));
		}
		else if(type.equals("shortZ")){
			/*
			 *   XX
			 *  XO
			 */
			target.add(new Vector2(-1,0));
			target.add(new Vector2(0,1));
			target.add(new Vector2(1,1));
		}
		return target;
	}
	
	private Vector2 getChessLayout(String type)
	{
		if(type.equals("1")){
			/*
			 *  O
			 */
			return new Vector2(10,10);
		}
		else if(type.equals("2")){
			/*
			 *  OX
			 */
			return new Vector2(50,10);
		}
		else if(type.equals("3")){
			/*
			 *  XOX
			 */
			return new Vector2(50,50);
		}
		else if(type.equals("shortZ")){
			/*
			 *   XX
			 *  XO
			 */
			return new Vector2(50,100);
		}
		return new Vector2(0,0);
	}
}
