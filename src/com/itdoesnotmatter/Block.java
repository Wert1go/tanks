package com.itdoesnotmatter;

public class Block {
	public static final int BRICK_RIGHT = 0;
	public static final int BRICK_BOTTOM = 1;
	public static final int BRICK_LEFT = 2;
	public static final int BRICK_TOP = 3;
	public static final int BRICK = 4;
	
	public static final int CONCRET_RIGHT = 5;
	public static final int CONCRET_BOTTOM = 6;
	public static final int CONCRET_LEFT = 7;
	public static final int CONCRET_TOP = 8;
	public static final int CONCRET = 9;
	
	public static final int WATER = 10;
	public static final int GRASS = 11;
	public static final int ICE = 12;
	
	public static final int EMPTY = 13;
	
	private int[][] tiles;
	
	public Block(int type) {
		tiles = new int[2][2];
		switch(type) {
		case BRICK_RIGHT:
			tiles[0][0] = Texture.EMPTY; tiles[0][1] = Texture.BRICK_FULL;
			tiles[1][0] = Texture.EMPTY; tiles[1][1] = Texture.BRICK_FULL;
			break;
		case BRICK_BOTTOM:
			tiles[0][0] = Texture.EMPTY; tiles[0][1] = Texture.EMPTY;
			tiles[1][0] = Texture.BRICK_FULL; tiles[1][1] = Texture.BRICK_FULL;
			break;
		case BRICK_LEFT:
			tiles[0][0] = Texture.BRICK_FULL; tiles[0][1] = Texture.EMPTY;
			tiles[1][0] = Texture.BRICK_FULL; tiles[1][1] = Texture.EMPTY;
			break;
		case BRICK_TOP:
			tiles[0][0] = Texture.BRICK_FULL; tiles[0][1] = Texture.BRICK_FULL;
			tiles[1][0] = Texture.EMPTY; tiles[1][1] = Texture.EMPTY;
			break;
		case BRICK:
			tiles[0][0] = Texture.BRICK_FULL; tiles[0][1] = Texture.BRICK_FULL;
			tiles[1][0] = Texture.BRICK_FULL; tiles[1][1] = Texture.BRICK_FULL;
			break;
		case CONCRET_RIGHT:
			tiles[0][0] = Texture.EMPTY; tiles[0][1] = Texture.CONCRET;
			tiles[1][0] = Texture.EMPTY; tiles[1][1] = Texture.CONCRET;
			break;
		case CONCRET_BOTTOM:
			tiles[0][0] = Texture.EMPTY; tiles[0][1] = Texture.EMPTY;
			tiles[1][0] = Texture.CONCRET; tiles[1][1] = Texture.CONCRET;
			break;
		case CONCRET_LEFT:
			tiles[0][0] = Texture.CONCRET; tiles[0][1] = Texture.EMPTY;
			tiles[1][0] = Texture.CONCRET; tiles[1][1] = Texture.EMPTY;
			break;
		case CONCRET_TOP:
			tiles[0][0] = Texture.CONCRET; tiles[0][1] = Texture.CONCRET;
			tiles[1][0] = Texture.EMPTY; tiles[1][1] = Texture.EMPTY;
			break;
		case CONCRET:
			tiles[0][0] = Texture.CONCRET; tiles[0][1] = Texture.CONCRET;
			tiles[1][0] = Texture.CONCRET; tiles[1][1] = Texture.CONCRET;
			break;
			
		case WATER:
			tiles[0][0] = Texture.WATER; tiles[0][1] = Texture.WATER;
			tiles[1][0] = Texture.WATER; tiles[1][1] = Texture.WATER;
			break;
		case GRASS:
			tiles[0][0] = Texture.GRASS; tiles[0][1] = Texture.GRASS;
			tiles[1][0] = Texture.GRASS; tiles[1][1] = Texture.GRASS;
			break;
		case ICE:
			tiles[0][0] = Texture.ICE; tiles[0][1] = Texture.ICE;
			tiles[1][0] = Texture.ICE; tiles[1][1] = Texture.ICE;
			break;
		case EMPTY:
			tiles[0][0] = Texture.EMPTY; tiles[0][1] = Texture.EMPTY;
			tiles[1][0] = Texture.EMPTY; tiles[1][1] = Texture.EMPTY;
			break;
			
		}
	}
	
	public int getTile(int index) {
		switch(index) {
		case 0:
			return tiles[0][0];
		case 1:
			return tiles[0][1];
		case 2:
			return tiles[1][0];
		case 3:
			return tiles[1][1];
		default:
			return 0;
		}
	}
}
