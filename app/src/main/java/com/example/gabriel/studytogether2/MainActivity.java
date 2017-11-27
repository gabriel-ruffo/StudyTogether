package com.example.gabriel.studytogether2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gabriel.studytogether2.dbMedium_package.DBMediumGet;
import com.example.gabriel.studytogether2.dbMedium_package.DBMediumGetGroups;
import com.example.gabriel.studytogether2.schedule_package.CalendarFragment;
import com.example.gabriel.studytogether2.groups_package.GroupFragment;
import com.example.gabriel.studytogether2.fragments.dummy.DummyContent;
import com.example.gabriel.studytogether2.groupActivities.ChatActivity;

public class MainActivity extends AppCompatActivity implements CalendarFragment.OnFragmentInteractionListener, GroupFragment.OnListFragmentInteractionListener
{
    //small change

    private Toast toastMsg;

    public DBMediumGet dbm;
    public DBMediumGetGroups dbmgg;
    public boolean returnFromCT;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private MyViewPager mViewPager;
    public ProgressBar spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivityContainer mac = MainActivityContainer.getInstance();
        try {
            int sid = getIntent().getExtras().getInt("SID");
            String tUsername = getIntent().getExtras().getString("EMAIL");
            mac.setSID(sid);
            mac.setUsername(tUsername);
        }catch (Exception e) {

        }
        spinner = (ProgressBar) findViewById(R.id.pb_group_fragment);
        spinner.setVisibility(View.VISIBLE);

        mac.setMain(this);

        returnFromCT = false;

        Toast.makeText(this, "welcome " + mac.getUsername().split("@")[0], Toast.LENGTH_LONG).show();

        //int tempint = getIntent().getExtras().getInt("SID");

        dbm = new DBMediumGet();
        dbm.refreshList();

        dbmgg = new DBMediumGetGroups();
        dbmgg.refreshList();

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (MyViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setEnableSwipe(false);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void refreshGroups() {
        dbmgg.refreshList();
    }

    public void refreshCalendar() {
        if (mSectionsPagerAdapter != null) {
            /*if (dbm.needsRefresh())
                dbm.refreshList();*/
            dbm.refreshList();

            //mSectionsPagerAdapter.notifyDataSetChanged();
        }
    }

    public void refreshCalendarEvents() {
        if (mSectionsPagerAdapter != null) {
            /*if (dbm.needsRefresh())
                dbm.refreshList();*/
            //dbm.refreshList();

            mSectionsPagerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (returnFromCT)
            returnFromCT = false;

        /*if (mSectionsPagerAdapter != null) {
            //ditEnvelope.getInstance().resetCount();
            dbm.resetRefresh();
            dbm.resetCount();
            mSectionsPagerAdapter.notifyDataSetChanged();
        }*/
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

        Context context = MainActivity.this;

        Class destinationActivity = ChatActivity.class;

        Intent startChildActivityIntent = new Intent(context, destinationActivity);

        startChildActivityIntent.putExtra("ID", item.id);
        startChildActivityIntent.putExtra("Content", item.content);
        startChildActivityIntent.putExtra("Details", item.details);

        startActivity(startChildActivityIntent);
    }

    public void finishGroups() {
        if (mSectionsPagerAdapter != null) {
            mSectionsPagerAdapter.notifyDataSetChanged();
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private CalendarFragment calendarFragment;
        private GroupFragment groupFragment;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            calendarFragment = new CalendarFragment();
            groupFragment = new GroupFragment();
        }

        @Override
        public int getItemPosition(Object object) {
            //return super.getItemPosition(object);
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            /*if (position != 2) {
                return PlaceholderFragment.newInstance(position + 1);
            } else {
                return calendarFragment.newInstance("", "");
            }*/

            switch (position) {
                case 0:
                    return groupFragment;
                    //return PlaceholderFragment.newInstance(position);
                case 1:
                    CalendarFragment c = new CalendarFragment();
                    c.secAdaptor = mSectionsPagerAdapter;
                    return c;
                /*case 2:
                    CalendarFragment c = new CalendarFragment();
                    c.secAdaptor = mSectionsPagerAdapter;
                    return c;
                    //return calendarFragment.newInstance("", "");
                case 3:
                    return extrasFragment;*/
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "GROUPS";
                case 1:
                    return "SCHEDULE";
                /*case 2:
                    return "SCHEDULE";
                case 3:
                    return "SETTINGS";*/
            }
            return null;
        }
    }
}
