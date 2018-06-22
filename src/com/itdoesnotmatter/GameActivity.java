package com.itdoesnotmatter;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity {
	
	private GameView mGameView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extra = this.getIntent().getExtras();
        boolean mode = extra.getBoolean(Global.MAP_MODE);
        mGameView = new GameView(this, mode);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(mGameView);
    }
    
	@Override
	public boolean onTouchEvent(MotionEvent event) {
			mGameView.dispatcher(event);
		return super.onTouchEvent(event);
	}
}