package com.itdoesnotmatter;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public abstract class GameObject {
	private String uoid;
	
	private int row;
	private int column;
	private float size;
	
	private FloatBuffer mVerticesBuffer = null;
	//private FloatBuffer mColorBuffer = null;	
	private ShortBuffer mIndicesBuffer = null;
	private FloatBuffer mTextureBuffer = null;

	int mTextureID = -1;
	boolean mShouldUseTexture = false;
	boolean stop;
	public int mIndicesCount;
	
	protected float centerX;
	protected float centerY;
	protected float centerZ;
	
	protected int upCellY;
	protected int downCellY;
	protected int leftCellX;
	protected int rightCellX;
	
	protected boolean upLeft;
	protected boolean downLeft;
	protected boolean upRight;
	protected boolean downRight;
	
	private float[] vertices;
	private float[] texture;
	
	private boolean drawFlag;
	enum MoveDirection {
		
		TOP,
		LEFT,
		RIGHT,
		DOWN;
		
		public static MoveDirection get(int i){
			return values()[i];
		}
	}
	
	public MoveDirection direction;
	protected float[] rgba = new float[] {1.0f, 1.0f, 1.0f, 0.0f };
	
	private boolean isAlive;
	
	public GameObject() {
		isAlive = true;
	}
	
	public String getId() {
		return this.uoid;
	}
	
	public void setId(String id) {
		this.uoid = id;
	}
	
	public float[] getVertices() {
		return vertices;
	}
	
	public float[] getTexture() {
		return texture;
	}
	
	private void calculateCenter(float[] vertices) {
		float [] coords =  vertices;
		float x_bottom = coords[0];
		float y_bottom = coords[1];
		float w = coords[9];
		float h = coords[10];
		this.centerX = x_bottom + ((w - x_bottom)/2);
		this.centerY = y_bottom + ((h - y_bottom)/2);
		
		//Log.d("centerX/centerY", "" + this.centerX + "/" + this.centerY);
	}
	
	public void setVertices(float[] vertices) {
		this.vertices = vertices;
		calculateCenter(vertices);
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		mVerticesBuffer = vbb.asFloatBuffer();
		mVerticesBuffer.put(vertices);
		mVerticesBuffer.position(0);
	}
	
	protected void setIndices(short[] indices) {
		ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		mIndicesBuffer = ibb.asShortBuffer();
		mIndicesBuffer.put(indices);
		mIndicesBuffer.position(0);
		mIndicesCount = indices.length;
	}
	
	protected void setTexture(float[] texture) {
		this.texture = texture;
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(texture.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		mTextureBuffer = byteBuf.asFloatBuffer();
		mTextureBuffer.put(texture);
		mTextureBuffer.position(0);
	}
	
	protected void setColor(int r, int g, int b, int alpha) {
		rgba[0] = (float) r/255;
		rgba[1] = (float) g/255;
		rgba[2] = (float) b/255;
		rgba[3] = (float) alpha/255;
	}
	
	protected void loadTexture(int texture) {
		mTextureID = texture;
		drawFlag = false;
	}
	
	public void draw(GL10 gl) {

		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVerticesBuffer);
		
		if (mTextureID != -1 && mTextureBuffer != null) {
			if (!drawFlag) {
				gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureID);
				drawFlag = true;
			}
			// Point to our buffers
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);	
		}

		gl.glDrawElements(GL10.GL_TRIANGLES, mIndicesCount, GL10.GL_UNSIGNED_SHORT, mIndicesBuffer);
	}
	

	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public float getSize() {
		return size;
	}
	public void setSize(float size) {
		this.size = size;
	}
	
	public float currentCenterX() {
		return this.centerX;
	}

	public float currentCenterY() {
		return this.centerY;
	}

	public float currentLeftBorder() {
		return this.centerX - this.getSize()/2;
	}

	public float currentRightBorder() {
		return this.centerX + this.getSize()/2;
	}

	public float currentTopBorder() {
		return this.centerY + this.getSize()/2;
	}

	public float currentBottomBorder() {
		return this.centerY - this.getSize()/2;
	}
	
	public void drawObject(GL10 gl, float inter) {
		this.draw(gl);
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public void kill() {
		isAlive = false;
	}
}
