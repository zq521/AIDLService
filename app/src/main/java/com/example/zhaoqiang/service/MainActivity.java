package com.example.zhaoqiang.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * 启动服务，停止服务，绑定服务，解除服务
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {
    private Intent myServiceIntent;
    private MyService.MyBinder binder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myServiceIntent = new Intent(this, MyService.class);
        findViewById(R.id.btn_start_service).setOnClickListener(this);
        findViewById(R.id.btn_stop_service).setOnClickListener(this);
        findViewById(R.id.btn_set_service_count_To_100).setOnClickListener(this);
        findViewById(R.id.btn_bind_service).setOnClickListener(this);
        findViewById(R.id.btn_unBind_service).setOnClickListener(this);
        findViewById(R.id.btn_set_bind_service_count_To_100).setOnClickListener(this);
        findViewById(R.id.btn_get_bind_service_count_to_100).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_service:
                startService(myServiceIntent);

                break;
            case R.id.btn_stop_service:
                stopService(myServiceIntent);
                break;
            case R.id.btn_set_service_count_To_100:
                Intent intent = new Intent(this, MyService.class);
                intent.putExtra("count", 100);
                startService(intent);
                break;
            case R.id.btn_bind_service:
                bindService(myServiceIntent, this, BIND_AUTO_CREATE);
                break;
            case R.id.btn_unBind_service:
                binder = null;
                unbindService(this);
                break;
            case R.id.btn_set_bind_service_count_To_100:
                if (binder != null) {
                    binder.setCount(100);
                }
                break;
            case R.id.btn_get_bind_service_count_to_100:
                if (binder != null) {
                    Toast.makeText(this, "" + binder.getConut(), Toast.LENGTH_SHORT).show();
                }
                break;


        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        binder = (MyService.MyBinder) service;

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
