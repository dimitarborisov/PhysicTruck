package com.mygdx.game.handlers;

public class MyUserData {
	
	private boolean state;
	private String name;
	
	public MyUserData(String name, boolean state){
		this.name = name;
		this.state = state;
	}
	
	public MyUserData(String name){
		this.name = name;
		state = false;
	}
	
	public MyUserData(){
		name = null;
		state = false;
	}
	
	
	public void setState(boolean state){
		this.state = state;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public boolean getState(){
		return state;
	}
	
	public String getName(){
		return name;
	}
	
	public String toString() {
		return name;
	}
	
}
