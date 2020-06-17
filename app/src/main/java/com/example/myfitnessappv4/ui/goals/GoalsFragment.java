package com.example.myfitnessappv4.ui.goals;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProviders;

import com.example.myfitnessappv4.MainActivity;
import com.example.myfitnessappv4.R;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class GoalsFragment extends Fragment {


    //Universal
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.myfitnessappv4.sharedprefsfile";
    private SharedPreferences.Editor preferencesEditor;
    private View root;

    //Current weight
    private TextView popupUnit;
    private EditText popupEnterWeight;
    private TextView userCurrentWeight;
    private LinearLayout clickableCurrentWeight;
    private PopupWindow popupWindow;

    //Current BF
    private EditText popupEnterBodyfat;
    private TextView userCurrentBodyfat;
    private LinearLayout clickableCurrentBodyfat;


    //Start Date
    private EditText displayStartDateField;
    private LinearLayout clickableStartDate;
    private Calendar myCalendarStart;

    //Final Date
    private EditText displayFinalDateField;
    private LinearLayout clickableEndDate;
    private Calendar myCalendarEnd;

    private java.text.DateFormat dateFormat;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

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
        userCurrentWeight.setText(String.valueOf( mPreferences.getInt("CURRENT_WEIGHT_KEY",0)) +  mPreferences.getString("CURRENT_WEIGHT_UNIT",""));




        //Set the currentweight row to be clickable and when clicked opens a window to enter an input
        clickableCurrentWeight = (LinearLayout) root.findViewById(R.id.currentWeightLayout);
        clickableCurrentWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_enter_weight, null);
                popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);

                //set weight text in popup
                popupEnterWeight = (EditText) popupView.findViewById(R.id.popupEnterWeight);
                popupEnterWeight.setText(String.valueOf( mPreferences.getInt("CURRENT_WEIGHT_KEY",0)) );

                //set weight unit
                popupUnit = (TextView) popupView.findViewById(R.id.popup_unit);
                popupUnit.setText(String.valueOf( mPreferences.getString("CURRENT_WEIGHT_UNIT","")));

                //open popup
                popupWindow.setAnimationStyle(R.style.Animation_Design_BottomSheetDialog);
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                dimBehind(popupWindow);

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
                Button buttonOkayEnterWeight = (Button) popupView.findViewById(R.id.buttonOkayEnterWeight);

                buttonOkayEnterWeight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(popupEnterWeight.getText().toString().matches("")){

                        }else
                        {
                            preferencesEditor.putInt("CURRENT_WEIGHT_KEY", Integer.parseInt(popupEnterWeight.getText().toString()));
                            preferencesEditor.apply();
                            preferencesEditor.commit();
                            userCurrentWeight.setText(String.valueOf( mPreferences.getInt("CURRENT_WEIGHT_KEY",0)) +  mPreferences.getString("CURRENT_WEIGHT_UNIT",""));
                            popupWindow.dismiss();
                        }


                    }
                });

            }


        });


        //**********************************************************************************
        //Current Bodyfat
        //**********************************************************************************

        //Current bodyfat View with functionality
        //When the row is clicked, a popup view will appear prompting the user to enter their bodyfat.
        //When entered, this closes the popup, updates the saved data and forces a refresh in the main layout
        userCurrentBodyfat = (TextView) root.findViewById(R.id.enterCurrentBodyfat);

        //set bodyfat text to that held in the preferences
        userCurrentBodyfat.setText(String.valueOf( mPreferences.getInt("CURRENT_BODYFAT",0)) +  "%");


        //Set the currentBFLayout row to be clickable and when clicked opens a window to enter an input
        clickableCurrentBodyfat = (LinearLayout) root.findViewById(R.id.currentBFLayout);





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
        //**********************************************************************************




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


                datePickerDialog.getDatePicker().setMinDate(mPreferences.getLong("START_DATE", 0));
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
        preferencesEditor.commit();

        editTextField.setText(dateFormat.format(new Date(mPreferences.getLong(preferencesDate, 0))));



//        Log.d("MYTAG",myString)


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

