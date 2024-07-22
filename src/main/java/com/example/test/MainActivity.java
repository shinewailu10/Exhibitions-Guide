package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    String[] lst = {" Art Gallery"," WWI Exhibition", " Exploring The Space"," Visual Show"};
    int[] exLst = {R.drawable.art, R.drawable.ww1,R.drawable.space,R.drawable.visual};

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_options);
        base b = new base(getApplicationContext(), lst, exLst);
        listView.setAdapter(b);
        listView.setOnItemClickListener((adapterView, view, i, id) -> {
            if (i == 0) {
                Intent intent = new Intent(MainActivity.this, ArtGallery.class);
                startActivity(intent);
            }
            if (i == 1) {
                Intent intent = new Intent(MainActivity.this, WW1.class);
                startActivity(intent);}
            if (i == 2) {
                Intent intent = new Intent(MainActivity.this, Space.class);
                startActivity(intent);}
            if (i == 3) {
                Intent intent = new Intent(MainActivity.this, Visual.class);
                startActivity(intent);}
            else {
                CharSequence text = "position=" + i + "row id=" + id;
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.show();
            }
        });

    }
}
