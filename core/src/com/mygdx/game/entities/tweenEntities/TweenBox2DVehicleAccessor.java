package com.mygdx.game.entities.tweenEntities;

import com.mygdx.game.api.Box2DVehicle;

import aurelienribon.tweenengine.TweenAccessor;

public class TweenBox2DVehicleAccessor implements TweenAccessor<Box2DVehicle> {

	public static final int MOVEBODY = 1;
	public static final int MOVELEFTWHEEL = 2;
	public static final int MOVERIGHTWHEEL = 3;

	@Override
	public int getValues(Box2DVehicle target, int tweenType, float[] returnValues) {
		switch (tweenType) {

		case MOVEBODY:
			returnValues[0] = target.getBody().getPosition().x;
			returnValues[1] = target.getBody().getPosition().y;
			returnValues[2] = target.getBody().getAngle();
			return 3;
		
		case MOVELEFTWHEEL:
			returnValues[0] = target.getLeftWheel().getPosition().x;
			returnValues[1] = target.getLeftWheel().getPosition().y;
			returnValues[2] = target.getLeftWheel().getAngle();
			return 3;
			
		case MOVERIGHTWHEEL:
			returnValues[0] = target.getRightWheel().getPosition().x;
			returnValues[1] = target.getRightWheel().getPosition().y;
			returnValues[2] = target.getRightWheel().getAngle();
			return 3;
			
		default:
			assert false;
			return -1;
		}
	}

	@Override
	public void setValues(Box2DVehicle target, int tweenType, float[] newValues) {
		switch (tweenType) {
		
		case MOVEBODY:
			target.getBody().setTransform(newValues[0], newValues[1], newValues[2]);
			break;

		case MOVELEFTWHEEL:
			target.getLeftWheel().setTransform(newValues[0], newValues[1], newValues[2]);
			break;

		case MOVERIGHTWHEEL:
			target.getRightWheel().setTransform(newValues[0], newValues[1], newValues[2]);
			break;

		default:
			assert false;
		}
	}
}
