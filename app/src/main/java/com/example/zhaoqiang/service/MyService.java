package com.example.zhaoqiang.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class MyService extends Service {
    private  boolean running=false;
    private int conut=0;
    public MyService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends Binder{

        public void setCount(int count){
            MyService.this.setConut(count);
        }
        public int getConut(){
          return  MyService.this.getConut();
        }

    }
    @Override
    public void onCreate() {
        super.onCreate();
        running=true;
        new Thread(){
            @Override
            public void run() {
                super.run();
                while (running){
                    try {
                        sleep(1000);
                        conut++;
                        System.out.println(conut);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        }.start();
        System.out.println("My Service.onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("My Service.onStartCommand");
        int count = intent.getIntExtra("count", 0);
        if (count!=0){
            this.conut=count;
        }
        return super.onStartCommand(intent, flags, startId);

    }

    public int getConut() {
        return conut;
    }

    public void setConut(int conut) {
        this.conut = conut;
    }

    @Override
    public void onDestroy() {
        System.out.println("My Service.onDestroy");
        super.onDestroy();
        running=false;
    }

}
