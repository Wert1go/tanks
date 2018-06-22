package com.itdoesnotmatter;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class GameView extends GLSurfaceView {
	GameRenderer mRenderer;
	
	public GameView(Context context, boolean random) {
		super(context);
		mRenderer = new GameRenderer(context, random);
		//setEGLConfigChooser(8, 8, 8, 8, 0, 0);
		setEGLConfigChooser(false); // 16 bit, no z-buffer
		//getHolder().setFormat(PixelFormat.RGBA_8888);
		setRenderer(mRenderer);
	}
	
	public void dispatcher(MotionEvent event) {
		Tank tank = mRenderer.tank;
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			int action = mRenderer.mInterface.dispatcher(event.getX(), event.getY());
			if (action == GUI.FIRE) {
				tank.shoot();
			}
			
			switch(action) {
			case GUI.LEFT:
				tank.turnLeft();
				tank.moving(true);
				break;
			case GUI.RIGHT:
				tank.turnRight();
				tank.moving(true);
				break;
			case GUI.TOP:
				tank.turnUp();
				tank.moving(true);
				break;
			case GUI.DOWN:
				tank.turnDown();
				tank.moving(true);
				break;
			}
			
			
			
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			tank.moving(false);
		}
	}
}
