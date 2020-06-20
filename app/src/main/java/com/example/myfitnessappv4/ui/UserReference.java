package com.example.myfitnessappv4.ui;

import android.content.SharedPreferences;

import com.example.myfitnessappv4.MainActivity;
import com.example.myfitnessappv4.ui.goals.GoalsFragment;

public class UserReference {

    //TODO Insert variables and methods to calculate user info here

    private SharedPreferences mPreferences = MainActivity.mPreferences;
    //profile stats
    int age = mPreferences.getInt("USER_AGE" ,0);
    String sex = mPreferences.getString("USER_SEX","ERROR_FETCHING_VAL");
    int height = mPreferences.getInt("USER_HEIGHT" ,0);
    String weightUnit = mPreferences.getString("CURRENT_WEIGHT_UNIT","ERROR_FETCHING_VAL");
    String heightUnit = mPreferences.getString("CURRENT_HEIGHT_UNIT","ERROR_FETCHING_VAL");
    double weight = mPreferences.getInt("CURRENT_WEIGHT_KEY",0);
    //TODO set up a heighunit shared preferences - IMPERIAL & METRIC
    String activityLevel = mPreferences.getString("USER_ACTIVITY_LEVEL" ,"ERROR_FETCHING_VAL");
    double activityValue;

    //goals stats
    int currentWeight;
    double bodyFatPercentage;


    public double convertToMeter(int feet, int inches){
        return 0;
        //TODO this logic
    }

    public String convertFromMeter(double kg){
        //TODO this should look at SharedPreferences for its vals
        int feet = 6;
        int inches = 0 ;
        String imperialHeight = feet + "' " + inches + "''";
        return imperialHeight;

    }
    public double convertToKG(double lbs){
        double kg = lbs / 2.20462;
        return kg;

    }

    public double convertFromKG(double kg){
        double lbs = kg * 2.20462;
        return lbs;

    }

    private double getActivityValue(){
        switch ( mPreferences.getString("USER_ACTIVITY_LEVEL" ,"ERROR_FETCHING_VAL")){
            case "SEDENTARY":
                this.activityValue = 1.2;
                break;
            case "LIGHTLY_ACTIVE":
                this.activityValue = 1.375;
                break;
            case "MODERATELY_ACTIVE":
                this.activityValue = 1.55;
                break;
            case "VERY_ACTIVE":
                this.activityValue = 1.725;
                break;
            case "EXTRA_ACTIVE":
                this.activityValue = 1.9;
                break;


        };

        return activityValue;
    }

    private int calculateBMR(){

        if (weightUnit == "kg"){
            //Ci
            weight = convertFromKG(weight);
        };

        return 0;

        /*
        BMR
        Mifflin-St Jeor Equation:
        For men: BMR = 10W + 6.25H - 5A + 5
        For women: BMR = 10W + 6.25H - 5A - 161


        Basic Activity Factor

        1.2: If you are sedentary (little or no exercise) = BMR x 1.2
        1.375: If you are lightly active (light exercise/sports 1-3 days/week) = BMR x 1.375
        1.55: If you are moderately active (moderate exercise/sports 3-5 days/week) = BMR x 1.55
        1.725: If you are very active (hard exercise/sports 6-7 days a week) = BMR x 1.725
        1.9: If you are extra active (very hard exercise/sports & physical job or 2x training) = BMR x 1.9
        */




     }

    public int calculateMaintenanceCalories(double bMR){
        return (int) (bMR * activityValue);
    }


    public void UserReference(int currentWeight, double bodyFatPercentage){
        this.currentWeight = currentWeight;
        this.bodyFatPercentage = bodyFatPercentage;
        getActivityValue();
    }

}


