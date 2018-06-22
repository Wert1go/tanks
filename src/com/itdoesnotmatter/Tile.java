package com.itdoesnotmatter;


public class Tile extends GameObject {

	private int mType;
	
	private boolean mSolid;
	
	public Tile(int type, int row, int column, float size) {
		super();
		this.setType(type);

		switch(type) {
		case Texture.EMPTY:
			mSolid = false;
			break;
		case Texture.BRICK_FULL:
			mSolid = true;
			break;
		case Texture.CONCRET:
			mSolid = true;
			break;
		case Texture.GRASS:
			mSolid = false;
			break;
		case Texture.WATER:
			mSolid = true;
			break;
		case Texture.BLOCK:
			mSolid = true;
			break;
		}

		this.setRow(row);
		this.setColumn(column);
		this.setSize(size);
	}
	
	public boolean compareType(int withType) {
		if (this.getType() == withType) {
			return true;
		}
		return false;
	}
	
	public int getType() {
		return this.mType;
	}
	
	public void setType(int type) {
		this.mType = type;
	}
	
	public boolean isSolid() {
		return mSolid;
	}
}