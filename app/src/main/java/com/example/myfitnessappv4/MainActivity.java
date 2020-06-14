package com.example.myfitnessappv4;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myfitnessappv4.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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