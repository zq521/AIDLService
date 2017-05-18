package com.example.bindservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * //AIDL进程间的通信
 */
public class MyServiceAIDL extends Service {
    private boolean running = false;
    private int conut = 0;

    public MyServiceAIDL() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }


    public class MyBinder extends IRemoteBinder.Stub {

        @Override
        public void setCount(int count) throws RemoteException {
            MyServiceAIDL.this.setConut(count);
        }

        @Override
        public void getCount() throws RemoteException {
            MyServiceAIDL.this.getConut();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        running = true;
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (running) {
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
        if (count != 0) {
            this.conut = count;
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
        running = false;
    }

}
