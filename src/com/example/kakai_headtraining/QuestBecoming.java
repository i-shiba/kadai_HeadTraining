package com.example.kakai_headtraining;

import java.util.ArrayList;
import java.util.List;

import com.example.kakai_headtraining.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class QuestBecoming extends MainActivity{
	public boolean addchk;
	public boolean subchk;
	public boolean milchk;
	public boolean divchk;
	public Integer memoryNum;
	public Integer speedNum;
	public Integer placeNum;
	public Integer questionNum;
	public String  calcQuest;
	public Integer calcAns;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading_screen);
		
		Intent qbIntent = getIntent();
    	this.addchk      = qbIntent.getBooleanExtra("addchk", true);
    	this.subchk      = qbIntent.getBooleanExtra("subchk", true);
    	this.milchk      = qbIntent.getBooleanExtra("milchk", true);
    	this.divchk      = qbIntent.getBooleanExtra("divchk", true);
    	this.memoryNum   = qbIntent.getIntExtra("memoryNum", 0);
    	this.speedNum    = qbIntent.getIntExtra("speedNum" , 0);
    	this.placeNum    = qbIntent.getIntExtra("placeNum" , 0);
    	this.questionNum = qbIntent.getIntExtra("questNum" , 0);
    	this.speedNum    = 10000 - (this.speedNum * 1000);

		Integer eNum;
    	
		AnswrDisplay ad = new AnswrDisplay();
		ad.qNum = (this.questionNum + 1) * 10;
		Integer i = 0;
		while(i < ad.qNum){
			eNum = (int)(Math.random() * 4);
			if(eNum == 0 && this.addchk == true){
				this.addCalc();
				ad.qQuest.add(this.calcQuest);
				ad.qAnswr.add(this.calcAns);
				i++;
			}
			if(eNum == 1 && this.subchk == true){
				this.subCalc();
				ad.qQuest.add(this.calcQuest);
				ad.qAnswr.add(this.calcAns);
				i++;
			}
			if(eNum == 2 && this.milchk == true){
				this.milCalc();
				ad.qQuest.add(this.calcQuest);
				ad.qAnswr.add(this.calcAns);
				i++;
			}
			if(eNum == 3 && this.divchk == true){
				this.divCalc();
				ad.qQuest.add(this.calcQuest);
				ad.qAnswr.add(this.calcAns);
				i++;
			}
		}
		ad.qTime   = this.speedNum;
		ad.qMemory = this.memoryNum;
    	Intent adIntent = new Intent(QuestBecoming.this, AnswrDisplay.class);
    	adIntent.putExtra("qNum"    , ad.qNum);
    	adIntent.putExtra("qTime"   , ad.qTime);
    	adIntent.putExtra("qMemory" , ad.qMemory);
    	adIntent.putStringArrayListExtra("qQuest", ad.qQuest);
    	adIntent.putIntegerArrayListExtra("qAnswr", ad.qAnswr);
    	
    	SharedPreferences prefs = getSharedPreferences("HeadTraining", MODE_PRIVATE);
    	SharedPreferences.Editor editor = prefs.edit();
    	editor.putInt("qNum", ad.qNum);
    	editor.putInt("qTime", ad.qTime);
    	editor.putInt("qMemory", ad.qMemory);
    	editor.putInt("qQuestCnt", ad.qQuest.size());
    	for(Integer j = 0; j < ad.qQuest.size(); j++){
    		editor.putString("qQuest" + j, ad.qQuest.get(j));
    	}
    	for(Integer j = 0; j < ad.qAnswr.size(); j++){
    		editor.putInt("qAnswr" + j, ad.qAnswr.get(j));
    	}
    	editor.putInt("setCount", 0);
    	editor.putInt("tryCount", 0);
    	editor.putInt("cmpCount", 0);
    	editor.putBoolean("running", true);
        editor.putString("strDisplay", "");
    	editor.commit();
    	
    	startActivity(adIntent);
		finish();
	}
	
	public void addCalc(){
		Integer iMax = 0;
		if(this.placeNum == 0){
			iMax = 10;
		}
		if(this.placeNum == 1){
			iMax = 100;
		}
		if(this.placeNum == 2){
			iMax = 1000;
		}
		if(this.placeNum == 3){
			iMax = 10000;
		}
		if(this.placeNum == 4){
			iMax = 100000;
		}
		Integer num1   = (int)(Math.random() * iMax);
		Integer num2   = (int)(Math.random() * iMax);
		this.calcQuest = (num1 + " ＋ " + num2 + " = ");
		this.calcAns   = num1 + num2;
	}
	public void subCalc(){
		Integer iMax = 0;
		if(this.placeNum == 0){
			iMax = 10;
		}
		if(this.placeNum == 1){
			iMax = 100;
		}
		if(this.placeNum == 2){
			iMax = 1000;
		}
		if(this.placeNum == 3){
			iMax = 10000;
		}
		if(this.placeNum == 4){
			iMax = 100000;
		}
		Integer num1   = (int)(Math.random() * iMax);
		Integer num2;
		do{
			num2       = (int)(Math.random() * iMax);
		}while(num1 < num2);
		this.calcQuest = (num1 + " － " + num2 + " = ");
		this.calcAns   = num1 - num2;
	}
	public void milCalc(){
		Integer iMax = 0;
		if(this.placeNum == 0){
			iMax = 10;
		}
		if(this.placeNum == 1){
			iMax = 100;
		}
		if(this.placeNum == 2){
			iMax = 1000;
		}
		if(this.placeNum == 3){
			iMax = 10000;
		}
		if(this.placeNum == 4){
			iMax = 100000;
		}
		Integer num1   = (int)(Math.random() * iMax);
		Integer num2   = (int)(Math.random() * iMax);
		this.calcQuest = (num1 + " × " + num2 + " = ");
		this.calcAns   = num1 * num2;
	}
	public void divCalc(){
		Integer iRandom = 0;
		Integer iMax    = 0;
		ArrayList<Integer> num3 = new ArrayList<Integer>();
		if(this.placeNum == 0){
			iRandom = 4;
			iMax    = 9;
		}
		if(this.placeNum == 1){
			iRandom = 49;
			iMax    = 99;
		}
		if(this.placeNum == 2){
			iRandom = 499;
			iMax    = 999;
		}
		if(this.placeNum == 3){
			iRandom = 4999;
			iMax    = 9999;
		}
		if(this.placeNum == 4){
			iRandom = 49999;
			iMax    = 99999;
		}
		
		Integer num2   = ((int)(Math.random() * iRandom)) + 1;
		for(Integer i = 1;(i * num2) < iMax; i++ ){
			num3.add(i * num2);
		}
		Integer num4   = (int)(Math.random() * num3.size());
		Integer num1   = num3.get(num4);
		this.calcQuest = (num1 + " ÷ " + num2 + " = ");
		this.calcAns   = num1 / num2;
	}


}
