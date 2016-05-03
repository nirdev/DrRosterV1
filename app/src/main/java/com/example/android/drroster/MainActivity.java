package com.example.android.drroster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.drroster.Signin.SigninActivity;
import com.example.android.drroster.Signin.SigninConstants;
import com.example.android.drroster.UI.MonthNavView;
import com.example.android.drroster.UI.SpaceBarColorHelper;
import com.example.android.drroster.activities.GenerateRosterActivity;
import com.example.android.drroster.databases.RosterHelper;
import com.example.android.drroster.databases.ShiftHelper;
import com.example.android.drroster.fragments.AddNewRosterFragment;
import com.example.android.drroster.fragments.MainMonthListFragment;
import com.example.android.drroster.utils.DateUtils;

import java.util.Date;


public class MainActivity extends AppCompatActivity {

    public static final String CURRENT_MONTH_KEY = "current_month_key";


    public static Date CURRENT_MONTH_DATE;
    public static Boolean IS_THERE_THIRD_CALL;
    String departmentName;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    ActionBarDrawerToggle drawerToggle;

    MonthNavView monthNavView;
    TextView navTitleMonth;
    TextView navTitleYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //If first time go to sign in , else start activity //TODO: remove comment
//        checkForSignIn();

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

//        Date date = new Date();
//        date = DateUtils.getFirstDayOfThisMonthDate();


//        date = DateUtils.addMonth(date);

//        LeaveDatesHelper.removeAllLeaveDatePersonForDate(date);
//        List<DutyDateDB> tempList = DutiesHelper.getAllDutiesForDate(date);
//        for (DutyDateDB duty : tempList){
//            Log.wtf("here", "--------------------------------------------" + duty.dutyType.type);
//        }


//        Log.wtf("here", " " + DateUtils.getYearFromDate(new Date()));
//        Log.wtf("here", " " + DateUtils.getMonthFromDate(new Date()));
//        Log.wtf("here", " " + DateUtils.getDayFromDate(new Date()));

//        ArrayList<Date> array1 = DateUtils.buildMonthOfDatesArray(4, 2016);
//        ArrayList<Date> array2 = DateUtils.buildMonthOfDatesArray2(CURRENT_MONTH_DATE);
//        ItemMainView itemMainView = ItemMainViewHelper.buildMainItemFromDB(array1.get(0));
//
//        Log.wtf("here", "--------------------------------------------");

//        Date date = new Date();
//        date = DateUtils.getFirstDayOfThisMonthDate();


//        date = DateUtils.addMonth(date);

//        ArrayList<Date>  monthOfDates = DateUtils.buildMonthOfDatesArray2(date);
//        for (Date date1 : monthOfDates) {
//            Log.wtf("here", " " + UIUtils.getDayNumber(date1));
//        }

//        Log.wtf("here", " " + DateUtils.isWeekend(date));
//
//        date = DateUtils.addOneDay(date);
//        Log.wtf("here", " " + DateUtils.isWeekend(date));
//
//        date = DateUtils.addOneDay(date);
//        Log.wtf("here", " " + DateUtils.isWeekend(date));
//
//        date = DateUtils.addOneDay(date);
//        Log.wtf("here", " " + DateUtils.isWeekend(date));
//
//        date = DateUtils.addOneDay(date);
//        Log.wtf("here", " " + DateUtils.isWeekend(date));


//        Log.wtf("here", " " + ShiftHelper.isThereThirdCall(date));
//        ItemMainView itemMainView = ItemMainViewHelper.buildMainItemFromDB(date);

//        Log.wtf("here", "--------------------------------------------");
//
//        RosterDB rosterDB = new RosterDB(date);
//        rosterDB.save();
//        Log.wtf("here", " " + RosterHelper.isAvailableRoster(date));
//        String[] dateUI = DateUtils.getDateUIMonthYear(DateUtils.removeMonth(new Date()));
//        Log.wtf("here", " month: " + dateUI[0] + " year: " + dateUI[1]);


//        Date newDate = DateUtils.addMonth(new Date());

//        //Go to choose month activity
//        Intent i1 = new Intent(this, GenerateRosterActivity.class);
//        startActivity(i1);
//        int nir34;
//        //get current date time with Date()
//        Date date = new Date();
//        date = DateUtils.getStartOfDay(date);
//


        //Save table of first dates in moth to make ready roster index


//        for (int ix = 0;ix  < 15;ix++) {
//            date = addOneDay(date);
//            RosterDB rosterDB = new RosterDB(date);
//            rosterDB.save();
//        }


//        Date date1 = DateUtils.getFirstDayOfThisMonthDate();
//        Log.wtf("here", " " + RosterHelper.getCurrentDayRosterIndex());

//        ArrayList<Date> rosters = RosterHelper.getAllRosterDates();
//        for (Date roster : rosters) {
//            System.out.println(" time: " + roster.getTime());
//        }
//
//        List<DutyDateDB> dutyDateDBs = DutiesHelper.getAllDutiesForDate(date);
//        Log.wtf("here", "--------------------------------------------" + dutyDateDBs.size());
//


//
//        PersonDB personDB = new PersonDB("nir2");
//        personDB.save();
//
//        List<PersonDB> people = getAllPeople();
//
//        for (PersonDB personDB1 : people){
//            System.out.print("peopleList:" + personDB1.name);
//            System.out.println(" id: " + personDB1.getId());
//        }
//
//        int temp = PersonDBHelper.getIdFromString("nir2");
//        Log.wtf("here", " " + temp);
//
//        AdditionalDutyDB dutyDB = new AdditionalDutyDB(date,"outreach","or");
//        dutyDB.save();
//
//        List<AdditionalDutyDB> dutyDBList = DutiesHelper.getAdditionalDutiesForDateList(date);
//
//        for (AdditionalDutyDB dutyDBModel : dutyDBList){
//            System.out.print("additional duty list:" + dutyDBModel.date);
//            System.out.print(" " + dutyDBModel.type);
//            System.out.println(" id: " + dutyDBModel.getId());
//        }
//
//
//
//        ShiftDB currentDayShift  = new ShiftDB(date,personDB,personDB,personDB);
//
//        int nir23;

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
            ft.replace(R.id.main_activity_place_holder,
                    new MainMonthListFragment(), "" + CURRENT_MONTH_DATE.getTime());
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
                RosterHelper.removeAllRosterForMonth(CURRENT_MONTH_DATE);
                Intent i1 = new Intent(this, MainActivity.class);
                startActivity(i1);
                break;

        }
//            //Build new instance of the fragment
//            PostFragment postFragment = PostFragment.newInstance(fPost);
//
//            //Set up fragment manager in order to make the transaction
//            FragmentManager fm = getSupportFragmentManager();
//            FragmentTransaction ft = fm.beginTransaction();
//            ft.replace(R.id.fragment_place_holder, postFragment, fTag);
//
//            // replace
//            ft.commit();


        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    @Override
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

