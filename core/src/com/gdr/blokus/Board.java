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
	final Color [] PLAYER_COLORS ={BACKGROUND_COLOR, Color.GREEN, Color.BLUE, Color.PINK, Color.YELLOW};
	final Color [] BORDER_COLORS ={EDGE_COLOR, Color.WHITE, Color.RED};
    private Texture mTextureBoard;
    private Pixmap mPixmapBoard;
    private Pixmap [] mPixmapGrid;
    private Pixmap [] mPixmapBorder;
	int mPlayers;
	int mGridNumber;
	int mBoardWidth;
	boolean mUpdateNeeded=true;
	
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
		Layout.BOARD_LAYOUT = new Axis();
		Layout.BOARD_LAYOUT.x = (Gdx.graphics.getWidth()-getWidth())/2;
		Layout.BOARD_LAYOUT.y = (Gdx.graphics.getHeight()-getWidth())/2;
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
		initBorderPixmap();
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
		mPixmapBoard = new Pixmap(mBoardWidth, mBoardWidth, Pixmap.Format.RGBA8888);
		drawBoardBgColor();
		drawBoardEdges();
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
	
	private void initFieldsPixmap()
	{
		int numberFieldsPixmap= mPlayers+1;
		mPixmapGrid = new Pixmap[numberFieldsPixmap];
		for(int i=0; i< numberFieldsPixmap; i++){
			mPixmapGrid[i] = new Pixmap(GRID_WIDTH_DEFAULT, GRID_WIDTH_DEFAULT, Pixmap.Format.RGBA8888);
			mPixmapGrid[i].setColor(PLAYER_COLORS[i]);
			mPixmapGrid[i].fill();
		}
	}
	
	private void initBorderPixmap()
	{
		mPixmapBorder = new Pixmap[BORDER_COLORS.length];
		for(int i=0; i < BORDER_COLORS.length; i++)
		{
			mPixmapBorder[i] = new Pixmap(GRID_WIDTH_DEFAULT+2, GRID_WIDTH_DEFAULT+2, Pixmap.Format.RGBA8888);
			mPixmapBorder[i].setColor(BORDER_COLORS[i]);
			mPixmapBorder[i].drawRectangle(0, 0, mPixmapBorder[i].getWidth(), mPixmapBorder[i].getHeight());
		}		
	}
	
	private void drawField(int i, int j)
	{
		Axis gridAxis;
		gridAxis = getGridAxis(i,j);
		mPixmapBoard.drawPixmap(mPixmapGrid[mGrids[i][j].owner], gridAxis.x, gridAxis.y);
	}
	
	private void drawBorder(int i,int j, boolean clear)
	{
		Axis borderAxis;
		borderAxis = getGridBorderAxis(i,j);
		if(clear)
			mPixmapBoard.drawPixmap(mPixmapBorder[0], borderAxis.x, borderAxis.y);
		else
			mPixmapBoard.drawPixmap(mPixmapBorder[mGrids[i][j].border], borderAxis.x, borderAxis.y);
	}
	
	private Texture drawBoardAll()
	{	
		for(int i=0;i<mGridNumber;i++)
			for(int j=0;j<mGridNumber;j++) {
				if(mGrids[i][j].owner!=0) {
					drawField(i,j);
				}
				if(mGrids[i][j].border!=0) {
					drawBorder(i,j,false);
				}
			}
		return new Texture(mPixmapBoard);
	}
	
	public void setBoardEmpty()
	{
		for(int i=0;i<mGridNumber;i++)
			for(int j=0;j<mGridNumber;j++) {
				mGrids[i][j].owner=0;
				mGrids[i][j].border=0;
			}
		mUpdateNeeded=true;
	}
	
	public void setColor(int playerID, int x, int y)
	{
		mGrids[x][y].owner = playerID;
		mUpdateNeeded = true;
	}
	
	public void setGridBorder(boolean enable, int x, int y)
	{
		if(enable)
			mGrids[x][y].border=1;
		else
			mGrids[x][y].border=2;
		mUpdateNeeded=true;
	}
	
	public void clearGridsBorder()
	{
		for(int i=0;i<mGridNumber;i++)
			for(int j=0;j<mGridNumber;j++) {
				if(mGrids[i][j].border!=0) {
					drawBorder(i,j,true);
					mGrids[i][j].border=0;
				}
			}
	}
	
	private Axis getGridAxis(int gridX, int gridY)
	{
		Axis axis=new Axis();
		axis.x = 1 + gridX * (GRID_WIDTH_DEFAULT+1) ;
		axis.y = 1 + gridY * (GRID_WIDTH_DEFAULT+1) ;
		return axis;
	}
	
	private Axis getGridBorderAxis(int gridX, int gridY)
	{
		Axis axis=new Axis();
		axis.x = gridX * (GRID_WIDTH_DEFAULT+1) ;
		axis.y = gridY * (GRID_WIDTH_DEFAULT+1) ;
		return axis;
	}

	
	public int getWidth()
	{
		return mBoardWidth;
	}
	
	
	private void drawBoard()
	{
		if(!mUpdateNeeded)
			return;
		mTextureBoard=drawBoardAll();
		mSprite = new Sprite(mTextureBoard);
		mSprite.setPosition(Layout.BOARD_LAYOUT.x, Layout.BOARD_LAYOUT.y);
	}

	public void draw(SpriteBatch batch)
	{
		drawBoard();
		mSprite.draw(batch);
	}
	
	private class Grid
	{
		int owner;
		int border;
	}
	
	public class Axis
	{
		int x;
		int y;
	}
}
