package com.example.kakai_headtraining;

import com.example.kakai_headtraining.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    public void startIdOnClick(View v){
    	
    	final CheckBox addchk = (CheckBox)this.findViewById(R.id.addcalc_check);
    	final CheckBox subchk = (CheckBox)this.findViewById(R.id.subcalc_check);
    	final CheckBox milchk = (CheckBox)this.findViewById(R.id.milcalc_check);
    	final CheckBox divchk = (CheckBox)this.findViewById(R.id.divcalc_check);
    	
    	if((addchk.isChecked() == true)
    	 ||(subchk.isChecked() == true)
    	 ||(milchk.isChecked() == true)
    	 ||(divchk.isChecked() == true))
    	{
	    	Spinner spinner1 = (Spinner)this.findViewById(R.id.memory_spn);
	    	Spinner spinner2 = (Spinner)this.findViewById(R.id.speed_spn);
	    	Spinner spinner3 = (Spinner)this.findViewById(R.id.place_spn);
	    	Spinner spinner4 = (Spinner)this.findViewById(R.id.question_spn);
	    	
	    	Intent qbIntent = new Intent(MainActivity.this, QuestBecoming.class);
	    	qbIntent.putExtra("addchk", addchk.isChecked());
	    	qbIntent.putExtra("subchk", subchk.isChecked());
	    	qbIntent.putExtra("milchk", milchk.isChecked());
	    	qbIntent.putExtra("divchk", divchk.isChecked());
	    	qbIntent.putExtra("memoryNum", spinner1.getSelectedItemPosition());
	    	qbIntent.putExtra("speedNum" , spinner2.getSelectedItemPosition());
	    	qbIntent.putExtra("placeNum" , spinner3.getSelectedItemPosition());
	    	qbIntent.putExtra("questNum" , spinner4.getSelectedItemPosition());
	    	startActivity(qbIntent);
		
    	}
    }
}
