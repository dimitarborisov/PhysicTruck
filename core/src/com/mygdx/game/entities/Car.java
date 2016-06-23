package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;
import com.mygdx.game.handlers.BodyEditorLoader;

public class Car extends InputAdapter{
	private Body body, leftWheel, rightWheel;
	private WheelJoint leftAxis, rightAxis;
	Vector2 bodyOrigin;
	private float motorSpeed = 25;

	public Car(World w, FixtureDef bodyFixture, FixtureDef wheelFixture, float x, float y, float scale){
		
		//body of the truck
		// 0. Create a loader for the file saved from the editor.
		BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("models/truck.json"));

		// 1. Create a BodyDef, as usual.
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		bd.position.set(x , y);
		
		// 3. Create a Body, as usual.
		body = w.createBody(bd);

		// 4. Create the body fixture automatically by using the loader.
		loader.attachFixture(body, "body", bodyFixture, scale);
		bodyOrigin = loader.getOrigin("body", scale).cpy();
			
		
		//left wheel
		bd.position.set(x , y);
		CircleShape wheelShape = new CircleShape();
		wheelShape.setRadius(((scale) / 2) / 15);
		
		wheelFixture.shape = wheelShape;
		leftWheel = w.createBody(bd);
		leftWheel.createFixture(wheelFixture);
		

		//right wheel
		rightWheel = w.createBody(bd);
		rightWheel.createFixture(wheelFixture);
		
		//left axis
		WheelJointDef axisDef = new WheelJointDef();
		axisDef.bodyA = body;
		axisDef.bodyB = leftWheel;
		axisDef.localAnchorA.set(x / scale - 1, - y / scale);
		axisDef.localAxisA.set(Vector2.Y);
		axisDef.frequencyHz = bodyFixture.density ;
		axisDef.maxMotorTorque = bodyFixture.density * 2;
		leftAxis = (WheelJoint) w.createJoint(axisDef);
		
		//right axis
		axisDef.frequencyHz = bodyFixture.density;
		axisDef.bodyB = rightWheel;
		axisDef.localAnchorA.set(- x / scale + 1, - y / scale);
		
		rightAxis = (WheelJoint) w.createJoint(axisDef);
	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch(keycode){
		case Keys.W:
			leftAxis.enableMotor(true);
			leftAxis.setMotorSpeed(-motorSpeed);
			
			rightAxis.enableMotor(true);
			rightAxis.setMotorSpeed(-motorSpeed);
			break;
		
		case Keys.S:
			leftAxis.enableMotor(true);
			leftAxis.setMotorSpeed(motorSpeed);
			
			rightAxis.enableMotor(true);
			rightAxis.setMotorSpeed(motorSpeed);
			break;
		
		case Keys.SPACE:
			leftWheel.setFixedRotation(true);
			rightWheel.setFixedRotation(true);
		
		}
		
		return true;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		switch(keycode){
		case Keys.W:
		case Keys.S:
			leftAxis.enableMotor(false);
			rightAxis.enableMotor(false);
			break;
			
		case Keys.SPACE:
			leftWheel.setFixedRotation(false);
			rightWheel.setFixedRotation(false);
		}
		return true;
	}
	
	public Body getBody(){
		return body;
	}
}
