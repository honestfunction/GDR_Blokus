package com.gdr.blokus;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Board {
	
    private Texture mTextureBoard;
    private Texture [] mTextureGrid;
    private Texture [] mTextureBorder;
	int mPlayers;
	int mGridNumber;
	int mBoardWidth;
	boolean mUpdateNeeded=true;
	SpriteBatch mBatch;
	
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
	
	public void initial(SpriteBatch batch)
	{
		mBatch=batch;
		setupBoard();
		initLayout();
	}
	
	private void initLayout()
	{
		Layout.BOARD_LAYOUT = new Vector2();
		//Layout.BOARD_LAYOUT.x = (Gdx.graphics.getWidth()-getWidth())/2;
		Layout.BOARD_LAYOUT.x = 20;
		Layout.BOARD_LAYOUT.y = (Gdx.graphics.getHeight()-getWidth())/2;
	}
	
	private void setupBoard()
	{
		if(mPlayers>2) {
			mGridNumber= GlobalConfig.BOARD_GRIDS_DEFAULT;
			
		} else {
			mGridNumber=GlobalConfig.BOARD_GRIDS_2P;
		}
		
		initBoardGrids();
		setBoardEmpty();
		initBoardImage();
		initFieldsImage();
		initBorderImage();
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
	
	private void initBoardImage()
	{
		mBoardWidth = mGridNumber*(GlobalConfig.GRID_WIDTH_DEFAULT+1)+1;
		Pixmap pBoard = new Pixmap(mBoardWidth, mBoardWidth, Pixmap.Format.RGBA8888);
		drawBoardBgColor(pBoard);
		drawBoardEdges(pBoard);
		mTextureBoard = new Texture(pBoard);
	}
		
	private void drawBoardBgColor(Pixmap pBoard)
	{
		pBoard.setColor(GlobalConfig.BACKGROUND_COLOR);
		pBoard.fill();
	}
	
	private void drawBoardEdges(Pixmap pBoard)
	{
		pBoard.setColor(GlobalConfig.EDGE_COLOR);
		for(int i=0; i<mBoardWidth; i+= (GlobalConfig.GRID_WIDTH_DEFAULT+1)) {
			pBoard.drawLine(i, 0, i, mBoardWidth);
			pBoard.drawLine(0, i, mBoardWidth, i);
		}
	}
	
	private void initFieldsImage()
	{
		int numberFieldsType= mPlayers+1;
		mTextureGrid = new Texture[numberFieldsType];
		Pixmap pixmapGrid;
		for(int i=0; i< numberFieldsType; i++){
			pixmapGrid = new Pixmap(GlobalConfig.GRID_WIDTH_DEFAULT, GlobalConfig.GRID_WIDTH_DEFAULT, Pixmap.Format.RGBA8888);
			pixmapGrid.setColor(GlobalConfig.PLAYER_COLORS[i]);
			pixmapGrid.fill();
			mTextureGrid[i] = new Texture(pixmapGrid);
		}
	}
	
	private void initBorderImage()
	{
		Pixmap pixmapBorder;
		mTextureBorder = new Texture[GlobalConfig.BORDER_COLORS.length];
		for(int i=0; i < GlobalConfig.BORDER_COLORS.length; i++)
		{
			pixmapBorder = new Pixmap(GlobalConfig.GRID_WIDTH_DEFAULT+2, GlobalConfig.GRID_WIDTH_DEFAULT+2, Pixmap.Format.RGBA8888);
			pixmapBorder.setColor(GlobalConfig.BORDER_COLORS[i]);
			pixmapBorder.drawRectangle(0, 0, pixmapBorder.getWidth(), pixmapBorder.getHeight());
			mTextureBorder[i] = new Texture(pixmapBorder);
		}		
	}
	
	private void drawField(int i, int j)
	{
		Vector2 gridAxis;
		gridAxis = getGridAxis(i,j);
		mBatch.draw(mTextureGrid[mGrids[i][j].owner],gridAxis.x, gridAxis.y);
	}
	
	private void drawBorder(int i,int j, boolean clear)
	{
		Vector2 borderAxis;
		borderAxis = getGridBorderAxis(i,j);
		if(clear)
			mBatch.draw(mTextureBorder[0], borderAxis.x, borderAxis.y);
		else
			mBatch.draw(mTextureBorder[mGrids[i][j].border], borderAxis.x, borderAxis.y);
	}
	
	private void drawBoardAll()
	{	
		if(!mUpdateNeeded)
			return;
		
		for(int i=0;i<mGridNumber;i++)
			for(int j=0;j<mGridNumber;j++) {
				if(mGrids[i][j].owner!=0) {
					drawField(i,j);
				}
				if(mGrids[i][j].border!=0) {
					drawBorder(i,j,false);
				}
			}
	}
	
	public void setBoardEmpty()
	{
		for(int i=0;i<mGridNumber;i++)
			for(int j=0;j<mGridNumber;j++) {
				mGrids[i][j].owner=0;
				mGrids[i][j].border=0;
			}
	}
	
	public void setColor(int playerID, int x, int y)
	{
		mGrids[x][y].owner = playerID;
	}
	
	public void setGridBorder(boolean enable, int x, int y)
	{
		if(enable)
			mGrids[x][y].border=1;
		else
			mGrids[x][y].border=2;
	}
	
	public void clearGridsBorder()
	{
		for(int i=0;i<mGridNumber;i++)
			for(int j=0;j<mGridNumber;j++) {
				if(mGrids[i][j].border!=0) {
					mGrids[i][j].border=0;
				}
			}
	}
	
	private Vector2 getGridAxis(int gridX, int gridY)
	{
		Vector2 axis=new Vector2();
		axis.x = 1 + gridX * (GlobalConfig.GRID_WIDTH_DEFAULT+1) + Layout.BOARD_LAYOUT.x ;
		axis.y = 1 + gridY * (GlobalConfig.GRID_WIDTH_DEFAULT+1) + Layout.BOARD_LAYOUT.y ;
		return axis;
	}
	
	private Vector2 getGridBorderAxis(int gridX, int gridY)
	{
		Vector2 axis=new Vector2();
		axis.x = gridX * (GlobalConfig.GRID_WIDTH_DEFAULT+1) + Layout.BOARD_LAYOUT.x ;
		axis.y = gridY * (GlobalConfig.GRID_WIDTH_DEFAULT+1) + Layout.BOARD_LAYOUT.y;
		return axis;
	}

	public int getGridOwner(int gridX, int gridY)
	{
		return mGrids[gridX][gridY].owner;
	}
	
	public int getWidth()
	{
		return mBoardWidth;
	}
	
	public int getGridNumber()
	{
		return mGridNumber;
	}
	
	public Vector2 getGridByAbs(int absX, int absY)
	{
		int relX = absX-(int)Layout.BOARD_LAYOUT.x;
		int relY = absY-(int)Layout.BOARD_LAYOUT.y;
		Gdx.app.error("getGridByAbs",String.format("%d %d", relX,relY));
		return new Vector2(relX/GlobalConfig.GRID_WIDTH_DEFAULT, relY/GlobalConfig.GRID_WIDTH_DEFAULT);
	}
	
	private void drawEmptyBoard()
	{
		mBatch.draw(mTextureBoard, Layout.BOARD_LAYOUT.x, Layout.BOARD_LAYOUT.y);
	}

	public void draw()
	{
		drawEmptyBoard();
		drawBoardAll();
	}
	
	private void diaposeArray(Texture [] textures)
	{
		for(int i=0; i< textures.length; i++)
		{
			textures[i].dispose();
		}
	}
	
	public void diapose()
	{
		mTextureBoard.dispose();
		diaposeArray(mTextureGrid);
		diaposeArray(mTextureBorder);
	}
	
	private class Grid
	{
		int owner;
		int border;
	}
	

}
