package com.example.demo003;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    private TextView textView2;
    private  boolean isPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView2=findViewById(R.id.text2);
        textView2.setOnClickListener(this);
        textView2.requestFocus();

    }
    public void onClick(View v){
        if(v.getId()==R.id.text2){
            isPaused=!isPaused;
            if(isPaused){
                textView2.setFocusable(false);
                textView2.setFocusableInTouchMode(false);}
            else{
                textView2.setFocusable(true);
                textView2.setFocusableInTouchMode(true);
                textView2.requestFocus();
            }
        }
    }
}


