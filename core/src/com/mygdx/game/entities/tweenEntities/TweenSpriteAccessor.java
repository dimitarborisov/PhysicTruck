package com.mygdx.game.entities.tweenEntities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;

import aurelienribon.tweenengine.TweenAccessor;

public class TweenSpriteAccessor implements TweenAccessor<Sprite> {

	public static final int POS_XY = 1;
	public static final int SCALE = 2;
	public static final int COLOR = 3;
	public static final int ALPHA = 4;
	
	@Override
	public int getValues(Sprite target, int tweenType, float[] returnValues) {
		switch (tweenType) {
		case POS_XY:
			returnValues[0] = target.getX();
			returnValues[1] = target.getY();
			return 2;

		case SCALE:
			returnValues[0] = target.getScaleX();
			returnValues[1] = target.getScaleY();
			return 2;

		case COLOR:
			returnValues[0] = target.getColor().r;
			returnValues[1] = target.getColor().g;
			returnValues[2] = target.getColor().b;
			returnValues[3] = target.getColor().a;
			return 4;
			
		case ALPHA:
			returnValues[3] = target.getColor().a;
			return 1;

		default:
			assert false;
			return -1;
		}
	}

	@Override
	public void setValues(Sprite target, int tweenType, float[] newValues) {
		switch (tweenType) {
		case POS_XY:
			target.setPosition(newValues[0], newValues[1]);
			break;

		case SCALE:
			target.setScale(newValues[0], newValues[1]);
			break;

		case COLOR:
			Color c = target.getColor();
			c.set(newValues[0], newValues[1], newValues[2], newValues[3]);
			target.setColor(c);
			break;
		
		case ALPHA:
			target.setAlpha(newValues[0]);
			break;

		default:
			assert false;
		}
	}
}