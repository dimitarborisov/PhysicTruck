package com.mygdx.game.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.entities.MyUserData;
import com.mygdx.game.states.Play;

public class MyContactListener implements ContactListener {

	Play state;
	boolean isFinished;

	int cratesOut;

	public MyContactListener(Play state) {
		super();
		this.state = state;
		isFinished = false;
		cratesOut = 0;
	}

	@Override
	public void beginContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();

		// DETECT PROPER END OF THE STAGE
		if ((fa.getBody().getUserData() != null && fb.getBody().getUserData() != null)) {
			if ((fa.getBody().getUserData().equals("FINISH") || fa.getBody().getUserData().equals("VEHICLE"))
					&& (fb.getBody().getUserData().equals("FINISH") || fb.getBody().getUserData().equals("VEHICLE"))) {
				// m.setState(GameStateManager.PLAY);
				// System.out.println("FINISH!");
				if (!isFinished) {
					state.finishStage(true, cratesOut);
					isFinished = true;
				}

			}
		}

		// DETECT NOT PROPER END OF THE STAGE
		if ((fa.getBody().getUserData() != null && fb.getBody().getUserData() != null)) {
			if ((fa.getBody().getUserData().equals("BOTTOMBORDER") || fa.getBody().getUserData().equals("VEHICLE"))
					&& (fb.getBody().getUserData().equals("BOTTOMBORDER") || fb.getBody().getUserData().equals("VEHICLE"))) {
				// m.setState(GameStateManager.PLAY);
				// System.out.println("FINISH!");
				if (!isFinished) {
					state.finishStage(false, cratesOut);
					isFinished = true;
				}

			}
		}

		// DETECT BOX OUT
		if ((fa.getBody().getUserData() != null && fb.getBody().getUserData() != null)) {
			if ((fa.getBody().getUserData() instanceof MyUserData) && (fb.getBody().getUserData().equals("GROUND") || fb.getBody().getUserData().equals("BOTTOMBORDER"))) {
				MyUserData mud = (MyUserData) fa.getBody().getUserData();
				if (!mud.isOut()) {
					cratesOut += 1;
					mud.setOut(true);
					state.updateBoxCounter(cratesOut);
				}
			}

			if ((fb.getBody().getUserData() instanceof MyUserData) && (fa.getBody().getUserData().equals("GROUND") || fa.getBody().getUserData().equals("BOTTOMBORDER") )) {
				MyUserData mud = (MyUserData) fb.getBody().getUserData();
				if (!mud.isOut()) {
					cratesOut += 1;
					mud.setOut(true);
					state.updateBoxCounter(cratesOut);
				}

			}

		}

		// System.out.println(cratesOut);
		// System.out.println(fa.getUserData() + " - " + fb.getUserData());

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
