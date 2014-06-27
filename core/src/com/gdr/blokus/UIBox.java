package com.gdr.blokus;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public abstract class UIBox {
	Vector2 mAbsolutePos=null;
	UIBox mParent=null;
	Array<UIBox> mChildren;
	
	UIBox(UIBox parent,float x, float y)
	{
		mChildren = new Array<UIBox>();
		mAbsolutePos = new Vector2();
		mParent = parent;
		if(mParent!=null){
			mParent.addChild(this);
			mAbsolutePos.x = mParent.getAbsolutePos().x + x;
			mAbsolutePos.y = mParent.getAbsolutePos().y + y;
		} else {
			mAbsolutePos.x = x;
			mAbsolutePos.y = y;
		}
	}
		
	public Vector2 getAbsolutePos()
	{
		return mAbsolutePos;
	}
	
	public boolean hasParent()
	{
		if (mParent!=null)
			return true;
		else
			return false;
	}
	
	public void addChild(UIBox child)
	{
		mChildren.add(child);
	}
	
	public void drawBox(SpriteBatch batch)
	{
		draw(batch);
		for(UIBox child: mChildren)
		{
			child.drawBox(batch);
		}
	}
	
	
	public abstract void draw(SpriteBatch batch);
	public abstract void dispose();
	
}
