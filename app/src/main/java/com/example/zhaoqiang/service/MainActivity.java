package com.example.zhaoqiang.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {
    private Intent myServiceIntent;
    private MyService.MyBinder bind;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myServiceIntent = new Intent(this, MyService.class);
        findViewById(R.id.btnstartService).setOnClickListener(this);
        findViewById(R.id.btnstoptService).setOnClickListener(this);
        findViewById(R.id.btnsetServiceCountTo100).setOnClickListener(this);
        findViewById(R.id.btnBindService).setOnClickListener(this);
        findViewById(R.id.btnunBindService).setOnClickListener(this);
        findViewById(R.id.btnSetCountTo100).setOnClickListener(this);
        findViewById(R.id.get_count_to_100).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnstartService:
                startService(myServiceIntent);

                break;
            case R.id.btnstoptService:
                stopService(myServiceIntent);
                break;
            case R.id.btnsetServiceCountTo100:
                Intent intent = new Intent(this, MyService.class);
                intent.putExtra("count", 100);
                startService(intent);
                break;
            case R.id.btnBindService:
                bindService(myServiceIntent, this, BIND_AUTO_CREATE);
                break;
            case R.id.btnunBindService:
                bind=null;
                unbindService(this);
                break;
            case R.id.btnSetCountTo100:
                if (bind!=null){
                    bind.setCount(100);
                }
                break;
            case R.id.get_count_to_100:
                if (bind!=null) {
                    Toast.makeText(this,""+bind.getConut(), Toast.LENGTH_SHORT).show();
                }
                break;


        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        bind= (MyService.MyBinder) service;

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
