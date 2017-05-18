package com.example.bindservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {
    private Intent myService;
    private IRemoteBinder binder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myService = new Intent();
        myService.setComponent(new ComponentName("com.example.bindservice", "com.example.bindservice.MyServiceAIDL"));
        findViewById(R.id.btn_bind_remote_service).setOnClickListener(this);
        findViewById(R.id.btn_unbind_remote_service).setOnClickListener(this);
        findViewById(R.id.btn_set_count_to_100).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bind_remote_service:
                bindService(myService, this, BIND_AUTO_CREATE);

                break;
            case R.id.btn_unbind_remote_service:
                binder=null;
                unbindService(this);
                break;
            case R.id.btn_set_count_to_100:
                   if (binder!=null){
                       try {
                           binder.setCount(100);

                       } catch (RemoteException e) {
                           e.printStackTrace();
                       }
                   }
                break;
        }

    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        binder = IRemoteBinder.Stub.asInterface(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
