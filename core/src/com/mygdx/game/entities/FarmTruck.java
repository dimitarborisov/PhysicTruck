package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
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
import com.mygdx.game.handlers.Box2DVariables;
import com.mygdx.game.handlers.MyUserData;
import com.mygdx.game.main.Game;

public class FarmTruck extends Box2DVehicle{
	private float carWidth;
	
	private Body body, leftWheel, rightWheel;
	private WheelJoint leftAxis, rightAxis;
	Vector2 bodyOrigin;
	
	private float motorSpeed = 55;
	
	private Sprite bodySprite;
	private Sprite wheelSprite;
	
	private Texture bodyTexture;
	private Texture wheelTexture;
	
	private Vector2 truckPos;

	public FarmTruck(World w, float x, float y, float scale){
		//Car width
		carWidth = scale;
		
		FixtureDef bodyFixture = new FixtureDef();
		FixtureDef wheelFixture = new FixtureDef();
		
		//truck
		bodyFixture.density = 2;
		bodyFixture.friction = 0.4f;
		bodyFixture.restitution = 0.3f;

		
		//wheel
		wheelFixture.density = bodyFixture.density + 2.5f;
		wheelFixture.friction = 5f;
		
		bodyFixture.friction = 0.3f;
		bodyFixture.restitution = 0.4f;
		
		//load fixtures
		bodyTexture = Game.cm.getTexture("FarmTruck");
		wheelTexture = Game.cm.getTexture("Wheel");
		
		wheelTexture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		bodyTexture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		
		//create sprites
		wheelSprite = new Sprite(wheelTexture);
		bodySprite = new Sprite(bodyTexture);
		
		//body of the truck
		// 0. Create a loader for the file saved from the editor.
		BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("models/truck1,1.json"));

		// 1. Create a BodyDef, as usual.
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		bd.position.set(x , y);
		
		// 3. Create a Body, as usual.
		body = w.createBody(bd);

		// 4. Create the body fixture automatically by using the loader.
		loader.attachFixture(body, "Name", bodyFixture, carWidth);
		bodyOrigin = loader.getOrigin("Name", carWidth).cpy();
		
		//setup basic Sprite options
		bodySprite.setSize(carWidth * Box2DVariables.PPM, (carWidth*bodySprite.getHeight()/bodySprite.getWidth()) * Box2DVariables.PPM);
		bodySprite.setOrigin(bodyOrigin.x * Box2DVariables.PPM, bodyOrigin.y * Box2DVariables.PPM);
		
		//left wheel
		bd.position.set(x , y);
		CircleShape wheelShape = new CircleShape();
		wheelShape.setRadius(33 / Box2DVariables.PPM);
		
		wheelFixture.shape = wheelShape;
		wheelFixture.friction = 5f;
		
		leftWheel = w.createBody(bd);
		leftWheel.createFixture(wheelFixture);
		

		//right wheel
		rightWheel = w.createBody(bd);
		rightWheel.createFixture(wheelFixture);
		//rightWheel.setUserData(new MyUserData("leftWheel", rightWheel, "sprites/tyre.png"));
		
		//left axis
		WheelJointDef axisDef = new WheelJointDef();
		axisDef.bodyA = body;
		axisDef.bodyB = leftWheel;
		//axisDef.localAnchorA.set(x / scale - 1, - (y / scale) - 0.4f);
		axisDef.localAnchorA.set(body.getPosition().x - 0.55f, body.getPosition().y - 0.7f);
		axisDef.localAxisA.set(Vector2.Y);
		axisDef.frequencyHz = bodyFixture.density + 4;
		axisDef.maxMotorTorque = bodyFixture.density * 2;
		
		
		leftAxis = (WheelJoint) w.createJoint(axisDef);
		
		//right axis
		axisDef.frequencyHz = bodyFixture.density + 3;
		axisDef.bodyB = rightWheel;
		axisDef.localAnchorA.set(body.getPosition().x + 0.46f, body.getPosition().y - 0.7f);
		
		rightAxis = (WheelJoint) w.createJoint(axisDef);
		
	}
	

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
			//rightWheel.setFixedRotation(true);
		
		}
		
		return true;
	}
	
	public boolean keyUp(int keycode) {
		switch(keycode){
		case Keys.W:
		case Keys.S:
			leftAxis.enableMotor(false);
			rightAxis.enableMotor(false);
			break;
			
		case Keys.SPACE:
			leftWheel.setFixedRotation(false);
			//rightWheel.setFixedRotation(false);
		}
		return true;
	}
	
	public Body getBody(){
		return body;
	}


	public void render(SpriteBatch sb) {
		
		renderRightWheel(sb);
		renderLeftWheel(sb);
		renderBody(sb);
	}
	
	private void renderLeftWheel(SpriteBatch sb){
		sb.begin();
			wheelSprite.setSize(66, 66);
			wheelSprite.setOrigin(wheelSprite.getHeight() / 2, wheelSprite.getWidth() / 2);
			
			wheelSprite.setPosition(leftWheel.getPosition().x * Box2DVariables.PPM - wheelSprite.getHeight() / 2,
									leftWheel.getPosition().y * Box2DVariables.PPM - wheelSprite.getHeight() / 2);
			
			wheelSprite.setRotation(leftWheel.getAngle() * MathUtils.radiansToDegrees);
		
			wheelSprite.draw(sb);

		sb.end();
	}
	
	private void renderRightWheel(SpriteBatch sb){
		sb.begin();
			wheelSprite.setSize(66, 66);
			wheelSprite.setOrigin(wheelSprite.getHeight() / 2, wheelSprite.getWidth() / 2);
			
			wheelSprite.setPosition(rightWheel.getPosition().x * Box2DVariables.PPM - wheelSprite.getHeight() / 2,
									rightWheel.getPosition().y * Box2DVariables.PPM - wheelSprite.getHeight() / 2);
			
			wheelSprite.setRotation(rightWheel.getAngle() * MathUtils.radiansToDegrees);
		
			wheelSprite.draw(sb);

		sb.end();
		
	}
	
	private void renderBody(SpriteBatch sb){
		sb.begin();
			//Save body position matrix
			truckPos = body.getPosition().sub(bodyOrigin);
			
			bodySprite.setPosition(truckPos.x * Box2DVariables.PPM, truckPos.y * Box2DVariables.PPM);
			bodySprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		
			bodySprite.draw(sb);

		sb.end();
	}


	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		
	}
}
