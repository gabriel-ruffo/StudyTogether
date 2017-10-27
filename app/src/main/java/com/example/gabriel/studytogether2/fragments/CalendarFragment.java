package com.example.gabriel.studytogether2.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.example.gabriel.studytogether2.EditEnvelope;
import com.example.gabriel.studytogether2.EditEvent;
import com.example.gabriel.studytogether2.MainActivity;
import com.example.gabriel.studytogether2.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalendarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<WeekViewEvent>> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<WeekViewEvent> listOfAllEvents = new ArrayList<>();

    private static final int DB_LOADER = 22;

    public MainActivity.SectionsPagerAdapter secAdaptor;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private static ArrayList<WeekViewEvent> eventList = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    EditEnvelope ee = new EditEnvelope();

    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
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

    WeekView mWeekView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);

        mWeekView = (WeekView) v.findViewById(R.id.weekView);

        //eventList.add(new WeekViewEvent(100, "name", 2017, 10, 15, 7, 30, 2017, 10, 15, 8, 30));

        mWeekView.setNumberOfVisibleDays(7);
        mWeekView.setHourHeight(150);

        // Get a reference for the week view in the layout.
        //mWeekView = (WeekView) findViewById(R.id.weekView);
        WeekView.EventClickListener mEventClickListener = new WeekView.EventClickListener() {
            @Override
            public void onEventClick(WeekViewEvent event, RectF eventRect) {
                /*ee.setEvent(event);

                Intent newEvent = new Intent(getActivity(), EditEvent.class);
                newEvent.putExtra("EDIT_EXISTING", true);
                startActivity(newEvent);*/
            }
        };

        mWeekView.setFirstDayOfWeek(8);

        mWeekView.setShowNowLine(true);

        DateTimeInterpreter myInterpreter = new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                try {
                    SimpleDateFormat sdf = 1 == 1 ? new SimpleDateFormat("EEEEE M/dd", Locale.getDefault()) : new SimpleDateFormat("E M/dd", Locale.getDefault());
                    return sdf.format(date.getTime()).toUpperCase();
                } catch (Exception e) {
                    e.printStackTrace();
                    return "";
                }
            }

            @Override
            public String interpretTime(int hour) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, 0);

                try {
                    SimpleDateFormat sdf = DateFormat.is24HourFormat(getContext()) ? new SimpleDateFormat("HH:mm", Locale.getDefault()) : new SimpleDateFormat("hh a", Locale.getDefault());
                    return sdf.format(calendar.getTime());
                } catch (Exception e) {
                    e.printStackTrace();
                    return "";
                }
            }
        };

        mWeekView.setDateTimeInterpreter(myInterpreter);

        MonthLoader.MonthChangeListener mMonthChangeListener = new MonthLoader.MonthChangeListener() {
            @Override
            public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                // TODO(2): RETURN LIST OF WEEKVIEWEVENTS QUERIED FROM DB
                //List<WeekViewEvent> events = .getEvents(newYear, newMonth);
                //List<WeekViewEvent> events = CalendarFragment.getMyEvents();
                //List<WeekViewEvent> events2 = new ArrayList<>();
                //ee.populateEvents();

                // TODO(3): this starts the second thread, charles
//                if (listOfAllEvents != null)
//                    return listOfAllEvents;
               // Toast.makeText(getContext(), "before if", Toast.LENGTH_LONG);

                if (listOfAllEvents.size() > 0) {
                   // Toast.makeText(getContext(), "in if", Toast.LENGTH_LONG);
                    return listOfAllEvents;
                }

                setUpLoader();

                return new ArrayList<>();
            }
        };

        WeekView.EventLongPressListener mEventLongPressListener = new WeekView.EventLongPressListener() {
            @Override
            public void onEventLongPress(WeekViewEvent event, RectF eventRect) {

            }
        };

        // Set an action when any event is clicked.
        mWeekView.setOnEventClickListener(mEventClickListener);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(mMonthChangeListener);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(mEventLongPressListener);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab_calendar_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newEvent = new Intent(getActivity(), EditEvent.class);
                newEvent.putExtra("EDIT_EXISTING", false);
                startActivity(newEvent);
            }
        });

        return v;
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


    private void setUpLoader() {
        LoaderManager loaderManager = getActivity().getSupportLoaderManager();
        Loader<String> loader = loaderManager.getLoader(DB_LOADER);

        if (loader == null)
            loaderManager.initLoader(DB_LOADER, null, this);
        else
            loaderManager.restartLoader(DB_LOADER, null, this);
    }

    @Override
    public Loader<ArrayList<WeekViewEvent>> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<ArrayList<WeekViewEvent>>(getActivity()) {
            private ArrayList<WeekViewEvent> query;

            @Override
            public void onStartLoading() {
                if (query != null) {
                    deliverResult(query);
                } else {
                    forceLoad();
                }
            }

            @Override
            public ArrayList<WeekViewEvent> loadInBackground() {
                query = ee.populateEvents();
                return query;
            }

            @Override
            public void deliverResult(ArrayList<WeekViewEvent> data) {
                query = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<WeekViewEvent>> loader, ArrayList<WeekViewEvent> data) {
        listOfAllEvents = data;
        Toast.makeText(getContext(), listOfAllEvents.get(0).getName(), Toast.LENGTH_LONG).show();
        secAdaptor.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<WeekViewEvent>> loader) {
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
        super.onResume();
    }
}
