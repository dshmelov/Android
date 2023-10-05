package com.example.mfa;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;

public class HelloActivity extends Activity {
    int i = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helloact);


        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = new Button(this);
        FrameLayout layout = findViewById(R.id.layout);
        TextView textView = findViewById(R.id.text1);
        FrameLayout.LayoutParams vParam = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        button3.setLayoutParams(vParam);
        button3.setText("Сброс");
        layout.addView(button3);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i += 1;
                textView.setText(Integer.toString(i));
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i += 1;
                textView.setText(Integer.toString(i));
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 0;
                textView.setText(Integer.toString(i));
            }
        });
    }

}
