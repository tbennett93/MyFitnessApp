package com.example.myfitnessappv4;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.myfitnessappv4.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;



public class MainActivity extends AppCompatActivity {

    public  static  enum ActivityLevel {
        SEDENTARY, LIGHTLY_ACTIVE, MODERATELY_ACTIVE, VERY_ACTIVE, EXTRA_ACTIVE;
    };

    public  static  enum HeightUnit {
        imperial, metric;
    };

    //TODO make mpreferences public or restricted to all classes in this scope

    public static SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.myfitnessappv4.sharedprefsfile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();

        //TODO uninitialise these variables
        //TODO 1) consider how to go about height measurements in shared preferences. Maybe 1 CURRENT_HEIGHT_CM, CURRENT_HEIGHT_FEET, CURRENT_HEIGHT_INCHES
        preferencesEditor.putInt("CURRENT_WEIGHT", 180);
        preferencesEditor.putString("CURRENT_WEIGHT_UNIT", "lbs");
        preferencesEditor.putInt("CURRENT_BODYFAT",12);
        preferencesEditor.putInt("GOAL_BODYFAT",10);
        preferencesEditor.putString("USER_SEX","male");
        preferencesEditor.putInt("USER_AGE",26);
        preferencesEditor.putString("USER_ACTIVITY_LEVEL", ActivityLevel.SEDENTARY.toString());


        preferencesEditor.putInt("USER_HEIGHT_CM",180);

        preferencesEditor.putInt("USER_HEIGHT_FEET",6);
        preferencesEditor.putInt("USER_HEIGHT_INCHES",0);

        preferencesEditor.putString("USER_HEIGHT_UNIT",HeightUnit.metric.toString());



        preferencesEditor.putLong("START_DATE",  new Date(System.currentTimeMillis()).getTime());
        preferencesEditor.putLong("END_DATE", new Date(System.currentTimeMillis()).getTime()+(5184001*60));
//        preferencesEditor.putString("START_DATE%",);
        preferencesEditor.apply();


        //setting the content view is enough to display the fragments and navigation bar
        // this is because activity_main's large screen portion points to a navgraph of mobile_navigation.xml which has objects for each fragment and allows each one to be assigned to a screen
        //it also points its navbar at bottom_nav_menu, which has a list of the icons and text for the links in the nav bar which seem to automatically be inlated
        setContentView(R.layout.activity_main);

        //setup a new object of type navview. This is the bottom navigation pane in the activity_main.xml
        BottomNavigationView navView = findViewById(R.id.nav_view);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        //setup button functionality

        ////        <p>The {@link AppBarConfiguration} you provide to Navigation.findNavController controls how the Navigation button is displayed
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_goals, R.id.navigation_planner, R.id.navigation_guides, R.id.navigation_profile)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);


        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

//        Sets up a BottomNavigationView for use with a NavController. This will call onNavDestinationSelected(MenuItem, NavController) when a menu item is selected. The selected item in the BottomNavigationView will automatically be updated when the destination changes.
        NavigationUI.setupWithNavController(navView, navController);




    }

}

//TODO create bodyfat calculator popup and link to both front end and the planner
//TODO make a decision on whether or not a user can enter weight and bf in the goals tab - Yes, the user sets the start and end goals here and that updates the system dynamically.
//TODO Option to save a journey to look back on at a later date. I.e. it provides details on start and end date, and shows the journey of fat loss and calories, amount lost, macros etc.
//TODO ability to enter daily calories and it shows the start and end pictures as well as
//TODO All text to come from strings.xml
//TODO Separate all instances of Bodyfat to Body Fat - This includes the switch
//TODO limit entered bodyfat% to between 1-100. Disallow decimals on entering BF and weight
//TODO a setting to set the frequency at which to set reminders to make progress update and this triggers a notification at that frequency
//TODO In stats have a chart showing weight over time and BF% over time. Have the user able to vary the scope of this
//TODO Dynamic calorie adjustment if accurate calories can be input. If not on track, prompt the user to estimate how many calories over they have gone and using this value, advise of a new maintenance calories (add the additional value in the goals tab EG Maintenance calories: 1200 (+1250).
//      Essentially if the user isnn't on track, find out if they've been cheated and whether they have or haven't factor this in to a re-calculation of calories.
//TODO Birthday to automatically update age