package com.example.gabriel.studytogether2.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.gabriel.studytogether2.R;
import com.example.gabriel.studytogether2.extrasActivities.HelpActivity;
import com.example.gabriel.studytogether2.extrasActivities.ProfileActivity;
import com.example.gabriel.studytogether2.extrasActivities.SettingsActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExtrasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExtrasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExtrasFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String testMsg;

    //private OnFragmentInteractionListener mListener;
    //private ExtrasButtonListener bListener;

    public ExtrasFragment() {
        // Required empty public constructor
        testMsg = "constructed1";
    }

    //public String getTestMsg

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExtrasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExtrasFragment newInstance(String param1, String param2) {
        ExtrasFragment fragment = new ExtrasFragment();
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_extras, container, false);


        //bListener = new ExtrasButtonListener();
        Button profileButton = (Button) rootView.findViewById(R.id.profile_button);
        Button settingsButton = (Button) rootView.findViewById(R.id.settings_button);
        Button helpButton = (Button) rootView.findViewById(R.id.help_button);

        profileButton.setOnClickListener(this);
        settingsButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);

        return rootView;

        //return inflater.inflate(R.layout.fragment_extras, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    /*public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    @Override
    public void onClick(View v) {
        int clickedId = v.getId();

        switch(clickedId) {
            case(R.id.profile_button):

                Intent intentProfile = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intentProfile);
                break;

            case(R.id.settings_button):

                Intent intentSettings = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intentSettings);
                break;

            case(R.id.help_button):

                Intent intentHelp = new Intent(getActivity(), HelpActivity.class);
                startActivity(intentHelp);
                break;

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
    /*public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/

    /*private class ExtrasButtonListener implements View.OnClickListener {

        private int clickedId;

        @Override
        public void onClick(View v) {
            clickedId = v.getId();

            System.out.println(clickedId);
            System.out.println(R.id.profile_button);
            System.out.println(R.id.settings_button);
            System.out.println(R.id.help_button);


            switch(clickedId) {
                case(R.id.profile_button):

                    Toast test = Toast.makeText(getActivity(), "profile", Toast.LENGTH_SHORT);
                    test.show();

                    Intent intentProfile = new Intent(getActivity(), ProfileActivity.class);
                    startActivity(intentProfile);

                case(R.id.settings_button):

                    Intent intentSettings = new Intent(getActivity(), SettingsActivity.class);
                    startActivity(intentSettings);

                case(R.id.help_button):

                    Intent intentHelp = new Intent(getActivity(), HelpActivity.class);
                    startActivity(intentHelp);

            }
        }


    }*/


    }
}
