package com.itdoesnotmatter;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

public class ActiveObject extends GameObject{
	
	protected float angle = 0f;
	public float moveX = 0f;
	public float moveY = 0f;
	public float velocity = 1f;


	public boolean stop = false;
	public boolean move = false;
	
	protected float interpolation;
	protected boolean rotate = false;
	
	public float scale = 0.01f;
	protected int a = 1;
	public ActiveObject() {
		super();
		this.direction = MoveDirection.TOP;
		Random rand = new Random();
		String id = "";
		for (int i = 0; i < rand.nextInt(15); i++) {
			id += "" + rand.nextInt(100);
		}
		setId(id);
	}
	
	public void turnRight() {
		rotate = true;
		switch(direction) {
		case TOP:
			angle += -90f;
			break;
		case DOWN:
			angle += 90f;
			break;
		case LEFT:
			angle += -180f;
			break;
		}
		
		direction = MoveDirection.RIGHT;
	}
	
	public void turnLeft() {
		rotate = true;
		switch(direction) {
		case TOP:
			angle += 90f;
			break;
		case DOWN:
			angle += -90f;
			break;
		case RIGHT:
			angle += 180f;
			break;
		}
		
		direction = MoveDirection.LEFT;
	}
	
	public void turnUp() {
		rotate = true;
		switch(direction) {
		case RIGHT:
			angle += 90f;
			break;
		case LEFT:
			angle += -90f;
			break;
		case DOWN:
			angle += 180f;
			break;
		}
		
		direction = MoveDirection.TOP;
	}
	
	public void turnDown() {
		rotate = true;
		switch(direction) {	
		case RIGHT:
			angle += -90f;
			break;
		case LEFT:
			angle += 90f;
			break;
		case TOP:
			angle += 180f;
			break;
		}

		direction = MoveDirection.DOWN;
	}
	
	public void updateState() {
		//move();
	}
	
	public void drawObject(GL10 gl, float inter) {
		this.interpolation = inter;
		if(this.interpolation < 0 || this.interpolation > 1)
			this.interpolation = 1;
		this.velocity = a * this.interpolation;
		draw(gl);
	}
	
	@Override
	public void draw(GL10 gl) {
		
		if (this.isAlive()) {
			updateState();
			gl.glPushMatrix();
	
		
	
			gl.glTranslatef(moveX, moveY, 0f);
	
			gl.glTranslatef(this.centerX, this.centerY, 0f);
			gl.glRotatef(angle, 0f, 0f, 1f);
			gl.glTranslatef(-this.centerX, -this.centerY, 0f);
			
			super.draw(gl);
	
			gl.glPopMatrix();
		}
	}
	
	
	public void setOnTile(float[] vertices, float size) {
		this.stop = true;
		moveX = 0; 
		moveY = 0;

		setVertices(vertices);
		this.setSize(size);
		calculateTile();
		this.stop = false;
	}
	

	public void calculateTile() {
		/*
		 * Половинка отнимается для того, чтобы фиксировать переход в другой тайл только тогда,
		 *  когда середина танка пересечет границу текущего тайла
		 *  
		 *  13 и 12 размерность поля за вычетом 1, заменить!
		 */

		this.setColumn((int) Math.floor((this.currentCenterX() - GameMap.getOriginX())/GameMap.tileSize())) ;
	
		this.setRow((int) Math.floor((GameMap.getOriginY() - this.currentCenterY())/GameMap.tileSize())) ;
		if (Global.DEBUG) {
			Log.d("Tank tile", "x: " + this.getColumn() + "; y: " + this.getRow() + " tank size: " + this.getSize());
		}
	}
	

	public void collision() {
		if (!stop) {
			if (Global.DEBUG) {
				Log.d("collision", "get collision! Direction: " + this.direction);
			}
		}
		this.moving(false);
		//stop = true;
	}
	
	public void moving(boolean state) {
		move = state;
	}
	
	protected void changeDirection(MoveDirection direction) {
		switch(direction) {
		case TOP:
			turnUp();
			break;
		case DOWN:
			turnDown();
			break;
		case LEFT:
			turnLeft();
			break;
		case RIGHT:
			turnRight();
			break;
		}
	}
	
	
	/************************** Getters / Setters *******************************/
	
	public float currentCenterX() {
		return this.centerX + moveX;
	}
	
	public float currentCenterY() {
		return this.centerY + moveY;
	}
	
	public float currentLeftBorder() {
		return this.centerX + moveX - (this.getSize()/2);
	}
	
	public float currentRightBorder() {
		return this.centerX + moveX + (this.getSize()/2);
	}
	
	public float currentTopBorder() {
		return this.centerY + moveY + (this.getSize()/2);
	}
	
	public float currentBottomBorder() {
		return this.centerY + moveY - (this.getSize()/2);
	}
	
	
	public float currentLeftBorder(float moveX) {
		return this.centerX + moveX - (this.getSize()/2);
	}
	
	public float currentRightBorder(float moveX) {
		return this.centerX + moveX + (this.getSize()/2);
	}
	
	public float currentTopBorder(float moveY) {
		return this.centerY + moveY + (this.getSize()/2);
	}
	
	public float currentBottomBorder(float moveY) {
		return this.centerY + moveY - (this.getSize()/2);
	}
	
	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}

}
