package com.example.demo002;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;




public class bbs extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener{
    private TextView textView4;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs);

        textView = findViewById(R.id.textView);
        textView.setOnClickListener(this);
        textView.setOnLongClickListener(this);
        textView4 = findViewById(R.id.textView4);
        textView4.setOnLongClickListener(this);
        textView4.setOnLongClickListener(this);

        textView4.setMovementMethod(new ScrollingMovementMethod());
    }

    private String[] a = {"哈哈哈哈哈哈哈", "嘻嘻嘻嘻嘻嘻嘻", "芜湖芜湖芜湖芜湖芜湖", "飞飞飞飞飞飞飞", "就这就这就这就这",};

    public void onClick(View v) {
        if (v.getId() == R.id.textView || v.getId() == R.id.textView4) ;
        int random = (int) (Math.random() * 10 )%5;

        String newStr = String.format("%s\n%s %s", textView4.getText().toString(),a[random]);
        textView4.setText(newStr);

    }

    public boolean onLongClick(View v) {
        if (v.getId() == R.id.textView4 || v.getId() == R.id.textView) {
            textView4.setText("");
        }
        return true;
    }
}