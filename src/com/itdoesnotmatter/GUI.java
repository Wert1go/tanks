package com.itdoesnotmatter;



public class GUI extends GameObject {
	public static final int LEFT = 0;
	public static final int TOP = 1;
	public static final int RIGHT = 2;
	public static final int DOWN = 3;
	
	public static final int FIRE = 4;
	
	private int uiElementCount = 5;
	private Button[] uiElements;
	
	float[] uiVertices;
	float[] uiTextures;
	short[] uiIndices;
	
	int verticesIndex = 0;
	int textureIndex = 0;
	int indicesIndex = 0;
	
	private short zero = 0;
	private short one = 1;
	private short two = 2;
	private short three = 3;
	
	public GUI() {
		uiElements = new Button[uiElementCount];
		
		uiVertices = new float[uiElementCount * 4 * 3];
		uiTextures = new float[uiElementCount * 4 * 2];
		uiIndices = new short[uiElementCount * 6];
		
		float dpadSize = 40f;
		float dpadCenterX = (40f*3)/2;
		float dpadCenterY = (40f*3)/2;
		
		float leftOrignX = dpadCenterX - dpadSize/2 - dpadSize;
		float leftOrignY = dpadCenterY + dpadSize/2;
		
		float[] vertices = generateVertices(leftOrignX, leftOrignY, dpadSize);
		
		pushVertices(vertices);
		pushTexture(Textures.getTexture(Texture.LEFT));
		pushIndices();
		
		Button left = new Button(LEFT);
		left.setVertices(vertices);
		left.setSize(dpadSize);
		uiElements[LEFT] = left;
		
		float rightOrignX = dpadCenterX + dpadSize/2;
		float rightOrignY = dpadCenterY + dpadSize/2;
		
		vertices = generateVertices(rightOrignX, rightOrignY, dpadSize);
		
		pushVertices(vertices);
		pushTexture(Textures.getTexture(Texture.RIGHT));
		pushIndices();
		
		Button right = new Button(RIGHT);
		right.setVertices(vertices);
		right.setSize(dpadSize);
		uiElements[RIGHT] = right;
		
		float topOrignX = dpadCenterX - dpadSize/2;
		float topOrignY = dpadCenterY + dpadSize/2 + dpadSize;
		vertices = generateVertices(topOrignX, topOrignY, dpadSize);
		pushVertices(vertices);
		pushTexture(Textures.getTexture(Texture.TOP));
		pushIndices();
		
		Button top = new Button(TOP);
		top.setVertices(vertices);
		top.setSize(dpadSize);
		uiElements[TOP] = top;
		
		float downOrignX = dpadCenterX - dpadSize/2;
		float downOrignY = dpadCenterY - dpadSize/2;
		vertices = generateVertices(downOrignX, downOrignY, dpadSize);
		pushVertices(vertices);
		pushTexture(Textures.getTexture(Texture.DOWN));
		pushIndices();
		
		Button down = new Button(DOWN);
		down.setVertices(vertices);
		down.setSize(dpadSize);
		uiElements[DOWN] = down;
		//Суровый хардкод
		float fireCenter = GameMap.getOriginX() + GameMap.getWidth() - 70;
		
		float fireSize = 64f;
		
		float fireOrignX = fireCenter - fireSize/2;
		float fireOrignY = dpadCenterY + fireSize/2;

		vertices = generateVertices(fireOrignX, fireOrignY, fireSize);
		pushVertices(vertices);
		pushTexture(Textures.getTexture(Texture.FIRE));
		pushIndices();
		
		Button fire = new Button(FIRE);
		fire.setVertices(vertices);
		fire.setSize(fireSize);
		uiElements[FIRE] = fire;
		
		loadTexture(Textures.loadTextureImage());
		
		this.setVertices(uiVertices);
		this.setTexture(uiTextures);
		this.setIndices(uiIndices);
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
	
	private void pushVertices(float[] vertices) {
		for (float v : vertices) {
			if (verticesIndex == 0 ) {
				uiVertices[verticesIndex++] = v;
			} else {
				uiVertices[verticesIndex++] = v;
			}
		}
	}
	
	private void pushTexture(float[] texture) {
		for (float v : texture) {
			if (textureIndex ==0 ) {
				uiTextures[textureIndex ++] = v;
			} else {
				uiTextures[textureIndex++] = v;
			}
		}
	}
	
	private void pushIndices() {
		
		short[] indices = new short[] { 
				zero, one, two,
				one, three, two
		};

		for (short v : indices) {
			if (indicesIndex == 0 ) {
				uiIndices[indicesIndex++] = v;
			} else {
				uiIndices[indicesIndex++] = v;
			}
		}
		
		zero += 4;
		one += 4;
		two += 4;
		three += 4;
	}
	
	public int dispatcher(float x, float y) {
		y = GameMap.screenHeight - y;
		for(Button button : uiElements) {
			if (x > button.currentLeftBorder() && x < button.currentRightBorder() &&
				y > button.currentBottomBorder() && y < button.currentTopBorder()) {
				return button.getButtonId();
			}
		}
		return -1;
	}
}
