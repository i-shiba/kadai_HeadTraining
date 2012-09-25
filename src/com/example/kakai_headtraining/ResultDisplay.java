package com.example.kakai_headtraining;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultDisplay extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_screen);
		
		this.resultSet();
	}
	
	public void resultSet(){
    	TextView tv  = (TextView) this.findViewById(R.id.textresult_id1);
    	tv.setText("終了しました。");
	}
	
	public void returnKeyOnClick(View v){
		finish();
	}

}
