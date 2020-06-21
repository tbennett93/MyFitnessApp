package com.example.myfitnessappv4.ui;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.myfitnessappv4.MainActivity;
import com.example.myfitnessappv4.ui.goals.GoalsFragment;

public class UserReference {

    //TODO Insert variables and methods to calculate user info here

    private static SharedPreferences mPreferences = MainActivity.mPreferences;

    //profile stats
    static int age = mPreferences.getInt("USER_AGE" ,0);
    static String sex = mPreferences.getString("USER_SEX","ERROR_FETCHING_VAL");

    static int heightCM = mPreferences.getInt("USER_HEIGHT_CM" ,0);
    static int heightFeet = mPreferences.getInt("USER_HEIGHT_FEET" ,0);
    static int heightInches = mPreferences.getInt("USER_HEIGHT_INCHES" ,0);
    static String heightUnit = mPreferences.getString("USER_HEIGHT_UNIT","ERROR_FETCHING_VAL");

    static String weightUnit = mPreferences.getString("CURRENT_WEIGHT_UNIT","ERROR_FETCHING_VAL");
    static double weight = mPreferences.getInt("CURRENT_WEIGHT",0);


    //goals stats
    double bodyFatPercentage = mPreferences.getInt("CURRENT_BODYFAT" ,0);



    public static int convertToCM(int feet, int inches){
        int cm = (int) ((30.48 * feet) + (2.54 * inches));
        //TODO this logic
        return cm;
    }


    public static double convertToKG(double lbs){
        double kg = lbs / 2.20462;
        return kg;
    }

    public double convertFromKG(double kg){
        double lbs = kg * 2.20462;
        return lbs;
    }

    private static double getActivityValue(){
        double activityValue;
        switch ( mPreferences.getString("USER_ACTIVITY_LEVEL" ,"ERROR_FETCHING_VAL")){
            case "SEDENTARY":
                activityValue = 1.2;
                break;
            case "LIGHTLY_ACTIVE":
                activityValue = 1.375;
                break;
            case "MODERATELY_ACTIVE":
                activityValue = 1.55;
                break;
            case "VERY_ACTIVE":
                activityValue = 1.725;
                break;
            case "EXTRA_ACTIVE":
                activityValue = 1.9;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mPreferences.getString("USER_ACTIVITY_LEVEL", "ERROR_FETCHING_VAL"));
        };

        return activityValue;
    }

    private static double calculateBMR(){


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


        double BMR;
        int sexVal = 9999;
        double weightKG  = mPreferences.getInt("CURRENT_WEIGHT",0);


        if (sex.matches("male")){
            sexVal = 5;
        } else if (sex.matches("female")){
            sexVal = -161;
        };

        if (weightUnit.matches("lbs")){
            weightKG = convertToKG(mPreferences.getInt("CURRENT_WEIGHT",0));

        }

        ;

        Log.d("Debug_weight1", String.valueOf(weightKG));

        if (heightUnit == "imperial"){
            heightCM = convertToCM(heightFeet, heightInches);
        }

        Log.d("Debug_height1", String.valueOf(heightCM));

        BMR = (10 * weightKG) + (6.25 * heightCM) - (5 * age) + sexVal;

        Log.d("Debug_BMR3", String.valueOf(BMR));

        return BMR;
     }


    public static int calculateMaintenanceCalories(){
        return (int) (calculateBMR() * getActivityValue());
    }


}


