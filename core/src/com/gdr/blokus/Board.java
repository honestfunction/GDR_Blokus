package com.gdr.blokus;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Board {
	
	final int BOARD_GRIDS_2P = 14; //grids
	final int BOARD_GRIDS_DEFAULT= 20;
	final int GRID_WIDTH_DEFAULT =30; //pixels
	final Color BACKGROUND_COLOR=Color.GRAY;
	final Color EDGE_COLOR=Color.BLACK; 
	final Color [] PLAYER_COLOR ={BACKGROUND_COLOR, Color.GREEN, Color.BLUE, Color.RED, Color.YELLOW};
    private Texture mTextureBoard;
    private Pixmap [] mPixmapGrid;
	int mPlayers;
	int mGridNumber;
	int mBoardWidth;
	boolean mUpdateNeeded=true;
	Axis mBoardLayout;
	Sprite mSprite;
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
		initLayout();
	}
	
	private void initLayout()
	{
		mBoardLayout = new Axis();
		mBoardLayout.x = (Gdx.graphics.getWidth()-getWidth())/2;
		mBoardLayout.y = (Gdx.graphics.getHeight()-getWidth())/2;
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
		initBoardPixmap();
		initFieldsPixmap();
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
	
	private void initBoardPixmap()
	{
		mBoardWidth = mGridNumber*(GRID_WIDTH_DEFAULT+1)+1;
		Pixmap pixmapBoard = new Pixmap(mBoardWidth, mBoardWidth, Pixmap.Format.RGB565);
		drawBoardBgColor(pixmapBoard);
		drawBoardEdges(pixmapBoard);
		mTextureBoard = new Texture(pixmapBoard);
		pixmapBoard.dispose();
	}
		
	private void drawBoardBgColor(Pixmap pBoard)
	{
		pBoard.setColor(BACKGROUND_COLOR);
		pBoard.fill();
	}
	
	private void drawBoardEdges(Pixmap pBoard)
	{
		pBoard.setColor(EDGE_COLOR);
		for(int i=0; i<mBoardWidth; i+= (GRID_WIDTH_DEFAULT+1)) {
			pBoard.drawLine(i, 0, i, mBoardWidth);
			pBoard.drawLine(0, i, mBoardWidth, i);
		}
	}
	
	private void initFieldsPixmap()
	{
		int numberFieldsPixmap= mPlayers+1;
		mPixmapGrid = new Pixmap[numberFieldsPixmap];
		for(int i=0; i< numberFieldsPixmap; i++){
			mPixmapGrid[i] = new Pixmap(GRID_WIDTH_DEFAULT, GRID_WIDTH_DEFAULT, Pixmap.Format.RGB565);
			mPixmapGrid[i].setColor(PLAYER_COLOR[i]);
			mPixmapGrid[i].fill();
		}
	}
	
	private void drawField(int i, int j)
	{
		Axis gridAxis;
		gridAxis = getGridAxis(i,j);
		mTextureBoard.draw(mPixmapGrid[mGrids[i][j].owner], gridAxis.x, gridAxis.y);
	}
	
	private void drawBoardFields()
	{
		if(!mUpdateNeeded)
			return;
		
		for(int i=0;i<mGridNumber;i++)
			for(int j=0;j<mGridNumber;j++) {
				if(mGrids[i][j].owner!=0) {
					drawField(i,j);
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
	
	private Axis getGridAxis(int gridX, int gridY)
	{
		Axis axis=new Axis();
		axis.x = 1 + gridX * (GRID_WIDTH_DEFAULT+1) ;
		axis.y = 1 + gridY * (GRID_WIDTH_DEFAULT+1) ;
		return axis;
	}
	
	public int getWidth()
	{
		return mBoardWidth;
	}
	
	
	private void drawBoard()
	{
		drawBoardFields();
		mSprite = new Sprite(mTextureBoard);
		mSprite.setPosition(mBoardLayout.x, mBoardLayout.y);
	}

	public void draw(SpriteBatch batch)
	{
		drawBoard();
		mSprite.draw(batch);
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
