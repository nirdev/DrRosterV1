package com.example.android.drroster;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.drroster.Signin.SigninActivity;
import com.example.android.drroster.Signin.SigninConstants;
import com.example.android.drroster.UI.MonthNavView;
import com.example.android.drroster.UI.SpaceBarColorHelper;
import com.example.android.drroster.activities.GenerateRosterActivity;
import com.example.android.drroster.databases.RosterHelper;
import com.example.android.drroster.databases.ShiftHelper;
import com.example.android.drroster.fragments.AddNewRosterFragment;
import com.example.android.drroster.fragments.MainMonthGridFragment;
import com.example.android.drroster.fragments.MainMonthListFragment;
import com.example.android.drroster.services.ExportToExcel;
import com.example.android.drroster.utils.DateUtils;
import com.example.android.drroster.utils.UIUtils;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Handler;


public class MainActivity extends AppCompatActivity {

    public static final String CURRENT_MONTH_KEY = "current_month_key";


    public static Date CURRENT_MONTH_DATE;
    public static Boolean IS_THERE_THIRD_CALL;
    String departmentName;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    ActionBarDrawerToggle drawerToggle;
    InterstitialAd mInterstitialAd;

    Handler mHandler;

    MonthNavView monthNavView;
    TextView navTitleMonth;
    TextView navTitleYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //If first time go to sign in , else start activity
        checkForSignIn();

        //Set view and drawer
        setContentView(R.layout.activity_main);
        setDrawerUI();
        inflateViews();

        //Check if first time or recreating the activity
        if (savedInstanceState == null) {
            //Set current roster date
            CURRENT_MONTH_DATE = DateUtils.getFirstDayOfThisMonthDate();
        }
        setNewMonthUI();
        //month changed listener
        monthNavView.setMonthChangeListener(new MonthNavView.IfMonthChangeListener() {
            @Override
            public void onMonthChange() {
                setNewMonthUI();
            }
        });

        initializeAdMob();

        int thiistestbranch = 6;
        Log.wtf("here", "--------------------------------------------" + thiistestbranch);
        Log.wtf("here", "some more wrk on branch");

    }

    private void initializeAdMob() {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.ad_unit_id_main_activity));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                downloadRoster();
            }
        });

        requestNewInterstitial();
    }


    private void setNewMonthUI() {
        IS_THERE_THIRD_CALL = ShiftHelper.isThereThirdCall(CURRENT_MONTH_DATE);
        changeMonthNavViewUI();
        changeNewMonthFragment();
    }

    private void changeNewMonthFragment() {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        //if roster available initialize list fragment
        if (RosterHelper.isAvailableRoster(CURRENT_MONTH_DATE)) {
            // Checks the orientation of the screen and toggle with respect
            int currentConfig = getResources().getConfiguration().orientation;

            if (currentConfig == Configuration.ORIENTATION_LANDSCAPE) {
                ft.replace(R.id.main_activity_place_holder,
                        new MainMonthGridFragment(), "" + CURRENT_MONTH_DATE.getTime());
            } else {
                ft.replace(R.id.main_activity_place_holder,
                        new MainMonthListFragment(), "" + CURRENT_MONTH_DATE.getTime());
            }

        }
        //if roster is not found initialize new roster fragment
        else {
            ft.replace(R.id.main_activity_place_holder,
                    new AddNewRosterFragment(), "" + CURRENT_MONTH_DATE.getTime());
        }

        ft.commit();
    }

    private void changeMonthNavViewUI() {
        String[] titleUI = DateUtils.getDateUIMonthYear(CURRENT_MONTH_DATE);
        navTitleMonth.setText(titleUI[0]);
        navTitleYear.setText(titleUI[1]);
    }

    public void setMonthNavVisibility(Boolean visible) {
        MonthNavView monthNavView = (MonthNavView) findViewById(R.id.month_nav_view_main_activity);
        if (monthNavView != null) {
            if (visible) {
                monthNavView.setVisibility(View.VISIBLE);
            } else {
                monthNavView.setVisibility(View.GONE);
            }
        }
    }

    private void setDrawerUI() {
        //set space bar color
        SpaceBarColorHelper.setLightColor(this);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Lookup navigation view
        NavigationView navigationView = (NavigationView) findViewById(R.id.nvView);

        // Setup drawer view
        setupDrawerContent(navigationView);

        // Inflate the header view at runtime
        View headerLayout = navigationView.inflateHeaderView(R.layout.navigation_drawer_header);
        //look up items within the header if needed
        TextView ivHeaderTitle = (TextView) headerLayout.findViewById(R.id.nav_drawer_header_title);
        //Set department name
        ivHeaderTitle.setText(departmentName);

        // Tie DrawerLayout events to the ActionBarToggle
        drawerToggle = setupDrawerToggle();
        mDrawer.addDrawerListener(drawerToggle);

    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    private void checkForSignIn() {
        //Retrieve string from sharedPreference
        SharedPreferences mSettings = getSharedPreferences(SigninConstants.SHAREDPREF_FILE_KEY, SigninConstants.SHAREDPREF_MODE_KEY);
        departmentName = mSettings.getString(SigninConstants.SHAREDPREF_DEPARTMENT_KEY, null);

        //check if this is first signIn
        if (departmentName == null || departmentName.isEmpty()) {

            //if true Got to signInActivity
            Intent i = new Intent(this, SigninActivity.class);
            startActivity(i);
        }
        // if false start app
    }

    //Handle drawer selection
    public void selectDrawerItem(MenuItem menuItem) {

        //Build Post instance with right post value and tag var with right tag value
        switch (menuItem.getItemId()) {

            case R.id.nav_first_fragment:
                addNewRoster(null);
                break;

            case R.id.nav_second_fragment:
                openSureDialog();
                break;

        }

        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    private void openSureDialog() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        RosterHelper.removeAllRosterForMonth(CURRENT_MONTH_DATE);
                        Intent i1 = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(i1);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        DrawerLayout mDrawerLayout;
                        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

                        if (mDrawerLayout != null) {
                            mDrawerLayout.closeDrawers();
                        }
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete "+ UIUtils.getMonthName(CURRENT_MONTH_DATE, Calendar.LONG) + "'s roster?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    @Override //Drawer toggle item
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE! Make sure to override the method with only a single `Bundle` argument
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (CURRENT_MONTH_DATE != null) {
            outState.putLong(CURRENT_MONTH_KEY, CURRENT_MONTH_DATE.getTime());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Long tempCurrentDate = savedInstanceState.getLong(CURRENT_MONTH_KEY);
        CURRENT_MONTH_DATE = new Date(tempCurrentDate);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
        setNewMonthUI();

    }

    private void inflateViews() {
        monthNavView = (MonthNavView) findViewById(R.id.month_nav_view_main_activity);
        navTitleMonth = (TextView) findViewById(R.id.view_month_nav_title_month);
        navTitleYear = (TextView) findViewById(R.id.view_month_nav_title_year);
    }

    public void addNewRoster(View view) {
        Intent i1 = new Intent(this, GenerateRosterActivity.class);
        i1.putExtra(CURRENT_MONTH_KEY, CURRENT_MONTH_DATE.getTime());
        startActivity(i1);
    }

    public void changeOrientation(View view) {

        // Checks the orientation of the screen and toggle with respect
        int currentConfig = getResources().getConfiguration().orientation;

        if (currentConfig == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        } else if (currentConfig == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    public void openAd(View view) {

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            downloadRoster();
        }

    }

    public void downloadRoster() {

        Intent msgIntent = new Intent(this, ExportToExcel.class);
        msgIntent.putExtra(CURRENT_MONTH_KEY, CURRENT_MONTH_DATE.getTime());
        startService(msgIntent);
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    //Automagically lose focus on any click outside editText - this way app don't crush
    // http://stackoverflow.com/questions/4828636/edittext-clear-focus-on-touch-outside
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );

    }

    //    public static List<AdditionalDutyDB> getAllADforDate(PersonDB personDB) {
//        // This is how you execute a query
//        return new Select()
//                .from(AdditionalDutyDB.class)
//                .where("person = ?", personDB.getId())
//                .execute();
//    }
//
//    public static List<PersonDB> getAllPeople() {
//        // This is how you execute a query
//        return new Select()
//                .from(PersonDB.class)
//                .orderBy("Name ASC")
//                .execute();
//    }
//
//    public static Date addOneDay(Date date) {
//        long timeadj = 24 * 60 * 60 * 1000; // one day in millisecond
//        return new Date(date.getTime() + timeadj);
//    }
}

