package com.gdr.blokus;

import com.badlogic.gdx.graphics.Color;

public final class GlobalConfig {
	static String VERSION="0.0.2.0";
	
	static int DEFAULT_PLAYERS=2;
	
	static int BOARD_GRIDS_2P = 14; //grids
	static int BOARD_GRIDS_DEFAULT= 20;
	static int GRID_WIDTH_DEFAULT =30; //pixels
	static Color BACKGROUND_COLOR=Color.GRAY;
	static Color EDGE_COLOR=Color.BLACK; 
	static Color [] PLAYER_COLORS ={BACKGROUND_COLOR, Color.GREEN, Color.BLUE, Color.PINK, Color.YELLOW};
	static Color [] BORDER_COLORS ={EDGE_COLOR, Color.WHITE, Color.RED};

	static String [] CHESS_TYPE={"1", "2", "3","shortZ"};
}
