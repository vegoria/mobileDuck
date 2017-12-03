package com.example.sylwia.mobileduck.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sylwia.mobileduck.R;
import com.example.sylwia.mobileduck.db.Manager;
import com.example.sylwia.mobileduck.db.tables.Item;

import java.util.ArrayList;

/**
 * Created by Michal on 03.12.2017.
 */

public class ShopListAdapter extends ArrayAdapter<Item>
{
    private ArrayList<Item> dataSet;
    Context mContext;
    private Manager manager;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtQuantity;
        CheckBox status;
    }

    public ShopListAdapter(ArrayList<Item> data, Context context)
    {
        super(context, R.layout.shoplist_row_item, data);
        this.dataSet = data;
        this.mContext=context;
        manager = new Manager();
    }

   /* @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        DataModel dataModel=(DataModel)object;

        switch (v.getId())
        {
            case R.id.item_info:
                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
    }*/

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final Item item = getItem(position);
        final ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.shoplist_row_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.shoplist_item_name);
            viewHolder.txtQuantity = (TextView) convertView.findViewById(R.id.shoplist_item_quantity);
            viewHolder.status = (CheckBox) convertView.findViewById(R.id.shoplist_item_status);

            viewHolder.status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Thread thread = new Thread(new Runnable()
                    {
                        @Override
                        public void run(){
                            if (viewHolder.status.isChecked())
                            {
                                item.setStatus(1);
                            }
                            else
                            {
                                item.setStatus(0);
                            }

                            //TODO
                            manager.updateItem(item);
                        }

                    });
                    thread.start();
                    try
                    {
                        thread.join();
                        //Toast.makeText(getContext(), String.valueOf(item.getStatus()), Toast.LENGTH_LONG).show();
                    }
                    catch(InterruptedException e)
                    {
                        Toast.makeText(getContext(), "Cannot update item", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }


        lastPosition = position;

        viewHolder.txtName.setText(item.getName());
        viewHolder.txtQuantity.setText(String.valueOf(item.getQuantity()));

        if(item.getStatus() == 0) {
            viewHolder.status.setChecked(false);
        }
        else {
            viewHolder.status.setChecked(true);
        }

        return convertView;
    }
}
