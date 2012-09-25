package com.example.kakai_headtraining;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.kakai_headtraining.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AnswrDisplay extends Activity{
	
	public Integer qNum;
	public Integer qTime;
	public Integer qMemory;
	public ArrayList<String>  qQuest = new ArrayList<String>();
	public ArrayList<Integer> qAnswr = new ArrayList<Integer>();
	public Integer setCount;
	public Integer tryCount;
	public Integer cmpCount;
	public Handler handler = new Handler();
	public Boolean running;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.answr_screen);
		
		Intent adIntent = getIntent();
    	this.qNum    = adIntent.getIntExtra("qNum" , 0);
    	this.qTime   = adIntent.getIntExtra("qTime", 0);
    	this.qMemory = adIntent.getIntExtra("qMemory", 0);
		this.qQuest  = adIntent.getStringArrayListExtra("qQuest");
		this.qAnswr  = adIntent.getIntegerArrayListExtra("qAnswr");
		
    	this.setCount = 0;
    	this.cmpCount = 0;
		this.readPreferences();
		this.questSet();
	}
	
	
	@Override
	protected void onStop() {
		super.onStop();
		this.writePreferences();
	}


	public void writePreferences(){
    	this.running = false;
		SharedPreferences prefs = getSharedPreferences("HeadTraining", MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("qNum", this.qNum);
		editor.putInt("qTime", this.qTime);
		editor.putInt("qMemory", this.qMemory);
    	editor.putInt("qQuestCnt", this.qQuest.size());
		for(Integer i = 0; i < this.qQuest.size(); i++){
			editor.putString("qQuest" + i, this.qQuest.get(i));
		}
		for(Integer i = 0; i < this.qAnswr.size(); i++){
			editor.putInt("qAnswr" + i, this.qAnswr.get(i));
		}
		editor.putInt("setCount", this.setCount);
		Log.d("tryCount", this.tryCount.toString());
		editor.putInt("tryCount", this.tryCount);
		editor.putInt("cmpCount", this.cmpCount);
		editor.putBoolean("running", this.running);
    	editor.putString("strDisplay", 
    		((TextView) this.findViewById(R.id.textplace_edit)).getText().toString());
		editor.commit();
	}
	public void readPreferences(){
		SharedPreferences prefs = getSharedPreferences("HeadTraining", MODE_PRIVATE);
		this.qNum = prefs.getInt("qNum", 0);
		this.qTime = prefs.getInt("qTime", 0);
		this.qMemory = prefs.getInt("qMemory", 0);
		Integer qQuestCnt = prefs.getInt("qQuestCnt", 0);
		for(Integer i = 0; i < qQuestCnt; i++){
			this.qQuest.add(prefs.getString("qQuest" + i, ""));
		}
		for(Integer i = 0; i < qQuestCnt; i++){
			this.qAnswr.add(prefs.getInt("qAnswr" + i, 0));
		}
		this.setCount = prefs.getInt("setCount", 0);
		this.tryCount = prefs.getInt("tryCount", 0);
		Log.d("tryCountread", this.tryCount.toString());
		this.cmpCount = prefs.getInt("cmpCount", 0);
		this.running = prefs.getBoolean("running", true);
		((TextView) this.findViewById(R.id.textplace_edit))
			.setText(prefs.getString("strDisplay", ""));
		
		SharedPreferences.Editor editor = prefs.edit();
		editor.clear().commit();
	}
	
	public void nextResult(){
		Log.d("next", "aaa");
    	Intent rsIntent = new Intent(AnswrDisplay.this, ResultDisplay.class);
//    	adIntent.putExtra("qNum", ad.qNum);
//    	adIntent.putStringArrayListExtra("qQuest", ad.qQuest);
//    	adIntent.putIntegerArrayListExtra("qAnswr", ad.qAnswr);
    	
    	startActivity(rsIntent);
    	finish();
	}
	
	public void questSet(){
		TextView tv3 = (TextView) this.findViewById(R.id.textplace_id3);
     	TextView tv2 = (TextView) this.findViewById(R.id.textplace_id2);
     	TextView tv1 = (TextView) this.findViewById(R.id.textplace_id1);
    	TextView tv  = (TextView) this.findViewById(R.id.textplace_edit);
		if(this.qNum > this.setCount){
    		tv3.setText(this.qQuest.get(this.setCount));
		}else{
			tv3.setText("あと" + ((this.qNum + this.qMemory) - this.setCount) + "問！！");
		}
		if(this.qNum > (this.setCount + 1)){
    		tv2.setText(this.qQuest.get(this.setCount + 1));
		}else{
			tv2.setText("");
		}
		if(this.qNum > (this.setCount + 2)){
    		tv1.setText(this.qQuest.get(this.setCount + 2));
		}else{
			tv1.setText("");
		}
		tv.setText("");
		this.answrCount();
	}
	public void answrCount(){
		nextTime(); // 次の問題への待ち時間処理
		
//		this.tryCount = 0;
		this.running  = true;
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(tryCount <= qTime && running == true){
					loadTime(); // 時間を計る処理
				}
				if(running){
					handler.post(new Runnable() {
						@Override
						public void run() {
							timeUp();
						}
					});
				}
			}
		}).start();			
	}
	
	
	private void loadTime() {
		try {
			Thread.sleep(1);
		} catch (InterruptedException ie) {
		}
		this.tryCount++;
	}	
	private void nextTime() {
		try {
			Thread.sleep(10);
		} catch (InterruptedException ie) {
		}
	}	
    public void numKeyOnClick(View v){
    	Button   button = (Button)v;
    	TextView tv     = (TextView) this.findViewById(R.id.textplace_edit);
    	String   str    = button.getText().toString();
    	String   strtv  = tv.getText().toString();
 		if(strtv.length() > 0){
 	 		tv.setText(strtv + str);
 		}else{
 	 		tv.setText(str);
 		}
    }
    public void fixOnClick(View v){
    	TextView tv     = (TextView) this.findViewById(R.id.textplace_edit);
    	String   strtv  = tv.getText().toString();
 		if(strtv.length() > 0){
 	    	this.running = false;
 	    	this.answrCheck();
 	    	this.setCount++;
 	    	if(this.setCount - this.qMemory < qNum){
 	    		this.tryCount = 0;
 	    		this.questSet();
 	    	}else{
 	    		nextResult();
 	    	}
 		}
 	}
    public void timeUp(){
    	this.answrCheck();
    	this.setCount++;
    	if(this.setCount - this.qMemory < qNum){
    		this.tryCount = 0;
    		this.questSet();
    	}else{
    		nextResult();
    	}
    }
    public void answrCheck(){
    	TextView tv1 = (TextView) this.findViewById(R.id.textplace_edit);
    	TextView tv2 = (TextView) this.findViewById(R.id.textview_id3);
    	
    	Integer num;
    	if(tv1.getText().toString().length() > 0){
    		num = Integer.parseInt(tv1.getText().toString());
    	}else{
    		num = -1;
    	}
    	if(setCount - this.qMemory >= 0){
    		if(qAnswr.get(this.setCount - this.qMemory) == num){
        		this.cmpCount++;
        		tv2.setText(cmpCount.toString());
    		}
    	}
    }
}
