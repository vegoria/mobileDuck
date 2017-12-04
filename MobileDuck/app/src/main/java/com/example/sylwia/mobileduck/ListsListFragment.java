package com.example.sylwia.mobileduck;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;

import com.example.sylwia.mobileduck.db.Manager;
import com.example.sylwia.mobileduck.db.tables.ShoppingList;
import com.example.sylwia.mobileduck.db.tables.User;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListsListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListsListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ListView groupView;
    private Manager dataManager;
    private User user;
    private List<ShoppingList> userShoppingList;
    private  ArrayAdapter<ShoppingList> adapter;

    public ListsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListsListFragment newInstance(String param1, String param2) {
        ListsListFragment fragment = new ListsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        dataManager=new Manager();
        SharedPreferences sharedPref = getActivity().getApplicationContext()
                .getSharedPreferences(
                        getString(R.string.preference_file_key),
                        Context.MODE_PRIVATE);
        final String userLogin = sharedPref.getString(getString(R.string.preference_user_login), null);

        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run(){
                user=dataManager.getUserByLogin(userLogin);
            }

        });
        thread.start();
        try
        {
            thread.join();
        }
        catch(InterruptedException e)
        {
        }
        Thread thread2 = new Thread(new Runnable()
        {
            @Override
            public void run(){
                userShoppingList = dataManager.getUserShoppingLists(userLogin);
            }
        });
        thread2.start();
        try
        {
            thread2.join();
        }
        catch(InterruptedException e)
        {
        }
    }

    //add checkbox
    private void setListViewSettings() {
                Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                //if(getView() != null)
                //    groupView = (ListView) getView().findViewById(R.id.shoppingList);

                groupView.setClickable(true);
                groupView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
                    {
                        ShoppingList selectedList = (ShoppingList) groupView.getItemAtPosition(position);
                        Intent newActivityIntent=new Intent(getActivity().getApplicationContext(),
                                ShopListActivity.class).putExtra("ListId",
                                selectedList.getId()).putExtra("OwnList", isOwnList(selectedList));
                        startActivity(newActivityIntent);

                    }
                });

                groupView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                        final ShoppingList selectedList = (ShoppingList) groupView.getItemAtPosition(position);
                        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity().getApplicationContext());
                        alert.setTitle("Delete shopping list?");
                        alert.setMessage("Are you sure?");
                        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dial, int i) {
                                Thread thread = new Thread(new Runnable()
                                {
                                    @Override
                                    public void run() {
                                        dataManager.removeShoppingList(selectedList.getName(),user.getLogin());
                                        userShoppingList = dataManager.getUserShoppingLists(user.getLogin());
                                        adapter=new ArrayAdapter<ShoppingList>(getActivity().getApplicationContext(),
                                                R.layout.row_shoplist_item,
                                                userShoppingList);
                                    }});
                                thread.start();
                                try{
                                    thread.join();
                                }
                                catch (InterruptedException e){}

                                groupView.setAdapter(adapter);
                            }
                        });
                        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dial, int i) {
                                dial.dismiss();
                            }
                        });
                        //alert.show();
                        return true;
                    }
                });
            }
        });

        thread.start();

    }

    boolean result = false;
    private boolean isOwnList(final ShoppingList list)
    {
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run(){
                List<ShoppingList> userList = dataManager.getUserShoppingLists(user.getId());
                for (ShoppingList singleList: userList)
                {
                    if(singleList.getId() == list.getId())
                        result = true;
                }
            }
        });
        thread.start();
        try
        {
            thread.join();
        }
        catch(InterruptedException e)
        {
        }
        return result;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lists_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final CheckBox checkboxvariable=(CheckBox)getView().findViewById(R.id.checkBox);

        checkboxvariable.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (checkboxvariable.isChecked())
                {
                    Thread thread = new Thread(new Runnable()
                    {
                        @Override
                        public void run() {
                            userShoppingList = dataManager.getUserShoppingLists(user.getId());
                            adapter=new ArrayAdapter<ShoppingList>(getActivity().getApplicationContext(),
                                    R.layout.row_shoplist_item,
                                    userShoppingList);
                        }});
                    thread.start();
                    try{
                        thread.join();
                    }
                    catch (InterruptedException e){}

                    groupView.setAdapter(adapter);}
            }
        });

        groupView = (ListView) getView().findViewById(R.id.shoppingList);
        adapter=new ArrayAdapter<ShoppingList>(getActivity().getApplicationContext(),
                R.layout.row_shoplist_item,userShoppingList
        );
        groupView.setAdapter(adapter);
        setListViewSettings();

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
