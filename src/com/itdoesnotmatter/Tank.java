package com.itdoesnotmatter;


public class Tank extends ActiveObject {

	private int bulletCount = 1;
	
	public Tank() {
		super();
		this.scale = 0.15f;
		
		direction = MoveDirection.TOP;
		short[] indices = {0, 1, 2, 1,3,2};
		this.setIndices(indices);
		loadTexture(Textures.loadTextureImage());

        setTexture(Textures.getTexture(Texture.TANK));
	}
	
	
	public void shoot() {
		if (getBulletCount() > 0) {
			Bullet bullet = new Bullet(this);
			//Manager.placeObject(bullet, this.getRow(), this.getColumn());
			World.addObjectToQueue(bullet);
			setBulletCount(0);
		}
	}
	
	public void setBulletCount(int count) {
		this.bulletCount = count;
	}
	
	public int getBulletCount() {
		return this.bulletCount;
	}
}
