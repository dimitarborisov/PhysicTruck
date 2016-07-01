package com.mygdx.game.handlers;

public class MyUserData {
	
	private String data;
	
	public MyUserData(String data){
		this.data = data;
	}
	
	public void setData(String data){
		this.data = data;
	}
	
	public String getData(){
		return data;
	}
	
	public String toString() {
		return data;
	}
	
}
