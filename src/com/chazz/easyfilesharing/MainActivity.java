package com.chazz.easyfilesharing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import javax.xml.transform.Source;

public class MainActivity extends Activity
{
	private EFSClient efsClient;
	private TextView textView;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		textView = new TextView(this);
		
		efsClient = new EFSClient(this);
		setContentView(textView);
		
    }
	
	public void writeDebug(String msg){
		textView.setText(msg);
		
	}
	
	
}
