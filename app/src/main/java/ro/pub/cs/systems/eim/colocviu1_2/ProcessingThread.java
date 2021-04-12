package ro.pub.cs.systems.eim.colocviu1_2;

import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

import java.util.Date;

public class ProcessingThread extends Thread{
    int sum = 0;
    private Context context = null;

    public ProcessingThread(Context context, int sum)
    {
        this.context = context;
        this.sum = sum;
    }
    @Override
    public void run() {
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has started! PID: " + Process.myPid() + " TID: " + Process.myTid());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        Intent intent = new Intent();
        intent.setAction(Constants.ACTIONTYPE);
        intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA,
                new Date(System.currentTimeMillis()) + " sum is " + sum);
        context.sendBroadcast(intent);
    }

    public void stopThread() {
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has stopped!");
    }

}
