package com.gdr.blokus;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public final class Executor {
	
	
	private static boolean checkNeighbor(Board board, Vector2 gridPos, int player)
	{
		if(gridPos.x+1< board.getGridNumber()){
			if(player == board.getGridOwner((int)gridPos.x+1, (int)gridPos.y))
				return false;
		}
		if(gridPos.y+1< board.getGridNumber()){
			if(player == board.getGridOwner((int)gridPos.x, (int)gridPos.y+1))
				return false;
		}
		if(gridPos.x-1>=0 ){
			if(player == board.getGridOwner((int)gridPos.x-1, (int)gridPos.y))
				return false;
		}
		if(gridPos.y-1>=0){
			if(player == board.getGridOwner((int)gridPos.x, (int)gridPos.y-1))
				return false;
		}
		return true;
	}
	
	
	public static void testBoard(Board board, Chess chess, int x, int y, int playerID)
	{
		int gridNum = board.getGridNumber();
		Vector2 gridPos = board.getGridByAbs(x, y);
		Array<Vector2> grids = chess.getCurGrids();
		Vector2 cur = new Vector2(); 
		
		board.clearGridsBorder();
		for(Vector2 perGrid: grids){
			cur.x= gridPos.x + perGrid.x;
			cur.y= gridPos.y + perGrid.y;
			
			if(cur.x >=0 && cur.x<gridNum && cur.y >=0 && cur.y<gridNum){
				if(checkNeighbor(board, cur,playerID))
					board.setGridBorder(true, (int)cur.x, (int)cur.y);
				else
					board.setGridBorder(false, (int)cur.x, (int)cur.x);
			}
		}
	}
	
	public static void putBoard(Board board, Chess chess, int x, int y, int playerID)
	{
		int gridNum = board.getGridNumber();
		Vector2 gridPos = board.getGridByAbs(x, y);
		Array<Vector2> grids = chess.getCurGrids();
		Vector2 cur = new Vector2(); 
		
		board.clearGridsBorder();
		for(Vector2 perGrid: grids){
			cur.x= gridPos.x + perGrid.x;
			cur.y= gridPos.y + perGrid.y;
			
			if(cur.x >=0 && cur.x<gridNum && cur.y >=0 && cur.y<gridNum){
				if(checkNeighbor(board, cur,playerID)){
					board.setColor(playerID, (int)cur.x, (int)cur.y);
					chess.setState(Chess.Status.BOARD);
				}
				else {
					chess.setState(Chess.Status.PANEL);
				}
			}
		}
	}
}
