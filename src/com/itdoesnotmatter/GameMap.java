package com.itdoesnotmatter;

import java.util.Random;

import android.util.Log;

public class GameMap extends GameObject{

	public static int screenWidth = 0;
	public static int screenHeight = 0;
	
	public static float originX= 0.0f;
	public static float originY= 0.0f;
	
	public static float sizeX= 0.0f;
	public static float sizeY= 0.0f;
	
	static float tileSize;
	static int screenWidthInTile;
	static int screenHeightInTile;
	
	/*
	 * Блок - контейнер для 4х тайлов
	 * Указываем размер игрового поля
	 * Все остальные размеры масштабируются исходя из высоты (или ширины в вертикальной ориентации)
	 */
	
	int widthInBlock = 13;
	int heightInBlock = 13;
	
	int widthInTile = widthInBlock*2 + 2;		// + 2 нужно для создания невидимой, но непроходимой, стенки
	int heightInTile = heightInBlock*2 + 2;
	/*
	 * Переменные для OpenGL
	 */
	int verticesIndex = 0;
	int textureIndex = 0;
	int indicesIndex = 0;

	float[] mapVertices;
	float[] mapTextures;
	short[] mapIndices;
	
	private short zero = 0;
	private short one = 1;
	private short two = 2;
	private short three = 3;
	
	/*
	 * Описание карт
	 */
	
	public Tile[][] tile_map;
	@SuppressWarnings("unused")
	private int[][] raw_map_1 = {

		new int[] { Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.BRICK, Block.BRICK, Block.EMPTY, Block.BRICK, Block.EMPTY, Block.EMPTY},
		new int[] { Block.BRICK, Block.BRICK, 10, Block.EMPTY, Block.EMPTY, Block.BRICK, 10, Block.BRICK, Block.BRICK, Block.EMPTY, Block.BRICK, Block.EMPTY, 10},
		new int[] { 10, 10, 10, Block.EMPTY, Block.EMPTY, Block.BRICK, Block.EMPTY, 10, Block.BRICK, Block.BRICK, Block.BRICK, Block.BRICK, Block.BRICK},
		new int[] { Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.BRICK, Block.BRICK, 11, Block.EMPTY, Block.EMPTY, Block.BRICK, Block.EMPTY, 10},
		new int[] { 11, Block.EMPTY, Block.EMPTY, 10, Block.EMPTY, Block.BRICK, Block.BRICK, 11, Block.EMPTY, Block.EMPTY, Block.BRICK, Block.EMPTY, 10},
		new int[] { 11, 11, 10, 10, Block.BRICK, Block.BRICK, Block.BRICK, 11, Block.EMPTY, Block.EMPTY, Block.BRICK, Block.BRICK, 10},
		new int[] { Block.EMPTY, Block.EMPTY, Block.BRICK, 10, Block.EMPTY, Block.BRICK, Block.EMPTY, Block.BRICK, Block.EMPTY, Block.EMPTY, 15, Block.BRICK, 10},
		new int[] { 10, Block.BRICK, Block.EMPTY, 10, Block.BRICK, Block.BRICK, 11, Block.BRICK, Block.EMPTY, Block.EMPTY, Block.BRICK, Block.BRICK, 10},
		new int[] { Block.EMPTY, Block.BRICK, 10, 10, Block.BRICK, Block.BRICK, Block.BRICK, Block.BRICK, 10, 10, 10, 11, 10},
		new int[] { Block.EMPTY, Block.BRICK, 10, 10, Block.BRICK, Block.EMPTY, Block.EMPTY, Block.EMPTY, 10, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY},
		new int[] { Block.EMPTY, Block.BRICK, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, 10, Block.BRICK, Block.EMPTY},
		new int[] { Block.EMPTY, Block.BRICK, Block.BRICK, Block.BRICK, Block.EMPTY, Block.BRICK, Block.BRICK, Block.BRICK, Block.EMPTY, Block.EMPTY, 11, 11, Block.BRICK},
		new int[] { Block.EMPTY, Block.BRICK, Block.EMPTY, Block.BRICK, Block.EMPTY, Block.BRICK, Block.EMPTY, Block.BRICK, Block.EMPTY, Block.EMPTY, Block.BRICK, 11, Block.EMPTY}
	};
	
	
	private int[][] raw_map = {

			new int[] { Block.EMPTY, Block.EMPTY, Block.EMPTY, 10, Block.EMPTY, Block.BRICK, Block.EMPTY, Block.BRICK, Block.BRICK, Block.EMPTY, Block.BRICK, Block.EMPTY, Block.EMPTY},
			new int[] { Block.BRICK, Block.EMPTY, 10, Block.EMPTY, Block.EMPTY, Block.EMPTY, 10, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY},
			new int[] { Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY},
			new int[] { Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY},
			new int[] { Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY},
			new int[] { Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY},
			new int[] { Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.BRICK_LEFT, Block.EMPTY, Block.EMPTY, 15, Block.EMPTY, Block.EMPTY},
			new int[] { Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY},
			new int[] { Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY},
			new int[] { Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY},
			new int[] { Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY},
			new int[] { Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY},
			new int[] { Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY}
		};
	public GameMap(int width, int height, boolean randomMode) {
		super();
		if (randomMode) {
			mapGeneration();
		}

		screenWidth = width;
		screenHeight = height;
		
		
		
		tileSize = (float) Math.floor(height/heightInTile);
		
		screenWidthInTile = (int) Math.floor(width/tileSize);
		screenHeightInTile = heightInTile;
		
		Log.d("screenInTile", "w: " + screenWidthInTile + "h: " + screenHeightInTile);
		
		tile_map = new Tile[screenHeightInTile][screenWidthInTile];
		sizeX = tileSize * screenWidthInTile;
		sizeY = tileSize * screenHeightInTile;
		
		originX = width/2 - sizeX/2;
		originY = height/2 + sizeY/2;
		
		//if (Global.DEBUG) {
			Log.d("MAP_ORIGIN", "x: " + originX + " y: " + originY);
			Log.d("MAP_SIZE", "x: " + sizeX + " y: " + sizeY);
			Log.d("TILE_SIZE", "size: " + tileSize);
		//}
		mapVertices = new float[this.getHeightInTile() * this.getWidthInTile() * 4 * 3];
		mapTextures = new float[this.getHeightInTile() * this.getWidthInTile() * 4 * 2];
		mapIndices = new short[this.getHeightInTile() * this.getWidthInTile() * 6];
		raw_map = this.setupScreen(screenWidthInTile, screenHeightInTile, translateMap(raw_map));
		for (int y = 0; y < this.getHeightInTile(); y ++) {
			for (int x = 0; x < this.getWidthInTile(); x++) {
				Tile tile = new Tile (raw_map[y][x], y, x, tileSize);
				
				float[] vertices = new float[] { 
						(float) (originX + (tileSize * x)), (float) (originY - (tileSize*y)  - tileSize), 0.0f,
						(float) (originX + (tileSize * x) + tileSize), (float) (originY - (tileSize*y) - tileSize), 0.0f,
						(float) (originX + (tileSize * x)), (float) (originY - (tileSize*y)), 0.0f, 
						(float) (originX + (tileSize * x) + tileSize), (float) (originY - (tileSize*y)), 0.0f };
				tile.setVertices(vertices);
				pushVertices(vertices);
				
				pushTexture(Textures.getTexture(tile.getType()));
				
				pushIndices();
				
				tile_map[y][x] = tile;
			}
		}
		
		loadTexture(Textures.loadTextureImage());
		
		this.setVertices(mapVertices);
		this.setTexture(mapTextures);
		this.setIndices(mapIndices);
	}
	
	private void pushVertices(float[] vertices) {
		for (float v : vertices) {
			if (verticesIndex == 0 ) {
				mapVertices[verticesIndex++] = v;
			} else {
				mapVertices[verticesIndex++] = v;
			}
		}
	}
	
	private void pushTexture(float[] texture) {
		for (float v : texture) {
			if (textureIndex ==0 ) {
				mapTextures[textureIndex ++] = v;
			} else {
				mapTextures[textureIndex++] = v;
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
				mapIndices[indicesIndex++] = v;
			} else {
				mapIndices[indicesIndex++] = v;
			}
		}
		
		zero += 4;
		one += 4;
		two += 4;
		three += 4;
	}
	
	private void mapGeneration() {
		Random rand = new Random();
		for (int y = 0; y < this.getHeightInTile(); y++) {
			for (int x = 0; x < this.getWidthInTile(); x++) {
				raw_map[y][x] = rand.nextInt(5);
			}
		}
	}
	
	//Убогий метод подмены текстуры учи openGL блеатЬ! glTexSubImage2D
	public void updateTileTexture(int id) {
		int index = id * 8;
		
		mapTextures[index++] = 0.8f; //x
		mapTextures[index++] = 1.0f; //y etc.
		mapTextures[index++] = 1.0f;
		mapTextures[index++] = 1.0f;
		mapTextures[index++] = 0.8f;
		mapTextures[index++] = 0.0f;
		mapTextures[index++] = 1.0f;
		mapTextures[index] = 0.0f;

		this.setTexture(mapTextures);
	}
	
	public int[][] translateMap(int[][] _map) {
		int[][] raw_map = new int[13*2][13*2];
		
		for (int row = 0; row < 13; row++) {
			for (int col = 0; col < 13; col++) {
				Block block = new Block(_map[row][col]);
				raw_map[0 + row *2][0 + col *2] = block.getTile(0);
				raw_map[0 + row *2][1 + col *2] = block.getTile(1);
				raw_map[1 + row *2][0 + col *2] = block.getTile(2);
				raw_map[1 + row *2][1 + col *2] = block.getTile(3);
			}
		}
		
		/*for (int row = 0; row < 13*2; row++) {
			String test = "";
			for (int col = 0; col < 13*2; col++) {
				test += raw_map[row][col] + ", ";
			}
			Log.d("string", "" + test);
		}*/
		return raw_map;
	}
	
	
	public int[][] setupScreen(int screenWidthInTile, int screenHeightInTile, int[][] raw_map) {
		int[][] screen = new int[screenHeightInTile][screenWidthInTile];
		
		int tileDelta = screenWidthInTile - 13*2;
		int pointer = 0;
		//Пока тайна почему 2, но так почти по центру
		int pointerY = 1;
		if (tileDelta%2 == 0) {
			pointer = tileDelta/2;
		} else {
			pointer = (int) (Math.floor(tileDelta/2));
		}
		
		for (int row = 0; row < screenHeightInTile; row++) {
			for (int col = 0; col < screenWidthInTile; col++) {
				if ((col >= pointer && col < pointer + 13*2) &&
						(row >= pointerY && row < 13*2 + pointerY)) {
					screen[row][col] = raw_map[row - pointerY][col - pointer];
				} else {
					screen[row][col] = -1;
				}
			}
		}
		return screen;
		
	}
	/************************** Getters / Setters *******************************/	
	
	public Tile[][] getMap() {
		return tile_map;
	}
	
	public int getWidthInTile() {
		return screenWidthInTile;
	}
	
	public int getHeightInTile() {
		return screenHeightInTile;
	}
	
	public static float tileSize() {
		return tileSize;
	}
	
	public static float getOriginX() {
		return originX;
	}
	
	public static float getOriginY() {
		return originY;
	}
	
	public static float getWidth() {
		return sizeX;
	}
	
	public static float getHeight() {
		return sizeY;
	}
	
	public Tile getTile(int row, int column) {
		if (row < 0 || column < 0 || row > this.getHeightInTile() || column > this.getWidthInTile()) {
			return new Tile(1, 0, 0, tileSize());
		}
		return tile_map[row][column];
	}
}
