package com.gdr.blokus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class boxTest extends UIBox {
	Color mColor;
	Rectangle mRectangle;
	Texture mTexture;
	
	boxTest(UIBox parent,float x,float y, float width, float height, Color type)
	{
		super(parent, x, y);
		
		mColor= new Color(type);
		Pixmap pixmap = new Pixmap((int)width, (int)height, Pixmap.Format.RGBA8888);
		pixmap.setColor(type);
		pixmap.fill();
		mTexture = new Texture(pixmap);
		pixmap.dispose();
	}
	
	public void draw(SpriteBatch batch)
	{
		batch.draw(mTexture, getAbsolutePos().x, getAbsolutePos().y);
	}
	
	public void dispose()
	{
		mTexture.dispose();
	}
}
