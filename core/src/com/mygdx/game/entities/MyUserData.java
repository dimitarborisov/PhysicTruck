package com.mygdx.game.entities;

public class MyUserData {
	
	private boolean isOut;
	
	public MyUserData(){
		this.isOut = false;
	}
	
	public boolean isOut(){
		return isOut;
	}
	
	public void setOut(boolean isOut){
		this.isOut = isOut;
	}
	
}
