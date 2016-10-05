package com.itheima.skywheeldemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SkyWheelLayout mSkyWheelLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSkyWheelLayout = (SkyWheelLayout) findViewById(R.id.sky_wheel);
        mSkyWheelLayout.setOnItemClickListener(new SkyWheelLayout.OnItemClickListener() {
            @Override
            public void onItemClick(View v) {
                Toast.makeText(MainActivity.this, v.getTag().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
