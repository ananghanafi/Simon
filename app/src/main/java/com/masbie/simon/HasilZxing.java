package com.masbie.simon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HasilZxing extends AppCompatActivity {
    TextView textView;
    Intent intent ;
    String hh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_zxing);
        textView = (TextView) findViewById(R.id.cobaText);
        hh = getIntent().getStringExtra("ambilNilai");
        textView.setText(hh);
    }
}
