package com.itdoesnotmatter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartActivity extends Activity implements OnClickListener {
	private Button mCustom;
	private Button mRandom;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        
        mCustom = (Button) findViewById(R.id.custom);
        mRandom = (Button) findViewById(R.id.random);
        mRandom.setOnClickListener(this);
        mCustom.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch(v.getId()) {
		case R.id.custom:
			intent.putExtra(Global.MAP_MODE, false);
			break;
		case R.id.random:
			intent.putExtra(Global.MAP_MODE, true);
			break;
		}
		intent.setClass(this, GameActivity.class);
		startActivity(intent);
		
	}
    

}
