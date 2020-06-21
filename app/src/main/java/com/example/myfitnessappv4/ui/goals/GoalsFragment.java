package com.example.myfitnessappv4.ui.goals;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myfitnessappv4.MainActivity;
import com.example.myfitnessappv4.R;
import com.example.myfitnessappv4.ui.UserReference;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class GoalsFragment extends Fragment {


    //Universal
    public static SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.myfitnessappv4.sharedprefsfile";
    private SharedPreferences.Editor preferencesEditor;
    private View root;

    //Current weight
    private TextView popupUnit;
    private EditText popupEnterWeight;
    private TextView userCurrentWeight;
    private LinearLayout clickableCurrentWeight;
    private PopupWindow popupWindowWeight;

    //Current BF
    private EditText popupEnterBF;
    private TextView userBF;
    private LinearLayout clickableBF;
    private PopupWindow popupWindowBF;
    private TextView popupAskBF;

    //Target
    private TextView popupEnterGoalValue;
    private TextView popupGoalValueUnit;
    private TextView userAskGoalType;
    private EditText enterGoalValue;
    private ToggleButton targetTypeSelector;
    private String sTargetType;
    private LinearLayout clickableMeasurementLayout;
    private PopupWindow popupWindowGoals;
    private TextView enterTargetAmount;
    private TextView popupAskGoalType;


    //Start Date
    private EditText displayStartDateField;
    private LinearLayout clickableStartDate;
    private Calendar myCalendarStart;

    //Final Date
    private EditText displayFinalDateField;
    private LinearLayout clickableEndDate;
    private Calendar myCalendarEnd;


    //Maintenance Calories
    private EditText popupEnterCals;
    private TextView userMaintenanceCalories;
    private LinearLayout clickableMaintenanceCalories;
    private PopupWindow popupWindowMaintenanceCalories;
    private TextView popupAskMaintenanceCalories;

    //Calculate Button
    private Button calculateButton;

    private java.text.DateFormat dateFormat;

    //Daily Deficit
    private TextView calcCalDeficit;

    //Daily Calories
    private TextView calcDailyCalories;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mPreferences = MainActivity.mPreferences;

        //Hides the title of the app
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();


        //Create a View object of the fragment for use in the app

        root = inflater.inflate(R.layout.fragment_goals, container, false);


        //establish objects fpr sharedpreferences queries
        mPreferences = this.getActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
        preferencesEditor = mPreferences.edit();


        //**********************************************************************************
        //Current Weight
        //**********************************************************************************

        //Current weight View with functionality
        //When the row is clicked, a popup view will appear prompting the user to enter their weight.
        //When entered, this closes the popup, updates the saved data and forces a refresh in the main layout
        userCurrentWeight = (TextView) root.findViewById(R.id.enterCurrentWeight);

        //set weight text to that held in the preferences
        userCurrentWeight.setText(String.valueOf( mPreferences.getInt("CURRENT_WEIGHT",0)) +  mPreferences.getString("CURRENT_WEIGHT_UNIT",""));


        //Set the currentweight row to be clickable and when clicked opens a window to enter an input
        clickableCurrentWeight = (LinearLayout) root.findViewById(R.id.currentWeightLayout);
        clickableCurrentWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View popupViewWeight = LayoutInflater.from(getActivity()).inflate(R.layout.popup_enter_weight, null);
                popupWindowWeight = new PopupWindow(popupViewWeight, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);

                //set weight text in popup
                popupEnterWeight = (EditText) popupViewWeight.findViewById(R.id.popupEnterWeight);
                popupEnterWeight.setText(String.valueOf( mPreferences.getInt("CURRENT_WEIGHT",0)) );

                //set weight unit
                popupUnit = (TextView) popupViewWeight.findViewById(R.id.popup_unit);
                popupUnit.setText(String.valueOf( mPreferences.getString("CURRENT_WEIGHT_UNIT","")));

                //open popup
                popupWindowWeight.setAnimationStyle(R.style.Animation_Design_BottomSheetDialog);
                popupWindowWeight.showAtLocation(popupViewWeight, Gravity.CENTER, 0, 0);
                dimBehind(popupWindowWeight);

                //Clear text from enter text field if clicked
                popupEnterWeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {

                        popupEnterWeight.setText("");


                        popupEnterWeight.setOnKeyListener(new View.OnKeyListener() {
                            @Override
                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                if (keyCode == KeyEvent.KEYCODE_ENTER) {


                                    InputMethodManager manager = (InputMethodManager) getContext()
                                            .getSystemService(INPUT_METHOD_SERVICE);
                                    if (manager != null)
                                        manager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                                    return true; //this is required to stop sending key event to parent


                                }
                                return false;
                            }
                        });


                    }
                });

                //okay button to finalise info and close
                Button buttonOkayEnterWeight = (Button) popupViewWeight.findViewById(R.id.buttonOkayEnterWeight);

                buttonOkayEnterWeight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(popupEnterWeight.getText().toString().matches("")){

                        }else
                        {
                            preferencesEditor.putInt("CURRENT_WEIGHT", Integer.parseInt(popupEnterWeight.getText().toString()));
                            preferencesEditor.apply();
                            userCurrentWeight.setText(String.valueOf( mPreferences.getInt("CURRENT_WEIGHT",0)) +  mPreferences.getString("CURRENT_WEIGHT_UNIT",""));
                            popupWindowWeight.dismiss();
                        }


                    }
                });

            }


        });





        //**********************************************************************************
        //**********************************************************************************











        //**********************************************************************************
        //Current Body Fat
        //**********************************************************************************

//        private EditText popupEnterBF;
//        private TextView userBF;
//        private LinearLayout clickableBF;
//        private PopupWindow popupWindowBF;
//        private TextView popupAskBF;z

        //Current weight View with functionality
        //When the row is clicked, a popup view will appear prompting the user to enter their weight.
        //When entered, this closes the popup, updates the saved data and forces a refresh in the main layout
        userBF = (TextView) root.findViewById(R.id.enterCurrentBodyfat);

        //set weight text to that held in the preferences
        userBF.setText(String.valueOf( mPreferences.getInt("CURRENT_BODYFAT",0)) + "%" );


        //Set the currentweight row to be clickable and when clicked opens a window to enter an input
        clickableBF = (LinearLayout) root.findViewById(R.id.currentBFLayout);
        clickableBF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View popupViewBF = LayoutInflater.from(getActivity()).inflate(R.layout.popup_enter_bodyfat, null);
                popupWindowBF = new PopupWindow(popupViewBF, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);

                //set weight text in popup
                popupEnterBF = (EditText) popupViewBF.findViewById(R.id.popupEnterBF);
                popupEnterBF.setText(String.valueOf( mPreferences.getInt("CURRENT_BODYFAT",0)) );

                //open popup
                popupWindowBF.setAnimationStyle(R.style.Animation_Design_BottomSheetDialog);
                popupWindowBF.showAtLocation(popupViewBF, Gravity.CENTER, 0, 0);
                dimBehind(popupWindowBF);

                //Clear text from enter text field if clicked
                popupEnterBF.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {

                        popupEnterBF.setText("");


                        popupEnterBF.setOnKeyListener(new View.OnKeyListener() {
                            @Override
                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                if (keyCode == KeyEvent.KEYCODE_ENTER) {


                                    InputMethodManager manager = (InputMethodManager) getContext()
                                            .getSystemService(INPUT_METHOD_SERVICE);
                                    if (manager != null)
                                        manager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                                    return true; //this is required to stop sending key event to parent


                                }
                                return false;
                            }
                        });


                    }
                });

                //okay button to finalise info and close
                Button buttonOkayEnterWeight = (Button) popupViewBF.findViewById(R.id.buttonOkayEnterBF);

                buttonOkayEnterWeight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(popupEnterBF.getText().toString().matches("")){

                        }else
                        {
                            preferencesEditor.putInt("CURRENT_BODYFAT", Integer.parseInt(popupEnterBF.getText().toString()));
                            preferencesEditor.apply();
                            userBF.setText(String.valueOf( mPreferences.getInt("CURRENT_BODYFAT",0)) + "%" );
                            popupWindowBF.dismiss();
                        }


                    }
                });

            }


        });





        //**********************************************************************************
        //**********************************************************************************














        //**********************************************************************************
        //Switch Unit Button
        //**********************************************************************************

        //Button toggle to change target type change units
        targetTypeSelector = (ToggleButton) root.findViewById(R.id.chkState);
        userAskGoalType = (TextView) root.findViewById(R.id.askTargetMeasurement);


        //TODO change this set text to use strings.xml rather than a straight value. Also change it to Goal Weight and Goal Body fat %
        sTargetType = ((targetTypeSelector.isChecked() ) ? getResources().getString(R.string.ask_weight) :  getResources().getString(R.string.ask_switch_bf)  );

        userAskGoalType.setText(sTargetType);

        targetTypeSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sTargetType = ((targetTypeSelector.isChecked() ) ? getResources().getString(R.string.ask_weight) :  getResources().getString(R.string.ask_switch_bf)  );
                userAskGoalType.setText(sTargetType);
                enterTargetAmount = (TextView) root.findViewById(R.id.enterTargetAmount);
                //Sets the unit of the target amount
                enterTargetAmount.setText( ((targetTypeSelector.isChecked() ) ? String.valueOf( mPreferences.getString("CURRENT_WEIGHT_UNIT","")) :  getResources().getString(R.string.unit_type_percentage)  ));
            }
        });




        //**********************************************************************************
        //**********************************************************************************



        //**********************************************************************************
        //Start Date
        //**********************************************************************************

        //Current start date View with functionality
        //When the row is clicked, a popup view will appear prompting the user to enter the start date
        displayStartDateField = (EditText) root.findViewById(R.id.enterStartDate);

        dateFormat = android.text.format.DateFormat.getDateFormat(getActivity());


        displayStartDateField.setText(dateFormat.format(new Date(mPreferences.getLong("START_DATE", 0))));

        //Set the currentBFLayout row to be clickable and when clicked opens a window to enter an input
        clickableStartDate = (LinearLayout) root.findViewById(R.id.startDate);

        myCalendarStart = Calendar.getInstance();



        displayStartDateField.setOnClickListener(new View.OnClickListener() {

            //when the date row is clicked, open a method dwith a date listener
            @Override
            public void onClick(View v) {
                // displays a calendar on the context using the mycalendar variables. The date field is the variable assigned when a user picks the date
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), date, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH));
//                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000); //USer might want a date in the past so dont do this
                datePickerDialog.show();



            }



            //the below listener indicates when a user has selected a date using the calendar that was viewable aboee
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                   //sets the values of the calendar object which the update label method then calls to use for updating the text field
                    myCalendarStart.set(Calendar.YEAR, year);
                    myCalendarStart.set(Calendar.MONTH, monthOfYear);
                    myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabelWithDate(displayStartDateField, myCalendarStart, "START_DATE");
                }

            };

        });








        //**********************************************************************************
        //End Date
        //**********************************************************************************


        //end date View with functionality
        //When the row is clicked, a popup view will appear prompting the user to enter the start date
        displayFinalDateField = (EditText) root.findViewById(R.id.enterTargetDate);

        displayFinalDateField.setText(dateFormat.format(new Date(mPreferences.getLong("END_DATE", 0))));

        //Set the currentBFLayout row to be clickable and when clicked opens a window to enter an input
        clickableEndDate = (LinearLayout) root.findViewById(R.id.targetDate);

        myCalendarEnd = Calendar.getInstance();

        displayFinalDateField.setOnClickListener(new View.OnClickListener() {

            //when the date row is clicked, open a method dwith a date listener
            @Override
            public void onClick(View v) {
                // displays a calendar on the context using the mycalendar variables. The date field is the variable assigned when a user picks the date
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), date, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH));


                datePickerDialog.getDatePicker().setMinDate(mPreferences.getLong("START_DATE", 0)+86400001);
                datePickerDialog.show();
            }

            //the below listener indicates when a user has selected a date using the calendar that was viewable aboee
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    //sets the values of the calendar object which the update label method then calls to use for updating the text field
                    myCalendarEnd.set(Calendar.YEAR, year);
                    myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                    myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    updateLabelWithDate(displayFinalDateField, myCalendarEnd, "END_DATE");


                }

            };

        });


        //**********************************************************************************
        //**********************************************************************************






        //**********************************************************************************
        //Goal Value and popup
        //**********************************************************************************

        enterTargetAmount = (TextView) root.findViewById(R.id.enterTargetAmount);

        enterTargetAmount.setText(String.valueOf( mPreferences.getInt("GOAL_BODYFAT",0)) +  ((targetTypeSelector.isChecked() ) ? String.valueOf( mPreferences.getString("CURRENT_WEIGHT_UNIT","")) :  getResources().getString(R.string.unit_type_percentage)  ));


        //Set the currentweight row to be clickable and when clicked opens a window to enter an input
        clickableMeasurementLayout = (LinearLayout) root.findViewById(R.id.targetMeasurementLayout);
        clickableMeasurementLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View popupViewGoals = LayoutInflater.from(getActivity()).inflate(R.layout.popup_enter_goal_value, null);
                popupWindowGoals = new PopupWindow(popupViewGoals, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);

                popupAskGoalType = (TextView) popupViewGoals.findViewById(R.id.popupAskGoalType);
                popupAskGoalType.setText((targetTypeSelector.isChecked() ) ? getResources().getString(R.string.ask_popup_goal_weight) :  getResources().getString(R.string.ask_popup_goal_bf)  );

                //set weight text in popup

                popupEnterGoalValue = (EditText) popupViewGoals.findViewById(R.id.popupEnterGoalValue)  ;

                if ( enterTargetAmount.getText().toString().matches("lbs") || enterTargetAmount.getText().toString().matches("kg") || enterTargetAmount.getText().toString().matches("%") ){
                    popupEnterGoalValue.setText("");
                } else {

                    if (targetTypeSelector.isChecked()){
                        popupEnterGoalValue.setText(String.valueOf( mPreferences.getInt("GOAL_BODYWEIGHT",0)) );
                    }else {
                        popupEnterGoalValue.setText(String.valueOf(mPreferences.getInt("GOAL_BODYFAT", 0)));
                    }
                };


                popupGoalValueUnit = (TextView) popupViewGoals.findViewById(R.id.popupGoalValueUnit);
                popupGoalValueUnit.setText((targetTypeSelector.isChecked() ) ? String.valueOf( mPreferences.getString("CURRENT_WEIGHT_UNIT","")) :  getResources().getString(R.string.unit_type_percentage)  );



                //open popup
                popupWindowGoals.setAnimationStyle(R.style.Animation_Design_BottomSheetDialog);
                popupWindowGoals.showAtLocation(popupViewGoals, Gravity.CENTER, 0, 0);
                dimBehind(popupWindowGoals);

                //Clear text from enter text field if clicked
                popupEnterGoalValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {

                        popupEnterGoalValue.setText("");


                        popupEnterGoalValue.setOnKeyListener(new View.OnKeyListener() {
                            @Override
                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                if (keyCode == KeyEvent.KEYCODE_ENTER) {


                                    InputMethodManager manager = (InputMethodManager) getContext()
                                            .getSystemService(INPUT_METHOD_SERVICE);
                                    if (manager != null)
                                        manager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                                    return true; //this is required to stop sending key event to parent


                                }
                                return false;
                            }
                        });


                    }
                });

                //okay button to finalise info and close
                Button buttonOkayEnterValueGoals = (Button) popupViewGoals.findViewById(R.id.buttonGoalsOkayEnterWeight);

                buttonOkayEnterValueGoals.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        if(popupEnterGoalValue.getText().toString().matches("")){

                        }else
                        {

                            if (targetTypeSelector.isChecked()){

                                preferencesEditor.putInt("GOAL_BODYWEIGHT", Integer.parseInt(popupEnterGoalValue.getText().toString()));
                                preferencesEditor.apply();
                                enterTargetAmount.setText(String.valueOf( mPreferences.getInt("GOAL_BODYWEIGHT",0)) +  ((targetTypeSelector.isChecked() ) ? String.valueOf( mPreferences.getString("CURRENT_WEIGHT_UNIT","")) :  getResources().getString(R.string.unit_type_percentage)  ));

                            }else {

                                preferencesEditor.putInt("GOAL_BODYFAT", Integer.parseInt(popupEnterGoalValue.getText().toString()));
                                preferencesEditor.apply();
                                enterTargetAmount.setText(String.valueOf( mPreferences.getInt("GOAL_BODYFAT",0)) +  ((targetTypeSelector.isChecked() ) ? String.valueOf( mPreferences.getString("CURRENT_WEIGHT_UNIT","")) :  getResources().getString(R.string.unit_type_percentage)  ));
                            }

                            preferencesEditor.apply();


                            popupWindowGoals.dismiss();
                        }


                    }
                });

            }


        });



        //**********************************************************************************
        //**********************************************************************************







        //**********************************************************************************
        //Calculate Button
        //**********************************************************************************
        //TODO set an onclickable that updates the SharedPreferencnes of all values required below and refreshes the values shown
        //putint the calculate method from an instance of UserReference.

        calculateButton = (Button) root.findViewById(R.id.calculateButton);

        userMaintenanceCalories = (TextView) root.findViewById(R.id.userMaintenanceCalories);
        calcCalDeficit = (TextView) root.findViewById(R.id.calcCalDeficit);
        calcDailyCalories = (TextView) root.findViewById(R.id.calcDailyCalories);

        calculateButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Update SharedPreferences
                preferencesEditor.putInt("MAINTENANCE_CALORIES", UserReference.calculateMaintenanceCalories());
                preferencesEditor.putInt("TARGET_DAILY_DEFICIT", UserReference.calculateTargetCalorieDeficit(targetTypeSelector.isChecked()));
                preferencesEditor.putInt("TARGET_DAILY_CALORIES", UserReference.calculateMaintenanceCalories() - UserReference.calculateTargetCalorieDeficit(targetTypeSelector.isChecked()));


                preferencesEditor.apply();

                //Refresh Fields
                userMaintenanceCalories.setText(mPreferences.getInt("MAINTENANCE_CALORIES",0) + "kcal");
                calcCalDeficit.setText(mPreferences.getInt("TARGET_DAILY_DEFICIT",0) + "kcal");
                calcDailyCalories.setText(mPreferences.getInt("TARGET_DAILY_CALORIES",0) + "kcal");
            }
        });
        //**********************************************************************************
        //**********************************************************************************






        //**********************************************************************************
        //Maintenance Calories
        //**********************************************************************************

//        private EditText popupEnterCals;
//        private TextView userMaintenanceCalories;
//        private LinearLayout clickableMaintenanceCalories;
//        private PopupWindow popupWindowMaintenanceCalories;


        //TODO think about whether the maintenance calories should be editable. Might be best to leave this until last.
        //  if implementing this. Have a warning popup box first saying 'are you sure you want to overwrite'
        userMaintenanceCalories = (TextView) root.findViewById(R.id.userMaintenanceCalories);
        userMaintenanceCalories.setText(String.valueOf(mPreferences.getInt("MAINTENANCE_CALORIES",0)) + "kcal");
        clickableMaintenanceCalories = (LinearLayout) root.findViewById(R.id.maintenanceCalories);
        clickableMaintenanceCalories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View popupViewMaintenanceCals = LayoutInflater.from(getActivity()).inflate(R.layout.popup_maintenance_calories, null);
                popupWindowMaintenanceCalories = new PopupWindow(popupViewMaintenanceCals, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);

                //set weight text in popup
                popupEnterCals = (EditText) popupViewMaintenanceCals.findViewById(R.id.popupEnterMaintenanceCalories);
                popupEnterCals.setText(String.valueOf( mPreferences.getInt("MAINTENANCE_CALORIES",0)) );
                popupAskMaintenanceCalories = (TextView) popupViewMaintenanceCals.findViewById((R.id.popupAskMaintenanceCalories));
                popupAskMaintenanceCalories.setText(R.string.ask_maintenance_cals);


                //open popup
                popupWindowMaintenanceCalories.setAnimationStyle(R.style.Animation_Design_BottomSheetDialog);
                popupWindowMaintenanceCalories.showAtLocation(popupViewMaintenanceCals, Gravity.CENTER, 0, 0);
                dimBehind(popupWindowMaintenanceCalories);

                //Clear text from enter text field if clicked
                popupEnterCals.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {

                        popupEnterCals.setText("");


                        popupEnterCals.setOnKeyListener(new View.OnKeyListener() {
                            @Override
                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                if (keyCode == KeyEvent.KEYCODE_ENTER) {


                                    InputMethodManager manager = (InputMethodManager) getContext()
                                            .getSystemService(INPUT_METHOD_SERVICE);
                                    if (manager != null)
                                        manager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                                    return true; //this is required to stop sending key event to parent


                                }
                                return false;
                            }
                        });


                    }
                });

                //okay button to finalise info and close
                Button buttonOkayEnterCals = (Button) popupViewMaintenanceCals.findViewById(R.id.buttonOkayEnterCals);

                buttonOkayEnterCals.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(popupEnterCals.getText().toString().matches("")){

                        }else
                        {
                            preferencesEditor.putInt("MAINTENANCE_CALORIES", Integer.parseInt(popupEnterCals.getText().toString()));
                            preferencesEditor.apply();
                            userMaintenanceCalories.setText(mPreferences.getInt("MAINTENANCE_CALORIES",0) + "kcal");
                            popupWindowMaintenanceCalories.dismiss();


                        }


                    }
                });

            }


        });





        //**********************************************************************************
        //**********************************************************************************





        return root;


    }


    private void updateLabelWithDate(EditText editTextField, Calendar calendar, String preferencesDate) {

        //TODO check time formatting and if it can be translated to the users accepted format
        //TODO check if the calendar view popup can skip years
        String myFormat = "MM/dd/yyyy"; //In which you need put here

        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());

        String myString = df.format(calendar.getTime());

        preferencesEditor.putLong(preferencesDate,  calendar.getTimeInMillis());
        preferencesEditor.apply();

        editTextField.setText(dateFormat.format(new Date(mPreferences.getLong(preferencesDate, 0))));





    }


    public static void dimBehind(PopupWindow popupWindow) {
        View container = popupWindow.getContentView().getRootView();
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.6f;
        wm.updateViewLayout(container, p);
    }


}

