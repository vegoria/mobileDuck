package com.example.sylwia.mobileduck;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.example.sylwia.mobileduck.db.Manager;
import com.example.sylwia.mobileduck.db.dao.readDao.ItemReadDao;
import com.example.sylwia.mobileduck.db.dao.readDao.ShoppingListReadDao;
import com.example.sylwia.mobileduck.db.tables.ShoppingList;

import java.util.Date;

public class DbSyncService extends Service
{
    private static long listId;
    private Date lastUpdate;
    private Manager dbManager;
    private boolean run;
    Thread thread;

    public DbSyncService()
    {
        dbManager = new Manager();
        lastUpdate = new Date();
        run = true;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId)
    {
        listId = intent.getLongExtra("list", 0);
        thread = new Thread(new Runnable() {
            @Override
            public void run()
            {
                while(run)
                {
                    CheckDatabaseStatus();
                    SystemClock.sleep(5000);
                }
                stopSelf();
            }
        });

        thread.start();
        //return START_STICKY;
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        run = false;
        thread.interrupt();
        Log.i("a", "onDestroy() , service stopped...");
    }

    private void CheckDatabaseStatus()
    {
        try
        {
                ShoppingList downloadedList = dbManager.getShoppingList(listId);

                if (downloadedList.getModificationDate().compareTo(lastUpdate) != 0)
                {
                    lastUpdate = downloadedList.getModificationDate();
                    Intent broadCastIntent = new Intent();
                    broadCastIntent.setAction("LIST_UPDATED");
                    Log.i("a", "wysylam broadcast : " + lastUpdate.toString());
                    sendBroadcast(broadCastIntent);
                }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
