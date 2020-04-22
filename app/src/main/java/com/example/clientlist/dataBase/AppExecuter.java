package com.example.clientlist.dataBase;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;

public class AppExecuter {

    private static final Object LOCK = new Object();
    private static AppExecuter instanceExecutor;
    private final Executor discIO;
    private final Executor mainIO;
    private final Executor networkIO;

    public Executor getDiscIO() {
        return discIO;
    }

    public Executor getMainIO() {
        return mainIO;
    }

    public Executor getNetworkIO() {
        return networkIO;
    }

    public AppExecuter(Executor discIO, Executor mainIO, Executor networkIO) {
        this.discIO = discIO;
        this.mainIO = mainIO;
        this.networkIO = networkIO;
    }

    public static AppExecuter getInstance(){
        if(instanceExecutor==null){
            synchronized (LOCK){
                instanceExecutor = new AppExecuter(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),new MainThreadHandler());
            }
        }
        return instanceExecutor;
    }

    private static class MainThreadHandler implements Executor{

        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);

        }
    }

}
