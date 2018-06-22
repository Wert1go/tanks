package com.itdoesnotmatter;


public class Bullet extends ActiveObject {
	Tank tank;
	
	public Bullet(Tank tank) {
		super();
		this.tank = tank;
		this.scale = 0.75f;
		this.move = true;
		this.stop = false;
		this.a = 2;
		changeDirection(tank.direction);
		short[] indices = {0, 1, 2, 1,3,2};
		this.setIndices(indices);
		loadTexture(Textures.loadTextureImage());

        setTexture(Textures.getTexture(Texture.BULLET));
        
        placeBullet();
	}
	
	private float[] generateVertices(float left, float top, float size) {
		float[] vertices = {
				left, top - size, 0.0f,
				left + size, top - size, 0.0f,
				left, top, 0.0f,
				left + size, top, 0.0f
		};
		
		return vertices;
	}
	
	private void placeBullet() {
		float[] vertices = null;
		float size = GameMap.tileSize() - GameMap.tileSize() * this.scale;
		switch(direction) {
		case TOP:
			vertices = generateVertices(tank.currentCenterX() - size/2, tank.currentTopBorder(), size);
			break;
		case DOWN:
			vertices = generateVertices(tank.currentCenterX() - size/2, tank.currentBottomBorder() + size, size);
			break;
		case LEFT:
			vertices = generateVertices(tank.currentLeftBorder(), tank.currentCenterY() + size/2, size);
			break;
		case RIGHT:
			vertices = generateVertices(tank.currentRightBorder() - size, tank.currentCenterY() + size/2, size);
			break;
		}
		
		this.setOnTile(vertices, size);
	}
	

	@Override
	public void collision() {
		tank.setBulletCount(1);
		this.kill();
	}
}
