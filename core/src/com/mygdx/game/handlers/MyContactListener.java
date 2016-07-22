package com.mygdx.game.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.states.Play;

public class MyContactListener implements ContactListener{
	
	Play state;
	
	
	public MyContactListener(Play state){
		super();
		this.state = state;
		
	}
	
	@Override
	public void beginContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();		
		
		if((fa.getBody().getUserData() != null && fb.getBody().getUserData() != null)){
			if((fa.getBody().getUserData().equals("FINISH") || fa.getBody().getUserData().equals("VEHICLE"))
				&& (fb.getBody().getUserData().equals("FINISH") || fb.getBody().getUserData().equals("VEHICLE"))){
				//m.setState(GameStateManager.PLAY);
				//System.out.println("FINISH!");
				state.finishStage(true);
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
