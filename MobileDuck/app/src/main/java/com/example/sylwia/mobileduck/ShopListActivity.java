package com.example.sylwia.mobileduck;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sylwia.mobileduck.adapters.ShopListAdapter;
import com.example.sylwia.mobileduck.db.Manager;
import com.example.sylwia.mobileduck.db.tables.Item;
import com.example.sylwia.mobileduck.db.tables.ShoppingList;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ShopListActivity extends AppCompatActivity {

    private long listId;
    private boolean ownList;
    private ShoppingList shoppingList;
    private List<Item> itemsList;
    private Manager manager;
    private ShopListAdapter adapter;
    private ListView itemsListView;
    private TextView listNameView;
    private Button addItemButton;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        Intent intent = getIntent();
        this.listId = intent.getLongExtra("ListId", 0);
        this.ownList = intent.getBooleanExtra("OwnList", true);
        itemsListView = (ListView)findViewById(R.id.shopListView);
        listNameView = (TextView)findViewById(R.id.shoplist_list_name);
        addItemButton = (Button)findViewById(R.id.add_item_button);
        manager = new Manager();

        downloadItems();
        populateList();
        prepareReceiver();

        listNameView.setText(shoppingList.getName());
        if(!ownList)
            addItemButton.setVisibility(View.INVISIBLE);

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        this.unregisterReceiver(receiver);
    }

    private void downloadItems()
    {
        final Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run(){
                shoppingList = manager.getShoppingList(listId);
                itemsList = manager.getItemsFromShoppingList(shoppingList);
            }

        });
        thread.start();
        try
        {
            thread.join();
        }
        catch(InterruptedException e)
        {
            Toast.makeText(this, getString(R.string.cannot_download_item), Toast.LENGTH_SHORT).show();
        }
    }

    private void populateList()
    {
        ArrayList<Item> array = new ArrayList<>();
        array.addAll(itemsList);

        adapter = new ShopListAdapter(array, this);
        itemsListView.setAdapter(adapter);
    }

    public void prepareReceiver()
    {
        filter = new IntentFilter();
        filter.addAction("LIST_UPDATED");
        this.registerReceiver(receiver, filter);

        // Przekazujesz mu id shoplisty
        Intent intent = new Intent(this, DbSyncService.class);
        intent.putExtra("list", shoppingList.getId());
        this.startService(intent);

        //TODO
        //context.stopService(intent);
        //zatrzymac serwis? Do przetestowania!
    }

    public void goToAddNewItemActivity(View view)
    {
        Intent newItemView = new Intent(this, NewItemActivity.class);
        newItemView.putExtra("shoppingListIdExtra", shoppingList.getId());
        startActivity(newItemView);
    }
    public void UpdateList()
    {
        // Wykonywane w momencie zmiany daty w bazie danych
        // Tu zrobic update listy
        Toast.makeText(this,"UPDATE! Some changes in list...", Toast.LENGTH_LONG).show();
        downloadItems();
        populateList();
    }
}
