package com.gdr.blokus;

import com.badlogic.gdx.graphics.Color;

public final class Utils {

	static public Color getPlayerColor(int playerID)
	{
		return GlobalConfig.PLAYER_COLORS[playerID];
	}
	
	static public int getChessUnitWidth()
	{
		return GlobalConfig.GRID_WIDTH_DEFAULT+1;
	}
}
