package com.example.aniket.businga;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

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

import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class HomePage extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

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
    private ViewPager mViewPager;
    private DrawerLayout drawer_layout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigation;
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build();
    GoogleApiClient mGoogleApiClient;
    NavigationView navigationView;
    View header;
    TextView name_nav_header;
    TextView email_nav_header;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer_layout = (DrawerLayout)findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, drawer_layout, R.string.open, R.string.close);
        drawer_layout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        navigationView = (NavigationView)findViewById(R.id.navigationViewLayout);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        name_nav_header = header.findViewById(R.id.textView2);
        email_nav_header = header.findViewById(R.id.textView4);
        sharedPreferences = getSharedPreferences("Mypref", MODE_PRIVATE);
        name_nav_header.setText(sharedPreferences.getString("Name",""));
        email_nav_header.setText(sharedPreferences.getString("email",""));
    }





    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == R.id.logout)
        {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    Intent intent = new Intent(getApplicationContext(), Loginpage.class);
                    SharedPreferences sharedPreferences = getSharedPreferences("Mypref", MODE_PRIVATE);
                    sharedPreferences.edit().putBoolean("isLogin", false).commit();
                    startActivity(intent);
                    finish();
                }
            });
        }
        else if (id == R.id.holiday_list)
        {
            Intent intent = new Intent(getApplicationContext(), HolidayList.class);
            startActivity(intent);
        }
        else if (id == R.id.time_table)
        {
            Intent intent = new Intent(getApplicationContext(), BusTimeTable.class);
            startActivity(intent);
        }
        else if(id == R.id.feedback)
        {
            Intent intent = new Intent(getApplicationContext(), FeedbackForm.class);
            startActivity(intent);
        }
        else if(id == R.id.home){
            Intent intent  = new Intent(getApplicationContext(), HomePage.class);
            startActivity(intent);
            finish();
        }
        else if(id == R.id.imp_contacts){
            Intent intent = new Intent(getApplicationContext(), ImportantContacts.class);
            startActivity(intent);
        }
        else if(id == R.id.setting){
            Intent intent = new Intent(getApplicationContext(), SettingsNavBar.class);
            startActivity(intent);
        }
        return true;
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
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
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
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


    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
                    BusTrack busTrack = new BusTrack();
                    return busTrack;
                case 2:
                    HolidayPoll holidayPoll = new HolidayPoll();
                    return holidayPoll;
                case 1:
                    Notification notification = new Notification();
                    return notification;
                case 3:
                    ComplaintPortal complaintPortal = new ComplaintPortal();
                    return complaintPortal;
            }

            return null;
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        name_nav_header.setText(sharedPreferences.getString("Name",""));
    }

    @Override
    public void onBackPressed() {
        if (this.drawer_layout.isDrawerOpen(GravityCompat.START)) {
            this.drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
