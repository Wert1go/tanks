package com.itdoesnotmatter;

public class Button extends GameObject{
	private int id;
	
	public Button(int id) {
		super();
		setButtonId(id);
	}
	
	public void setButtonId(int id) {
		this.id = id;
	}
	
	public int getButtonId() {
		return id;
	}
}
