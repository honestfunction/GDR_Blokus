package com.gdr.blokus;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;

public class Board {
	
	final int BOARD_GRIDS_2P = 14; //grids
	final int BOARD_GRIDS_DEFAULT= 20;
	final int GRID_WIDTH_DEFAULT =30; //pixels
	final Color BACKGROUND_COLOR=Color.GRAY;
	final Color EDGE_COLOR=Color.BLACK; 
	final Color [] PlayerColor ={BACKGROUND_COLOR, Color.GREEN, Color.BLUE, Color.RED, Color.YELLOW};
    private Pixmap mPixmapBoard;
    private Texture mTextureBoard;

	int mPlayers;
	int mGridNumber;
	int mBoardWidth;
	boolean mUpdateNeeded=true;
	
	Grid[][] mGrids;
	
	Board()
	{
		mPlayers = GlobalConfig.DEFAULT_PLAYERS;
	}
	
	Board(int players)
	{
		mPlayers = players;
	}
	
	public void initial()
	{
		setupBoard();
		//drawBoard();
	}
	
	private void setupBoard()
	{
		if(mPlayers>2) {
			mGridNumber=BOARD_GRIDS_DEFAULT;
			
		} else {
			mGridNumber=BOARD_GRIDS_2P;
		}
		
		initBoardGrids();
		setBoardEmpty();
		initPixmap();
	}
	
	private void initBoardGrids()
	{
		mGrids = new Grid [mGridNumber][];
		for (int i=0; i < mGridNumber ; i++)
			mGrids[i] = new Grid[mGridNumber]; // create arrays of references to objects
		
		for (int i=0; i < mGridNumber ; i++)
			   for (int j=0; j < mGridNumber ; j++)
				   mGrids[i][j] = new Grid();
	}
	
	private void initPixmap()
	{
		mBoardWidth = mGridNumber*(GRID_WIDTH_DEFAULT+1)+1;
		mPixmapBoard = new Pixmap(mBoardWidth, mBoardWidth, Pixmap.Format.RGB565);
		drawBoardBgColor();
		drawBoardEdges();		
	}
	
	private void drawBoard()
	{
		drawBoardFields();
		mTextureBoard = new Texture(mPixmapBoard);
	}
	
	private void drawBoardBgColor()
	{
		mPixmapBoard.setColor(BACKGROUND_COLOR);
		mPixmapBoard.fill();
	}
	
	private void drawBoardEdges()
	{
		mPixmapBoard.setColor(EDGE_COLOR);
		for(int i=0; i<mBoardWidth; i+= (GRID_WIDTH_DEFAULT+1)) {
			mPixmapBoard.drawLine(i, 0, i, mBoardWidth);
			mPixmapBoard.drawLine(0, i, mBoardWidth, i);
		}
	}
	
	private void drawBoardFields()
	{
		Axis startAxis;
		for(int i=0;i<mGridNumber;i++)
			for(int j=0;j<mGridNumber;j++) {
				if(mGrids[i][j].owner!=0) {
					mPixmapBoard.setColor(PlayerColor[mGrids[i][j].owner]);
					startAxis = getStartAxis(i,j);
					mPixmapBoard.fillRectangle(startAxis.x, startAxis.y, 
							GRID_WIDTH_DEFAULT, GRID_WIDTH_DEFAULT);
				}
			}
	}
	
	public void setBoardEmpty()
	{
		for(int i=0;i<mGridNumber;i++)
			for(int j=0;j<mGridNumber;j++) {
				mGrids[i][j].owner=0;
			}
	}
	
	public void setColor(int playerID, int x, int y)
	{
		mGrids[x][y].owner = playerID;
		mUpdateNeeded = true;
	}
	
	public Texture updateBoard()
	{
		drawBoard();
		mUpdateNeeded=false;
		return mTextureBoard;
	}
	
	public boolean needToUpdate()
	{
		return mUpdateNeeded;
	}
	
	private Axis getStartAxis(int gridX, int gridY)
	{
		Axis axis=new Axis();
		axis.x = 1 + gridX * (GRID_WIDTH_DEFAULT+1) ;
		axis.y = 1 + gridY * (GRID_WIDTH_DEFAULT+1) ;
		return axis;
	}
	
	private class Grid 
	{
		int owner;
	}
	
	private class Axis
	{
		int x;
		int y;
	}
}
