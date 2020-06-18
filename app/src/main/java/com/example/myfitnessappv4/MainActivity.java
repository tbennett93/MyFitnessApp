package com.example.myfitnessappv4;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.myfitnessappv4.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
    //TODO make mpreferences public or restricted to all classes in this scope

    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.myfitnessappv4.sharedprefsfile";

//    @Override
//    protected void onPause(){
//        super.onPause();
//
//         ...
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();

        //TODO remove this as we don't want to set it to 180 mandatorily
        preferencesEditor.putInt("CURRENT_WEIGHT_KEY", 180);
        preferencesEditor.putString("CURRENT_WEIGHT_UNIT", "lbs");
        preferencesEditor.putInt("CURRENT_BODYFAT",12);
        preferencesEditor.putInt("USER_GOAL_VAL",10);


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

//TODO make a decision on whether or not a user can enter weight and bf in the goals tab.
//Separate all instances of Bodyfat to Body Fat
//TODO limit entered bodyfat% to between 1-100