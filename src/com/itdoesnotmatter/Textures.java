package com.itdoesnotmatter;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

public class Textures {
	
	static int[] textures;
	
	/******** NEED TO KNOW **********/
	private static int textureSize = 64;
	
	private static float textureW;
	private static float textureH;
	
	public Textures(Context context, GL10 gl) {
		Bitmap textureMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.texture_map);

		textures = new int[1];
		
		gl.glGenTextures(1, textures, 0);
		
	
			

		int width = 1024;
		int height = 128;
		
		Log.d("bitmap", "w: " + width + "h: " + height);
		
		textureW = 1.0f / (width/textureSize);
		textureH = 1.0f / (height/textureSize);
		
		Log.d("textureW", "" + textureW);
		Log.d("textureH", "" + textureH);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR); 
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, textureMap, 0);	
	}
	
	public static int loadTextureImage() {
		return textures[0];
	}
	
	public static float[] getTexture(int id) {
		//Мега хаки, лень рисовать атлас
		if (id == Texture.BLOCK) {
			id = Texture.EMPTY;
		}
		int rowIndex = (int) Math.floor(id/16);
		int col = id;
		if (col > 15) {
			col = id - 16;
		}
		float[] texture = {
				
				
				textureW*col, textureH + (rowIndex * textureH),
				textureW + textureW*col, textureH + (rowIndex * textureH),
				textureW*col, 0.0f + (rowIndex * textureH),
				textureW + textureW*col, 0.0f + (rowIndex * textureH)
		};
		
		//Log.d("getTexture", "id: " + id + " texture[0]: " + texture[0]);
		return texture;
	}

}
