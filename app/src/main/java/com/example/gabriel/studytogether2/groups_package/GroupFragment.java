package com.example.gabriel.studytogether2.groups_package;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.gabriel.studytogether2.MainActivity;
import com.example.gabriel.studytogether2.R;
import com.example.gabriel.studytogether2.dbMedium_package.DBMediumGetGroups;
import com.example.gabriel.studytogether2.fragments.MyItemRecyclerViewAdapter;
import com.example.gabriel.studytogether2.fragments.dummy.DummyContent;
import com.example.gabriel.studytogether2.fragments.dummy.DummyContent.DummyItem;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class GroupFragment extends Fragment
implements GroupsRVAdapter.ListItemClickListener{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GroupFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static GroupFragment newInstance(int columnCount) {
        GroupFragment fragment = new GroupFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }




    }

    class Person {
        String name;
        String age;
        int photoId;

        Person(String name, String age, int photoId) {
            this.name = name;
            this.age = age;
            this.photoId = photoId;
        }
    }

    private ArrayList<DBMediumGetGroups.GroupCard> groups;

    private ArrayList<Person> persons;

    private DBMediumGetGroups dbmgg;

    // This method creates an ArrayList that has three Person objects
// Checkout the project associated with this tutorial on Github if
// you want to use the same images.
    private void initializeData(){
        this.dbmgg = ((MainActivity) getActivity()).dbmgg;
        groups = dbmgg.getGroups();

        if (groups == null)
            groups = new ArrayList<>();

        /*persons = new ArrayList<>();
        persons.add(new Person("Emma Wilson", "23 years old", R.drawable.ic_add));
        persons.add(new Person("Lavery Maiss", "25 years old", R.drawable.ic_add));
        persons.add(new Person("Lillie Watts", "35 years old", R.drawable.ic_add));*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        //RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv_groups);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        /* adapter = new RVAdapter(persons);
        rv.setAdapter(adapter);*/
        initializeData();

        ((MainActivity) getActivity()).spinner.setVisibility(View.GONE);
        // Set the adapter
        if (rv instanceof RecyclerView) {
            Context context = view.getContext();
            //RecyclerView recyclerView = (RecyclerView) view;
            /*if (mColumnCount <= 1) {
                rv.setLayoutManager(new LinearLayoutManager(context));
            } else {
                rv.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }*/
            GroupsRVAdapter grva = new GroupsRVAdapter(groups, this);
            rv.setAdapter(grva);

        }

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_group_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newEvent = new Intent(getActivity(), CreateGroup.class);
                //newEvent.putExtra("EDIT_EXISTING", false);
                startActivity(newEvent);
            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent newIntent = new Intent(getContext(), GroupScreen.class);

        int gid = dbmgg.getGid(clickedItemIndex);

        newIntent.putExtra("GROUP_ID", gid);

        ArrayList<String> uNames = dbmgg.getGroups().get(clickedItemIndex).groupmembers;
        newIntent.putExtra("GROUP_SIZE", uNames.size());

        for (int i = 0; i < uNames.size(); i++) {
            newIntent.putExtra("MEMBER" + i, uNames.get(i));
        }

        startActivity(newIntent);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
