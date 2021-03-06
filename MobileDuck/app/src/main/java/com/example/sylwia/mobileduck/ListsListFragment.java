package com.example.sylwia.mobileduck;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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
import com.example.sylwia.mobileduck.db.tables.UserFriendKey;

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
    private ArrayAdapter<ShoppingList> adapter;
    private List <User> friendsList;
    private List<UserFriendKey> userFriendKeyList;
    private String userLogin;
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
        userLogin = sharedPref.getString(getString(R.string.preference_user_login), null);
        getOwnShoppingList();
    }

    private void getOwnShoppingList()
    {
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

                groupView.setLongClickable(true);
                groupView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                        final ShoppingList selectedList = (ShoppingList) groupView.getItemAtPosition(position);
                        if(isOwnList(selectedList)){
                            dataManager.removeShoppingList(selectedList);
                        }
                        //dataManager.removeShoppingList(selectedList.getName().replaceAll("[^ a-zA-Z0-9]", ""),user.getLogin().replaceAll("[^ a-zA-Z0-9]", ""));
                        userShoppingList = dataManager.getUserShoppingLists(user.getLogin().replaceAll("[^ a-zA-Z0-9]", ""));
                        CheckBox checkboxvariable=(CheckBox)getView().findViewById(R.id.checkBox);

                        if (checkboxvariable.isChecked()) {
                            getFriendsLists();
                        }
                        adapter=new ArrayAdapter<ShoppingList>(getActivity().getApplicationContext(),
                                R.layout.row_shoplist_item,
                                userShoppingList);
                        groupView.setAdapter(adapter);

                        return true;
                    }
                });
            }
        });

        thread.start();

    }

    private boolean isOwnList(ShoppingList list)
    {
        return user.getId() == list.getOwner();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lists_list, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        groupView = (ListView) getView().findViewById(R.id.shoppingList); //extract to method

        final CheckBox checkboxvariable=(CheckBox)getView().findViewById(R.id.checkBox);

        checkboxvariable.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                userShoppingList.clear();
                getOwnShoppingList();
                if (checkboxvariable.isChecked())
                {
                    getFriendsLists();

                }
                groupView = (ListView) getView().findViewById(R.id.shoppingList);
                adapter=new ArrayAdapter<ShoppingList>(getActivity().getApplicationContext(),
                        R.layout.row_shoplist_item,userShoppingList
                );
                groupView.setAdapter(adapter);
            }
        });

        groupView = (ListView) getView().findViewById(R.id.shoppingList);
        adapter=new ArrayAdapter<ShoppingList>(getActivity().getApplicationContext(),
                R.layout.row_shoplist_item,userShoppingList
        );
        groupView.setAdapter(adapter);
        setListViewSettings();

    }

    private void getFriendsLists()
    {
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run() {
                friendsList = dataManager.getUserFriends(user.getLogin());

                for(User user : friendsList){
                    for(ShoppingList shoppingList : dataManager.getUserShoppingLists(user.getId())){
                        userShoppingList.add(shoppingList);
                    }
                }
                adapter=new ArrayAdapter<ShoppingList>(getActivity().getApplicationContext(),
                        R.layout.row_shoplist_item,
                        userShoppingList);
            }});
        thread.start();
        try{
            thread.join();
        }
        catch (InterruptedException e){}
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

    @Override
    public void onResume() {
        userShoppingList.clear();
        getOwnShoppingList();
        CheckBox checkboxvariable=(CheckBox)getView().findViewById(R.id.checkBox);

        if (checkboxvariable.isChecked()) {
            getFriendsLists();
        }
        groupView = (ListView) getView().findViewById(R.id.shoppingList);
        adapter=new ArrayAdapter<ShoppingList>(getActivity().getApplicationContext(),
                R.layout.row_shoplist_item,userShoppingList
        );
        groupView.setAdapter(adapter);
        super.onResume();
    }
}
