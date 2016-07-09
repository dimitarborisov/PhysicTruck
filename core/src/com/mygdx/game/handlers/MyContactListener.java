package com.mygdx.game.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class MyContactListener implements ContactListener{
	
	GameStateManager m;
	
	public MyContactListener(GameStateManager m){
		super();
		this.m = m;
	}
	
	@Override
	public void beginContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();		
		
		if(fa.getBody().getUserData() != null){
			if(fa.getBody().getUserData().equals("FINISH")){
				m.setState(m.PLAY);
			}
		}
		
		if(fb.getBody().getUserData() != null){
			if(fb.getBody().getUserData().equals("FINISH")){
				m.setState(m.PLAY);
			}
		}
		
		//System.out.println(fa.getUserData() + " - " + fb.getUserData());
		
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
