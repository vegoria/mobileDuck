package com.example.sylwia.mobileduck;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;

/**
 * Created by Michal on 29.11.2017.
 */

public class ShoppingListReceiverTest
{
    //Tylko na potrzeby testu dla wywolania Toast
    private Context context;

    public ShoppingListReceiverTest(Context context)
    {
        this.context = context;

        filter = new IntentFilter();
        filter.addAction("LIST_UPDATED");
        context.registerReceiver(receiver, filter);

        // Przekazujesz mu id shoplisty
        Intent intent = new Intent(context, DbSyncService.class);
        intent.putExtra("list", 30);
        context.startService(intent);

        context.stopService(intent);
        //zatrzymac serwis? Do przetestowania!
    }

    private IntentFilter filter;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("LIST_UPDATED"))
            {
                Log.i("a", "odebralem broadcast");
                UpdateList();
            }
        }
    };

    public void UpdateList()
    {
        // Wykonywane w momencie zmiany daty w bazie danych
        // Tu zrobic update listy
        Toast.makeText(context,"UPDATE!!!!", Toast.LENGTH_LONG).show();
    }
}
